/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.agenci.model;

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
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.TermVector;

/**
 *
 * @author Lukman Jaji <lukman@lukmanjaji.com>
 */
@Entity
@Indexed
@Table(name = "work_location")
public class WorkLocationModel implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    
    @Field(indexNullAs="_null_")
    @Column(name="location_title")
    String locationTitle;
    
    @Field(indexNullAs="_null_")
    @Column(name="location_address")
    String locationAddress;
    
    @Column(name="location_coordinates")
    String locationCoordinates;
    
    @Column(name="location_email")
    String locationEmail;
    
    @Column(name="location_phone")
    String locationPhone;
    
    @Column(name="agency_id")
    String agencyID;
    
    @Field(indexNullAs="_null_")
    @Column(name="location_focal_person")
    String locationFocalPerson;
    
    @Column(name = "created_at", updatable=false)
    @CreationTimestamp
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date createdAt;

    public String getAgencyID() {
        return agencyID;
    }

    public void setAgencyID(String agencyID) {
        this.agencyID = agencyID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocationTitle() {
        return locationTitle;
    }

    public void setLocationTitle(String locationTitle) {
        this.locationTitle = locationTitle;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

    public String getLocationCoordinates() {
        return locationCoordinates;
    }

    public void setLocationCoordinates(String locationCoordinates) {
        this.locationCoordinates = locationCoordinates;
    }

    public String getLocationEmail() {
        return locationEmail;
    }

    public void setLocationEmail(String locationEmail) {
        this.locationEmail = locationEmail;
    }

    public String getLocationPhone() {
        return locationPhone;
    }

    public void setLocationPhone(String locationPhone) {
        this.locationPhone = locationPhone;
    }

    public String getLocationFocalPerson() {
        return locationFocalPerson;
    }

    public void setLocationFocalPerson(String locationFocalPerson) {
        this.locationFocalPerson = locationFocalPerson;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    
    
    
}
