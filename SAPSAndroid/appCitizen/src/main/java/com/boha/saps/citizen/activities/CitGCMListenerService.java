package com.boha.saps.citizen.activities;

/**
 * Created by aubreyM on 15/08/16.
 */

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.boha.saps.citizen.R;
import com.boha.vodacom.dto.PanicIncidentDTO;
import com.google.android.gms.gcm.GcmListenerService;
import com.google.gson.Gson;

/**
 * This service listens for incoming Google Cloud Messaging messages.
 * It creates and sends notifications based on the type of message received.
 */
public class CitGCMListenerService extends GcmListenerService {

    private static final Gson GSON = new Gson();
    private static final String TAG = "StaGCMListenerService";

    /**
     * Called when a Google Cloud Messaging message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    @Override
    public void onMessageReceived(String from, Bundle data) {
        Log.i(TAG,"###### onMessageReceived, data: " + data.toString());
        String message = data.getString("message");
        if (message != null) {
            Log.d(TAG, "** GCM message From: " + from);
            Log.d(TAG, "Message: " + message);
            sendNotification(message);
            return;
        }
//        message = data.getString("track");
//        if (message != null) {
//            LocationTrackerDTO m = GSON.fromJson(message, LocationTrackerDTO.class);
//            Log.d(TAG, "** GCM track message From: " + from);
//            Log.d(TAG, "Track: " + message);
//            sendNotification(m);
//            return;
//        }
//        message = data.getString("simpleMessage");
//        if (message != null) {
//            SimpleMessageDTO m = GSON.fromJson(message, SimpleMessageDTO.class);
//            m.setDateReceived(new Date().getTime());
//            if (m.getLocationRequest() == null) {
//                m.setLocationRequest(Boolean.FALSE);
//            }
//            Log.d(TAG, "** GCM simpleMessage From: " + from);
//            Log.d(TAG, "SimpleMessage: " + m.getMessage());
//            broadcastMessage(m);
//            return;
//        }

    }
    public static final String LOCATION_REQUESTED = "stafflocationRequested";
    public static final String BROADCAST_ACTION =
            "com.boha.staff.LOCATION.REQUESTED";
    /**
     * Cache the received SimpleMessageDTO on the device
     * @param message
     */
    private void broadcastMessage(final PanicIncidentDTO message) {
//        if (message.getLocationRequest().equals(Boolean.TRUE)) {
//            //todo use broadcast service to ask for location from StaffmainActivity
//            Log.w(TAG, "@@@@ CitGCMListenerService responding to loc request. Broadcasting Request! ");
//
//            Intent m = new Intent(LocationTrackerReceiver.BROADCAST_ACTION);
//            m.putExtra(LOCATION_REQUESTED, true);
//            m.putExtra("simpleMessage",message);
//            LocalBroadcastManager.getInstance(getApplicationContext())
//                    .sendBroadcast(m);
//            return;
//        }
//        if (message.getLocationTracker() != null) {
//            sendNotification(message.getLocationTracker());
//            return;
//        }

    }

    /**
     * Build and send SimpleMessageDTO notification
     * @param simpleMessage
     */
    private void sendNotification(PanicIncidentDTO simpleMessage) {
//        Intent intent = new Intent(this, SimpleMessagingActivity.class);
//        intent.putExtra("simpleMessage",simpleMessage);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this,
//                LOCATION_REQUEST_CODE, intent,
//                PendingIntent.FLAG_ONE_SHOT);


        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_favorite)
                .setContentTitle("Message received")
                .setContentText(simpleMessage.getPanicType().getName())
                .setAutoCancel(true)
                .setSound(defaultSoundUri);
//                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    static final int LOCATION_REQUEST_CODE = 7763;
    /**
     * Create and show a simple notification containing the received GCM message.
     *
     * @param message GCM message received.
     */
    private void sendNotification(String message) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_check_circle_black_24dp)
                .setContentTitle("Welcome Aboard")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}