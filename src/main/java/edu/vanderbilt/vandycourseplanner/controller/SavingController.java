package edu.vanderbilt.vandycourseplanner.controller;


import edu.vanderbilt.vandycourseplanner.domain.SavingRequest;
import edu.vanderbilt.vandycourseplanner.pojo.RespBean;
import edu.vanderbilt.vandycourseplanner.service.ISavingService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/")
    public RespBean getSavingByUser(@RequestParam String email) {
        return RespBean.success(null, savingService.getSavingByUser(email));
    }

    @PostMapping("/add")
    public RespBean addOneSaving(@RequestBody SavingRequest request) {
        return savingService.addOneSaving(request.getEmail(), request.getSubject(),
                request.getNumber());
    }

    @CrossOrigin
    @PostMapping("/delete")
    public RespBean deleteOneSaving(@RequestBody SavingRequest request) {
        return savingService.deleteOneSaving(request.getEmail(), request.getSubject(),
                request.getNumber());
    }

}
