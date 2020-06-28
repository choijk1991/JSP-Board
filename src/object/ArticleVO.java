package object;

import java.util.*;

public class ArticleVO 
{
	private int depth;
	private int number;
	private int parentNo;
	private String title;
	private String content;
	private String imagePath;
	private String writerID;
	private Date creationDate;
	
	public Date getCreationDate() 
	{
		return creationDate;
	}
	public void setCreationDate(Date creationDate) 
	{
		this.creationDate = creationDate;
	}
	public String getWriterID() 
	{
		return writerID;
	}
	public void setWriterID(String writerID) 
	{
		this.writerID = writerID;
	}
	public String getImagePath() 
	{
		return imagePath;
	}
	public void setImagePath(String imagePath) 
	{
		this.imagePath = imagePath;
	}
	public String getContent() 
	{
		return content;
	}
	public void setContent(String content) 
	{
		this.content = content;
	}
	public String getTitle() 
	{
		return title;
	}
	public void setTitle(String title) 
	{
		this.title = title;
	}
	public int getParentNo() {
		return parentNo;
	}
	public void setParentNo(int parentNo) {
		this.parentNo = parentNo;
	}
	public int getNumber() 
	{
		return number;
	}
	public void setNumber(int number) 
	{
		this.number = number;
	}
	public int getDepth() 
	{
		return depth;
	}
	public void setDepth(int depth) 
	{
		this.depth = depth;
	}
}