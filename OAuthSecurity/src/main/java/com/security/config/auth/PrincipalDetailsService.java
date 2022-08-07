package com.security.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.security.VO.User;
import com.security.repository.UserRepository;

// 시큐리티 설정에서 loginProcessionUrl("/login")
// login 요청이 오면 자동으로 UserDetailsService 타입으로 Ioc 되어 있는 loadUserByUserName함수가 실행된다.
// 따라서 login 페이지에서 폼에서 전송되면 이곳이 호출되어 loadUserByUsername() 함수로 username이 있는지 검사한다. 
@Service
public class PrincipalDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	// 시큐리티 session 타입 => Authentication = UserDetails
	// 시큐리티 session(내부 Authentication(내부 UserDetails))
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("userDetails 의 loadUserByUserName : " + username);
		User user = userRepository.findByUsername(username);// 해당 유저가 있는지 확인하는 곳 
		if(user == null) {// 유저가 없을 경우 null 리턴 
			return null;
		}else {// 유저가 있을경우 PrincipalDetails이 Authentication 의 형태로 시큐리티 Session으로 전달
			return new PrincipalDetails(user);
		}
		
	}

}
