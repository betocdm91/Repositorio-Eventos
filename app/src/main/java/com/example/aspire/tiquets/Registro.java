package com.example.aspire.tiquets;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;
import android.widget.Toast;
import android.view.Gravity;

/**
 * Created by Aspire on 18/08/2017.
 */

public class Registro extends AppCompatActivity{

    private Button buttonCrear;
    EditText emailver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        final Verificar comprueba=new Verificar();

        buttonCrear = (Button)findViewById(R.id.buttonCrear);
        emailver=(EditText)findViewById(R.id.editTextemail);

        buttonCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        if(!comprueba.validateEmail(emailver.getText().toString())) {
                            Toast toast1 = Toast.makeText(getApplicationContext(), "Email no válido", Toast.LENGTH_SHORT);
                            toast1.setGravity(Gravity.CENTER,0 ,0 );

                            toast1.show();                }

                        else {
                            Intent intent = new Intent(Registro.this, Inicio.class);
                            startActivity(intent);
                        }
            }
        });

    }

}