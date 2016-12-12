package unlam.news;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

public class News {
	
	static DateFormat dateformat    = new SimpleDateFormat("dd/MM/yyyy");
	private String text;
	private long date;

	public void setDateToNow()	{ setDate(new Date()); }

	public void setText(String s) 	{ text = s; }
	public String getText()		{ return text; } 

	public void setLong(long i)	{ date = i; }
	public long getLong()		{ return date; }

	public String dateToString()	{ return dateformat.format(getDate()); }

	public void setDate(Date d)	{ date = d.getTime()/1000; }
	public Date getDate() 		{ 
						Date d = new Date();
						d.setTime(date*1000);
						return d;
					}

}
