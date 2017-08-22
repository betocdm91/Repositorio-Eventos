package com.example.aspire.tiquets;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.util.ArrayList;

/**
 * Created by Aspire on 18/08/2017.
 */

public class FragmentMiCuenta extends Fragment implements GoogleApiClient.OnConnectionFailedListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    ListView listViewOpciones;
    ArrayList<String> arrayListOpcion = new ArrayList<>();
    private GoogleApiClient googleApiClient;
    private Button logoutButton;

    private Button buttonMapa;
    int CODIGO =777;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mi_cuenta, container, false);
        arrayListOpcion.clear();
        cargarLista(view);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(getContext()).enableAutoManage(getActivity(),this).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();
        logoutButton = (Button) view.findViewById(R.id.buttonCerrarSesion);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        if(status.isSuccess()){
                            regresarPantallaPrincipal();
                        }else{
                            Toast.makeText(getContext(),"No se puede cerrar sesión",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        buttonMapa = (Button) view.findViewById(R.id.buttonMapa);
        buttonMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarMapa();
            }
        });

        return view;
    }

    public void regresarPantallaPrincipal(){
        Intent intent = new Intent(getContext(),Inicio.class);
        startActivity(intent);
    }

    public void mostrarMapa(){
        Intent intent = new Intent(getContext(),MapsActivity.class);
        startActivity(intent);
    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }*/
    
    ////////////////////////////////////////////////////
    
     @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void cargarLista(View view) {
        arrayListOpcion.add("Eventos Guardados");
        arrayListOpcion.add("Mis Entradas");

        listViewOpciones = (ListView) view.findViewById(R.id.listViewMiCuenta);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, arrayListOpcion);
        listViewOpciones.setAdapter(adapter);
        listViewOpciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Intent intent = new Intent(getActivity(), EventosGuardados.class);
                    startActivity(intent);
                }
                else if (position == 1) {
                    Intent intent = new Intent(getActivity(), MisEntradas.class);
                    startActivity(intent);
                }

            }
        });

    }
}
