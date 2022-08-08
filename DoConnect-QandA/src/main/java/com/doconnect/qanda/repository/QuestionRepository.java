package com.doconnect.qanda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doconnect.qanda.entity.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long>{
	List<Question> findByIsApprovedByAdmin(boolean isApprovedByAdmin);
}
