package qwerdsa53.trackmyfinance.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//LoginTest
@RestController
@RequestMapping("/api/v1/home")
public class HomeController {
    @GetMapping
    public String sayHome(){
        return "home";
    }
}
