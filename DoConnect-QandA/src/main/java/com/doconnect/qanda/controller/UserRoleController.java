package com.doconnect.qanda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doconnect.qanda.entity.Answer;
import com.doconnect.qanda.entity.Question;
import com.doconnect.qanda.serviceImpl.UserServiceImpl;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserRoleController {
	
	@Autowired
	UserServiceImpl userService;
	
	@GetMapping("/getQuestionsAskedByUser/{userId}")
	public List<Question> getQuestionsAskedByUser(@PathVariable Long userId){
		return userService.getQuestionsAskedByUser(userId);
	}
	
	@GetMapping("/getAnswersGivenByUser/{userId}")
	public List<Answer> getAnswersGivenByUser(@PathVariable Long userId){
		return userService.getAnswersGivenByUsers(userId);
	}
	
	
	

}
