package com.example.demo;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.demo.services.*;

@Component
public class Startup implements CommandLineRunner
{
	@Autowired
	private UserService userService;

	@Override
	public void run(String... args) throws Exception
	{
		userService.createUser("admin", "admin", "admin", "admin@admin.com", Arrays.asList("ROLE_USER", "ROLE_ADMIN"));
		System.out.println(userService.findAllUsers());
	}
}
