package unlam.gui.adapter;
 
import unlam.gui.R;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.util.Log;
import unlam.news.News;
 
public class NewsAdapter extends BaseAdapter {

    	private Activity activity;
    	private LayoutInflater inflater;
	private List<News> news;
 
    	public NewsAdapter(Activity activity, List<News> news) {
        	this.activity 	= activity;
        	this.news	= news;
    	}
 
    	@Override
    	public int getCount() { return news.size(); }
 
 	@Override
    	public Object getItem(int location) { return news.get(location); }
 
    	@Override
    	public long getItemId(int position) { return position; }
 
    	@Override
    	public View getView(int position, View convertView, ViewGroup parent) {
 
        	if (inflater == null)
            		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	if (convertView == null)
            		convertView = inflater.inflate(R.layout.list_news, null);

			
        	TextView mytext = (TextView) convertView.findViewById(R.id.mytext);
        	TextView mydate = (TextView) convertView.findViewById(R.id.mydate);

		News n = news.get(position);		
        	mytext.setText(n.getText());
        	mydate.setText(n.dateToString());
 
        	return convertView;
    }
 
}
