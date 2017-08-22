package com.example.aspire.tiquets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Aspire on 18/08/2017.
 */

public class MisEntradas extends AppCompatActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    ImageButton imgButtonRegresar;


    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_mis_entradas);
        imgButtonRegresar = (ImageButton) findViewById(R.id.imageButtonRegresar2) ;
        imgButtonRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MisEntradas.this, MainActivity.class);
                startActivity(intent);
            }
        });*/

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_mis_entradas);
        imgButtonRegresar = (ImageButton) findViewById(R.id.imageButtonRegresar2) ;
        imgButtonRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MisEntradas.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.expandableListViewEntradas);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Festival de Cine");
        listDataHeader.add("Concierto");
        listDataHeader.add("Obra de Teatro");

        // Adding child data
        List<String> evento1 = new ArrayList<String>();
        evento1.add("Entrada 1");
        evento1.add("Entrada 2");


        List<String> evento2 = new ArrayList<String>();
        evento2.add("Entrada 1");


        List<String> evento3 = new ArrayList<String>();
        evento3.add("Entrada 1");
        evento3.add("Entrada 2");
        evento3.add("Entrada 3");

        listDataChild.put(listDataHeader.get(0), evento1); // Header, Child data
        listDataChild.put(listDataHeader.get(1), evento2);
        listDataChild.put(listDataHeader.get(2), evento3);
    }
}
