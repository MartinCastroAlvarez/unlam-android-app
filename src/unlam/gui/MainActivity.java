package unlam.gui;

import unlam.gui.adapter.ToolsAdapter;
import unlam.gui.model.ToolsModel;
import unlam.gui.tabs.HomeFragment;
import unlam.gui.tabs.MapFragment;
import unlam.gui.tabs.CalendarFragment;
import unlam.os.UNLaMService;
import android.content.Intent;
import java.util.ArrayList;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends Activity {

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;
	private ArrayList<ToolsModel> navDrawerItems;
	private ToolsAdapter adapter;
        private Intent serviceIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// Start service.-
        	serviceIntent = new Intent(this, UNLaMService.class);
                startService(serviceIntent);

		// Create view 
		super.onCreate(savedInstanceState);
		if (android.os.Build.VERSION.SDK_INT >= 11)
			getActionBar().show();
		setContentView(R.layout.activity_main);

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
		navDrawerItems = new ArrayList<ToolsModel>();

		// adding nav drawer items to array
		navDrawerItems.add(new ToolsModel(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
		navDrawerItems.add(new ToolsModel(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
		navDrawerItems.add(new ToolsModel(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));

		// Recycle the typed array
		navMenuIcons.recycle();
		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new ToolsAdapter(getApplicationContext(), navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		if (android.os.Build.VERSION.SDK_INT >= 11) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
			getActionBar().setHomeButtonEnabled(true);
		}

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, //nav menu toggle icon
				R.string.app_name, // nav drawer open - description for accessibility
				R.string.app_name // nav drawer close - description for accessibility
		) {
			public void onDrawerClosed(View view) {
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0);
		}
	}

	// Slide menu item click listener
	private class SlideMenuClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		return super.onOptionsItemSelected(item);
	}

	// Called when invalidateOptionsMenu() is triggered
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		// menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	// Diplaying fragment view for selected nav drawer list item
	private void displayView(int position) {

		// update the main content by replacing fragments
		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new HomeFragment();
			break;
		case 1:
			fragment = new CalendarFragment();
			break;
		case 2:
			fragment = new MapFragment();
			break;
		default:
			break;
		}
		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).addToBackStack("tag").commit();
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	// When using the ActionBarDrawerToggle, you must call it during
	// onPostCreate() and onConfigurationChanged()...
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

/* End of class */
}
