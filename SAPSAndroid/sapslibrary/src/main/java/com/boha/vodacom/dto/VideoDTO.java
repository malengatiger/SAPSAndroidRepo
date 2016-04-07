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
public class VideoDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer videoID;
    private Long dateTaken;
    private Long dateUploaded;
    private String url, citizenName, officerName, incidentName;
    private Integer citizenID;
    private Integer officerID;
    private Integer panicIncidentID;
    public VideoDTO() {
    }


    public Long getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(Long dateTaken) {
        this.dateTaken = dateTaken;
    }

    public Long getDateUploaded() {
        return dateUploaded;
    }

    public void setDateUploaded(Long dateUploaded) {
        this.dateUploaded = dateUploaded;
    }

    public String getCitizenName() {
        return citizenName;
    }

    public void setCitizenName(String citizenName) {
        this.citizenName = citizenName;
    }

    public String getOfficerName() {
        return officerName;
    }

    public void setOfficerName(String officerName) {
        this.officerName = officerName;
    }

    public String getIncidentName() {
        return incidentName;
    }

    public void setIncidentName(String incidentName) {
        this.incidentName = incidentName;
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

    public Integer getPanicIncidentID() {
        return panicIncidentID;
    }

    public void setPanicIncidentID(Integer panicIncidentID) {
        this.panicIncidentID = panicIncidentID;
    }

    public Integer getVideoID() {
        return videoID;
    }

    public void setVideoID(Integer videoID) {
        this.videoID = videoID;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (videoID != null ? videoID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VideoDTO)) {
            return false;
        }
        VideoDTO other = (VideoDTO) object;
        if ((this.videoID == null && other.videoID != null) || (this.videoID != null && !this.videoID.equals(other.videoID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.boha.vodacom.data.Video[ videoID=" + videoID + " ]";
    }
    
}
