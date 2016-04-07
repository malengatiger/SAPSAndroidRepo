/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.vodacom.dto;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author aubreymalabie
 */
public class CityDTO implements Serializable {
    private List<PoliceStationDTO> policeStationList;
    private static final long serialVersionUID = 1L;
    private Integer cityID;
    private String name;
    private Double latitude;
    private Double longitude;

    public CityDTO() {
    }


    public Integer getCityID() {
        return cityID;
    }

    public void setCityID(Integer cityID) {
        this.cityID = cityID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cityID != null ? cityID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CityDTO)) {
            return false;
        }
        CityDTO other = (CityDTO) object;
        if ((this.cityID == null && other.cityID != null) || (this.cityID != null && !this.cityID.equals(other.cityID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.boha.vodacom.data.City[ cityID=" + cityID + " ]";
    }

    public List<PoliceStationDTO> getPoliceStationList() {
        return policeStationList;
    }

    public void setPoliceStationList(List<PoliceStationDTO> policeStationList) {
        this.policeStationList = policeStationList;
    }
    
}
