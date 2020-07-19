package com.example.demo.Controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.User;

@Controller
@RequestMapping("/login")
public class LoginController {
	@GetMapping
	public String login(@AuthenticationPrincipal User user) {
		if(user == null) {
			return "login";
		}
		else {
			return "redirect:/dashboard";
		}
	}
	
}
