package com.doconnect.qanda.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.doconnect.qanda.entity.Answer;
import com.doconnect.qanda.entity.Message;
import com.doconnect.qanda.entity.Question;
import com.doconnect.qanda.entity.User;
import com.doconnect.qanda.exceptions.UsernameNotFoundException;
import com.doconnect.qanda.repository.AnswerRepository;
import com.doconnect.qanda.repository.MessageRepository;
import com.doconnect.qanda.repository.QuestionRepository;
import com.doconnect.qanda.repository.UserRepository;

@Service
public class UserServiceImpl {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AnswerServiceImpl answerService;
	
	@Autowired
	QuestionServiceImpl questionService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	QuestionRepository questionRepository;
	
	@Autowired
	AnswerRepository answerRepository;
	
	@Autowired
	MessageRepository messageRepository;
	
	@Autowired
	WebSocketService webSocketService;
	
	public int addUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		User isThereAUserWithUsername = userRepository.findByUsername(user.getUsername());
		User isThereAUserWithEmail = userRepository.findByEmail(user.getEmail());
		
		if(isThereAUserWithUsername != null) {
			return -1;
		}
		else if(isThereAUserWithEmail != null)
		   return -1;
		
		else {
			user.setRoles("ROLE_USER,");
			System.out.println(user.getRoles());
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			userRepository.save(user);
			return 1;
	}
	}
	
	@Transactional
	public List<User> getUserList(){
		return userRepository.findAll();
	}
	
	public User findUserById(Long UserId){
		User user = new User();
		try {
			Optional<User> _user = Optional.of(userRepository.findById(UserId).orElseThrow(()->new UsernameNotFoundException("Username Not Found!")));
			user = _user.get();
		} catch (UsernameNotFoundException e) {
			System.out.println(e.getMessage());
			return null;
		}
		return user;
	}
	
	public User updateUser(Long userId, User userToBeUpdated) {
		User user = findUserById(userId);
		user = userToBeUpdated;
		return userRepository.save(user);
	}
	
	public void deleteUserById(Long userId) {
		 User user = findUserById(userId);
		 if(user.getRoles().equals("ROLE_ADMIN")) {
			 return;
		 }
		 Optional<Message> message =  messageRepository.findByUser(user);
		 if(message.isPresent()) {
			 messageRepository.delete(message.get());
			userRepository.delete(user);
		 }
		 else {
			 userRepository.delete(user);
		 }
		webSocketService.sendMessage("user");
	}
	
	public List<Question> getQuestionsAskedByUser(Long userId){
		User user =  findUserById(userId);
		return user.getQuestions();
	}
	
	public User findUserByAnswerId(Long id) {
		Answer answer = answerService.findAnswerById(id);
		return answer.getUser_answer();
	}
	
	public User findUserByQuestionId(Long id) {
		Question question = questionService.findQuestionById(id);
		return question.getUser_question();
	}
	
	public List<Question> findQuestionsByUserId(Long userId) {
		User user =  findUserById(userId);
		return user.getQuestions();
	}
	
	public List<Answer> findAnswersByUserId(Long userId){
		User user = findUserById(userId);
		return user.getAnswers();
	}
	
	public User getUserDetails() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userRepository.findByUsername(auth.getName());
		return user;
	}

	public List<Answer> getAnswersGivenByUsers(Long userId) {
		User user = findUserById(userId);
		return user.getAnswers();
	}

	public int approveQuestionById(Long questionId) {
		Question question = questionService.findQuestionById(questionId);
		question.setApprovedByAdmin(true);
		questionRepository.save(question);
		return 1;
	}
	
	public int approveAnswerById(Long answerId) {
		Answer answer = answerService.findAnswerById(answerId);
		answer.setApprovedByAdmin(true);
		answerRepository.save(answer);
		return 1;
	}
}
