/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.agenci.repository;

import com.jajitech.agenci.model.ShiftModel;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Lukman Jaji <lukman@lukmanjaji.com>
 */
public interface ShiftRepository extends CrudRepository<ShiftModel, Long> {
    
    @Query(value = "SELECT * from s_shift order by (id) desc", nativeQuery = true)
    List<ShiftModel> listAllShifts();
    
    @Query(value = "SELECT * from s_shift where id=?1 order by (id) desc", nativeQuery = true)
    ShiftModel getShiftInfo(String id);
    
    @Query(value = "SELECT * from s_shift where agency_id order by (id) desc", nativeQuery = true)
    List<ShiftModel> getAgencyShifts(String id);
    
}
