package com.doconnect.qanda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.doconnect.qanda.entity.Answer;
import com.doconnect.qanda.entity.Question;
import com.doconnect.qanda.serviceImpl.QuestionServiceImpl;

@RestController
@RequestMapping("/question")
@CrossOrigin(origins = "*")
public class QuestionController {

	@Autowired
	QuestionServiceImpl questionService;
	
	@PostMapping("/addQuestion")
	public Question addQuestion(@RequestBody Question question,@RequestParam(name = "userId") Long userId) {
		return questionService.addQuestion(question,userId);
	}
	
	@PostMapping("/fileUpload/{id}")
	public int questionFileUpload(@PathVariable Long id,@RequestParam MultipartFile file) {
		return questionService.storeImage(file, id);
	}
	
	@GetMapping("/getQuestions")
	public List<Question> getQuestins() {
		return questionService.getQuestionList();
	}
	
	@GetMapping("/getQuestionWithTheseWords")
	public List<Question> getQuestionsWithTheseWords(@RequestParam(name = "words") String words){
		return questionService.getQuestionsWithTheseWords(words);
	}
	
	@GetMapping("/findAnswersByQuestionId/{questionId}")
	public List<Answer> findAnswersByQuestionId(@PathVariable Long questionId){
		return questionService.findAnswersByQuestionId(questionId);
	}
	
	@GetMapping("/findQuestionByAnswerId/{answerId}")
	public Question findQuestionByAnswerId(@PathVariable Long answerId) {
		return questionService.findQuestionByAnswerId(answerId);
	}
	
}
