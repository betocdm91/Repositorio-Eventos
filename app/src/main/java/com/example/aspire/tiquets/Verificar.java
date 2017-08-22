package com.example.aspire.tiquets;


import 	java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Created by CARLOS OSORIO on 21/8/2017.
 */

public class Verificar {

    protected boolean validateEmail(String email) {
        String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }
}

