/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.agenci.repository;

import com.jajitech.agenci.model.WorkerModel;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Lukman Jaji Name <lukman@lukmanjaji.com>
 */
public interface WorkerRepository extends CrudRepository<WorkerModel, Long> {
    
    
    
}
