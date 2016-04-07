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
public class CitizenDTO implements Serializable {
    private List<PanicIncidentDTO> panicIncidentList;
    private static final long serialVersionUID = 1L;
    private Integer citizenID, gender;
    private String name;
    private String email, password;
    private String cellphone;
    private Long dateRegistered;
    private Double latitude;
    private Double longitude;
    private List<PhotoDTO> photoList;
    private List<GcmDeviceDTO> deviceList;
    private List<VideoDTO> videoList;

    public CitizenDTO() {
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public List<PhotoDTO> getPhotoList() {
        if (photoList == null) {
            photoList = new ArrayList<>();
        }
        return photoList;
    }

    public void setPhotoList(List<PhotoDTO> photoList) {
        this.photoList = photoList;
    }

    public List<GcmDeviceDTO> getDeviceList() {
        if (deviceList == null) {
            deviceList = new ArrayList<>();
        }
        return deviceList;
    }

    public void setDeviceList(List<GcmDeviceDTO> deviceList) {
        this.deviceList = deviceList;
    }

    public List<VideoDTO> getVideoList() {
        if (videoList == null) {
            videoList = new ArrayList<>();
        }
        return videoList;
    }

    public void setVideoList(List<VideoDTO> videoList) {
        this.videoList = videoList;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

   
    public Integer getCitizenID() {
        return citizenID;
    }

    public void setCitizenID(Integer citizenID) {
        this.citizenID = citizenID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Long getDateRegistered() {
        return dateRegistered;
    }

    public void setDateRegistered(Long dateRegistered) {
        this.dateRegistered = dateRegistered;
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
        hash += (citizenID != null ? citizenID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CitizenDTO)) {
            return false;
        }
        CitizenDTO other = (CitizenDTO) object;
        if ((this.citizenID == null && other.citizenID != null) || (this.citizenID != null && !this.citizenID.equals(other.citizenID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.boha.vodacom.data.Citizen[ citizenID=" + citizenID + " ]";
    }

    public List<PanicIncidentDTO> getPanicIncidentList() {
        return panicIncidentList;
    }

    public void setPanicIncidentList(List<PanicIncidentDTO> panicIncidentList) {
        this.panicIncidentList = panicIncidentList;
    }
    
}
