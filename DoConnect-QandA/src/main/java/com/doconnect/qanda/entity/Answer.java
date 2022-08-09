package com.doconnect.qanda.entity;


import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "answers")
@Data
@NoArgsConstructor
public class Answer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "answer_id")
	private Long answerId;
	
	private String answer;
	
	private LocalDateTime answeredDateAndTime;
	
	private boolean isApprovedByAdmin;
	
	@ManyToOne
	@JoinColumn(name = "q_id", referencedColumnName = "question_id")
	@ToString.Exclude
	private Question question;
	
	@ManyToOne
	@JoinColumn(name = "u_id",referencedColumnName = "user_id")
	@ToString.Exclude
	private User user_answer;
	
	
}
