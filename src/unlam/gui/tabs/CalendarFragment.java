package unlam.gui.tabs;

import unlam.gui.R;
import java.util.ArrayList;  
import java.util.Arrays; 
import unlam.gui.MainActivity;
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
import unlam.gui.adapter.CalendarAdapter;
import android.util.Log;
import android.widget.Toast;
import android.app.Fragment;
import android.app.FragmentManager;
import unlam.gui.tabs.HomeFragment;

public class CalendarFragment extends Fragment {
	
	public CalendarFragment(){}
	private ListView listView; 
	private CalendarAdapter adapter; 
	private CalendarDB cdb;
	
	@Override
    	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		if (android.os.Build.VERSION.SDK_INT >= 11)
			getActivity().getActionBar().setTitle("Calendario");
        	View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);
		cdb = new CalendarDB(getActivity(),null,null,1);
		Calendario cal = cdb.getCalendar();

		if (cal.isCalendarLoaded()) { 
        		adapter = new CalendarAdapter((MainActivity)getActivity(), cal);
			listView = (ListView) rootView.findViewById(R.id.list);
			listView.setAdapter(adapter);
		} 

		else { 
			Toast.makeText(getActivity(), 
				"Descargando calendario...", 
				Toast.LENGTH_SHORT).show();	
			HomeFragment fragment = new HomeFragment();
			FragmentManager fragmentManager = getActivity().getFragmentManager();
               		fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).addToBackStack("tag").commit();
	
		}

		return rootView;
    	}
}
