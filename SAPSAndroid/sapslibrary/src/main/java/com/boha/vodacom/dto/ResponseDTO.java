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
public class ResponseDTO implements Serializable {
    int statusCode;
    String message;
    List<CitizenDTO> citizens;
    List<CityDTO> cities;
    List<GcmDeviceDTO> devices;
    List<OfficerDTO> officers;
    List<PanicTypeDTO> panicTypes;
    List<PanicIncidentDTO> incidents;
    List<PoliceStationDTO> policeStations;
    List<PhotoDTO> photos;
    List<VideoDTO> videos;
    double elapsedSeconds;

    public double getElapsedSeconds() {
        return elapsedSeconds;
    }

    public void setElapsedSeconds(double elapsedSeconds) {
        this.elapsedSeconds = elapsedSeconds;
    }


    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<PhotoDTO> getPhotos() {
        if (photos == null) {
            photos = new ArrayList<>();
        }
        return photos;
    }

    public void setPhotos(List<PhotoDTO> photos) {
        this.photos = photos;
    }

    public List<VideoDTO> getVideos() {
        if (videos == null) {
            videos = new ArrayList<>();
        }
        return videos;
    }

    public void setVideos(List<VideoDTO> videos) {
        this.videos = videos;
    }

    public List<CitizenDTO> getCitizens() {
        if (citizens == null) {
            citizens = new ArrayList<>();
        }
        return citizens;
    }

    public void setCitizens(List<CitizenDTO> citizens) {
        this.citizens = citizens;
    }

    public List<CityDTO> getCities() {
        if (cities == null) {
            cities = new ArrayList<>();
        }
        return cities;
    }

    public void setCities(List<CityDTO> cities) {
        this.cities = cities;
    }

    public List<GcmDeviceDTO> getDevices() {
        if (devices == null) {
            devices = new ArrayList<>();
        }
        return devices;
    }

    public void setDevices(List<GcmDeviceDTO> devices) {
        this.devices = devices;
    }

    public List<OfficerDTO> getOfficers() {
        if (officers == null) {
            officers = new ArrayList<>();
        }
        return officers;
    }

    public void setOfficers(List<OfficerDTO> officers) {
        this.officers = officers;
    }

    public List<PanicTypeDTO> getPanicTypes() {
        if (panicTypes == null) {
            panicTypes = new ArrayList<>();
        }
        return panicTypes;
    }

    public void setPanicTypes(List<PanicTypeDTO> panicTypes) {
        this.panicTypes = panicTypes;
    }

    public List<PanicIncidentDTO> getIncidents() {
        if (incidents == null) {
            incidents = new ArrayList<>();
        }
        return incidents;
    }

    public void setIncidents(List<PanicIncidentDTO> incidents) {
        this.incidents = incidents;
    }

    public List<PoliceStationDTO> getPoliceStations() {
        if (policeStations == null) {
            policeStations = new ArrayList<>();
        }
        return policeStations;
    }

    public void setPoliceStations(List<PoliceStationDTO> policeStations) {
        this.policeStations = policeStations;
    }
    
    
}
