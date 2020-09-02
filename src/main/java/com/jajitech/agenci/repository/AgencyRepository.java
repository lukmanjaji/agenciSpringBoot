/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.agenci.repository;

import com.jajitech.agenci.model.AgencyModel;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Lukman Jaji <lukman@lukmanjaji.com>
 */
@Component
public interface AgencyRepository extends CrudRepository<AgencyModel, Long> {
    
    @Query(value = "SELECT * FROM agency where a_name LIKE %:keyword% order by (id) desc", nativeQuery = true)
    List<AgencyModel> search(@Param("keyword") String keyword);
    
    @Query(value = "SELECT * from agency order by (id) desc", nativeQuery = true)
    List<AgencyModel> listAllAgencies();
    
    @Query(value = "SELECT * from agency where id=?1 order by (id) desc", nativeQuery = true)
    AgencyModel getAgencyInfo(String id);
    
    @Transactional
    @Modifying
    @Query(value = "update agency set is_logo = true where id = ?1", nativeQuery = true)
    void updateLogo(String id);
    
    @Transactional
    @Modifying
    @Query(value = "update agency set p_u = ?2 where id = ?3 and email = ?1", nativeQuery = true)
    void u_p(String u, String p, String id);
    
    @Query(value = "SELECT p_u from agency where id=?1 and email=?2", nativeQuery = true)
    String getOp(String agency_id, String u_p);
    
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM AgencyModel c WHERE c.agencyEmail = :email")
    boolean existsByEmail(@Param("email") String email);
    
}
