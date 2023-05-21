package com.Geekster.ExpenseTrackerAPI.Controller;

import com.Geekster.ExpenseTrackerAPI.DTO.SignInRequest;
import com.Geekster.ExpenseTrackerAPI.Model.User;
import com.Geekster.ExpenseTrackerAPI.Service.AuthenticationService;
import com.Geekster.ExpenseTrackerAPI.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/home")
    public String welcomePage(){
        return "Welcome to Expense Tracker Application..!!";
    }


    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody User user){
        return userService.signUp(user);
    }

    @PostMapping("/signin")
    public ResponseEntity<String> signIn(@RequestBody SignInRequest signInRequest){
        return userService.signIn(signInRequest);
    }

    @DeleteMapping("/signout")
    public ResponseEntity<String> signOut(@RequestParam String email , @RequestParam String token){
        HttpStatus status;
        String msg=null;
        if(authenticationService.authenticate(email,token)) {
            authenticationService.deleteToken(token);
            msg = "logged out Successfully!!";
            status = HttpStatus.OK;
        }else {
            msg = "Invalid User";
            status = HttpStatus.FORBIDDEN;
        }
        return new ResponseEntity<>(msg , status);
    }
}
