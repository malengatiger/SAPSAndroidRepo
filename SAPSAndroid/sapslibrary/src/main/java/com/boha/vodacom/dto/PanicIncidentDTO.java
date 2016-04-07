/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.vodacom.dto;

import java.io.Serializable;

/**
 *
 * @author aubreymalabie
 */
public class PanicIncidentDTO implements Serializable {
    private Integer officerID;
    private Integer citizenID, policeStationID;
    private String officerName, citizenName, policeStationName;
    private static final long serialVersionUID = 1L;
    private Integer panicIncidentID;
    private Long dateSent;
    private Double latitude,longitude, distance;
    private Long responseDate;
    private PanicTypeDTO panicType;

    public PanicIncidentDTO() {
    }

    public Integer getPoliceStationID() {
        return policeStationID;
    }

    public void setPoliceStationID(Integer policeStationID) {
        this.policeStationID = policeStationID;
    }

    public String getPoliceStationName() {
        return policeStationName;
    }

    public void setPoliceStationName(String policeStationName) {
        this.policeStationName = policeStationName;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getOfficerID() {
        return officerID;
    }

    public void setOfficerID(Integer officerID) {
        this.officerID = officerID;
    }

    public Integer getCitizenID() {
        return citizenID;
    }

    public void setCitizenID(Integer citizenID) {
        this.citizenID = citizenID;
    }

    public String getOfficerName() {
        return officerName;
    }

    public void setOfficerName(String officerName) {
        this.officerName = officerName;
    }

    public String getCitizenName() {
        return citizenName;
    }

    public void setCitizenName(String citizenName) {
        this.citizenName = citizenName;
    }

    public Long getDateSent() {
        return dateSent;
    }

    public void setDateSent(Long dateSent) {
        this.dateSent = dateSent;
    }

    public Long getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(Long responseDate) {
        this.responseDate = responseDate;
    }

   

    public Integer getPanicIncidentID() {
        return panicIncidentID;
    }

    public void setPanicIncidentID(Integer panicIncidentID) {
        this.panicIncidentID = panicIncidentID;
    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (panicIncidentID != null ? panicIncidentID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PanicIncidentDTO)) {
            return false;
        }
        PanicIncidentDTO other = (PanicIncidentDTO) object;
        if ((this.panicIncidentID == null && other.panicIncidentID != null) || (this.panicIncidentID != null && !this.panicIncidentID.equals(other.panicIncidentID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.boha.vodacom.data.PanicIncident[ panicIncidentID=" + panicIncidentID + " ]";
    }

    public PanicTypeDTO getPanicType() {
        return panicType;
    }

    public void setPanicType(PanicTypeDTO panicType) {
        this.panicType = panicType;
    }

    
    
}
