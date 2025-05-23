package com.example.schedulemanagementappupgrade.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SomeController {

    @GetMapping("/my-info")
    public String getMyInfo(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            return "Hello, " + userDetails.getUsername();
        }
        return "User not authenticated";
    }
}
