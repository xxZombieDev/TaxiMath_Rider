package com.example.ray.prueba;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;

public class Rutas extends FragmentActivity implements OnMapReadyCallback {
    Button btn_Solicitar;
    private GoogleMap mMap;
    private MapFragment mapFragment;
    String TAG = "My Project";
    PlacesClient placesClient;

    private LatLng sydney = new LatLng(-8.579892, 116.095239);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutas);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);


        btn_Solicitar =  findViewById(R.id.btn_SolicitaTaxi);

        String apiKey =  getString(R.string.google_maps_key);

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }

        // Create a new Places client instance.
        placesClient = Places.createClient(this);

        // Initialize the AutocompleteSupportFragment.
        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });



        btn_Solicitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();

                Intent intent  = new Intent(Rutas.this, RegistroUsuario.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                startActivity(intent);
            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 8.5f));
        mMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Origen")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

    }

    private void ConfirmarDatos() {

        final CharSequence[] opciones = {"Aceptar","Cancelar"};
        final AlertDialog.Builder alertOpciones = new AlertDialog.Builder(this);
        alertOpciones.setTitle("¿Estan correctos sus datos?");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (opciones[i].equals("Aceptar")){
                    Toast.makeText(Rutas.this,"Su taxi esta por llegar, espere por favor",Toast.LENGTH_LONG).show();
                }
                else {
                    dialog.dismiss();
                }
            }
        });
        alertOpciones.show();
    }



}
