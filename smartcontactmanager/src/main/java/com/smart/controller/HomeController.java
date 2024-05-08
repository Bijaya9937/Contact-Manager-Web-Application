package com.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.smart.dao.UserRepository;
import com.smart.entity.User;
import com.smart.message.MyMessage;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class HomeController {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/")
	public String homePage(Model model)
	{
		model.addAttribute("file","home");
		model.addAttribute("title","Home Page");
		return "home";
	}
	
	@GetMapping("/about")
	public String aboutPage(Model model)
	{
		model.addAttribute("file","about");
		model.addAttribute("title","About Page");
		return "about";
	}
	@GetMapping("/signup")
	public String signup(Model model)
	{
		model.addAttribute("user",new User());
		model.addAttribute("file","signup");
		model.addAttribute("title","SignUp Page");
		return "signup";
	}
	@GetMapping("/signin")
	public String loginPage(Model model)
	{
		model.addAttribute("file","login");
		model.addAttribute("title","Signin Page");
		return "login";
	}
	
	// API for registering the user 
	
	@PostMapping("/do_register")
	public String userRegister(@ModelAttribute("user") User user,HttpServletRequest req,Model model,HttpSession session)
	{
		try {
			
			model.addAttribute("file","signup");
			
			System.out.println("USER :"+user);
			user.setRole("ROLE_USER");
			user.setAbout("Software Engineer");
			user.setImageUrl("default.png");
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			User result = this.userRepository.save(user);
			System.out.println("Result :"+result);
			model.addAttribute("user",new User());
			session.setAttribute("message", new MyMessage("Successfully Registered !!!", "alert-success"));
			System.out.println("Session Message :");
			return "signup";
			
		}catch(Exception e)
		{
			e.printStackTrace();
			
			session.setAttribute("message",new MyMessage("Something went wrong !!!", "alert-danger"));
			model.addAttribute("user",user);
			
			model.addAttribute("file","signup");
			
			return "signup";
		}
	}

}
