package com.tiffinbox.onboarding.service.handler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.tiffinbox.onboarding.data.model.UserProfile;
import com.tiffinbox.onboarding.data.model.Users;
import com.tiffinbox.onboarding.data.response.UserData;
import com.tiffinbox.onboarding.data.response.UserProfileData;
import com.tiffinbox.onboarding.repository.UserProfileRepository;

@EnableTransactionManagement
@Component
public class UserProfileHandler {

	@Autowired
	private UserProfileRepository userProfileRepository;

	public UserProfileHandler() {

	}

	public List<UserProfileData> findUserAddressesByPhoneNumber(String phoneNum) {
		List<UserProfile> userProfile = userProfileRepository.findByPhoneNumber(phoneNum);
		if (userProfile == null)
			return null;
		List<UserProfileData> profileData = new ArrayList();
		for (UserProfile userProf : userProfile) {
			UserProfileData userData = new UserProfileData();
			userData.setId(userProf.getId());
			userData.setFirstName(userProf.getFirstName());
			userData.setLastName(userProf.getLastName());
			userData.setPhoneNumber(userProf.getPhoneNumber());
			userData.setAddressLine1(userProf.getAddressLine1());
			userData.setAddressLine2(userProf.getAddressLine2());
			userData.setCity(userProf.getCity());
			userData.setState(userProf.getState());
			userData.setPincode(userProf.getPincode());
			userData.setCreatedAt(userProf.getCreatedTimestamp().toString());
			profileData.add(userData);
		}
		return profileData;
	}

	public boolean saveUserAddress(UserProfile userProfile) {
		try {
			userProfileRepository.save(userProfile);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public int updateUserAddress(String userProfile) throws ParseException {
		JSONParser parser = new JSONParser();
		JSONObject jsonBody = (JSONObject) parser.parse(userProfile);
		JSONArray newAddress = (JSONArray) jsonBody.get("newAddress");
		JSONArray oldAddress = (JSONArray) jsonBody.get("oldAddress");
		String newAddressLine1=null;
		String newAddressLine2=null;
		String newCity=null;
		String newState=null;
		String newPincode=null;
		String oldAddressLine1=null;
		String oldAddressLine2=null;
		String oldCity=null;
		String oldState=null;
		String oldPincode=null;

		
		Iterator newIt = newAddress.iterator();
		Iterator oldIt = oldAddress.iterator();

		while (newIt.hasNext()) {
			JSONObject object = (JSONObject) newIt.next();
			newAddressLine1 = (String) object.get("addressLine1");
			newAddressLine2 = (String) object.get("addressLine2");
			newCity = (String) object.get("city");
			newState = (String) object.get("state");
			newPincode = (String) object.get("pincode");
			
			System.out.println("new Address : "+newAddressLine1 + newAddressLine2+newCity+newState+newPincode);
		}

		while (oldIt.hasNext()) {
			JSONObject object = (JSONObject) oldIt.next();
			oldAddressLine1 = (String) object.get("addressLine1");
			oldAddressLine2 = (String) object.get("addressLine2");
			oldCity = (String) object.get("city");
			oldState = (String) object.get("state");
			oldPincode = (String) object.get("pincode");
			System.out.println("old Address : "+oldAddressLine1 + oldAddressLine2+oldCity+oldState+oldPincode);

		}

		int resp = userProfileRepository.updateUserAddress(newAddressLine1, newAddressLine2, newCity, newState,
				newPincode, oldAddressLine1, oldCity, oldState, oldPincode);
		return resp;
	}
	
	public int deleteUserAddress(String requestBody) throws ParseException {
		JSONParser parser = new JSONParser();
		JSONObject jsonBody = (JSONObject) parser.parse(requestBody);
		JSONArray address = (JSONArray) jsonBody.get("address");
		
		String addressLine1=null;
		String addressLine2=null;
		String city=null;
		String state=null;
		String pincode=null;
		
		Iterator it = address.iterator();
		while (it.hasNext()) {
			JSONObject object = (JSONObject) it.next();
			addressLine1 = (String) object.get("addressLine1");
			addressLine2 = (String) object.get("addressLine2");
			city = (String) object.get("city");
			state = (String) object.get("state");
			pincode = (String) object.get("pincode");
		}
		
		int resp = userProfileRepository.deleteUserAddress(addressLine1, city, state, pincode);
		return resp;
	}
}
