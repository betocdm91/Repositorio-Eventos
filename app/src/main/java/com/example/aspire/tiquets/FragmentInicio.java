package com.example.aspire.tiquets;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;

import android.os.Parcel;
import android.os.Parcelable;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * Created by Aspire on 12/06/2017.
 */

public class FragmentInicio extends Fragment{

    TextView usuario;
    Usuario u;

    ListView list;
    String[] web = {
            "Evento 1",
            "Evento 2",
            "Evento 3"

    } ;
    Integer[] imageId = {
            R.drawable.evento1,
            R.drawable.evento2,
            R.drawable.evento3
    };

    /*GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent();
    GoogleSignInAccount acct = result.getSignInAccount();
    String personName = acct.getDisplayName();
    String personGivenName = acct.getGivenName();
    String personFamilyName = acct.getFamilyName();
    String personEmail = acct.getEmail();
    String personId = acct.getId();
    */


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {




        View view = inflater.inflate(R.layout.fragment_inicio, container, false);

        usuario = (TextView) view.findViewById(R.id.textView_usuario);
        u = (Usuario) getActivity().getIntent().getParcelableExtra("usuario");
        usuario.setText("Hola " + u.getUsuario());

        CustomList adapter = new CustomList(getActivity(), web, imageId);

        list=(ListView)view.findViewById(R.id.lista_eventos);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, final long id) {
                Toast.makeText(getContext(), "Evento seleccionado: " +web[+ position], Toast.LENGTH_SHORT).show();

                PopupMenu popupMenu = new PopupMenu(getActivity(),view);

                popupMenu.getMenuInflater().inflate(R.menu.popmenu_evento,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
                    @Override
                    public boolean onMenuItemClick(MenuItem item){
                        Toast.makeText( getContext(), item.getTitle(), Toast.LENGTH_SHORT).show();

                        if(item.getTitle().equals("Ver")){
                            DialogEvento dialogoEvento = new DialogEvento();
                            dialogoEvento.show(getActivity().getFragmentManager(),"");
                        }

                        else if (item.getTitle().equals("Guardar")){

                            DBEventosGuardados event=new DBEventosGuardados(getActivity().getApplicationContext(), "DBEventos", null, 1);
                            SQLiteDatabase db=event.getWritableDatabase();
                            String resul ="";
                            Log.d("asasasjasa",""+resul);
                            db.execSQL("INSERT INTO Eventos (Nombre, Fecha, Hora, Informacion) VALUES('evento1','fecha1','hora1','informacion1')" );

                            event.getAllUsos();

                            db.close();
                            //Toast toast1 = Toast.makeText(getApplicationContext(), "Evento ingresado correctamente", Toast.LENGTH_SHORT);
                            //toast1.setGravity(Gravity.CENTER,0 ,0 );
                        }

                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        return view;
    }
}
