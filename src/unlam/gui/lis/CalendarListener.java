package unlam.gui.lis;
 
import unlam.gui.R;
import unlam.gui.MainActivity;
import android.app.Activity;
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
import android.content.Intent;
import unlam.db.CalendarDB;
import unlam.cal.Cluster;
 
public class CalendarListener implements View.OnClickListener {
	
	private int index;
	private MainActivity activity;

    	public void CalendarListener() {
    	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setActivity(MainActivity m) {
		this.activity = m;
	}
	
    	@Override
    	public void onClick(View v) {
		ClusterFragment fragment = new ClusterFragment();
		fragment.setClusterID(index);
		FragmentManager fragmentManager = activity.getFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).addToBackStack("tag").commit();
    	}	
}
