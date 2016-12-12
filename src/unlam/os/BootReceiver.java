package unlam.os;

import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import unlam.os.UNLaMService;

public class BootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED))
			context.startService(new Intent(context, UNLaMService.class));
	}

}
