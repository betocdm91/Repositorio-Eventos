package com.example.aspire.tiquets;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    private GoogleMap mMap;// el mapa de google
    //marcadores, zoom

    ArrayList<Marcador> listaMarcadores = new ArrayList<Marcador>();
    Marker marcador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        agregarMarcadores(-0.2033951,-78.4957163, "titulo marcador", "otro texto");
        createMarker(-0.2033951,-78.4957163 , "Evento Zona", "Concierto");
        createMarker(-0.2088766,-78.5007665 , "Evento Ejido", "Evento Ejido");
        createMarker(-0.2099281,-78.4956854 , "Concierto CCE", "Concierto");
        createMarker(-0.2039736,-78.4921127 , "Epicentro Bar", "Concierto");

        obtenerUbicacion();

        for(int i = 0 ; i < listaMarcadores.size() ; i++ ) {
            createMarker(listaMarcadores.get(i).getLatitude(),
                    listaMarcadores.get(i).getLongitude(),
                    listaMarcadores.get(i).getTitle(),
                    listaMarcadores.get(i).getSnippet());
        }

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                agregarMarcadores(latLng.latitude,latLng.longitude,"","");
            }
        });

       /* mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                agregarMarcadores(cameraPosition.target.latitude,
                        cameraPosition.target.longitude,"","");
            }
        });*/

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,16));//10-20

    }

    public void agregarMarcadores(double lat, double lng, String titulo, String snippet) {

        LatLng posicion = new LatLng(lat, lng);
        CameraUpdate posActual = CameraUpdateFactory.newLatLngZoom(posicion, 14);

        if(marcador!=null) marcador.remove();

        marcador = mMap.addMarker(new MarkerOptions()
                .position(posicion)
                .title(titulo)
                .icon(BitmapDescriptorFactory.defaultMarker())
                .draggable(true)
                .snippet(snippet));

        mMap.animateCamera(posActual);
    }

    protected Marker createMarker(double latitude, double longitude, String title, String snippet) {

        return mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title)
                .snippet(snippet));

    }

    //sacar del gps
    public void actualizarPosicion(Location location) {
        if (location != null) {
            location.getLatitude();
            location.getLongitude();

            agregarMarcadores(location.getLatitude(), location.getLongitude(), "Posicion Actual", "Aqui estoy");
        }

    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            actualizarPosicion(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    public void obtenerUbicacion() {
        //obtener ubicacion por gps

        if (ActivityCompat.checkSelfPermission(this, android.Manifest
                .permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            //solicitar permisos en tiempo de ejecucion
            return;
        }

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location locationActual = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);

        actualizarPosicion(locationActual);
        //PROVEEDOR,TIEMPOACTUALIZA,MILLAS,
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,1000,0,locationListener);


    }

}