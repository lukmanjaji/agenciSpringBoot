/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.agenci.repository;

import com.jajitech.agenci.model.VerifyWorkerForAgencyModel;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Lu0kman Jaji Name <lukman@lukmanjaji.com>
 * Verify code sent to new worker joining an agency
 */
public interface VerifyWorkerForAgencyRepository extends CrudRepository<VerifyWorkerForAgencyModel, Long> {
    
    @Query(value = "SELECT * from s_verify where v_code=?1 and s_code=?2 and ag_code=?3", nativeQuery = true)
    List<VerifyWorkerForAgencyModel> veryfiyWorkerCode(String v_code, String s_code, String ag_code);
    
    @Transactional
    @Modifying
    @Query(value = "update s_verify set s_verified = true where v_code=?1 and s_code=?2 and ag_code=?3", nativeQuery = true)
    void verifyWorker(String v_code, String s_code, String ag_code);
}
