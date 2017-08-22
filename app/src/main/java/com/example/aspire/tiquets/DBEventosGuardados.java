package com.example.aspire.tiquets;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.widget.Toast;


/**
 * Created by CARLOS OSORIO on 21/8/2017.
 */

public class DBEventosGuardados extends SQLiteOpenHelper {


    String sql="CREATE TABLE Eventos (Nombre TEXT, Fecha TEXT, Hora TEXT, Informacion TEXT)";

    public DBEventosGuardados(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS Evento");
        db.execSQL(sql);
    }

    public void getAllUsos(){
        String sql_select = "SELECT * FROM " + "Eventos";
        SQLiteDatabase db = this.getWritableDatabase();
        db.rawQuery("SELECT * FROM " + "Eventos",null);//execSQL("SELECT * FROM " + "Eventos");
        Cursor cursor = db.rawQuery(sql_select, null);
        if(cursor.moveToFirst()){
            while(cursor.moveToNext()){
            //cursor.getString(0);
            Log.d("hola mundo mierda",cursor.getString(0));
            }
        }


    }
}
