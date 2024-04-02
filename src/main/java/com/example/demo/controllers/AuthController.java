package com.example.demo.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.models.*;
import com.example.demo.services.*;

@Controller
public class AuthController
{
	@Autowired
	private UserService userService;

	@GetMapping("/index")
	public String home()
	{
		return "index";
	}

	@GetMapping("/login")
	public String login(Model model)
	{
		return "login.html";
	}

	@GetMapping("/register")
	public String showRegistrationForm(Model model)
	{
		UserDto user = new UserDto();
		model.addAttribute("user", user);
		return "register";
	}

	@PostMapping("/register/save")
	public String registration(@Validated @ModelAttribute("user") UserDto userDto, BindingResult result, Model model)
	{
		User existingUser = userService.findUserByEmail(userDto.getEmail());

		if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty())
		{
			result.rejectValue("email", null, "There is already an account registered with the same email");
		}

		if (result.hasErrors())
		{
			model.addAttribute("user", userDto);
			return "/register";
		}

		userService.createUser(userDto.getFirstName(), userDto.getLastName(), userDto.getPassword(), userDto.getEmail(), Arrays.asList("ROLE_USER"));
		return "redirect:/register?success";
	}

	@GetMapping("/users")
	public String users(Model model)
	{
		List<UserDto> users = userService.findAllUsersDto();
		model.addAttribute("users", users);
		return "users";
	}
}
