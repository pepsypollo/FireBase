package com.example.itp1dam.firebase;

import android.os.StrictMode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ITP1DAM on 12/02/2018.
 */

public class PHP {
    private final String URL = "https://pepsypollo.000webhostapp.com/";
    private final String enviar = URL + "firebase.php";
    private final String lUsers = URL + "listausuarios.php";

    public PHP() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }


    public boolean enviar( String token, String Titulo, String mensaje) throws PHPException {
        String form = enviar + "?titulo="+Titulo+"&content="+mensaje+"&key="+token;
        JSONParser parser = new JSONParser();
        JSONArray datos;
        boolean enviado = false;
        try {
            datos = parser.getJSONArrayFromUrl(form,null);
            enviado = datos.getJSONObject(0).getBoolean("success");
        } catch (IOException | JSONException e) {
            throw new PHPException(e.toString());
        }
        return enviado;
    }

    public String[] getUserNames() throws PHPException {
        JSONParser parser = new JSONParser();
        JSONArray datos;

        try {
            System.out.println("fuk of"+lUsers);
            datos = parser.getJSONArrayFromUrl(lUsers,null);
            System.out.println("shit");
            /*System.out.println("fuk"+datos);
            String[] users = new String[datos.length()];
            /*for (int i = 0; i>datos.length(); i++){
                users[i] = datos.getJSONArray(i).getString(0);
                System.out.println(lUsers+users[i]);
            }*/
            return new String[1];
        } catch (IOException | JSONException e) {
            System.err.println("error");
        }
        return new String[1];
    }
}
