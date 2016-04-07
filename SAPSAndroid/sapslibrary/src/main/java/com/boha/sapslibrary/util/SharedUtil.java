package com.boha.sapslibrary.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.util.Log;

import com.boha.vodacom.dto.CitizenDTO;
import com.boha.vodacom.dto.GcmDeviceDTO;
import com.boha.vodacom.dto.OfficerDTO;
import com.boha.vodacom.dto.PanicTypeDTO;
import com.boha.vodacom.dto.PhotoDTO;
import com.boha.vodacom.dto.ResponseDTO;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by aubreyM on 2014/10/12.
 */
public class SharedUtil {
    static final Gson gson = new Gson();
    public static final String
            COMPANY_STAFF_JSON = "companyStaff",
            TYPES = "types",
            GCMDEVICE = "gcmd",
            MONITOR_JSON = "monitor",
            PROJECT_ID = "projectID",
            LAST_MONITOR_ID = "lastMonID",
            LAST_STAFF_ID = "lastStaffID",
            GCM_REGISTRATION_ID = "gcm",
            SESSION_ID = "sessionID",
            SITE_LOCATION = "siteLocation",
            DRAWER = "drawer",
            THEME = "theme",
            CREDIT_CARD = "credCard",
            TRACK = "track",
            PHOTO = "photo",
            AUTH_TOKEN = "token",
            LOG = "SharedUtil",
            REMINDER_TIME = "reminderTime",
            APP_VERSION = "appVersion";

    public static void setThemeSelection(Context ctx, int theme) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(ctx);

        SharedPreferences.Editor ed = sp.edit();
        ed.putInt(THEME, theme);
        ed.commit();

