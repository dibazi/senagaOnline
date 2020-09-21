package sengaOneToManySecurity2.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;


import sengaOneToManySecurity2.entities.User;
import sengaOneToManySecurity2.web.dto.UserRegistrationDto;

public interface UserService extends UserDetailsService{
	User save(UserRegistrationDto registrationDto);
	

	User getUserByEmail(String username);

}
