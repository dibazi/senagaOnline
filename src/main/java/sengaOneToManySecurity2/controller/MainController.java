package sengaOneToManySecurity2.controller;

import java.security.Principal;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import sengaOneToManySecurity2.entities.Orders;
import sengaOneToManySecurity2.entities.User;
import sengaOneToManySecurity2.service.UserServiceImpl;
import sengaOneToManySecurity2.web.dto.UserRegistrationDto;

@Controller
public class MainController {
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping({"/", "/index"})
	public String home( Orders order,Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		
		model.addAttribute("current_email", userDetails.getUsername());
		model.addAttribute("order", order);
		return "index";
	}

}
