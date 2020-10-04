/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.agenci.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.hibernate.annotations.CreationTimestamp;

/**
 *
 * @author Lukman Jaji <lukman@lukmanjaji.com>
 */
@Entity
@Table(name="s_shift")
public class ShiftModel implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    
    @Column(name = "created_at", updatable=false)
    @CreationTimestamp
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    Date createdAt;
    
    @Column(name = "agency_id")
    String agency_id;
    
    @JsonFormat(pattern="dd-MM-yyyy kk:mm")
    @Column(name = "shift_date_time")
    Date shiftDateTime;
    
    @Column(name = "shift_address")
    String address;
    
    @Column(name = "shift_contact_person")
    String contactPerson;
    
    @Column(name = "shift_coord")
    String shiftCoordinates;
    
    @Column(name = "shift_status")
    String status;
    
    @Column(name = "shift_remark")
    String remark;

    public String getAgency_id() {
        return agency_id;
    }

    public void setAgency_id(String agency_id) {
        this.agency_id = agency_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getShiftDateTime() {
        return shiftDateTime;
    }

    public void setShiftDateTime(Date shiftDateTime) {
        this.shiftDateTime = shiftDateTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getShiftCoordinates() {
        return shiftCoordinates;
    }

    public void setShiftCoordinates(String shiftCoordinates) {
        this.shiftCoordinates = shiftCoordinates;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}