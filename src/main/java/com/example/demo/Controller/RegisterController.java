package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.RegistrationForm;
import com.example.demo.User;
import com.example.demo.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequestMapping("/register")
public class RegisterController {
	private UserRepository userRepo;
	private PasswordEncoder passEncoder;
	
	@Autowired
	public RegisterController(UserRepository userRepo, PasswordEncoder passEncoder) {
		this.userRepo = userRepo;
		this.passEncoder = passEncoder;
	}
	
	@GetMapping
	public String registerForm(@AuthenticationPrincipal User user) {
		if(user == null)
			return "registration";
		else 
			return "redirect:/dashboard";
	}
	
	@PostMapping
	public String processRegistration(RegistrationForm form) {
		log.info(form.toString());
		this.userRepo.save(form.toUser(passEncoder));
		return "redirect:/login";
	}
	
	
}
