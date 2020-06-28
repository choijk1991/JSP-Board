package controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.*;
import org.apache.commons.fileupload.servlet.*;

import object.*;
import service.*;

@WebServlet(name = "BoardController", urlPatterns = "/board/*")
public class BoardController extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private BoardService _service;
       
    public BoardController() 
    {
        super();
    }
    
    private String getImageRepositoryPath() 
    {
    	return "D:\\Workspace\\Java\\pro17\\WebContent\\images";
    }
    
    public void init() throws ServletException
    {
    	_service = new BoardService();
    }

    private String getDirection(String action) 
    {
    	if (action == null) return "";
    	
    	String[] elements = action.split("/");
    	
		return elements[elements.length-1];
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String direction = getDirection(request.getPathInfo());
		
		switch(direction) 
		{
			case "list.do":
				loadArticles(request);
				moveToPage(request, response, "/test02/list.jsp");
				break;
			case "write.do":
				moveToPage(request, response, "/test02/write.jsp");
				break;
			case "insert.do":
				int result = insertArticle(request);
				response.sendRedirect(request.getContextPath()+"/board/article.do?no="+result);
				break;
			case "download.do":
				downloadFile(request, response);
				break;
			case "article.do":
				loadArticle(request);
				moveToPage(request, response, "/test02/article.jsp");
				break;
			case "modify.do":
				loadArticle(request);
				moveToPage(request, response, "/test02/modify.jsp");
				break;
			case "update.do":
				int no = updateArticle(request);
				response.sendRedirect(request.getContextPath()+"/board/article.do?no="+no);
				break;
			case "delete.do":
				deleteArticle(request);
				response.sendRedirect(request.getContextPath()+"/board/list.do");
				break;
			case "reply.do":
				loadArticle(request);
				moveToPage(request, response, "/test02/reply.jsp");
				break;
			default:
				break;
		}
	}
	
	private void deleteArticle(HttpServletRequest request) 
	{
		int no = Integer.parseInt(request.getParameter("no"));
		int[] relatedNos = _service.deleteArticle(no);
		
		for(int element : relatedNos) {
			deleteFilePath(element);
		}		
	}

	private void deleteFilePath(int element) 
	{
		String destination = getImageRepositoryPath()+"\\"+element;
		File destDir = new File(destination);

		if (destDir.exists()) 
		{
			File[] files = destDir.listFiles();
			
			for(File file : files)
			{
				file.delete();
			}
		}
		
		destDir.delete();
	}

	private int updateArticle(HttpServletRequest request) {
		try {
			List<FileItem> items = getFileItem(request);
			Map<String, String> contents = getContents(items);
			ArticleVO article = new ArticleVO();
			
			article.setNumber(Integer.parseInt(request.getParameter("no")));
			article.setContent(contents.get("txtContent"));
			article.setTitle(contents.get("txtTitle"));
			article.setImagePath(contents.get("fileImage"));
			
			_service.updateArticle(article);
			
			uploadImage(article, items);
			
			return article.getNumber();
		}catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}
	
	private Map<String, String> getContents(List<FileItem> items) throws UnsupportedEncodingException
	{
		Map<String, String> result = new HashMap<String, String>();
		
		for(FileItem item : items) 
		{
			if (item.isFormField()) {
				result.put(item.getFieldName(), item.getString("UTF-8"));
			} else {
				int index = getNameIndex(item.getName());
				
				result.put(item.getFieldName(), item.getName().substring(index + 1));
			}
		}
		
		return result;
	}
	
	private List<FileItem> getFileItem(HttpServletRequest request)
	{
		List<FileItem> result = new ArrayList<FileItem>();
		ServletFileUpload upload = getUpload();
		
		try 
		{
			result = upload.parseRequest(request);
		} catch(Exception exception) {
			exception.printStackTrace();
		}
		
		return result;
	}

	private void uploadImage(ArticleVO article, List<FileItem> items) throws Exception 
	{
		if (article.getImagePath() == null) return;
		if (article.getImagePath() == "") return;
		
		for(FileItem item : items) 
		{			
			if (item.isFormField()) continue;
			
			int index = getNameIndex(item.getName());
			String fileName = item.getName().substring(index + 1);
			String destination = getImageRepositoryPath()+"\\"+article.getNumber();
			
			deleteFilePath(article.getNumber());
			
			File destDir = new File(destination);

			destDir.mkdirs();
			
			File imageFile = new File(destination+"\\"+fileName);
			
			item.write(imageFile);
		}
	}

	private void loadArticle(HttpServletRequest request) 
	{
		String articleNo = request.getParameter("no");
		int no = Integer.parseInt(articleNo);
		ArticleVO article = _service.getArticle(no);
		
		request.setAttribute("article", article);
	}

	private void downloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String imageFileName = request.getParameter("fileImage");
		String articleNo = request.getParameter("no");
			
		response.setHeader("Cache-Control", "no-cache");
		response.addHeader("Content-disposition", "attachment;fileName="+imageFileName);

		FileInputStream inStream = getFileInputStream(imageFileName, articleNo);
		OutputStream outStream = response.getOutputStream();

		writeFile(inStream, outStream);
	}
	
	private void writeFile(FileInputStream inStream, OutputStream outStream) throws IOException 
	{
		byte[] buffer = new byte[1024*8];
		
		while(true)
		{
			int length = inStream.read(buffer);
			
			if (length == -1) break;
			
			outStream.write(buffer, 0, length);
		}
		
		inStream.close();
		outStream.close();
	}

	private FileInputStream getFileInputStream(String imageFileName, String articleNo) throws FileNotFoundException 
	{
		String path = getImageRepositoryPath()+"\\"+articleNo+"\\"+imageFileName;
		File file = new File(path);
		
		return new FileInputStream(file);
	}

	private int insertArticle(HttpServletRequest request) throws IOException 
	{
		try {
			List<FileItem> items = getFileItem(request);
			Map<String, String> contents = getContents(items);
			ArticleVO article = new ArticleVO();
			
			article.setParentNo(getParentNo(request));
			article.setWriterID("hong");
			article.setContent(contents.get("txtContent"));
			article.setTitle(contents.get("txtTitle"));
			article.setImagePath(contents.get("fileImage"));
			
			int no = _service.insertArticle(article);
			
			article.setNumber(no);
			
			uploadImage(article, items);
			
			return no;
		}catch (Exception exception) {
			exception.printStackTrace();
		}
		
		return 0;
	}

	private int getParentNo(HttpServletRequest request) {
		String parentNo = request.getParameter("parentNo");
		
		if (parentNo == null) return 0;
		if (parentNo.equals("")) return 0;
		
		return Integer.parseInt(parentNo);
	}

	private int getNameIndex(String name) {
		int index = name.lastIndexOf("\\");
		
		if (index != -1) return index;
		
		return name.lastIndexOf("/");
	}

	private ServletFileUpload getUpload() 
	{
		ServletFileUpload upload = new ServletFileUpload();
		
		upload.setFileItemFactory(getDiskFileItemFactory());
		
		return upload;
	}

	private DiskFileItemFactory getDiskFileItemFactory() 
	{
		DiskFileItemFactory factory = new DiskFileItemFactory();
		
		factory.setRepository(new File(getImageRepositoryPath()));
		factory.setSizeThreshold(1024*1024);
		
		return factory;
	}

	private void loadArticles(HttpServletRequest request) 
	{
		int pageNo = getInteger(request, "pageNo");
		PageInfo info = _service.getCurrentPageInfo(pageNo);
		List<ArticleVO> list = _service.getArticles(info);
		
		request.setAttribute("list", list);
		request.setAttribute("pageInfo", info);
	}

	private int getInteger(HttpServletRequest request, String paramName)
	{
		String parameter = request.getParameter(paramName);
		
		if (parameter == null) return 1;
		
		return Integer.parseInt(parameter);
	}
	
	private void moveToPage(HttpServletRequest request, HttpServletResponse response, String uri) throws ServletException, IOException 
	{
		RequestDispatcher dispatcher = request.getRequestDispatcher(uri);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}
}