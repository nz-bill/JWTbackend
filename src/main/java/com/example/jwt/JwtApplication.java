package com.example.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

@RestController
@SpringBootApplication
public class JwtApplication {

    @Autowired
    CustomerService customerService;

    @Autowired
    HttpServletRequest request;

    public static JWTHandler jwtHandler;

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(JwtApplication.class, args);
        CustomerService customerSerevice = context.getBean(CustomerService.class);

        jwtHandler = new JWTHandler();

    }
    @GetMapping("/authorize")
    public String authenticateCustomer(@RequestParam(value="name") String name, @RequestParam(value="mail") String mail){
        Customer c = customerService.getCustomerByName(name);
        if(c.getEmail().equals(mail)){
            String token = jwtHandler.generateToken(c);
            return token;
        }
        else return "wrong password";
    }

    @GetMapping("/hello")
    public String hello(){
        String token = extractToken();
        String subject = jwtHandler.validateToken(token);
        if(subject.equals("invalid token")){
            return "please log in again";
        }
        //metodanrop etc
        return "General Kenobi!";

    }


    @GetMapping("/checktoken")
    public String checkToken(@RequestParam(value= "token") String token){
        return jwtHandler.validateToken(token);
    }

    private String extractToken(){
       String bearer = request.getHeader("Autorization");
        String onlyToken = bearer.substring(6);
        return onlyToken;
    }


}
