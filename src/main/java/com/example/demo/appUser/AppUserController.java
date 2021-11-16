package com.example.demo.appUser;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

//User Controller
@RestController
@RequestMapping(path = "api/v1/user")
public class AppUserController {

    private final AppUserRepository appUserRepository;

    public AppUserController(AppUserRepository appUserRepository)  {
        this.appUserRepository = appUserRepository;
    }


    @GetMapping
    public List<AppUser> getAppUsers()
    {
        return appUserRepository.findAll();
    }

    @GetMapping("/{userId}")
    public AppUser getUserById(@PathVariable Long userId){
        return appUserRepository.findById(userId).get();
    }

    @PostMapping("/login/")
    public AppUser login(@RequestBody LoginUser user)
    {
        AppUser appUser = appUserRepository.findByUsername(user.getUsername()).get();

        if(!appUser.getPassword().equals(user.getPassword()) ||
                !appUser.getUsername().equals(user.getUsername())
        )
        {
            throw new IllegalStateException("Invalid Username or Password");
        }

        return appUser;

    }

    @PostMapping
    public AppUser registerNewAppUser(@RequestBody AppUser appUser)
    {
        Optional<AppUser> appUserByEmail = appUserRepository.findByEmail(appUser.getEmail());
        Optional<AppUser> appUserByUsername = appUserRepository.findByUsername(appUser.getUsername());

        if (appUserByEmail.isPresent()){
            throw new IllegalStateException("email already exists");
        }else if(appUserByUsername.isPresent())
        {
            throw new IllegalStateException("username already exists");
        }

        appUserRepository.save(appUser);
        return appUser;
    }


}
