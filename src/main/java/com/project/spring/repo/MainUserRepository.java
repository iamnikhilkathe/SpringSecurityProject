package com.project.spring.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.project.spring.mdoel.MainUser;

@Repository
public interface MainUserRepository extends JpaRepository<MainUser, Long > {
	
	
	MainUser findByUserName(String username);

	@Modifying
    @Transactional
	@Query( value = "delete from main_user where user_name =:userName" , nativeQuery = true)
	void  deleteMainUser(@Param("userName") String userName);

}
