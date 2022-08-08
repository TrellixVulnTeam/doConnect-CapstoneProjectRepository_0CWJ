package com.doconnect.qanda.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.doconnect.qanda.entity.User;
import com.doconnect.qanda.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserDetailsImpl userDetailsImpl;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if(user != null) {
			return new UserDetailsImpl(user);
		}
		throw new UsernameNotFoundException("Username Not Found!");
	}

}
