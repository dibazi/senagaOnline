package sengaOneToManySecurity2.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sengaOneToManySecurity2.entities.Orders;
import sengaOneToManySecurity2.entities.User;
import sengaOneToManySecurity2.repositories.OrdersRepository;
import sengaOneToManySecurity2.repositories.UserRepository;
import sengaOneToManySecurity2.service.UserService;
import sengaOneToManySecurity2.web.dto.UserRegistrationDto;

@Controller
public class OrdersController {
	
	@Autowired
	private OrdersRepository ordersRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	private UserService userService;

	public OrdersController(UserService userService) {
		super();
		this.userService = userService;
	}
	
	@ModelAttribute("/user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }
	
	@GetMapping("/Order/edit/{id}")
	public String UpdateForm(@PathVariable("id") Long id, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		
		//Optional<Orders> order= ordersRepository.findById(id);
		model.addAttribute("current_email", userDetails.getUsername());
		model.addAttribute("order", ordersRepository.findById(id));
		return "Order";
	}
	
	@PostMapping("/update")
	public String update(@ModelAttribute("order") Orders order) {
		
		ordersRepository.save(order);
		return "redirect:/index?update";
	}
	
	@GetMapping("/Order/delete/{id}")
	public String Delete(@PathVariable("id") Long id,Orders order, Model model) {

        ordersRepository.deleteById(id);
        
		return "redirect:/index?delete";
	}

	@PostMapping("/saveOrder")
	public String registerUserAccount(@ModelAttribute("order") Orders order) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		
		order.setUser_email(userDetails.getUsername());
		
		ordersRepository.save(order);
		return "redirect:/index?success";
	}
	
	@PostMapping
	public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto registrationDto,@ModelAttribute("order") Orders order,User user) {
		
		//user.setOrders(Arrays.asList(order));
		userService.save(registrationDto);
		return "redirect:/Orders?success";
	}
	
	@GetMapping("Orders/{email}")
	public String showUpdateForm(@PathVariable("email") String email,Model model) {
		model.addAttribute("orders", ordersRepository.findAllByEmail(email));
		return "Orders";
	}
	@GetMapping("table/{email}")
	public String table(@PathVariable("email") String email,Model model) {
		model.addAttribute("orders", ordersRepository.findAllByEmail(email));
		return "table";
	}
	
	@GetMapping("/voir")
	public String voir() {
		//model.addAttribute("orders", ordersRepository.findAllByEmail(email));
		return "voir";
	}
	
	@GetMapping("/table")
	public String table() {
		//model.addAttribute("orders", ordersRepository.findAllByEmail(email));
		return "table";
	}

}
