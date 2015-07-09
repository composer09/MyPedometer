package kr.co.composer.pedometer.location.layout;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import kr.co.composer.pedometer.R;


public class LocationFragment extends Fragment implements OnMyLocationChangeListener{
	public static final int SECONDSDELAYED = 4;
	private GoogleMap googleMap;
	private LatLng latLng;
	private CameraPosition cp;
	private MarkerOptions mkOption;
	private Marker marker;
	private List<LatLng> latLngList;
	
	private double latitude, longitude;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final ProgressDialog dialog = ProgressDialog.show(getActivity(),"","Loading",true,false);            
		latLngList = new ArrayList<LatLng>();
		
		latLng = new LatLng(37,128);
		cp = new CameraPosition.Builder().target(latLng).zoom(6).build();
		
        new Handler().postDelayed(new Runnable()
                {
                    public void run()
        {               
            dialog.dismiss();
        }
        }, SECONDSDELAYED * 1000);      
	
        
	}
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
			View v = inflater.inflate(R.layout.google_map, container, false);

			googleMap = ((SupportMapFragment)this.getChildFragmentManager().findFragmentById(R.id.map)).getMap();
			googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cp));
			
			googleMap.setMyLocationEnabled(true);
			googleMap.setOnMyLocationChangeListener(this);
			
			
	        return v;

	    }

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onMyLocationChange(Location location) {
		latitude = location.getLatitude(); // 占쏙옙占쏙옙 占쏙옙占쏙옙
		longitude = location.getLongitude(); // 占쏙옙占쏙옙 占썸도
		
		latLng = new LatLng(latitude, longitude);
		latLngList.add(latLng);
//		PolylineOptions po = new PolylineOptions().add(new LatLng(37.401002, 126.921420)).add(latLng);
		System.out.println("리스트사이즈 검사"+latLngList.size());
		PolylineOptions po = new PolylineOptions();
//		Iterable<LatLng> latlng = new Iterable<LatLng>() {
//			@Override
//			public Iterator<LatLng> iterator() {
//				Iterator iter = latLngList.iterator();
//				if(iter.hasNext()){
//					iter.next();
//				}
//				return iter;
//			}
//		};
		
		po.color(Color.BLUE);
		
		po.addAll(latLngList);
		Polyline pl = googleMap.addPolyline(po);
		
		 // 占쏙옙占쏙옙 占쏙옙치占쏙옙 占쏙옙占쌜몌옙 占싱듸옙
        //mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));       
        // 확占쏙옙 占쏙옙 占쏙옙占�(Zoom)
       // mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(17));        
        
    	
        
        //占쏙옙커,타占쏙옙틀, 占쏙옙占쏙옙占쏙옙 표占쏙옙
         if(marker!=null){
        	 marker.remove(); //占쏙옙占쏙옙占쏙옙커占쏙옙占쏙옙占�
         }
         cp = new CameraPosition.Builder().target((latLng)).zoom(16).build();
    	 googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cp));
        mkOption = new MarkerOptions().position(latLng).title("占쏙옙占쏙옙占쏙옙치").snippet("클占쏙옙占싹쇽옙占쏙옙");
        marker = googleMap.addMarker(mkOption); 
        
        //占쏙옙커占쏙옙 타占쏙옙틀,占쏙옙占쏙옙占쏙옙占쏙옙 클占쏙옙占쏙옙占쏙옙 占쏙옙 호占쏙옙占�
        googleMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

        	@Override

        	public void onInfoWindowClick(Marker marker) {

        	// TODO Auto-generated method stub

        	       	
        	 AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
             alert.setTitle("占쏙옙占쏙옙占쏙옙치占쏙옙 占쏙옙占쏙옙占쏙옙 占쌉뤄옙占싹시겠쏙옙占싹깍옙?");
             alert.setIcon(R.drawable.ic_launcher);
             //positive占쏙옙튼클占쏙옙占쏙옙 처占쏙옙占쏙옙 占싱븝옙트 占쏙옙체 占쏙옙占쏙옙
             DialogInterface.OnClickListener positiveClick=new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					//占쏙옙占쏙옙占싶븝옙占싱쏙옙 占쌉뤄옙占쌜억옙 占쏙옙占� 占쏙옙占쏙옙
					Toast.makeText(getActivity(), "占쌉뤄옙占쌜억옙占쏙옙占쏙옙", Toast.LENGTH_LONG).show();
					
				}
			};
             alert.setPositiveButton("확占쏙옙", positiveClick);
             alert.setNegativeButton("占쏙옙占�", null);
             alert.show();
        	}

        	});

	}

}
