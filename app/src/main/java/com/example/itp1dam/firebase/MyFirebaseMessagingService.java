package com.example.itp1dam.firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Este servicio se invocará cuando llegue una notificación a nuestra aplicación y esta esté
 * en primer plano.
 * Hay que añadir el servicio en el AndroidManifest.xml
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService
{
    private static final String LOGTAG = "MyFirebaseMessagingService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String titulo = "", texto = "";

        // Mensaje de notificación
        if (remoteMessage.getNotification() != null) {
            titulo = remoteMessage.getNotification().getTitle();
            texto = remoteMessage.getNotification().getBody();
        }

        showNotification(titulo, texto);
    }

    /**
     * Muestra una notificación en el dispositivo
     * @param title Título de la notificación
     * @param text Texto de la notificación
     */
    private void showNotification(String title, String text)
    {
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(android.R.drawable.stat_sys_warning)
                        .setContentTitle(title)
                        .setContentText(text);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }

    /**
     * Muestra una notificación en el dispositivo
     * @param title Título de la notificación
     * @param text Texto de la notificación
     * @param image Imagen de la notificación
     */
    private void showNotificationv2(String title, String text, Bitmap image)
    {
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setContentTitle(title)
                .setContentText(text)
                .setAutoCancel(true)
                .setSound(defaultSoundUri);

        if( image != null )
        {
            notificationBuilder.setLargeIcon(image);    /*Notification icon image*/
            notificationBuilder.setStyle(new NotificationCompat.BigPictureStyle()
                            .bigPicture(image)); /*Notification with Image*/
        }

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    /**
     * Muestra una notificación en el dispositivo con una acción de ir a otra actividad
     * @param title Título de la notificación
     * @param text Texto de la notificación
     * @param image Imagen de la notificación
     */
    private void showNotificationv3(String title, String text, Bitmap image)
    {
        // Donde pone MainActivity.class se pondrá la actividad a la que queremos ir
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setContentTitle(title)
                .setContentText(text)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        if( image != null )
        {
            notificationBuilder.setLargeIcon(image);    /*Notification icon image*/
            notificationBuilder.setStyle(new NotificationCompat.BigPictureStyle()
                    .bigPicture(image)); /*Notification with Image*/
        }

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    /**
     * Obtiene una imagen de una URL dada
     * @param imageUrl URL de la imagen
     * @return Imagen
     */
    public Bitmap getBitmapfromUrl(String imageUrl)
    {
        try
        {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;
        }
        catch (Exception e)
        {
            System.out.println("Error obteniendo la imagen -> " + e.toString());
            return null;
        }
    }
}
