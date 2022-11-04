package edu.vanderbilt.vandycourseplanner.controller;


import edu.vanderbilt.vandycourseplanner.pojo.RespBean;
import edu.vanderbilt.vandycourseplanner.service.ISavingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Toby Zhu
 * @since 2022-11-04
 */
@RestController
@RequestMapping("/savings")
public class SavingController {

    @Autowired
    private ISavingService savingService;

    @PostMapping("/")
    public RespBean addOneSaving(String email, String subject, int number) {
        return savingService.addOneSaving(email, subject, number);
    }

}
