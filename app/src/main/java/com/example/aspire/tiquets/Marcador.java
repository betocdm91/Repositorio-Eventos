package com.example.aspire.tiquets;

import android.os.AsyncTask;
import android.util.Log;


import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Aspire on 18/08/2017.
 */

public class Marcador {

    double latitude;
    double longitude;
    String title;
    String snippet;
    public ArrayList<String> arrayListMarcadores = new ArrayList<String>();

    public Marcador(double latitude, double longitude, String title, String snippet) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.title = title;
        this.snippet = snippet;
    }

    public Marcador() {
    }

    public double getLatitude(){

        //WebServiceSOAP ws = new WebServiceSOAP();

        //ws.execute();

        for(int i=0; i<arrayListMarcadores.size(); i++) {
            String[] latitud = arrayListMarcadores.get(i).split(";");
            latitude = Double.parseDouble(latitud[0]);
        }
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    private class WebServiceSOAP extends AsyncTask {

        /*variables final wsdl*/
        private final String NAMESPACE = "http://epn.com/";
        private final String NOMBREMETODO = "marcadores";
        private final String URI = "http://172.29.88.211:8080/ServicioWebService/ServicioWeb?WSDL";
        private final String ACCION_SOAP = NAMESPACE+NOMBREMETODO;
        private String respuesta="";

        @Override
        protected Object doInBackground(Object[] params) {
            Log.d("error","prob");

            final SoapObject request = new SoapObject(NAMESPACE, NOMBREMETODO);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = false;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URI);
            //androidHttpTransport.debug = true;
            try {
                androidHttpTransport.call(ACCION_SOAP, envelope);
                //SoapObject response = (SoapObject) envelope.getResponse();
                Vector response = (Vector) envelope.getResponse();
                for(int i=0;i<response.size();i++){
                    arrayListMarcadores.add(response.elementAt(i).toString());
                }
                Log.d("error1",arrayListMarcadores.toArray().toString());
            }
            catch (Exception e) {
                e.printStackTrace();
                Log.d("error catch",e.toString());
            }
            return arrayListMarcadores;
        }


        /*public void mostrarMensaje(String msj){
            Toast.makeText(getContext(),msj,Toast.LENGTH_SHORT).show();
        }*/
    }
}

