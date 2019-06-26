package com.tiffinbox.onboarding.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tiffinbox.onboarding.data.model.UserProfile;


@Repository
@Transactional
public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {

	List<UserProfile> findByPhoneNumber(String phoneNumber);
	
	@Modifying
	@Query(value = "update user_profile set address_line1 =:addressLine1 , address_line2 =:addressLine2 , city =:city , state =:state , pincode =:pincode where address_line1 =:oldAddressLine1 AND city=:oldCity AND state=:oldState AND pincode=:oldPincode", nativeQuery=true)
	int updateUserAddress(@Param("addressLine1") String addressLine1 , @Param("addressLine2") String addressLine2 , @Param("city") String city, @Param("state") String state, @Param("pincode") String pincode, @Param("oldAddressLine1") String oldAddressLine1, @Param("oldCity") String oldCity, @Param("oldState") String oldState, @Param("oldPincode") String oldPincode);
	
	@Modifying
	@Query(value = "delete from user_profile where address_line1=:addressLine1 AND city=:city AND state=:state AND pincode=:pincode", nativeQuery=true)
	int deleteUserAddress(@Param("addressLine1") String addressLine1 , @Param("city") String city, @Param("state") String state, @Param("pincode") String pincode);
}
