package controller;

import java.io.IOException;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import object.*;

@WebServlet(name = "MemberController", urlPatterns = "/member/*")
public class MemberController extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private MemberDAO _dao;
	
    public MemberController() 
    {
        super();
    }
    
    public void init() throws ServletException
    {
    	_dao = new MemberDAO();
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
			case "members.do":
				loadMembers(request);
				moveToPage(request, response, "/test01/members.jsp");
				break;
			case "insert.do":
				insertMember(request);
				moveToPage(request, response, "/member/members.do");
				break;
			case "register.do":
				moveToPage(request, response, "/test01/register.jsp");
				break;
			case "delete.do":
				deleteMember(request);
				moveToPage(request, response, "/member/members.do");
				break;
			case "modify.do":
				loadMember(request);
				moveToPage(request, response, "/test01/modify.jsp");
				break;
			case "update.do":
				updateMember(request);
				moveToPage(request, response, "/member/members.do");
				break;
			default:
				break;
		}		
	}

	private void updateMember(HttpServletRequest request) 
	{
		MemberVO member = new MemberVO();
		
		member.setId(request.getParameter("txtUserID"));
		member.setPassword(request.getParameter("txtUserPW"));
		member.setName(request.getParameter("txtName"));
		member.setEmail(request.getParameter("txtEmail"));

		_dao.updateMember(member);
	}

	private void loadMember(HttpServletRequest request) {
		String userID = request.getParameter("userID");
		
		MemberVO member = _dao.getMember(userID);
		
		request.setAttribute("member", member);
	}

	private void deleteMember(HttpServletRequest request) {
		String userID = request.getParameter("userID");
		
		_dao.deleteMember(userID);
	}

	private void insertMember(HttpServletRequest request) 
	{
		MemberVO member = new MemberVO();
		
		member.setId(request.getParameter("txtUserID"));
		member.setPassword(request.getParameter("txtUserPW"));
		member.setName(request.getParameter("txtName"));
		member.setEmail(request.getParameter("txtEmail"));
		
		_dao.insertMember(member);
	}

	private void loadMembers(HttpServletRequest request) 
	{
		List<MemberVO> list = _dao.getMemberList();
		
		request.setAttribute("list", list);
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