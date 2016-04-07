/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.vodacom.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aubreymalabie
 */
public class PoliceStationDTO implements Serializable {
    private Integer cityID;
    private static final long serialVersionUID = 1L;
    private Integer policeStationID;
    private String name;
    private String address;
    private Double latitude;
    private Double longitude, distance;
    private String email;
    private String cellphone;
    private List<OfficerDTO> officerList;
    private List<PanicIncidentDTO> panicIncidentList;

    public PoliceStationDTO() {
    }

    public Integer getPoliceStationID() {
        return policeStationID;
    }

    public void setPoliceStationID(Integer policeStationID) {
        this.policeStationID = policeStationID;
    }

    public List<PanicIncidentDTO> getPanicIncidentList() {
        if (panicIncidentList == null) {
            panicIncidentList = new ArrayList<>();
        }
        return panicIncidentList;
    }

    public void setPanicIncidentList(List<PanicIncidentDTO> panicIncidentList) {
        this.panicIncidentList = panicIncidentList;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public List<OfficerDTO> getOfficerList() {
        if (officerList == null) {
            officerList = new ArrayList<>();
        }
        return officerList;
    }

    public void setOfficerList(List<OfficerDTO> officerList) {
        this.officerList = officerList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (policeStationID != null ? policeStationID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PoliceStationDTO)) {
            return false;
        }
        PoliceStationDTO other = (PoliceStationDTO) object;
        if ((this.policeStationID == null && other.policeStationID != null) || (this.policeStationID != null && !this.policeStationID.equals(other.policeStationID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.boha.vodacom.data.PoliceStation[ policeStationID=" + policeStationID + " ]";
    }

    public Integer getCityID() {
        return cityID;
    }

    public void setCityID(Integer cityID) {
        this.cityID = cityID;
    }

}
