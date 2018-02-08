package com.example.itp1dam.firebase;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Esta clase implementa el servicio que nos permite saber cuando cambia el token de la aplicación
 * por el motivo que sea.
 * Hay que añadir el servicio en el AndroidManifest.xml
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService
{

    private static final String LOGTAG = "MyFirebaseInstanceIDService";

    @Override
    public void onTokenRefresh()
    {
        //Se obtiene el token actualizado
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        // Actualizar token del usuario/dispositivo

        System.out.println(LOGTAG + ": Token actualizado: " + refreshedToken);

        // Si queremos tener una aplicación de mensajería deberíamos de actualizar el
        // token en nuestro sistema de usuarios.
    }
}