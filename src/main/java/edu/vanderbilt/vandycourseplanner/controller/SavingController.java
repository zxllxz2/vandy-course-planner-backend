package edu.vanderbilt.vandycourseplanner.controller;


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

    @PostMapping("/")
    public RespBean addOneSaving(@NotNull @RequestParam String email,
                                 @RequestParam(defaultValue = "CS") String subject,
                                 @NotNull @RequestParam Integer number) {
        return savingService.addOneSaving(email, subject, number);
    }

    @CrossOrigin
    @DeleteMapping("/")
    public RespBean deleteOneSaving(@NotNull @RequestParam String email,
                                    @RequestParam(defaultValue = "CS") String subject,
                                    @NotNull @RequestParam Integer number) {
        return savingService.deleteOneSaving(email, subject, number);
    }

}
