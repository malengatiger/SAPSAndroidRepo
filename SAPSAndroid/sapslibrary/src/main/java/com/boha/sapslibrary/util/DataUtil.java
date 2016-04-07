package com.boha.sapslibrary.util;

import android.content.Context;

import com.boha.vodacom.dto.CitizenDTO;
import com.boha.vodacom.dto.GcmDeviceDTO;
import com.boha.vodacom.dto.OfficerDTO;
import com.boha.vodacom.dto.PanicIncidentDTO;
import com.boha.vodacom.dto.RequestDTO;
import com.boha.vodacom.dto.SimpleMessageDTO;

/**
 * Created by aubreymalabie on 4/6/16.
 */
public class DataUtil {

    public static void findIncidentsByRadius(Context ctx, double latitude,double longitude, int radius, OKUtil.OKListener listener) {
        RequestDTO w = new RequestDTO(RequestDTO.FIND_INCIDENTS_IN_RADIUS);
        w.setRadius(radius);
        w.setLatitude(latitude);
        w.setLongitude(longitude);

        OKUtil util = new OKUtil();
        try {
            util.sendGETRequest(ctx,w,null,listener);
        } catch (OKHttpException e) {
            e.printStackTrace();
        }
    }
    public static void findPoliceStationsByRadius(Context ctx, double latitude,double longitude, int radius, OKUtil.OKListener listener) {
        RequestDTO w = new RequestDTO(RequestDTO.FIND_POLICE_STATIONS_IN_RADIUS);
        w.setRadius(radius);
        w.setLatitude(latitude);
        w.setLongitude(longitude);

        OKUtil util = new OKUtil();
        try {
            util.sendGETRequest(ctx,w,null,listener);
        } catch (OKHttpException e) {
            e.printStackTrace();
        }
    }
    public static void updateGCMRegistration(Context ctx, GcmDeviceDTO device, OKUtil.OKListener listener) {
        RequestDTO w = new RequestDTO(RequestDTO.UPDATE_GCM_REGISTRATION);
        w.setGcmDevice(device);

        OKUtil util = new OKUtil();
        try {
            util.sendGETRequest(ctx,w,null,listener);
        } catch (OKHttpException e) {
            e.printStackTrace();
        }
    }
    public static void sendGCMRegistration(Context ctx, String registrationID, OKUtil.OKListener listener) {
        RequestDTO w = new RequestDTO(RequestDTO.SEND_GCM_REGISTRATION);
        w.setRegistrationID(registrationID);

        OKUtil util = new OKUtil();
        try {
            util.sendGETRequest(ctx,w,null,listener);
        } catch (OKHttpException e) {
            e.printStackTrace();
        }
    }
    public static void sendSimpleMessage(Context ctx, SimpleMessageDTO msg, OKUtil.OKListener listener) {
        RequestDTO w = new RequestDTO(RequestDTO.SEND_SIMPLE_MESSAGE);
        w.setSimpleMessage(msg);

        OKUtil util = new OKUtil();
        try {
            util.sendGETRequest(ctx,w,null,listener);
        } catch (OKHttpException e) {
            e.printStackTrace();
        }
    }
    public static void sendIncident(Context ctx, PanicIncidentDTO incident, OKUtil.OKListener listener) {
        RequestDTO w = new RequestDTO(RequestDTO.ADD_INCIDENT);
        w.setIncident(incident);

        OKUtil util = new OKUtil();
        try {
            util.sendGETRequest(ctx,w,null,listener);
        } catch (OKHttpException e) {
            e.printStackTrace();
        }
    }
    public static void signinCitizen(Context ctx, String email,String password, GcmDeviceDTO device, OKUtil.OKListener listener) {
        RequestDTO w = new RequestDTO(RequestDTO.SIGN_IN_CITIZEN);
        w.setEmail(email);
        w.setPassword(password);
        w.setGcmDevice(device);

        OKUtil util = new OKUtil();
        try {
            util.sendGETRequest(ctx,w,null,listener);
        } catch (OKHttpException e) {
            e.printStackTrace();
        }
    }
    public static void signinOfficer(Context ctx, String email,String password, GcmDeviceDTO device,OKUtil.OKListener listener) {
        RequestDTO w = new RequestDTO(RequestDTO.SIGN_IN_OFFICER);
        w.setEmail(email);
        w.setPassword(password);
        w.setGcmDevice(device);

        OKUtil util = new OKUtil();
        try {
            util.sendGETRequest(ctx,w,null,listener);
        } catch (OKHttpException e) {
            e.printStackTrace();
        }
    }
    public static void registerCitizen(Context ctx, CitizenDTO c, OKUtil.OKListener listener) {
        RequestDTO w = new RequestDTO(RequestDTO.REGISTER_CITIZEN);
        w.setCitizen(c);

        OKUtil util = new OKUtil();
        try {
            util.sendGETRequest(ctx,w,null,listener);
        } catch (OKHttpException e) {
            e.printStackTrace();
        }
    }
    public static void registerOfficer(Context ctx, OfficerDTO c, OKUtil.OKListener listener) {
        RequestDTO w = new RequestDTO(RequestDTO.REGISTER_OFFICER);
        w.setOfficer(c);

        OKUtil util = new OKUtil();
        try {
            util.sendGETRequest(ctx,w,null,listener);
        } catch (OKHttpException e) {
            e.printStackTrace();
        }
    }
}
