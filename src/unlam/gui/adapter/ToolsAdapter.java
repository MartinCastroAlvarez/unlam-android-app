package unlam.gui.adapter;

import unlam.gui.R;
import unlam.gui.model.ToolsModel;
import java.util.ArrayList;
import android.util.Log;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ToolsAdapter extends BaseAdapter {
	
	private Context context;
	private ArrayList<ToolsModel> navDrawerItems;
	
	public ToolsAdapter(Context context, ArrayList<ToolsModel> navDrawerItems){
		this.context = context;
		this.navDrawerItems = navDrawerItems;
	}

	@Override
	public int getCount() {
		return navDrawerItems.size();
	}

	@Override
	public Object getItem(int position) {		
		return navDrawerItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) { 
		if (convertView == null) {
        		LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        		convertView = mInflater.inflate(R.layout.list_tools, null);
        	}
		ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
		TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
		imgIcon.setImageResource(navDrawerItems.get(position).getIcon());        
		txtTitle.setText(navDrawerItems.get(position).getTitle());
		return convertView;
	}

}
