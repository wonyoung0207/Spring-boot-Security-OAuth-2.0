package com.security.repository;

import java.util.Optional;

import org.omg.CORBA.Principal;
import org.springframework.data.jpa.repository.JpaRepository;

import com.security.VO.User;
import com.security.config.auth.PrincipalDetails;

/**
 * 
 * @author cos
 * JPA는 기본 CRUD를 JpaRepository가 상속하는 CrudRepository가 가지고 있음.
 * JPA는 method names 전략을 가짐. README.md 사진 참고
 */

// JpaRepository 를 상속하면 자동 컴포넌트 스캔됨.
public interface UserRepository extends JpaRepository<User, Integer>{
	// Jpa 의 함수 규칙 => findBy 까지는 동일하게 사용한다.
	// 따라서 findByUsername 함수를 호출하면 자동으로 다음 쿼리문이 만들어진다. 	
	// SELECT * FROM user WHERE username = ?1
	User findByUsername(String username);//JPA 의 Query Method 라고 부른다. 

	// SELECT * FROM user WHERE provider = ?1 and providerId = ?2
	Optional<User> findByProviderAndProviderId(String provider, String providerId);
}


