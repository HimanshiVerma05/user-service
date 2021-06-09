package com.assignment.microservices.userservice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.assignment.microservices.userservice.dao.User;
import com.assignment.microservices.userservice.entity.UserEntity;
import com.assignment.microservices.userservice.repository.UserRepository;


@RestController
public class UserController {

	@Autowired
    private UserRepository userRepository;
	
	@GetMapping("getUsers")
	public List<UserEntity> getAllUsers()
	{
		return userRepository.findAll();
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	 @GetMapping("/user/{id}")
	    public ResponseEntity < UserEntity > getUserById(@PathVariable(value = "id") Long userId)
	     {
		 Optional<UserEntity> userFind = userRepository.findById(userId);
         UserEntity user=new UserEntity();
		if(userFind.isPresent())
		{
			user=userFind.get();
			return ResponseEntity.ok().body(user);
		}
	        return null;
	    }
	
	
	@PostMapping(value = "addUser", consumes = MediaType.APPLICATION_JSON_VALUE)
	
	public Long addUser(@RequestBody UserEntity newUser)
	{
		
        UserEntity user=new UserEntity();
		
		
		user.setEmail(newUser.getEmail());
		user.setName(newUser.getName());
		user.setPhone_number(newUser.getPhone_number());
		user.setUser_type(newUser.getUser_type());
		
		
     final UserEntity newUsers = userRepository.save(user);
     return newUsers.getId();
		
	
	}


	
	@PutMapping("/updateUser/{id}")
    public ResponseEntity < UserEntity > updateUser(@PathVariable(value = "id") Long userId,
         @RequestBody UserEntity userDetails)  {
		Optional<UserEntity> userFind = userRepository.findById(userId);
           UserEntity user=new UserEntity();
		if(userFind.isPresent())
		{
			user=userFind.get();
		user.setEmail(userDetails.getEmail());
		user.setName(userDetails.getName());
		user.setPhone_number(userDetails.getPhone_number());
		user.setUser_type(userDetails.getUser_type());
		
        final UserEntity updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
		}
		
			return null;
    }

    @DeleteMapping("/deleteUser/{id}")
    public Map < String, Boolean > deleteUser(@PathVariable(value = "id") Long userId)
     {
        Optional<UserEntity> userFind = userRepository.findById(userId);
        UserEntity user=new UserEntity();
		if(userFind.isPresent()) {    
        user=userFind.get();
        userRepository.delete(user);
        Map < String, Boolean > response = new HashMap < > ();
        response.put("deleted", Boolean.TRUE);
        return response;
     }
    return null;
    
}
	
}
