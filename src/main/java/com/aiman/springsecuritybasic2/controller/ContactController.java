package com.aiman.springsecuritybasic2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactController {

    @GetMapping("/contact")
    public String saveContactDetails(String input){
        return "Contact details saved";
    }
}
