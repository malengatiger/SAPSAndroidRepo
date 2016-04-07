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
public class PanicTypeDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer panicTypeID;
    private String name;
    private List<PanicIncidentDTO> panicIncidentList;

    public PanicTypeDTO() {
    }


    public Integer getPanicTypeID() {
        return panicTypeID;
    }

    public void setPanicTypeID(Integer panicTypeID) {
        this.panicTypeID = panicTypeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PanicIncidentDTO> getPanicIncidentList() {
        return panicIncidentList;
    }

    public void setPanicIncidentList(List<PanicIncidentDTO> panicIncidentList) {
        this.panicIncidentList = panicIncidentList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (panicTypeID != null ? panicTypeID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PanicTypeDTO)) {
            return false;
        }
        PanicTypeDTO other = (PanicTypeDTO) object;
        if ((this.panicTypeID == null && other.panicTypeID != null) || (this.panicTypeID != null && !this.panicTypeID.equals(other.panicTypeID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.boha.vodacom.data.PanicType[ panicTypeID=" + panicTypeID + " ]";
    }
    
}
