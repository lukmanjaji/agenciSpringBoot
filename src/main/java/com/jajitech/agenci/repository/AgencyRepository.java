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
    
    @Transactional
    @Modifying
    @Query(value = "update agency set is_logo = true where id = ?1", nativeQuery = true)
    void updateLogo(String id);
    
}
