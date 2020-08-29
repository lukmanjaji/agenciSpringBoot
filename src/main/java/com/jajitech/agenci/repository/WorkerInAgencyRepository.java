/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.agenci.repository;

import com.jajitech.agenci.model.WorkerInAgencyModel;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Lukman Jaji <lukman@lukmanjaji.com>
 */
public interface WorkerInAgencyRepository extends CrudRepository<WorkerInAgencyModel, Long>{
    
    @Query(value = "SELECT * from sn_agency where agency_id = ?1 order by (id) desc", nativeQuery = true)
    List<WorkerInAgencyModel> getAllWorkersInAgency(String agency_id);
    
    @Query(value = "SELECT * from sn_agency order by (id) desc", nativeQuery = true)
    List<WorkerInAgencyModel> getAllWorkers();
    
}
