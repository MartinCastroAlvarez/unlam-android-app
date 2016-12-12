package unlam.os;

import android.content.Intent;
import android.os.IBinder;
import android.app.Service;
import android.widget.Toast;
import android.content.Context;
import unlam.db.CalendarDB;
import unlam.db.NewsDB;
import android.util.Log;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import android.os.Binder;
import java.util.Properties;
import java.lang.Integer;
import java.io.InputStream;
import android.content.res.Resources;
import unlam.news.News;
import android.net.NetworkInfo;
import android.net.ConnectivityManager;

public class UNLaMService extends Service {

	private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
	private NewsDB DB_NEWS;
	private CalendarDB DB_CAL;
	private int lapse = 0;
	private int delay = 0;
	private final LocalBinder binder = new LocalBinder();
	private int maxNews;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) { 
		/* This prevents the process from being killed */
    		return START_STICKY;
	}

	/* onCreate: Invoked when the service is called. If the service is 
 	 * already running, this is not invoked. */
	public void onCreate() {

  		super.onCreate();

		try {
			scheduler.scheduleAtFixedRate(new Runnable() {
				public void run() {
					if (isConnectionReliable()) {
						try { 
							syncCalendar();
						} catch (Exception e) {
							Log.e("UMLaM", "Error while syncing calendar:"+e);
						}
					}
					else
						Log.e("UMLaM", "Connection is not reliable");
				}
			}, 0, 432000, TimeUnit.SECONDS); 	
		} catch (Exception e) {
			Log.e("UMLaM", "Error while syncing calendar:"+e);
		}

  	}		

	public void syncCalendar() {
		DB_CAL = new CalendarDB(this,null,null,1);
		new SyncCalendar().execute(DB_CAL);
		postNews("Calendario académico actualizado.");
		DB_CAL.close();
	}

	private void postNews(String t) {
		NewsDB DB_NEWS		= new NewsDB(this,null,null,1);
		News n 			= new News();
		n.setDateToNow();
		n.setText(t);
		DB_NEWS.storeNews(n);
		DB_NEWS.deleteMoreThan(5);
		DB_NEWS.close();
	}

	/* These two functions allows you to invoke public methods from the Activity */
	@Override
	public IBinder onBind(Intent intent) {
		super.onCreate();	
		return binder;
	}

	public class LocalBinder extends Binder {
       		public UNLaMService getService() {
            		return UNLaMService.this;
 	       }
        }

	private boolean isConnectionReliable() {
                ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
                boolean isWiFi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
                if (isConnected) //  && isWiFi)
                        return true;
                else
                        return false;
        }

}

