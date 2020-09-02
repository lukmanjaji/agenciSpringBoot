/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.agenci.repository;

import com.jajitech.agenci.model.WorkerModel;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Lukman Jaji Name <lukman@lukmanjaji.com>
 */
public interface WorkerRepository extends CrudRepository<WorkerModel, Long> {
    
    @Transactional
    @Modifying
    @Query(value = "update a_worker set s_photo = true where id = ?1", nativeQuery = true)
    void updatePhoto(String id);
    
    @Transactional
    @Modifying
    @Query(value = "delete from a_worker where id = ?1 and agency_id=?2", nativeQuery = true)
    void deleteWorker(String id, String agencyid);
    
    @Query(value = "SELECT * from a_worker where agency_id=?1 order by (id) desc", nativeQuery = true)
    List<WorkerModel> listAllWorkersForAgency(String agency_id);
    
    @Query(value = "SELECT * from a_worker where id=?1", nativeQuery = true)
    WorkerModel getWorkerInfo(String id);
    
    @Query(value = "SELECT id from a_worker where s_email=?1", nativeQuery = true)
    String verifyWorkerEmail(String email);
    
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM WorkerModel c WHERE c.workerEmail = :email")
    boolean existsByEmail(@Param("email") String email);
    
}
