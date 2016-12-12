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
 
public class CommentsAdapter extends BaseAdapter {

    	private Activity activity;
    	private LayoutInflater inflater;
	private List<String> comments;
	private String s;
 
    	public CommentsAdapter(Activity activity, List<String> comments) {
        	this.activity = activity;
        	this.comments = comments;
    	}
 
    	@Override
    	public int getCount() { return comments.size(); }
 
 	@Override
    	public Object getItem(int location) { return comments.get(location); }
 
    	@Override
    	public long getItemId(int position) { return position; }
 
    	@Override
    	public View getView(int position, View convertView, ViewGroup parent) {
 
        	if (inflater == null)
            		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	if (convertView == null)
            		convertView = inflater.inflate(R.layout.list_comments, null);

			
		s = comments.get(position);		
        	TextView title 	= (TextView) convertView.findViewById(R.id.title);
        	title.setText(s);
 
        	return convertView;
    }
 
}
