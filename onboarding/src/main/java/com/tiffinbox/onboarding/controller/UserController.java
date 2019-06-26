package com.tiffinbox.onboarding.controller;

import java.util.List;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.tiffinbox.onboarding.data.model.Users;
import com.tiffinbox.onboarding.data.response.UserData;
import com.tiffinbox.onboarding.repository.UserRepository;
import com.tiffinbox.onboarding.service.handler.UserHandler;

@RestController
@EnableWebMvc
@RequestMapping("/v1")
public class UserController {

	@Autowired
	private UserHandler userHandler;
		
	@RequestMapping(path = "/users/{phoneNumber}", method = RequestMethod.GET)
	public ResponseEntity<UserData> getUserByPhoneNumber(@PathVariable("phoneNumber") String phoneNum) {

		System.out.println("Inside");
		HttpStatus httpStatus = null;
		UserData userData = null;

		try {
			userData = userHandler.fetchByPhone(phoneNum);
			httpStatus = HttpStatus.OK;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(userData, httpStatus);

	}

	@GetMapping("/users")
	public List<Users> getUsers() {
		return userHandler.getAllUsers();
	}
	
	@PostMapping(path="/users/sign-up")
	public String signUpUser(@RequestBody Users user) {
		UserData userData = null;
		boolean flag=false;
		try {
			userData = userHandler.fetchByPhone(user.getPhoneNumber());
			if(userData == null)
				flag = userHandler.saveUser(user);
			else 
				return "User Already Registered";
				
		}catch(Exception e) {
			return "Some Error Occured";
		}
		return "Successfully Registered";
	}
	
	@RequestMapping(path="/users/update/profile" , method = RequestMethod.PUT)
	public String updateProfile(@RequestBody String body) throws ParseException {
		String update = userHandler.updateUserProfile(body);
		return update;
	}
	
}
