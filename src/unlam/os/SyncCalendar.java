package unlam.os;

import android.os.AsyncTask;
import unlam.cal.Calendario;
import unlam.cal.Cluster;
import unlam.cal.Event;
import java.util.ArrayList;
import unlam.db.CalendarDB;
import android.util.Log;
import android.content.Context;
import unlam.db.NewsDB;

// This syncs the calendar online to the local database 
// It must be executed in an async thread. Otherwise, it 
// will throw a NetworkOnMainThreadException
class SyncCalendar extends AsyncTask<CalendarDB, Void, String> {

	/* Invoked when the async task is created */
	protected String doInBackground(CalendarDB... params) {
		try {
			long ts = System.currentTimeMillis();
			Calendario cal = new Calendario();
			cal.download();
			if ( cal.isCalendarLoaded() ) {
				CalendarDB db = params[0];
				db.clean();
				for (Cluster c: cal.getCalendar()) {
					db.createCluster(c);
				}
				Log.w("UNLaM","Total rows: "+db.count());
			}
		} catch (Exception e) {
			Log.w("UNLaM","SYNC CALENDAR: "+e);
		}
		return null;
	}

	/* Invoked after the main code is fired */
	protected void onPostExecute() {
	}

	/* Invoked before the main code */
	@Override
        protected void onPreExecute() {
        }

	/* Invoke during the main code execution */
        @Override
        protected void onProgressUpdate(Void... values) {
        }

}
