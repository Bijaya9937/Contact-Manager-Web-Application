package com.smart.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smart.dao.ContactRepository;
import com.smart.dao.UserRepository;
import com.smart.entity.Contact;
import com.smart.entity.User;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ContactRepository contactRepository;
	
	@ModelAttribute
	public void addCommonData(Model model,Principal principal)
	{
		String username = principal.getName();
		User user=this.userRepository.getUserByUserName(username);
		model.addAttribute("user",user);
	}
	
	// User Dashboard Handler
	// User Dashboard Handler
	// User Dashboard Handler
	// User Dashboard Handler

	@GetMapping("/dashboard")
	public String dashboard(Model model,Principal principal)
	{
		model.addAttribute("file","user_dashboard");
		model.addAttribute("title","User Dashboard Page");
		return "user/user_dashboard";
	}
	
	// User Add Contact Handler
	
	@GetMapping("/add-contact")
	public String addContact(Model model)
	{
		model.addAttribute("file","add-contact");
		model.addAttribute("title","Add Contact Page");
		model.addAttribute("contact",new Contact());
		return "user/add-contact";
	}
	
	// Save Contact Handler
	// Save Contact Handler
	// Save Contact Handler
	// Save Contact Handler
	
	
	
	@PostMapping("/save-contact")
	public String saveContact(@ModelAttribute Contact contact,@RequestParam("profileImage") MultipartFile file,Principal principal,Model model)
	{
		try {
			String name=principal.getName();
			User user = this.userRepository.getUserByUserName(name);
			
			
			if(file.isEmpty())
			{
				System.out.println("File is Empty !!!!!!!");
				contact.setImageUrl("contact.png");
			}
			else
			{
				contact.setImageUrl(file.getOriginalFilename());
				
				File saveFile= new ClassPathResource("static/images").getFile();
				Path path=Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
				Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
				
				System.out.println("Image is uploaded Succesfully");
			}
			
			contact.setUser(user);
			
			
			user.getContact().add(contact);
			
			User newContact= this.userRepository.save(user);
			System.out.println("Data of Contact :"+newContact);
		}catch(Exception e)
		{
			
			e.printStackTrace();
		}
		
		model.addAttribute("file","add-contact");
		return "redirect:/user/view-contact/0";
	}
	
	// View Contact 
	// View Contact 
	// View Contact 
	
	// Page will indicate the current page 
	@GetMapping("/view-contact/{page}")
	public String viewContact(@PathVariable("page") Integer page,Model m,Principal principal)
	{
		String userEmail = principal.getName();
		User user = this.userRepository.getUserByUserName(userEmail);
		int userId= user.getId();
		
		Pageable pageable = PageRequest.of(page,3);
		
		Page<Contact> contacts= this.contactRepository.getContactById(userId,pageable);
		
		
		
		m.addAttribute("contacts",contacts);
		m.addAttribute("currentpage",page);
		m.addAttribute("totalpages",contacts.getTotalPages());
		m.addAttribute("file","view-contact");
		m.addAttribute("title","View Contact Page");
		
		return "/user/view-contact";
	}
	
	
	// Showing Single Contact Details
	// Showing Single Contact Details
	// Showing Single Contact Details
	
	@GetMapping("/contact/{cid}")
	public String showOneContact(@PathVariable("cid") Integer cid,Model model,Principal principal)
	{
		Optional<Contact> contactOptional=this.contactRepository.findById(cid);
		Contact singleContact = contactOptional.get();
		
		String userEmail = principal.getName();
		User user =this.userRepository.getUserByUserName(userEmail);
		
		if(user.getId() == singleContact.getUser().getId())
		{			
			model.addAttribute("contact",singleContact);
		}
		model.addAttribute("file","show-contact");
		model.addAttribute("title","Show Contact Page");
		
		return "/user/show-contact";
	}
	
	// Deleting a Contact details from a particular user
	// Deleting a Contact details from a particular user
	// Deleting a Contact details from a particular user
	
	@GetMapping("/delete/{cid}")
	public String deleteContact(@PathVariable("cid") Integer cid,Principal principal)
	{
		Contact contact = this.contactRepository.findById(cid).get();
		
		contact.setUser(null);
		
		this.contactRepository.save(contact);
		
		this.contactRepository.deleteById(contact.getcId());
		//session.setAttribute("message", new MyMessage("Contact Deleted Successfully !!!","alert-success"));
		System.out.println("Deleted Successfully .......");
		
		return "redirect:/user/view-contact/0";
	}
	
	
	// Updating the Contact Details 
	// Updating the Contact Details 
	// Updating the Contact Details 
	
	@GetMapping("/update-contact/{cId}")
	public String updateContact(@PathVariable("cId") Integer cid,Model model)
	{
		Contact contact=this.contactRepository.findById(cid).get();
		model.addAttribute("contact",contact);
		model.addAttribute("file","add-contact");
		model.addAttribute("title","Update Contact Page");
		return "/user/update-contact";
	}
	
	// Updated Contact POST handler
	
	@PostMapping("/updateContactForm")
	public String updatedContact(@ModelAttribute Contact contact,Principal principal,@RequestParam("profileImage") MultipartFile file)
	{
		System.out.println("Contact :"+contact);
		try {
			
			
			System.out.println("Contact CID: "+contact.getcId());
			Contact oldContact= this.contactRepository.findById(contact.getcId()).get();
			System.out.println("Old Contact ImageURL :"+oldContact.getImageUrl());
			if(oldContact != null)
			{
				if(!file.isEmpty())
				{
					// Code for rewriting the Image 
					// Delete the old image
					File deleteFile= new ClassPathResource("static/images").getFile();
					File newFile= new File(deleteFile,oldContact.getImageUrl());
					
					newFile.delete();
					
					// Update the new Image
					contact.setImageUrl(file.getOriginalFilename());
					
					File saveFile= new ClassPathResource("static/images").getFile();
					Path path=Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
					Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
					
				}
				else
				{
					contact.setImageUrl(oldContact.getImageUrl());
				}
			}
			
			
			String userName = principal.getName();
			System.out.println("User Email :"+userName);
			User user = this.userRepository.getUserByUserName(userName);
			
			contact.setUser(user);
			
			Contact updatedContact = this.contactRepository.save(contact);
			System.out.println("Updated Contact is ::: "+updatedContact);
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		return "redirect:/user/view-contact/0";
	}
	
	//Show User Profile
	//Show User Profile
	//Show User Profile
	
	@GetMapping("/user-profile")
	public String userProfile(Model model)
	{
		model.addAttribute("file","user-profile");
		model.addAttribute("title","User Profile Page");
		
		return "/user/user-profile";
	}
	
	
	
	
	
	
	//Setting up User Profile
	//Setting up User Profile
	//Setting up User Profile
	
	@GetMapping("/setting")
	public String setting(Model model)
	{
		
		model.addAttribute("file","setting");
		model.addAttribute("title","Setting Page");
		return "/user/setting";
	}
	
	
	// Update User Profile
	// Update User Profile
	// Update User Profile
	@GetMapping("/user-update-profile")
	public String updateUserProfile(Model model)
	{
		model.addAttribute("file","user-update-profile");
		model.addAttribute("title","Update User Profile Page");
		return "/user/user-update-profile";
	}
	
	@PostMapping("/updatedUserData")
	public String updateUserData(@ModelAttribute User user,@RequestParam("profileImage")MultipartFile file)
	{
		try {
		
			String fileName=file.getOriginalFilename();
			user.setImageUrl(fileName);
			File saveFile= new ClassPathResource("static/images").getFile();
			Path path=Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
			Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
			
			
			this.userRepository.save(user);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return "redirect:/user/user-profile";
	}
	
	
	// Change User Password
	// Change User Password
	// Change User Password
	
	@GetMapping("/change-password")
	public String changePassword()
	{
		return "/user/change-password";
	}
	
	@GetMapping("/delete-user")
	public String deleteUser()
	{
		return "/user/delete-user";
	}
	
	
	
	
	
}
