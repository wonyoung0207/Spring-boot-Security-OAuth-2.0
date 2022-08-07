package com.security.VO;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id // primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String username;
	private String pwd;
	private String email;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date birthday;
	private String gender;
	private String role; //ROLE_USER, ROLE_ADMIN
	private String provider; // 어떤 플렛폼으로 로그인 했는지 -> ex) google, facebook
	private String providerId; //
	@CreationTimestamp
	private Timestamp createDate;
	
//	
//	private String id;
//	private String pwd;
//	private String name;
//	
//	@DateTimeFormat(pattern="yyyy-MM-dd")
//	private Date birth;	
//	private int point;
//	private String sex;
//	
	@Builder
	public User(String username, String pwd, String email,Date birthday,String gender, String role, String provider, String providerId,
			Timestamp createDate) {
		super();
		this.username = username;
		this.pwd = pwd;
		this.email = email;
		this.birthday = birthday;
		this.gender = gender;
		this.role = role;
		this.provider = provider;
		this.providerId = providerId;
		this.createDate = createDate;
	}
	
	
	
}