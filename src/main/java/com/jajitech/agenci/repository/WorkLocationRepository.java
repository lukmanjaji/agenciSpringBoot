/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.agenci.repository;

import com.jajitech.agenci.model.WorkLocationModel;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Lukman Jaji <lukman@lukmanjaji.com>
 */
public interface WorkLocationRepository extends CrudRepository<WorkLocationModel, Long> {
    
    @Query(value = "SELECT * FROM work_location where email LIKE %:keyword% or "
            + "address LIKE %:keyword% order by (id) desc", nativeQuery = true)
    List<WorkLocationModel> search(@Param("keyword") String keyword);
    
    
}
