package unlam.cal;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.util.Log;
import android.content.Context;

public class Calendario {

	private ArrayList<Cluster> queue = new ArrayList<Cluster>();
	private Document doc;
	private String URL = "http://www.unlam.edu.ar/index.php?seccion=3&idArticulo=449";

	public ArrayList<Cluster> getCalendar() 	{ return queue; }
	public void setCalendar(ArrayList<Cluster> a) 	{ queue = a; }
	public boolean isCalendarLoaded() 		{ return queue.size() > 0; }

	public boolean download() {
		try {
			doc = Jsoup.connect(URL).get();
			Elements tables = doc.getElementsByClass("full");
			for (Element table: tables ) {
				Cluster cluster = new Cluster();
				try {
					Elements head = table.select("thead tr th");
					String title = head.first().text();
  					cluster.setTitle(title);

				}
				catch (Exception e) {
  					Log.w("UNLaM",e.toString());
				}
				try {
					
					Elements body = table.select("tbody tr");
					body.remove(0); // The first row is just the title.-
					for (Element row : body ) {
						try { 
							Element name = row.getElementsByTag("th").first();
							Element date = row.getElementsByTag("td").first();
  							cluster.addEvent(name.text(),date.text()); 
						} 
						catch (Exception e) {
  							Log.w("UNLaM",e);
						}
					}
				}
				catch (Exception e) {
  					Log.w("UNLaM",e.toString());
				}
				queue.add(cluster);

			}
			return true;

		} catch (Exception e) {
  			Log.w("UNLaM","CALENDAR: "+e.toString());
			return false;
		}

	} 
	
} 
