/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.vodacom.dto;

import java.io.Serializable;

/**
 * @author aubreymalabie
 */
public class RequestDTO implements Serializable {
    public RequestDTO(int requestType) {
        this.requestType = requestType;
    }

    public static final int
            REGISTER_CITIZEN = 1,
            REGISTER_OFFICER = 2,
            REGISTER_POLICE_STATION = 3,

    SIGN_IN_CITIZEN = 4,
            SIGN_IN_OFFICER = 5,
            ADD_INCIDENT = 6,
            RESPOND_INCIDENT = 7,

    GET_PANIC_TYPES = 10,
            FIND_POLICE_STATIONS_IN_RADIUS = 11,
            GET_INCIDENTS_BY_STATION = 12,
            GET_INCIDENTS_BY_OFFICER = 13,
            FIND_INCIDENTS_IN_RADIUS = 14,
            UPDATE_GCM_REGISTRATION = 15,
            SEND_GCM_REGISTRATION = 16,
            SEND_SIMPLE_MESSAGE = 17,

    LOAD_POLICE_STATIONS = 99;

    SimpleMessageDTO simpleMessage;
    CitizenDTO citizen;
    OfficerDTO officer;
    PanicTypeDTO panicType;
    PoliceStationDTO policeStation;
    Integer citizenID, officerID, policeStationID, panicTypeID;
    PanicIncidentDTO incident;
    GcmDeviceDTO gcmDevice;
    int requestType;
    int radius;
    double latitude, longitude;
    String email, password, registrationID;
    boolean zipResponse;

    public SimpleMessageDTO getSimpleMessage() {
        return simpleMessage;
    }

    public void setSimpleMessage(SimpleMessageDTO simpleMessage) {
        this.simpleMessage = simpleMessage;
    }

    public GcmDeviceDTO getGcmDevice() {
        return gcmDevice;
    }

    public void setGcmDevice(GcmDeviceDTO gcmDevice) {
        this.gcmDevice = gcmDevice;
    }

    public String getRegistrationID() {
        return registrationID;
    }

    public void setRegistrationID(String registrationID) {
        this.registrationID = registrationID;
    }

    public boolean isZipResponse() {
        return zipResponse;
    }

    public void setZipResponse(boolean zipResponse) {
        this.zipResponse = zipResponse;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getRadius() {
        if (radius == 0) {
            radius = 25;
        }
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public CitizenDTO getCitizen() {
        return citizen;
    }

    public void setCitizen(CitizenDTO citizen) {
        this.citizen = citizen;
    }

    public OfficerDTO getOfficer() {
        return officer;
    }

    public void setOfficer(OfficerDTO officer) {
        this.officer = officer;
    }

    public PanicTypeDTO getPanicType() {
        return panicType;
    }

    public void setPanicType(PanicTypeDTO panicType) {
        this.panicType = panicType;
    }

    public PoliceStationDTO getPoliceStation() {
        return policeStation;
    }

    public void setPoliceStation(PoliceStationDTO policeStation) {
        this.policeStation = policeStation;
    }


    public Integer getCitizenID() {
        return citizenID;
    }

    public void setCitizenID(Integer citizenID) {
        this.citizenID = citizenID;
    }

    public Integer getOfficerID() {
        return officerID;
    }

    public void setOfficerID(Integer officerID) {
        this.officerID = officerID;
    }

    public Integer getPoliceStationID() {
        return policeStationID;
    }

    public void setPoliceStationID(Integer policeStationID) {
        this.policeStationID = policeStationID;
    }

    public Integer getPanicTypeID() {
        return panicTypeID;
    }

    public void setPanicTypeID(Integer panicTypeID) {
        this.panicTypeID = panicTypeID;
    }

    public PanicIncidentDTO getIncident() {
        return incident;
    }

    public void setIncident(PanicIncidentDTO incident) {
        this.incident = incident;
    }

    public int getRequestType() {
        return requestType;
    }

    public void setRequestType(int requestType) {
        this.requestType = requestType;
    }


}
