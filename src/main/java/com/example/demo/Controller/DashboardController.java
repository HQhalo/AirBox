package com.example.demo.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.FileResponse;
import com.example.demo.User;
import com.example.demo.repository.FileRepository;
import com.example.demo.repository.UserRepository;



@Controller
@RequestMapping("/dashboard")
public class DashboardController {
	private UserRepository userRepo;
	private FileRepository fileRepo;
	
	public DashboardController(UserRepository userRepo, FileRepository fileRepo) {
		this.userRepo = userRepo;
		this.fileRepo = fileRepo;
	}

	@GetMapping
	public String dashboard(@AuthenticationPrincipal User user,Model model) {
		List<FileResponse> files = new ArrayList<>();
		fileRepo.findAllByUser_id(user.getId()).forEach(i -> files.add(i));
		
		model.addAttribute("files", files);
		return "dashboard";
	}
}
