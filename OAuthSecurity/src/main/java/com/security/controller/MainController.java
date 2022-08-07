package com.security.controller;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.security.VO.User;
import com.security.config.auth.PrincipalDetails;
import com.security.config.auth.PrincipalDetailsCustVO;
import com.security.repository.UserRepository;


@Controller
public class MainController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	

	
	@RequestMapping("/")
	public String main(Model m) { 

		return "index";
	}
	
	@RequestMapping("/loginsuccess")
	public String loginsuccess(Model m, @AuthenticationPrincipal PrincipalDetailsCustVO principalDetails,HttpSession session) { 
		System.out.println("principalDetails : " + principalDetails.getUser());
		session.setAttribute("user", principalDetails);
		
		return "redirect:/";
	}
	
	
	@RequestMapping("/joinForm")
	public String joinForm(Model m) { 
		
		return "joinForm";
	}
	
	@RequestMapping("/joinProc")
	public String joinProc(Model m,User user) { 
		System.out.println(user);
		user.setRole("ROLE_USER");// 회원가입한 아이디의 ROle을 설정 
		String rawPassword = user.getPwd();
		
		// SecurityConfig에 있음 =>패스워드를 암호화하기 위해 사용
		String encPassword = bCryptPasswordEncoder.encode(rawPassword); 
		user.setPwd(encPassword);
		
		
		userRepository.save(user);// 자동 회원가입 - > 회원가입 되지만 시큐리티로 로그인을 할 수 없음.
		//이유는 패스워드가 암호화가 안되었기 때문
		
		return "redirect:/loginForm";
	}
	
	@Secured("ROLE_ADMIN")// 접근 권한 설정
	@RequestMapping("/info")
	public @ResponseBody String info(Model m) { 
		
		return "개인정보";
	}
	
	@RequestMapping("/admin")
	public @ResponseBody String admin(Model m) { 
		
		return "admin페이지 ";
	}
	
	@PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")// 접근권한 설정
	@RequestMapping("/data")
	public @ResponseBody String data(Model m) { 
		
		return "데이터 정보";
	}
	
	@RequestMapping("/loginForm")
	public String loginForm(Model m) { 
		
		return "loginForm";
	}
	
	// 로그인 세션정보 확인 - 일반 로그인시 사용 
	@RequestMapping("/test/login")
	public @ResponseBody String testLogin(Authentication authentication, 
			@AuthenticationPrincipal PrincipalDetails userDetails) {//DI(의존성 주입) 
		System.out.println("test/login/ ==========");
		
		// 로그인한 유저 정보를 찾는 방법 
		
		// 첫번째. Authentication 객체를 이용해서 유저 정보 찾기 
		PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
		System.out.println("Authentication : " + principalDetails.getUser());//로그인한 유저의 정보가 담겨있다.
		
		
		// 두번째. @AuthenticationPrincipal 걸어서 유저정보 찾기  
		// @AuthenticationPrincipal 어노테이션을 이용해서 세션정보에 접근할 수 있다. 
		System.out.println("userDetails : " + userDetails.getUser());
		
		return "세션정보 확인하기 ";
	}
	
	// 로그인 세션정보 확인 - 플랫폼 로그인시 사용 (google, facebook) 
	@RequestMapping("/test/oauth/login")
	public @ResponseBody String testOAuthLogin(Authentication authentication,
			@AuthenticationPrincipal OAuth2User userDetails) {//DI(의존성 주입) 
		System.out.println("test/oauth/login/ ==========");

		OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
		// 방법 1
		System.out.println("Authentication : " + oauth2User.getAttributes());//로그인한 유저의 정보가 담겨있다.
		
		// 방법 2
		System.out.println("userDetails : " + userDetails.getAttributes());//로그인한 유저의 정보가 담겨있다.
		return "OAuth 세션정보 확인하기 ";
	}
	
	
	// 로그인 세션정보 확인 - 플랫폼 로그인시 사용 (google, facebook) 
	// 일반로그인 , 플랫폼 로그인으로 해도 모두 PrincipalDetails 로 받을 수 있다. 
	@RequestMapping("/user")
	public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails) {//DI(의존성 주입) 
		System.out.println("principalDetails : " + principalDetails.getUser());
		
		return "user";
	}

	
	
	@RequestMapping("/join")
	public @ResponseBody String join(Model m) { 
		//birthday=02-07, birthyear=1996}
		String day = "02-07";
		String year = "1996";
//		String[] month_day_split = day.split("-");
		String myString = year + "-" + day;// yyyy-MM-dd 년월일 순으로 설정 
		Date mydate = new Date();
//		String myString = year+month_day_split[0]+month_day_split[1];// 년월일 순으로 설정 
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			mydate = sdf.parse(myString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		System.out.println(mydate);
		
		
		return "join"+mydate;
	}
	
	@RequestMapping("/manager")
	public @ResponseBody String manager(Model m) { 
		
		return "manager";
	}


	
	
	
}
