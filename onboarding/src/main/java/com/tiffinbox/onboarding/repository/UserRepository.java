package com.tiffinbox.onboarding.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.tiffinbox.onboarding.data.model.Users;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<Users, Integer> {
	Users findByPhoneNumber(String phoneNumber);
		
	@Query(value="select email from user where email=:newEmail",nativeQuery=true)
	String findByEmail(@Param("newEmail") String newEmail);
	

	@Modifying
	@Query(value = "update user set phone_number=:newPhone where phone_number=:oldPhone",nativeQuery=true)
	int updateUserPhone(@Param("newPhone") String newPhone,@Param("oldPhone") String oldPhone);

	@Modifying
	@Query(value = "update user set email=:newEmail where email=:oldEmail",nativeQuery=true)
	int updateUserEmail(@Param("newEmail") String newEmail, @Param("oldEmail") String oldEmail);
}
