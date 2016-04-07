package com.boha.vodacom.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aubreymalabie on 4/6/16.
 */
public class SimpleMessageDTO {
    PanicIncidentDTO incident;
    String message;
    List<Integer> officerIDs, citizenIDs;
    List<String> registrationIDs = new ArrayList<>();

    public List<Integer> getOfficerIDs() {
        return officerIDs;
    }

    public void setOfficerIDs(List<Integer> officerIDs) {
        this.officerIDs = officerIDs;
    }

    public List<Integer> getCitizenIDs() {
        return citizenIDs;
    }

    public void setCitizenIDs(List<Integer> citizenIDs) {
        this.citizenIDs = citizenIDs;
    }

    public PanicIncidentDTO getIncident() {
        return incident;
    }

    public void setIncident(PanicIncidentDTO incident) {
        this.incident = incident;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getRegistrationIDs() {
        return registrationIDs;
    }

    public void setRegistrationIDs(List<String> registrationIDs) {
        this.registrationIDs = registrationIDs;
    }
}
