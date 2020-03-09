package co.com.tablesa.serviapp.presentacion;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

import co.com.tablesa.serviapp.R;
import co.com.tablesa.serviapp.logica.GoogleMapsLogic;
import co.com.tablesa.serviapp.logica.IGoogleMapsLogic;
import co.com.tablesa.serviapp.modelo.dto.RutaDTO;


public class MapaActivity extends AppCompatActivity implements OnMapReadyCallback,
        LocationListener, GoogleMap.OnMarkerClickListener {
    private GoogleMap mapa;
    private boolean isAuthorized = true;
    private LocationManager locationManager;
    private Double inicialLatitude;
    private Double inicialLongitude;
    private static final int CODIGO_PERMISOS_LOCATION = 1;
    private PolylineOptions polylineOptions;
    private LatLng posicionInicial;
    private LatLng posicionFinal;
    private int contadorMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapa);

        supportMapFragment.getMapAsync(this);
        obtenerUbicacionActual();
    }

    public void obtenerUbicacionActual(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

                isAuthorized = !isAuthorized;
                //Toast.makeText(this,"Habilitar permiso de ubicacion",Toast.LENGTH_LONG).show();
                //este llama a onRequestPermissionsResult para validar si se dio o no el permiso :D
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        CODIGO_PERMISOS_LOCATION);

            }
        }

        if (isAuthorized){
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,1000,0,this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;
        mapa.getUiSettings().setZoomControlsEnabled(true);
        mapa.setOnMarkerClickListener(this);

        //marcador final para armar la ruta
        posicionFinal = new LatLng(3.5302173,-76.3085789);
        MarkerOptions marcadorFinal = new MarkerOptions();
        marcadorFinal.title("estacion del Tren");
        marcadorFinal.position(posicionFinal);
        mapa.addMarker(marcadorFinal);

    }


    public void Ubicar(View view){

        try{
            IGoogleMapsLogic iGoogleMapsLogic = new GoogleMapsLogic();
            RutaDTO rutaDTO = iGoogleMapsLogic.obtenerRuta(posicionInicial,posicionFinal);

            if(rutaDTO!=null && rutaDTO.getlPuntos()!=null &&
                                rutaDTO.getlPuntos().size()>0){

                PolylineOptions polylineOptions = new PolylineOptions();
                polylineOptions.width(4);
                polylineOptions.color(Color.RED);

                List<LatLng> puntoPintar = rutaDTO.getlPuntos();

                for (int i=0;i<puntoPintar.size();i++){
                    polylineOptions.add(puntoPintar.get(i));
                }

                mapa.addPolyline(polylineOptions);
            }

        }catch(Exception exception){
                Log.e("Error","Error pintando la ruta:"+
                                            exception.getMessage());
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        inicialLatitude = location.getLatitude();
        inicialLongitude = location.getLongitude();

        posicionInicial = new LatLng(inicialLatitude,inicialLongitude);
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(posicionInicial,13));

        MarkerOptions marcadorInicial = new MarkerOptions();
        marcadorInicial.title("Tu ubicacion");
        marcadorInicial.position(posicionInicial);
        marcadorInicial.icon(BitmapDescriptorFactory.fromResource(R.drawable.bug));
        mapa.addMarker(marcadorInicial);

        CameraPosition cameraPosition = CameraPosition.builder().target(posicionInicial).zoom(15).bearing(0).build();

        //mapa.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),0,null);

        Log.i("Info","Posicion inicial: "+inicialLatitude+" ---- "+inicialLongitude);
        Log.i("Info","Posicion final: "+posicionFinal);
        //consultar solo una vez la ubicaciòn incial, para que no consuma a cada rato recursos cuando recargue
        //locationManager.removeUpdates(this);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CODIGO_PERMISOS_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this,"Permiso de ubicacion concedido", Toast.LENGTH_LONG).show();
                    isAuthorized = true;
                    obtenerUbicacionActual();

                } else {
                    Toast.makeText(this,"Permiso de ubicacion no fue concedido", Toast.LENGTH_LONG).show();
                }
                break;
            // Aquí más casos dependiendo de los permisos
            // case OTRO_CODIGO_DE_PERMISOS...
        }
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}


