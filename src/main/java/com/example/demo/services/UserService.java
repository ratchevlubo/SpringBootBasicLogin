package com.example.demo.services;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.models.*;
import com.example.demo.repo.*;

@Service
public class UserService implements UserDetailsService
{
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public void saveUser(UserDto userDto)
	{
		User user = new User();
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setEmail(userDto.getEmail());
		
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		//user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
		Role role = roleRepository.findByName("ROLE_ADMIN");
		System.out.println("123FROM service " + role);
//		if (role == null)
//		{
//			role = checkRoleExist();
//		}
		
		//user.setRoles(Arrays.asList(role));
		userRepository.save(user);
	}

	public User findUserByEmail(String email)
	{
		return userRepository.findByEmail(email);
	}

	public List<UserDto> findAllUsersDto()
	{
		List<User> users = userRepository.findAll();
		return users.stream().map((user) -> mapToUserDto(user)).collect(Collectors.toList());
	}
	
	public List<User> findAllUsers()
	{
		List<User> users = userRepository.findAll();
		return users;
	}

	private UserDto mapToUserDto(User user)
	{
		UserDto userDto = new UserDto();
		userDto.setFirstName(user.getFirstName());
		userDto.setLastName(user.getLastName());
		userDto.setEmail(user.getEmail());
		return userDto;
	}

	private Role checkRoleExist()
	{
		Role role = new Role();
		role.setName("ROLE_ADMIN");
		return roleRepository.save(role);
	}
	
	public User createUser(String firstName, String lastName, String password, String email, List<String> roles) {
		User user = findUserByEmail(email);
		if (user != null) {
			return user;
		} else {
			user = new User();
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setPassword(passwordEncoder.encode(password));
			user.setEmail(email);			
			List<Privilege> userRoles = new ArrayList<>();
			for (String rolename : roles) {
				Role role = roleRepository.findByName(rolename);
				if (role == null) {
					role = new Role();
					role.setName(rolename);
					roleRepository.save(role);
				}
				userRoles.add(new Privilege(user, role));
			}			
			user.setRoles(userRoles);
			return userRepository.save(user);
		}
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
	{
		User user = userRepository.findByEmail(email);
		
		if (user == null)
		{
			throw new UsernameNotFoundException("Username not found");
		}	
		return user;
	}
}