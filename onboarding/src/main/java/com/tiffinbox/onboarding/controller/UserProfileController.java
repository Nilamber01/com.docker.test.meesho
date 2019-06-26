package com.tiffinbox.onboarding.controller;

import java.util.List;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.tiffinbox.onboarding.data.model.UserProfile;
import com.tiffinbox.onboarding.data.model.Users;
import com.tiffinbox.onboarding.data.response.UserData;
import com.tiffinbox.onboarding.data.response.UserProfileData;
import com.tiffinbox.onboarding.service.handler.UserProfileHandler;

@RestController
@EnableWebMvc
@RequestMapping("/v1")
public class UserProfileController {

	@Autowired 
	private UserProfileHandler userProfileHandler;
	
	@PostMapping(path="/users/address/add")
	public boolean  addUserAddress(@RequestBody UserProfile userProfile) {
		UserProfileData userProfileData = null;
		boolean flag=false;
		try {
			flag = userProfileHandler.saveUserAddress(userProfile);				
		}catch(Exception e) {
			return false;
		}
		return flag;
	}
	
	@GetMapping(path="/users/address/{phoneNumber}")
	public List<UserProfileData> getAdressesOfUserByPhone(@PathVariable("phoneNumber") String phone){
		List<UserProfileData> userData= userProfileHandler.findUserAddressesByPhoneNumber(phone);
		return userData;	
	}
	
	
	@RequestMapping(path="/users/address/update" , method = RequestMethod.PUT)
	public String updateUserAddress(@RequestBody String body) throws ParseException {
		int resp = userProfileHandler.updateUserAddress(body);
		return "Number of rows affected : "+resp;
	}
	
	@RequestMapping(path="/users/address/delete" , method = RequestMethod.DELETE)
	public String deleteUserAddress(@RequestBody String body) throws ParseException {
		int resp = userProfileHandler.deleteUserAddress(body);
		return "Number of rows affected : "+resp;
	}
}
