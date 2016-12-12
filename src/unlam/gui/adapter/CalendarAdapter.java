package unlam.gui.adapter;
 
import unlam.gui.R;
import java.util.List;
import unlam.gui.lis.CalendarListener;
import unlam.gui.MainActivity;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ImageView;
import android.util.Log;
import android.app.Fragment;
import android.app.FragmentManager;
import unlam.gui.tabs.ClusterFragment; 
import android.support.v4.widget.DrawerLayout;
import android.content.Context;
import unlam.cal.Cluster;
import unlam.cal.Calendario;
import android.content.Context;
 
public class CalendarAdapter extends BaseAdapter {

    	private MainActivity   activity;
    	private LayoutInflater inflater;
	private List<Cluster>  clusters;
 
    	public CalendarAdapter(MainActivity activity, Calendario cal) {
        	this.activity = activity;
        	this.clusters = cal.getCalendar();
    	}
	
    	@Override
    	public int getCount() { return clusters.size(); }
 
 	@Override
    	public Object getItem(int location) { return clusters.get(location); }
 
    	@Override
    	public long getItemId(int position) { return position; }
 
    	@Override
    	public View getView(int position, View convertView, ViewGroup parent) {

		// Create a list if it does not already exist.-
        	if (inflater == null)
            		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	if (convertView == null)
            		convertView = inflater.inflate(R.layout.list_calendar, null);

		// Set the contents of the list once per cluster.-
        	ImageView thumbNail 	= (ImageView) convertView.findViewById(R.id.thumbnail);
        	TextView title 		= (TextView) convertView.findViewById(R.id.title);
        	Cluster m 		= clusters.get(position);

		// Set arrow listener to switch to another fragment.-
		CalendarListener cl = new CalendarListener();
		cl.setIndex(m.getID());
		cl.setActivity(activity);
		convertView.setOnClickListener(cl);

		// Return element.-
        	title.setText(m.getTitle());
        	return convertView;
    	}

}
