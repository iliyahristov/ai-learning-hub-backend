package com.ih.ailh.service;

import com.ih.ailh.dto.AnswerDTO;
import com.ih.ailh.dto.QuestionDTO;
import com.ih.ailh.dto.QuizDTO;
import com.ih.ailh.model.*;
import com.ih.ailh.repository.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class QuizService {
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final MaterialRepository materialRepository;
    private final ModelMapper modelMapper;

    public QuizDTO createQuiz(QuizDTO quizDTO) {
        Material material = materialRepository.findById(quizDTO.getMaterialId())
                .orElseThrow(() -> new RuntimeException("Material not found"));

        Quiz quiz = modelMapper.map(quizDTO, Quiz.class);
        quiz.setMaterial(material);
        Quiz savedQuiz = quizRepository.save(quiz);

        // Create questions and answers
        if (quizDTO.getQuestions() != null) {
            for (QuestionDTO questionDTO : quizDTO.getQuestions()) {
                Question question = createQuestion(savedQuiz, questionDTO);

                if (questionDTO.getAnswers() != null) {
                    for (AnswerDTO answerDTO : questionDTO.getAnswers()) {
                        createAnswer(question, answerDTO);
                    }
                }
            }
        }

        return getQuizById(savedQuiz.getId());
    }

    private Question createQuestion(Quiz quiz, QuestionDTO questionDTO) {
        Question question = modelMapper.map(questionDTO, Question.class);
        question.setQuiz(quiz);
        return questionRepository.save(question);
    }

    private Answer createAnswer(Question question, AnswerDTO answerDTO) {
        Answer answer = modelMapper.map(answerDTO, Answer.class);
        answer.setQuestion(question);
        return answerRepository.save(answer);
    }

    public QuizDTO getQuizById(Long id) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));
        return convertToDTO(quiz);
    }

    public QuizDTO getQuizByMaterialId(Long materialId) {
        Material material = materialRepository.findById(materialId)
                .orElseThrow(() -> new RuntimeException("Material not found"));

        Quiz quiz = quizRepository.findByMaterial(material)
                .orElse(null);

        return quiz != null ? convertToDTO(quiz) : null;
    }

    public BigDecimal calculateScore(Long quizId, Map<Long, Long> userAnswers) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        List<Question> questions = questionRepository.findByQuiz(quiz);
        int totalQuestions = questions.size();
        int correctAnswers = 0;

        for (Question question : questions) {
            Long userAnswerId = userAnswers.get(question.getId());
            if (userAnswerId != null) {
                Answer answer = answerRepository.findById(userAnswerId)
                        .orElse(null);

                if (answer != null && answer.isCorrect()) {
                    correctAnswers++;
                }
            }
        }

        return BigDecimal.valueOf((double) correctAnswers / totalQuestions * 100);
    }

    private QuizDTO convertToDTO(Quiz quiz) {
        QuizDTO dto = modelMapper.map(quiz, QuizDTO.class);

        List<QuestionDTO> questionDTOs = questionRepository.findByQuiz(quiz).stream()
                .map(question -> {
                    QuestionDTO questionDTO = modelMapper.map(question, QuestionDTO.class);
                    List<AnswerDTO> answerDTOs = answerRepository.findByQuestion(question).stream()
                            .map(answer -> modelMapper.map(answer, AnswerDTO.class))
                            .collect(Collectors.toList());
                    questionDTO.setAnswers(answerDTOs);
                    return questionDTO;
                })
                .collect(Collectors.toList());

        dto.setQuestions(questionDTOs);
        return dto;
    }
}