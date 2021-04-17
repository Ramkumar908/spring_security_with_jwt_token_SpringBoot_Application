package com.smart.web.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smart.web.model.SmartUser;


@Repository
public interface UserRepo extends JpaRepository<SmartUser,Integer> {
	
	//for mail Authentication for new user
	@Query(value="select usermail from smart_user where usermail= ?1",nativeQuery=true)
	String existUserByMail(String usermail);
	
	
	//Optional<UserDao> findByUsermail(String usermail);
	//String findByUserName(String username);
	
	
	// for status check while register new user
	
	@Query(value="select userstatus from smart_user where usermail= ?1",nativeQuery=true)
	String  existUserstaus(String usermail);
	
	// COnatct authenticate for new user at register time 
	
	@Query(value="select usercontact from smart_user",nativeQuery=true)
	String  existUsercontact();
	
	// update status after verify otp
	@Modifying
	@Transactional
	@Query(value="UPDATE smart_user set userstatus='1' where usermail= ?1",nativeQuery=true)
	void updateVerifyUserStatus(String usermail);
	
	
	//update user authenticated Token
	@Modifying
	@Transactional
	 @Query(value = "UPDATE smart_user  set usertoken =:authenticateTOken where usermail = :userMail",nativeQuery=true)
	 void  updateUserAuthenticateToken(@Param("authenticateTOken") String authenticateTOken,@Param("userMail") String userMail);
	 
	SmartUser findByUsername(String username);
	SmartUser findByUsermail(String usermail);
	SmartUser findByUsercontact(String usercontact);


}
