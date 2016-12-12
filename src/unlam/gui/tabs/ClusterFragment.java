package unlam.gui.tabs;

import unlam.gui.R;
import java.util.ArrayList;  
import java.util.Arrays; 
import android.app.Activity;  
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;  
import android.widget.ListView;  
import unlam.cal.Calendario;
import unlam.cal.Cluster;
import unlam.cal.Event;
import unlam.db.CalendarDB;
import unlam.gui.adapter.ClusterAdapter;
import unlam.gui.adapter.CommentsAdapter;
import android.util.Log;
import android.widget.TextView;
import android.app.Fragment;
import android.app.FragmentManager;
import unlam.gui.tabs.HomeFragment;
import android.widget.Toast;

public class ClusterFragment extends Fragment {
	
	public ClusterFragment(){}
	private ListView listView; 
	private ListView commView; 
	private TextView titleView;
	private TextView subTitleView;
	private ClusterAdapter e_adapter; 
	private CalendarDB cdb;
	private int clusterID;
	private Cluster cluster;
	private CommentsAdapter c_adapter;
	
	public void setClusterID(int cid) {
		this.clusterID = cid;
	}
	
	@Override
    	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_cluster, container, false);
		try {

			cdb 		= new CalendarDB(getActivity(),null,null,1);
			cluster 	= cdb.getCluster(clusterID);
			if (android.os.Build.VERSION.SDK_INT >= 11)
				getActivity().getActionBar().setTitle(cluster.getTitle());

			commView 	= (ListView) rootView.findViewById(R.id.comments);
			c_adapter 	= new CommentsAdapter(getActivity(), cluster.getComments() );	
			commView.setAdapter(c_adapter);

			listView 	= (ListView) rootView.findViewById(R.id.list2);
			e_adapter 	= new ClusterAdapter(getActivity(), cluster.getEvents() );	
			listView.setAdapter(e_adapter);
			
		}
		catch (Exception e) {
			Log.e("UNLaM","Error at ClusterFragment: "+e.toString());
			Toast.makeText(getActivity(),
                                "Vuelva a intentar!",
                                Toast.LENGTH_SHORT).show();
                        HomeFragment fragment = new HomeFragment();
                        FragmentManager fragmentManager = getActivity().getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).addToBackStack("tag").commit();
		}
		return rootView;
	}
}
