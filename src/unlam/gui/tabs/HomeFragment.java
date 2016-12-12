package unlam.gui.tabs;

import unlam.gui.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;
import android.widget.ListView;
import unlam.gui.adapter.NewsAdapter;
import unlam.db.NewsDB;
import unlam.news.News;
import java.util.concurrent.TimeUnit;
import java.util.Timer;
import java.util.TimerTask;
import android.widget.Toast;
import android.widget.LinearLayout;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import android.app.Fragment;
import android.app.FragmentManager;

public class HomeFragment extends Fragment {
	
	public HomeFragment(){}
	private NewsAdapter n_adapter;
        private ListView listView;
        private LinearLayout syncBox;
	private NewsDB ndb;
	private View rootView;
	private Timer timer = new Timer();
        private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
	
	@Override
    	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        	rootView 	= inflater.inflate(R.layout.fragment_home, container, false);
		ndb 		= new NewsDB(getActivity(),null,null,1);
        	listView 	= (ListView) rootView.findViewById(R.id.listNews);
        	syncBox  	= (LinearLayout) rootView.findViewById(R.id.syncBox);

		if (android.os.Build.VERSION.SDK_INT >= 11)
			getActivity().getActionBar().setTitle("UNLaM");

		if (ndb.count()>0) {
		
			syncBox.setVisibility(LinearLayout.INVISIBLE);
        		n_adapter = new NewsAdapter(getActivity(), ndb.getNews() );
        		listView.setAdapter(n_adapter);
		}
		else try {
			scheduler.scheduleAtFixedRate(new Runnable() {
				public void run() {
					HomeFragment fragment = new HomeFragment();
                        		FragmentManager fragmentManager = getActivity().getFragmentManager();
                        		fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).addToBackStack("tag").commit();	
				}
			}, 5, 1123132, TimeUnit.SECONDS);
		} 
		catch (Exception e) {
			Log.e("UNLaM","Error at HomeFragment Sync: "+e.toString());
		}

		ndb.close();
        	return rootView;
    	}

}
