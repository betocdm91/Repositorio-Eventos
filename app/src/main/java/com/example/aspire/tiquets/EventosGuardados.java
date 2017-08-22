package com.example.aspire.tiquets;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Aspire on 18/08/2017.
 */

public class EventosGuardados extends AppCompatActivity {

    ListView listViewEventosGuardados;
    ArrayList<String> arrayListEventosGuardados = new ArrayList<>();
    ImageButton imgButtonRegresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_eventos_guardados);
        imgButtonRegresar = (ImageButton) findViewById(R.id.imageButtonRegresar) ;
        imgButtonRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventosGuardados.this, MainActivity.class);
                startActivity(intent);
            }
        });
        cargarLista();
    }


    public void cargarLista() {

        arrayListEventosGuardados.add("Ultimas 15k");
        arrayListEventosGuardados.add("Festival de Cine");
        arrayListEventosGuardados.add("Concierto");

        listViewEventosGuardados = (ListView) this.findViewById(R.id.listViewEventosGuardados);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, arrayListEventosGuardados);
        listViewEventosGuardados.setAdapter(adapter);
        listViewEventosGuardados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PopupMenu popupmenu = new PopupMenu(EventosGuardados.this, view);
                popupmenu.getMenuInflater().inflate(R.menu.popmenu, popupmenu.getMenu());
                popupmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // Toast.makeText(Main2Activity.this, item.getTitle(),Toast.LENGTH_LONG).show();

                        switch (item.getTitle().toString())
                        {
                            case "Ver Informacion":
                                DialogEvento dialogoEvento = new DialogEvento();
                                dialogoEvento.show(getFragmentManager(),"");
                                Toast.makeText(EventosGuardados.this,"Informacion del Evento",Toast.LENGTH_LONG).show();
                                break;
                            default:
                                break;


                        }

                        return true;
                    }
                });
                popupmenu.show();


               /* DBEventosGuardados event=new DBEventosGuardados(getActivity().getApplicationContext(), "DBEventos", null, 1);
                SQLiteDatabase db=event.getWritableDatabase();

                db.execSQL("SELECT * FROM " + "Eventos");*/

            }
        });

    }


}