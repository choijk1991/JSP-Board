package service;

import java.util.*;
import object.*;

public class BoardService 
{
	private BoardDAO _dao;
	
	public BoardService() 
	{
		_dao = new BoardDAO();
	}
	
	public List<ArticleVO> getArticles(PageInfo info)
	{
		return _dao.getArticles(info);
	}

	public int insertArticle(ArticleVO article) 
	{
		return _dao.insertArticle(article);
	}

	public ArticleVO getArticle(int no) 
	{
		return _dao.getArticle(no);
	}

	public void updateArticle(ArticleVO article) {
		_dao.updateArticle(article);		
	}

	public int[] deleteArticle(int no) {
		return _dao.deleteArticle(no);
	}

	public PageInfo getCurrentPageInfo(int pageNo) {		
		return _dao.getCurrentPageInfo(pageNo);
	}
}