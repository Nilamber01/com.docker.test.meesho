package com.tiffinbox.onboarding.service.handler;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import com.tiffinbox.onboarding.data.model.Users;
import com.tiffinbox.onboarding.data.response.UserData;
import com.tiffinbox.onboarding.repository.UserRepository;

@Component
public class UserHandler {

	@Autowired
	private UserRepository userRepository;

	public UserHandler() {

	}

	public UserData fetchByPhone(String phoneNum) {

		Users users = userRepository.findByPhoneNumber(phoneNum);
		if (users == null)
			return null;
		UserData userData = new UserData();
		userData.setId(users.getId());
		userData.setFirstName(users.getFirstName());
		userData.setLastName(users.getLastName());
		userData.setEmail(users.getEmail());
		userData.setPhoneNumber(users.getPhoneNumber());
		userData.setCreatedAt(users.getCreatedTimestamp().toString());

		return userData;
	}

	public List<Users> getAllUsers() {
		List<Users> users = userRepository.findAll();
		for (Users user : users) {
			System.out.println(user.getCreatedTimestamp());
		}
		return users;
	}

	public boolean saveUser(Users user) {
		try {
			Users newUser = userRepository.save(user);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public String updateUserProfile(String body) throws ParseException {
		JSONParser parser = new JSONParser();
		JSONObject jsonBody = (JSONObject) parser.parse(body);
		String newPhone = null;
		String oldPhone = null;
		String newEmail = null;
		String oldEmail = null;
		boolean updateFlag = false;

		try {
			newPhone = jsonBody.get("newPhone").toString();
			oldPhone = jsonBody.get("oldPhone").toString();
		}catch (Exception e) {

		}
		try {
			newEmail = jsonBody.get("newEmail").toString();
			oldEmail = jsonBody.get("oldEmail").toString();
		} catch (Exception e) {

		}

		if (!(newPhone == null)) {
			Users users = null;
			users = userRepository.findByPhoneNumber(newPhone);
			if (users == null)
				userRepository.updateUserPhone(newPhone, oldPhone);
			else
				return "Number already registered";
		}

		if (!(newEmail == null)) {
			String email = userRepository.findByEmail(newEmail);
			if (email == null)
				userRepository.updateUserEmail(newEmail, oldEmail);
			else
				return "Email already registered";
		}
		return "Successfully updated Profile";
	}

}