        Log.w(LOG, "#### theme saved: " + theme);

    }

    public static int getThemeSelection(Context ctx) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        int j = sp.getInt(THEME, -1);
        Log.i(LOG, "#### theme retrieved: " + j);
        return j;
    }

    public static void setDrawerCount(Context ctx, int count) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor ed = sp.edit();
        ed.putInt(DRAWER, count);
        ed.commit();
    }

    public static int getDrawerCount(Context context) {
        final SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        int count = prefs.getInt(DRAWER, 0);

        return count;
    }

    public static final int MAX_SLIDING_TAB_VIEWS = 1000;

    /**
     * Stores the registration ID and app versionCode in the application's
     * {@code SharedPreferences}.
     *
     * @param context application's monApp.
     * @param regId   registration ID
     */
    public static void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        int appVersion = getAppVersion(context);
        Log.i(LOG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(GCM_REGISTRATION_ID, regId);
        editor.putInt(APP_VERSION, appVersion);
        editor.commit();
        Log.e(LOG, "GCM registrationId saved in prefs! Yebo!!!");
    }

    /**
     * Gets the current registration ID for application on GCM service.
     * <p/>
     * If result is empty, the app needs to register.
     *
     * @return registration ID, or empty string if there is no existing
     * registration ID.
     */
    public static String getRegistrationId(Context context) {
        final SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        String registrationId = prefs.getString(GCM_REGISTRATION_ID, null);
        if (registrationId == null) {
            Log.i(LOG, "GCM Registration ID not found on device.");
            return null;
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        int registeredVersion = prefs.getInt(APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = SharedUtil.getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(LOG, "App version changed.");
            return null;
        }
        return registrationId;
    }

    public static void saveReminderTime(Context ctx, Date date) {

        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor ed = sp.edit();
        ed.putLong(REMINDER_TIME, date.getTime());
        ed.commit();
        Log.e("SharedUtil", "%%%%% reminderTime: " + date + " saved in SharedPreferences");
    }

    public static Date getReminderTime(Context ctx) {

        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        long t = sp.getLong(REMINDER_TIME, 0);
        if (t == 0) {
            Calendar cal = GregorianCalendar.getInstance();
            cal.roll(Calendar.DAY_OF_YEAR, false);
            return cal.getTime();
        }
        return new Date(t);
    }

    public static void saveSessionID(Context ctx, String sessionID) {

        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString(SESSION_ID, sessionID);
        ed.commit();
        Log.e("SharedUtil", "%%%%% SessionID: " + sessionID + " saved in SharedPreferences");
    }


    public static String getSessionID(Context ctx) {

        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        return sp.getString(SESSION_ID, null);
    }

    public static void savePanicTypes(Context ctx, List<PanicTypeDTO> list) {

        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        ResponseDTO w = new ResponseDTO();
        w.setPanicTypes(list);
        String x = gson.toJson(w);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString(TYPES, x);
        ed.commit();
        Log.e("SharedUtil", "%%%%% Panic Types saved: ");
    }


    public static List<PanicTypeDTO> getPanicTypes(Context ctx) {

        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        String mon = sp.getString(TYPES, null);
        ResponseDTO w;
        if (mon != null) {
            w = gson.fromJson(mon, ResponseDTO.class);
            return w.getPanicTypes();
        }
        return null;
    }
    public static void saveCitizen(Context ctx, CitizenDTO dto) {

        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        String x = gson.toJson(dto);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString(MONITOR_JSON, x);
        ed.commit();
        Log.e("SharedUtil", "%%%%% Citizen saved: " + dto.getName());
    }


    public static CitizenDTO getCitizen(Context ctx) {

        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        String mon = sp.getString(MONITOR_JSON, null);
        CitizenDTO monitorDTO = null;
        if (mon != null) {
            monitorDTO = gson.fromJson(mon, CitizenDTO.class);

        }
        return monitorDTO;
    }

    public static void saveOfficer(Context ctx, OfficerDTO dto) {

        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        String x = gson.toJson(dto);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString(COMPANY_STAFF_JSON, x);
        ed.commit();
        Log.e("SharedUtil", "%%%%% Officer saved: " + dto.getName());
    }


    public static OfficerDTO getOfficer(Context ctx) {

        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        String adm = sp.getString(COMPANY_STAFF_JSON, null);
        OfficerDTO golfGroup = null;
        if (adm != null) {
            golfGroup = gson.fromJson(adm, OfficerDTO.class);

        }
        return golfGroup;
    }

        public static void saveGCMDevice(Context ctx, GcmDeviceDTO dto) {


        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        String x = gson.toJson(dto);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString(GCMDEVICE, x);
        ed.commit();
        System.out.println("%%%%% Device saved in SharedPreferences");
    }


    public static GcmDeviceDTO getGCMDevice(Context ctx) {

        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        String adm = sp.getString(GCMDEVICE, null);
        GcmDeviceDTO co = null;
        if (adm != null) {
            co = gson.fromJson(adm, GcmDeviceDTO.class);

        }
        if (co != null)
            Log.e("SharedUtil", "%%%%% Device found in SharedPreferences: " + co.getModel());
        return co;
    }

    public static void savePhoto(Context ctx, PhotoDTO dto) {


        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        String x = gson.toJson(dto);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString(PHOTO, x);
        ed.commit();
        Log.i("SharedUtil", "%%%%% Photo saved in SharedPreferences");
    }


    public static PhotoDTO getPhoto(Context ctx) {

        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        String adm = sp.getString(PHOTO, null);
        PhotoDTO co = null;
        if (adm != null) {
            co = gson.fromJson(adm, PhotoDTO.class);

        }
        return co;
    }


    /**
     * @return Application's version code from the {@code PackageManager}.
     */
    public static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }


    public static void saveLastProjectID(Context ctx, Integer projectID) {

        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor ed = sp.edit();
        ed.putInt(PROJECT_ID, projectID);
        ed.commit();
        Log.e("SharedUtil", "%%%%% projectID: " + projectID + " saved in SharedPreferences");
    }

    public static Integer getLastProjectID(Context ctx) {
        if (ctx == null) {
            return 0;
        }
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        int id = sp.getInt(PROJECT_ID, 0);
        return id;
    }

    public static void saveLastStaffID(Context ctx, Integer staffID) {

        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor ed = sp.edit();
        ed.putInt(LAST_STAFF_ID, staffID);
        ed.commit();
        Log.e("SharedUtil", "%%%%% staffID: " + staffID + " saved in SharedPreferences");
    }

    public static Integer getLastStaffID(Context ctx) {

        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        int id = sp.getInt(LAST_STAFF_ID, 0);
        return id;
    }
    public static void saveLastMonitorID(Context ctx, Integer monitorID) {

        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor ed = sp.edit();
        ed.putInt(LAST_MONITOR_ID, monitorID);
        ed.commit();
        Log.e("SharedUtil", "%%%%% monitorID " + monitorID + " saved in SharedPreferences");
    }

    public static Integer getLastMonitorID(Context ctx) {

        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        int id = sp.getInt(LAST_MONITOR_ID, 0);
        return id;
    }


    public static void saveAuthToken(Context ctx, String token) {

        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString(AUTH_TOKEN, token);
        ed.commit();
        Log.e("SharedUtil", "%%%%% auth-token: " + token + " saved in SharedPreferences");
    }

    public static String getAuthToken(Context ctx) {

        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        String id = sp.getString(AUTH_TOKEN, null);
        return id;
    }
}
