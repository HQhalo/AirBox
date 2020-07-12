package com.example.demo;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.Data;

@Data
public class RegistrationForm {
	private String mail;
	  private String password;
	  private String firstname;
	  private String lastname;
	  private String city;
	  private String country;

	  public User toUser(PasswordEncoder passwordEncoder) {
	    return new User(mail,passwordEncoder.encode(password),firstname+lastname);
	    }
}
