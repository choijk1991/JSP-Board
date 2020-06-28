package object;

public class PageInfo {
	private int pageNo;
	private int articleTotal;
	
	public PageInfo() {
		
	}
	
	public int getSectionFirstPage()
	{
		int section = getSectionNo();
		
		return (section - 1)*10 + 1;
	}
	
	public int getSectionLastPage()
	{
		int section = getSectionNo();
		int last = (section) * 10;
		int totalPage = getPageTotal();
		
		return (last > totalPage) ? totalPage : last;
	}
	
	public int getSectionTotal()
	{
		return getPageTotal() / 10 + (getPageTotal() % 10 == 0 ? 0 : 1);
	}
	
	public int getPageTotal() 
	{
		return (articleTotal / getArticleCount()) 
				+ (articleTotal % getArticleCount() == 0? 0:1);
	}
	
	public boolean checkNextSection() 
	{
		return getSectionNo() < getSectionTotal();
	}
	
	public boolean checkPreviousSection()
	{
		return getSectionNo() > 1;
	}
	
	public int getArticleCount() 
	{
		return 2;
	}

	public int getPageNo() 
	{
		return pageNo;
	}

	public void setPageNo(int pageNo) 
	{
		this.pageNo = pageNo;
	}

	public int getArticleTotal() 
	{
		return articleTotal;
	}

	public void setArticleTotal(int articleTotal) 
	{
		this.articleTotal = articleTotal;
	}

	public int getSectionNo() 
	{
		return (pageNo / 10) + (pageNo%10 == 0 ? 0 : 1);
	}
}