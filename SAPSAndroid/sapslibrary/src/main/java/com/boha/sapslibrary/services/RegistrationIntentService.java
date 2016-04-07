package com.boha.sapslibrary.services;

/**
 * Created by aubreyM on 15/08/16.
 */

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.boha.sapslibrary.R;
import com.boha.sapslibrary.util.DataUtil;
import com.boha.sapslibrary.util.OKUtil;
import com.boha.sapslibrary.util.SharedUtil;
import com.boha.sapslibrary.util.Statics;
import com.boha.vodacom.dto.GcmDeviceDTO;
import com.boha.vodacom.dto.RequestDTO;
import com.boha.vodacom.dto.ResponseDTO;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

public class RegistrationIntentService extends IntentService {

    private static final String LOG = "RegIntentService";
    private static final String[] MONITOR_TOPICS = {"general", "projects", "monitors"};
    private static final String[] STAFF_TOPICS = {"general", "projects", "monitors", "staff"};

    public RegistrationIntentService() {
        super(LOG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(LOG, "RegistrationIntentService onHandleIntent");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        synchronized (LOG) {
            InstanceID instanceID = InstanceID.getInstance(this);
            String token = null;
            try {
                token = instanceID.getToken(getString(R.string.gcm_sender_id),
                        GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                Log.i(LOG, "GCM Registration Token: " + token);

                sendRegistrationToServer(token);
                subscribeTopics(token);
            } catch (IOException e) {
                sharedPreferences.edit().putBoolean("SENT_TOKEN_TO_SERVER", false).apply();
                Log.e(LOG, "GCM getToken failed", e);
            }

        }
    }

    /**
     * Persist registration to third-party servers.
     * <p/>
     * Modify this method to associate the user's GCM registration token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(final String token) {
        Log.i(LOG, "RegistrationIntentService sendRegistrationToServer: " + token);
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final GcmDeviceDTO device = SharedUtil.getGCMDevice(getApplicationContext());
        device.setRegistrationID(token);

        RequestDTO w = new RequestDTO(RequestDTO.UPDATE_GCM_REGISTRATION);
        w.setGcmDevice(device);

        DataUtil.updateGCMRegistration(getApplicationContext(), device, new OKUtil.OKListener() {
            @Override
            public void onResponse(ResponseDTO response) {
                if (response.getStatusCode() == 0) {
                    sharedPreferences.edit().putBoolean(Statics.TOKEN_SENT_TO_SEVER, true).apply();
                    SharedUtil.storeRegistrationId(getApplicationContext(), token);
                    SharedUtil.saveGCMDevice(getApplicationContext(),device);
                    Log.w(LOG, "############ Device registered on Monitor Server GCM regime");
                }
            }

            @Override
            public void onError(String message) {
                sharedPreferences.edit().putBoolean(Statics.TOKEN_SENT_TO_SEVER, false).apply();
                Log.e(LOG, "############ Device failed to register on server GCM regime\n" + message);
            }
        });


    }

    /**
     * Subscribe to any GCM topics of interest, as defined by the TOPICS constant.
     *
     * @param token GCM token
     * @throws IOException if unable to reach the GCM PubSub service
     */
    // [START subscribe_topics]
    private void subscribeTopics(String token) throws IOException {

//        if (SharedUtil.getMonitor(getApplicationContext()) != null) {
//            for (String topic : MONITOR_TOPICS) {
//                GcmPubSub pubSub = GcmPubSub.getInstance(this);
//                pubSub.subscribe(token, "/topics/" + topic, null);
//            }
//        }
//        if (SharedUtil.getCompanyStaff(getApplicationContext()) != null) {
//            for (String topic : STAFF_TOPICS) {
//                GcmPubSub pubSub = GcmPubSub.getInstance(this);
//                pubSub.subscribe(token, "/topics/" + topic, null);
//            }
//        }
        Log.e(LOG, "############ subscribeTopics: Topics on GCM");
    }
}
// [END subscribe_topics]

