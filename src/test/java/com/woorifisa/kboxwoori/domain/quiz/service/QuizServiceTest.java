package com.woorifisa.kboxwoori.domain.quiz.service;

import com.woorifisa.kboxwoori.domain.quiz.dto.QuizRequestDto;
import com.woorifisa.kboxwoori.domain.quiz.dto.QuizResponseDto;
import com.woorifisa.kboxwoori.domain.quiz.dto.QuizResultResponseDto;
import com.woorifisa.kboxwoori.domain.quiz.entity.Quiz;
import com.woorifisa.kboxwoori.domain.quiz.exception.QuizDuplicatePartipation;
import com.woorifisa.kboxwoori.domain.quiz.repository.QuizRepository;
import com.woorifisa.kboxwoori.domain.user.entity.Club;
import com.woorifisa.kboxwoori.domain.user.entity.Gender;
import com.woorifisa.kboxwoori.domain.user.entity.Role;
import com.woorifisa.kboxwoori.domain.user.entity.User;
import com.woorifisa.kboxwoori.domain.user.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@Transactional
class QuizServiceTest {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuizService quizService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private String key;

    @BeforeEach
    void beforeEach() {
        User user = User.builder()
                .userId("userid")
                .gender(Gender.M)
                .role(Role.ROLE_USER)
                .birth(LocalDate.now())
                .phone("000")
                .point(0)
                .password("password")
                .svcAgmt(true)
                .name("name")
                .wooriLinked(true)
                .club(Club.NC)
                .addr("addr")
                .infoAgmt(true)
                .build();
        userRepository.save(user);
        key = "quiz:participant:" + LocalDate.now();
        redisTemplate.delete(key);
    }

    @Test
    @Order(1)
    void testGetCurrentQuizWithNoQuiz() {
        //퀴즈 없을 떄
        List<Quiz> emptyQuizList = new ArrayList<>();
        QuizResponseDto quizResponseDTO = quizService.getCurrentQuiz();

        //결과
        assertThat(quizResponseDTO).isNull();
    }

    @Test
    @Order(2)
    void testGetCurrentQuizwithQuiz() {
        //퀴즈 있을때
        Quiz quiz = Quiz.builder()
                .createdAt(LocalDate.now())
                .question("한화에서 등번호 1번인 선수는?")
                .choice1("노시환")
                .choice2("문현빈")
                .choice3("문동주")
                .choice4("이태양")
                .answer('3').build();

        quizRepository.save(quiz);

        QuizResponseDto quizResponseDTO = quizService.getCurrentQuiz();

        //결과 검증
        assertThat(quizResponseDTO).isNotNull();
        assertThat(quizResponseDTO.getQuestion()).isEqualTo("한화에서 등번호 1번인 선수는?");
        assertThat(quizResponseDTO.getChoice1()).isEqualTo("노시환");
        assertThat(quizResponseDTO.getChoice2()).isEqualTo("문현빈");
        assertThat(quizResponseDTO.getChoice3()).isEqualTo("문동주");
        assertThat(quizResponseDTO.getChoice4()).isEqualTo("이태양");
        assertThat(quizResponseDTO.getAnswer()).isNotNull();
    }

    @Test
    @Order(3)
    void testsubmitAnswerCorrect() {
        // db 사용하여 테스트
        char choice = '3';

        Quiz quiz = Quiz.builder()
                .createdAt(LocalDate.now())
                .question("한화에서 등번호 1번인 선수는?")
                .choice1("노시환")
                .choice2("문현빈")
                .choice3("문동주")
                .choice4("이태양")
                .answer('3').build();

        quizRepository.save(quiz);

        QuizResponseDto currentQuiz = quizService.getCurrentQuiz();
        QuizRequestDto quizRequestDTO = new QuizRequestDto(1L, '3');

        QuizResultResponseDto responseDTO = quizService.submitAnswer(quizRequestDTO, "userid");
        assertThat(responseDTO.getCorrect()).isTrue();
        assertThat(1L).isEqualTo(redisTemplate.opsForSet().size(key));
    }

    @Test
    @Order(4)
    void testSubmitAnswerIncorrect() {
        //오답시
        char choice = 2; //2번 선택

        Quiz quiz = Quiz.builder()
                .createdAt(LocalDate.now())
                .question("한화에서 등번호 1번인 선수는?")
                .choice1("노시환")
                .choice2("문현빈")
                .choice3("문동주")
                .choice4("이태양")
                .answer('3').build();

        quizRepository.save(quiz);

        //submitAnswer 메소드 호출
        QuizResponseDto quizResponseDTO = quizService.getCurrentQuiz();

        QuizRequestDto quizRequestDTO = new QuizRequestDto(1L, '2');
        //오답 제출
        QuizResultResponseDto responseDTO = quizService.submitAnswer(quizRequestDTO, "userid");

        //결과
        assertThat(responseDTO.getCorrect()).isFalse();
        assertThat(0L).isEqualTo(redisTemplate.opsForSet().size(key));
    }

    @Test
    @Order(5)
    void testSubmitAnswerAlreadyParticipated() {
        //이미 참여한 사용자 재참여 하려고 할 떄 테스트
        char choice = '3';
        Quiz quiz = Quiz.builder()
                .createdAt(LocalDate.now())
                .question("한화에서 등번호 1번인 선수는?")
                .choice1("노시환")
                .choice2("문현빈")
                .choice3("문동주")
                .choice4("이태양")
                .answer('3').build();
        quizRepository.save(quiz);

        QuizRequestDto quizRequestDTO = new QuizRequestDto(1L, '3');
        // 사용자 이미 참여한 걸로 표시
        quizService.submitAnswer(quizRequestDTO, "userid");

        //이미 참여한 사용자 재참여 하려고 할 떄
        assertThatThrownBy(() -> quizService.submitAnswer(quizRequestDTO, "userid")).isInstanceOf(QuizDuplicatePartipation.class);
    }
}











