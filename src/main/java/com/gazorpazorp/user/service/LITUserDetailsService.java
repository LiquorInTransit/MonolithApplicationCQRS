package com.gazorpazorp.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gazorpazorp.user.config.LITUserDetails;
import com.gazorpazorp.user.model.UserEntity;
import com.gazorpazorp.user.repository.UserRepository;

@Service("LITUserDetailsService")
public class LITUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userDao;
	
	@Override
	public LITUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity user = userDao.findByEmail(email);
		if (user == null)
			throw new UsernameNotFoundException("User not found");
		return LITUserDetails.create(user);//new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.isEnabled(), user.isAccountNonExpired(), true, user.isAccountNonLocked(), getGrantedAuthorities(user));
	}


}
