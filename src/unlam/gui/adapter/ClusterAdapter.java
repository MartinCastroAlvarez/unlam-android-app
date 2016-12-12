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
import android.widget.ImageView;
import android.util.Log;
import unlam.cal.Event;
 
public class ClusterAdapter extends BaseAdapter {

    	private Activity activity;
    	private LayoutInflater inflater;
	private List<Event> events;
 
    	public ClusterAdapter(Activity activity, List<Event> events) {
        	this.activity = activity;
        	this.events = events;
    	}
 
    	@Override
    	public int getCount() { return events.size(); }
 
 	@Override
    	public Object getItem(int location) { return events.get(location); }
 
    	@Override
    	public long getItemId(int position) { return position; }
 
    	@Override
    	public View getView(int position, View convertView, ViewGroup parent) {
 
        	if (inflater == null)
            		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	if (convertView == null)
            		convertView = inflater.inflate(R.layout.list_clusters, null);

        	TextView title 	= (TextView) convertView.findViewById(R.id.event_title);
        	TextView genre 	= (TextView) convertView.findViewById(R.id.genre);
 
        	// getting movie data for the row
        	Event m = events.get(position);
        	title.setText(m.getTitle());
		if (m.hasSubtitle()) 
        		genre.setText(m.getSubtitle());
		else
        		genre.setText(m.dateToString());
 
        	return convertView;
    }
 
}
