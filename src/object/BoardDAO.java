package object;

import java.sql.*;
import java.util.*;

import javax.naming.*;
import javax.sql.*;

public class BoardDAO 
{
	private DataSource _source;
	
	public BoardDAO() 
	{
		try {
			_source = getDataSource();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private DataSource getDataSource() throws NamingException 
	{
		Context ctx = new InitialContext();
		Context env = (Context) ctx.lookup("java:/comp/env");
		
		return (DataSource) env.lookup("jdbc/mysql");
	}

	public List<ArticleVO> getArticles(PageInfo info) 
	{
		List<ArticleVO> list = new ArrayList<ArticleVO>();
		
		try {
			Connection connection = _source.getConnection();
			StringBuilder builder = new StringBuilder();
			
			builder.append("CALL UP_ARTICLE_LIST_Q(?, ?)");
			
			PreparedStatement statement = connection.prepareStatement(builder.toString());
			
			statement.setInt(1, info.getPageNo());
			statement.setInt(2, info.getArticleCount());
			
			ResultSet set = statement.executeQuery();
			
			while (set.next())
			{
				ArticleVO article = new ArticleVO();
				
				article.setNumber(set.getInt("NO"));
				article.setParentNo(set.getInt("PRT_NO"));
				article.setWriterID(set.getString("CRTER_ID"));
				article.setTitle(set.getString("TITLE"));
				article.setCreationDate(set.getDate("CRTE_DT"));
				article.setDepth(set.getInt("DEPTH"));
				
				list.add(article);
			}
			
			set.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	public int insertArticle(ArticleVO article) 
	{
		int result = -1;
		
		try {
			Connection connection = _source.getConnection();
			StringBuilder builder = new StringBuilder();

            builder.append("CALL UP_ARTICLE_I(?, ?, ?, ?, ?)");
            
            PreparedStatement statement = connection.prepareStatement(builder.toString());
            
            statement.setString(1, article.getWriterID());
            statement.setInt(2, article.getParentNo());
            statement.setString(3, article.getTitle());
            statement.setString(4, article.getContent());
            statement.setString(5, article.getImagePath());

            ResultSet set = statement.executeQuery();
            
            set.next();
            
            result = set.getInt("NO");
            
            statement.close();
            connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public ArticleVO getArticle(int no) {
		ArticleVO article = new ArticleVO();
		
		try {
			Connection connection = _source.getConnection();
			StringBuilder builder = new StringBuilder();

            builder.append("CALL UP_ARTICLE_Q(?)");
            
            PreparedStatement statement = connection.prepareStatement(builder.toString());
            
            statement.setInt(1, no);

            ResultSet set = statement.executeQuery();
            
            while (set.next()) 
            {
            	article.setNumber(set.getInt("NO"));
            	article.setTitle(set.getString("TITLE"));
            	article.setContent(set.getString("CONTENT"));
            	article.setImagePath(set.getString("IMG_NM"));
            	article.setCreationDate(set.getDate("CRTE_DTM"));
            	article.setWriterID(set.getString("CRTER_ID"));
            }
            
            statement.close();
            connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return article;
	}

	public void updateArticle(ArticleVO article) {
		try {
			Connection connection = _source.getConnection();
			StringBuilder builder = new StringBuilder();

            builder.append("CALL UP_ARTICLE_U(?, ?, ?, ?)");
            
            PreparedStatement statement = connection.prepareStatement(builder.toString());
            
            statement.setInt(1, article.getNumber());
            statement.setString(2, article.getTitle());
            statement.setString(3, article.getContent());
            statement.setString(4, article.getImagePath());
            
            statement.execute();
            statement.close();
            connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int[] deleteArticle(int no) {
		List<Integer> result = new ArrayList<Integer>();
		
		try {
			Connection connection = _source.getConnection();
			StringBuilder builder = new StringBuilder();

            builder.append("CALL UP_ARTICLE_D(?)");
            
            PreparedStatement statement = connection.prepareStatement(builder.toString());
            
            statement.setInt(1, no);

            ResultSet set = statement.executeQuery();
            
            while (set.next()) 
            {
            	result.add(set.getInt("NO"));
            }
            
            statement.close();
            connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result.stream().mapToInt(element -> element).toArray();
	}

	
	public int getArticleCount() {
		int result = 0;
		
		try {
			Connection connection = _source.getConnection();
			StringBuilder builder = new StringBuilder();

            builder.append("CALL UP_ARTICLE_CNT_Q()");
            
            PreparedStatement statement = connection.prepareStatement(builder.toString());
            
            ResultSet set = statement.executeQuery();            
            set.next();
            
            result = set.getInt("COUNT");
            
            statement.close();
            connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public PageInfo getCurrentPageInfo(int pageNo) {
		PageInfo result = new PageInfo();
		
		result.setPageNo(pageNo);
		
		try {
			Connection connection = _source.getConnection();
			StringBuilder builder = new StringBuilder();

            builder.append("CALL UP_ARTICLE_PAGE_INFO()");
            
            PreparedStatement statement = connection.prepareStatement(builder.toString());
            
            ResultSet set = statement.executeQuery();            
            set.next();
            
            result.setArticleTotal(set.getInt("TOTAL_ART"));
            
            statement.close();
            connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}