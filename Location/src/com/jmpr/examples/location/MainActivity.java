package com.jmpr.examples.location;

import java.util.List;

import android.app.Activity;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity implements LocationListener {

	private static final String[] A = { "n/a", "precise", "imprecise" };
	private static final String[] P = { "n/a", "low", "medium", "high" };
	private static final String[] E = { "out of service",
			"not available temporally ", "available" };
	private LocationManager handler;
	private TextView output;
	private String provider;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		output = (TextView) findViewById(R.id.output);

		// Location handler creation
		handler = (LocationManager) getSystemService(LOCATION_SERVICE);
		
		log("Location providers : \n ");
		showProviders();
		Criteria criteria = new Criteria();
		
		// If a empty criteria is passed, defaults to best precision criteria 
		provider = handler.getBestProvider(criteria, true);
		
		log("Best provider : " + provider + "\n");
		log("Last know location :");
		Location location = handler.getLastKnownLocation(provider);
		showLocation(location);
	}

	// Lifecycle callback methods
	@Override
	protected void onResume() {
		super.onResume();
		// Location notification activation
		handler.requestLocationUpdates(provider, 10000, 1, this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		// Location notification deactivation
		handler.removeUpdates(this);
	}

	// LocationListener interface methods
	public void onLocationChanged(Location location) {
		log("New location : ");
		showLocation(location);
	}

	public void onProviderDisabled(String provider) {
		log("Provider disabled : " + provider + "\n");
	}

	public void onProviderEnabled(String provider) {
		log("Provider enabled : " + provider + "\n");
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		log("Provider state changed : " + provider + ", state="
				+ E[Math.max(0, status)] + ", extras=" + extras + "\n");
	}

	// Logging methods
	private void log(String message) {
		output.append(message + "\n");
	}

	private void showLocation(Location localizacion) {
		if (localizacion == null)
			log("Unknown location\n");
		else
			log(localizacion.toString() + "\n");
	}

	private void showProviders() {
		List<String> proveedores = handler.getAllProviders();
		for (String proveedor : proveedores) {
			showProvider(proveedor);
		}
	}

	private void showProvider(String provider) {
		LocationProvider info = handler.getProvider(provider);
		log("LocationProvider[ " + "getName=" + info.getName()
				+ ", isProviderEnabled="
				+ handler.isProviderEnabled(provider) + ", getAccuracy="
				+ A[Math.max(0, info.getAccuracy())] + ", getPowerRequirement="
				+ P[Math.max(0, info.getPowerRequirement())]
				+ ", hasMonetaryCost=" + info.hasMonetaryCost()
				+ ", requiresCell=" + info.requiresCell()
				+ ", requiresNetwork=" + info.requiresNetwork()
				+ ", requiresSatellite=" + info.requiresSatellite()
				+ ", supportsAltitude=" + info.supportsAltitude()
				+ ", supportsBearing=" + info.supportsBearing()
				+ ", supportsSpeed=" + info.supportsSpeed() + " ]\n");
	}
}
