package com.boha.sapslibrary.util;


/**
 * Created by aubreyM on 2014/05/11.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.boha.sapslibrary.R;
import com.boha.vodacom.dto.GcmDeviceDTO;
import com.boha.vodacom.dto.RequestDTO;
import com.boha.vodacom.dto.ResponseDTO;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

/**
 * Handle registration of device to Google Cloud Messaging
 */
public class GCMUtil {
    public interface GCMUtilListener {
        public void onDeviceRegistered(String id);

        public void onGCMError();
    }

    static Context ctx;
    static GCMUtilListener gcmUtilListener;
    static String registrationID, msg;
    static final String LOG = "GCMUtil";
    static GoogleCloudMessaging gcm;
    static boolean weCool;

//SenderID fir GCM: 235297950926

    /**
     * Start device registration to Google Cloud Messaging
     * Receive GCM registration string and complete GCM registration by calling back-end
     *
     * @param context
     * @param listener
     */
    public static void startGCMRegistration(final Context context, final GCMUtilListener listener) {
        ctx = context;
        gcmUtilListener = listener;
        weCool = false;
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Log.e(LOG, "... startin GCM registration");
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(ctx);
                    }

                    registrationID = gcm.register(context.getString(R.string.gcm_sender_id));
                    msg = "Device registered, registration ID = \n" + registrationID;
                    SharedUtil.storeRegistrationId(ctx, registrationID);
                    RequestDTO w = new RequestDTO(RequestDTO.SEND_GCM_REGISTRATION);
                    w.setRegistrationID(registrationID);
                    w.setZipResponse(false);
                    DataUtil.sendGCMRegistration(context, registrationID, new OKUtil.OKListener() {
                        @Override
                        public void onResponse(ResponseDTO response) {
                            if (response.getStatusCode() == 0) {
                                Log.w(LOG, "############ Device registered to Google on MONITOR PLATFORM server GCM regime");
                                GcmDeviceDTO gcmDevice = new GcmDeviceDTO();
                                gcmDevice.setManufacturer(Build.MANUFACTURER);
                                gcmDevice.setModel(Build.MODEL);
                                gcmDevice.setSerialNumber(Build.SERIAL);
                                gcmDevice.setAndroidVersion(Build.VERSION.RELEASE);
                                gcmDevice.setApp(ctx.getPackageName());
                                gcmDevice.setRegistrationID(registrationID);
                                SharedUtil.saveGCMDevice(ctx, gcmDevice);
                                weCool = true;
                                listener.onDeviceRegistered(registrationID);
                            }
                        }

                        @Override
                        public void onError(String message) {

                        }
                    });


                } catch (IOException e) {
                    listener.onGCMError();
                }
            }
        });


    }


}
