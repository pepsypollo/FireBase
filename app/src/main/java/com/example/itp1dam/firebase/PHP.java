package com.example.itp1dam.firebase;

import android.os.StrictMode;

import org.json.JSONArray;
import org.json.JSONException;

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

    public String[] getUsers() throws PHPException {
        JSONParser parser = new JSONParser();
        JSONArray datos;
        ArrayList users = new ArrayList();

        try {
            datos = parser.getJSONArrayFromUrl(lUsers,null);
            String[] username = new String[datos.length()];
            String[] pass = new String[datos.length()];
            String[] token = new String[datos.length()];
            for (int i = 0; i>datos.length(); i++){
                username[i] = datos.getJSONObject(i).getString("username");
                pass[i] = datos.getJSONArray(i).getString(1);
                token[i] = datos.getJSONArray(i).getString(2);
            }
            return username;
        } catch (IOException | JSONException e) {
            throw new PHPException(e.toString());
        }
    }
}
