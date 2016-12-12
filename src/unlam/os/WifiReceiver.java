package unlam.os;

import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import unlam.os.UNLaMService;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class WifiReceiver extends BroadcastReceiver {

	Context context;

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equalsIgnoreCase(context.CONNECTIVITY_SERVICE)) {
			if (isConnectivityReliable(context)) {
				try { 
					context.stopService(new Intent(context, UNLaMService.class));
				}
				catch (Exception e) {
				}
				context.startService(new Intent(context, UNLaMService.class));
			}
		}
	}

	private boolean isConnectivityReliable(Context context) {
		ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		if(activeNetwork != null){
    			boolean isConnected = activeNetwork.isConnected();
    			boolean isWiFi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
    			if(isConnected && isWiFi){
				return true;
    			}
		}
		return false;
	}

}
