package unlam.gui.tabs;

import unlam.gui.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import java.text.DecimalFormat;
import android.app.Activity;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Bundle;
import android.widget.TextView;
import com.ortiz.touch.TouchImageView;
import com.ortiz.touch.TouchImageView.OnTouchImageViewListener;
import android.app.Fragment;
import android.app.FragmentManager;
import android.widget.Toast;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import java.io.InputStream;

public class MapFragment extends Fragment {
	
	private TouchImageView image;
	
	@Override
    	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		if (android.os.Build.VERSION.SDK_INT >= 11)
			getActivity().getActionBar().setTitle("Mapa");

		InputStream istr;
		Bitmap bitmap = null;	
		try {
        		istr = getActivity().getAssets().open("mapa_unlam.jpg");
        		bitmap = BitmapFactory.decodeStream(istr);
    		} catch (Exception e) {	
			Log.e("UNLaM","Error at MapFragment Sync: "+e.toString());
		}

        	View rootView = inflater.inflate(R.layout.fragment_map, container, false);
		image = (TouchImageView)rootView.findViewById(R.id.img);
		image.setImageBitmap(bitmap);
		image.setZoom(2f);
		image.setMinZoom(2f);
		image.setMaxZoom(8f);

       		return rootView;
	}
}
