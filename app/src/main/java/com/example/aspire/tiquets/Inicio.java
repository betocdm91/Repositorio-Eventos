package com.example.aspire.tiquets;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;


import com.google.android.gms.common.api.GoogleApiClient;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Arrays;



/**
 * Created by Aspire on 18/08/2017.
 */

public class Inicio extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private Button buttonRegistrar;
    private Button buttonIniciar;
    private String respuesta;

    private Usuario usuario;

    EditText user,pass;
    String msj="";

    Handler handler = new Handler();

    private GoogleApiClient googleApiClient;
    private SignInButton googleSignInButton;
    int CODIGO =777;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        user = (EditText) findViewById(R.id.editText_user);
        pass = (EditText) findViewById(R.id.editText_pass);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.equals(buttonRegistrar)) {
                    Intent intent = new Intent(Inicio.this,Registro.class);
                    startActivity(intent);
                }else if(v.equals(buttonIniciar)){
                    //Toast.makeText(getApplication(),pass.getText().toString(),Toast.LENGTH_SHORT).show();
                    //msj = "user: " + user.getText() + " pass: " + pass.getText();
                    new WebServiceSOAP().execute(user.getText().toString(),pass.getText().toString());
                    usuario = new Usuario(user.getText().toString());
                    Log.d("error","error ws");

                    Intent intent = new Intent(Inicio.this, MainActivity.class);
                    intent.putExtra("usuario",usuario);
                    startActivity(intent);
                } else if(v.equals(googleSignInButton)) {
                    // do something else
                    Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                    startActivityForResult(intent,CODIGO);

                }else
                    return;
            }
        };
        /*buttonRegistrar = (Button)findViewById(R.id.buttonRegistrar);
        buttonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inicio.this,Registro.class);
                startActivity(intent);
            }
        });*/

        buttonRegistrar = (Button)findViewById(R.id.buttonRegistrar);
        buttonRegistrar.setOnClickListener(listener);

        /*buttonIniciar = (Button)findViewById(R.id.buttonIniciar);
        buttonIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(getApplication(),pass.getText().toString(),Toast.LENGTH_SHORT).show();
                //msj = "user: " + user.getText() + " pass: " + pass.getText();
                new WebServiceSOAP().execute(user.getText().toString(),pass.getText().toString());
                usuario = new Usuario(user.getText().toString());
                Log.d("error","error ws");

                Intent intent = new Intent(Inicio.this, MainActivity.class);
                intent.putExtra("usuario",usuario);
                startActivity(intent);
            }
        });*/
        buttonIniciar = (Button)findViewById(R.id.buttonIniciar);
        buttonIniciar.setOnClickListener(listener);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();

        googleSignInButton = (SignInButton) findViewById(R.id.botonGoogleIngreso);
        /*googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent,CODIGO);

            }
        });*/
        googleSignInButton.setOnClickListener(listener);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CODIGO){
            GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            GoogleSignInAccount acct = googleSignInResult.getSignInAccount();

            //String personName = acct.getDisplayName();
            //String personGivenName = acct.getGivenName();
            //String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            //String personId = acct.getId();
            logeo(googleSignInResult);
        }
    }

    public void logeo(GoogleSignInResult googleSignInResult){
        if(googleSignInResult.isSuccess()){
            abrirPantallaPrincipal();

        }else{
            Toast.makeText(this,"No se puede iniciar sesión",Toast.LENGTH_LONG).show();
        }
    }

    public void abrirPantallaPrincipal(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private class WebServiceSOAP extends AsyncTask {

        /*variables final wsdl*/
        private final String NAMESPACE = "http://epn.com/";
        private final String NOMBREMETODO = "logueoSQL";
        private final String URI = "http://172.29.88.50:8080/ServicioWebService/ServicioWeb?WSDL";
        private final String ACCION_SOAP = NAMESPACE+NOMBREMETODO;
        //private String respuesta="";


        @Override
        protected Object doInBackground(Object[] params) {
            Log.d("error","prob");

            SoapObject request = new SoapObject(NAMESPACE,NOMBREMETODO);

            request.addProperty("user",params[0]);
            request.addProperty("pass",params[1]);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = false;

            envelope.setOutputSoapObject(request);
            Log.d("error2","and");
            HttpTransportSE transportSE = new HttpTransportSE(URI);

            try{
                transportSE.call(ACCION_SOAP, envelope);
                //respuesta web service
                final SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                respuesta = response.toString();

                Log.d("salida",respuesta);

                //rutina
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mostrarMensaje(respuesta);
                    }
                });

            }catch (IOException e) {
                e.printStackTrace();
                Log.d("error3",e.toString());
            }catch (XmlPullParserException e){
                e.printStackTrace();
                Log.d("error4",e.toString());
            }

            return respuesta;
        }
    }

    public void mostrarMensaje(String msj){

        Toast.makeText(this,msj,Toast.LENGTH_SHORT).show();
    }


}