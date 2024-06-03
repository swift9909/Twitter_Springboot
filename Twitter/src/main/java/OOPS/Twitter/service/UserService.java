package OOPS.Twitter.service;

import OOPS.Twitter.UserDetails;
import OOPS.Twitter.dao.User_repo;
import OOPS.Twitter.model.User_Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private User_repo userRepo;

    public ResponseEntity<String> login(String email, String password) {
        Optional<User_Entity> optionalUser = userRepo.findByEmail(email);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User does not exist");
        }
        User_Entity user = optionalUser.get();
        if (!user.getPassword().equals(password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username/Password Incorrect");
        }
        return ResponseEntity.ok("Login Successful");
    }

    public ResponseEntity<? > signup(String email, String name, String password) {
        Optional<User_Entity> ExistingEmails = userRepo.findByEmail(email);
        if (ExistingEmails.isPresent()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Forbidden, Account already exists");
        }
        else{
            User_Entity newUser = new User_Entity(email, name, password);
            userRepo.save(newUser);
            return ResponseEntity.status(HttpStatus.CREATED).body("Account Creation Successful");
        }

    }

    public ResponseEntity<?> getUserDetail(int userId) {
        Optional<User_Entity> optionalUser = userRepo.findById(userId);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User does not exist");
        }
        User_Entity user = optionalUser.get();
        UserDetails userDetail=new UserDetails();
        userDetail.setEmail(user.getEmail());
        userDetail.setName(user.getName());
        userDetail.setUserID(user.getUserId());
        return ResponseEntity.ok().body(userDetail);
    }

    public ResponseEntity<List<UserDetails>> getAllUsers() {
        List<User_Entity> users = userRepo.findAll();
        List<UserDetails> ud=new ArrayList<>();
        for(var u:users){
            UserDetails user=new UserDetails();
            user.setEmail(u.getEmail());
            user.setName(u.getName());
            user.setUserID(u.getUserId());
            ud.add(user);
        }
        return ResponseEntity.ok().body(ud);
    }

}

