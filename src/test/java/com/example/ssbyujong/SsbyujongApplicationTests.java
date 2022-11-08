package com.example.ssbyujong;

import com.example.ssbyujong.question.Question;
import com.example.ssbyujong.question.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


//작성한 repository를 테스트하기 위해, JUnit 기반의 스프링부트의 테스트 프레임워크를 사용해보자
//'@SpringBootTest': '클래스 SsbyujongApplicationTests'가 '스프링부트 테스트 클래스'임을 의미함.
//'@Autowired': 스프링의 DI기능. 아래에서는 이제, 'questionRepository 객체'를 스프링이 대신 자동으로 가져와서 생성해줌.
//				객체를 주입하는 방식에는 '@Autowired'외에도 'Setter'나 '생성자'를 사용하는 방식이 있다.
//				다만, '순환참조 문제'와 같은 이유로, '@Autowired'보다는 '생성자'를 통한 객체 주입방식이 권장됨
//				다만, '테스트 코드'의 경우에는, '생성자를 통한 객체의 주입이 불가능'하므로, '테스트 코드 작성 시에만'
//				@Autowired를 사용하고, '실제 코드 작성 시'에는 '생성자를 통한 객체 주입방식'을 사용하겠음


@SpringBootTest
class SsbyujongApplicationTests {

	@Autowired //@Autowired를 활용한 의존성 주입방법: 이렇게 딱 '필드 위에 @Autowired'만 붙히면 됨.
			   //'Setter'를 활용한다면, 'Setter' 위에 붙혀도 됨.
			   //근데, @Autowired를 사용한 DI를 할 때는, 보통 '필드 위'에 많이 붙힘.
			   //왜냐하면, 'Setter 주입' 사용한다 해도, 'Setter 메소드 위'에 '@Autowired' 안 붙혀도 되는 게,
			   //'필드 위'에 @Autowired 붙히면, 스프링은 자동으로 'setter 주입'을 사용하게 됨.
	private QuestionRepository questionRepository;

	//- '@Test': '메소드 testJpa'가 '테스트 메소드'임을 나타냄. 아래 클래스를 'JUnit'으로 실행하면,
	// 이제 '@Test'가 붙은 메소드들이 실행된다.
	//- 'JUnit': '테스트코드'를 작성하고, 작성한 테스트코드를 실행하기 위해 사용하는 '자바의 프레임워크'.
	@Test
	void testJpa() {
		
		//===================================================================================================
		//- [ repository의 메소드 findAll ] : 데이터 조회
		//: 'question 테이블'에 작성된 모든 데이터를 조회
		//'테이블 question'에 저장된 모든 데이터를 조회하기 위해, 'repository'의 '메소드 findAll'을 사용함
		//'메소드 findAll': 데이터를 조회할 때 사용하는 메소드
		List<Question> all = this.questionRepository.findAll();

		//우리는 총 2건의 데이터를 저장했기 때문에, 데이터의 사이즈는 2가 되어야 함. 데이터 사이즈가 2인지를 확인하기 위해 아래를 사용.
		//'JUnit의 메소드 assertEquals': 'assertEquals(기대값, 실제값)'. '기대값'과 '실제값'이 동일한지 여부를 조사
		//만약, '기대값'과 '실제값'이 동일하지 않으면, 테스트는 실패로 처리됨.
		assertEquals(2, all.size());

		Question q1 = all.get(0);
		assertEquals("sbb가 무엇인가요?", q1.getSubject());
		//===================================================================================================



		//===================================================================================================
		//- [ repository의 메소드 findById ]
		//: 'question 테이블'에 작성된 데이터 중, '질문 entity의 특정 id값'에 해당하는 데이터를 조회하기 위해서 이 메소드 사용
		//'questionRepository'가 'findById'로 '값을 반환'할 때, 그 '반환값의 리턴타입'이 'Question'이 아니기 때문에,
		//아래처럼 '클래스 Optional'을 사용하여 'Question'에 씌워줌.
		//'URL로 들어온 변수 id값'을 찾았는데, 만약 db에 '해당 id값이 없다면', 'null'을 반환하라는 의미.
		//'클래스 Optional'은 'null'을 유연하게 처리하기 위해 사용하는 클래스로, 아래와 같이
		//1)'메소드 isPresent'로 null인지 아닌지 여부를 확인한 후에,
		//2)'메소드 get'으로 '실제 Question 객체 값'을 얻어와야 한다.
		Optional<Question> oq = this.questionRepository.findById(1);

		//https://samba-java.tistory.com/17
		//https://www-swpro-com.tistory.com/60
		if(oq.isPresent()){ //'클래스 Optional의 메소드 isPresent': db에 접근하여 해당하는 값이 있는 경우 true,
							// 									  해당하는 값이 없으면 false를 반환
							//즉, 'Optional 객체'가 '값을 포함하고 있는지 여부'를 확인해주는 메소드
			Question q2 = oq.get(); //'클래스 Optional의 메소드 get': db에 해당 값이 존재하면, 해당 값을 리턴하고
									//								db에 해당 값이 없으면, '예외 발생'시킴
			assertEquals("sbb가 무엇인가요?", q2.getSubject());
		}
		//===================================================================================================



		//===================================================================================================
		//- [ repository의 메소드 findBySubject ]
		//: '질문 entity의 subject 속성'으로 '데이터를 조회'하기
		//'질문 repository'는 '메소드 findBySubject'를 기본적으로 제공하지 않는다.
		//따라서, '메소드 findBySubject'를 사용하려면, '인터페이스 QuestionRepository'의 내부에 아래 코드들을 넣는다
		//'Question findBySubject(String subject);'
		Question q3 = this.questionRepository.findBySubject("sbb가 무엇인가요?");
		assertEquals(1, q3.getId());
		//===================================================================================================



		//===================================================================================================
		//- [ repository의 메소드 findBySubjectAndContent ]
		//: '질문 entity의 '제목(subject 속성)'과 '내용(content 속성)'' '함께' 조회하기.
		//'And 조건'으로 조회함. '인터페이스 repository'에 '관련 repository 메소드' 추가해줬음
		Question q4 = this.questionRepository.findBySubjectAndContent("sbb가 무엇인가요?",
				"sbb에 대해서 알고 싶습니다");
		assertEquals(1, q4.getId()); //'1번 id값'인 데이터이기 때문에(저 아래, 테스트용으로 데이터 2개 넣었을 때
											 //"sbb가 무엇인가요?"와 "sbb에 대해서 알고 싶습니다"는 '1번 id값'인 데이터에
											 //속하기 때문), 당연히 '기댓값은 1'이고, '실제값'은 그것의 id값을 db로부터 조회해오는
											 // 것임.
		//===================================================================================================



		//===================================================================================================
		//- [ repository의 메소드 findBySubjectAndContent ]
		//: '질문 entity의 '제목' 속성'에 '특정 문자열'이 포함되어 있는 데이터를 조회하기.
		//'인터페이스 repository'에 '관련 repository 메소드' 추가해줬음.
		List<Question> qList = this.questionRepository.findBySubjectLike("sbb%");
		//sbb% : 'sbb로 시작하는 문자열'
		//%sbb : 'sbb로 끝나는 문자열'
		//%sbb%: 'sbb를 포함하는 문자열'

		Question q5 = qList.get(0);
		assertEquals("sbb가 무엇인가요?", q5.getSubject());
		//===================================================================================================



		//===================================================================================================
		//- [ 데이터 수정하기 ]
		//1.'1번 id값의 질문글'을 조회하기
		Optional<Question> oq2 = this.questionRepository.findById(1);
		assertTrue(oq2.isPresent()); //'클래스 Optional의 메소드 isPresent': db에 접근하여 위에 들어온 id값에 해당하는 값이 있는 경우 true,
									 // 								   db에 접근하여 위에 들어온 id값에 해당하는 값이 없으면 false를 반환
									 // 즉, 'Optional 객체'가 '값을 포함하고 있는지 여부'를 확인해주는 메소드
		Question q6 = oq2.get(); //'클래스 Optional의 메소드 get': db에 접근하여 위에 들어온 id값에 해당하는 값이 존재하면, 해당 값을 리턴하고
								 //								 db에 접근하여 위에 들어온 id값에 해당하는 값이 없으면, '예외 발생'시킴

		//2.'질문 entity의 제목(subject) 속성'을 '수정'하기
		q6.setSubject("수정된 제목");

		//3.'수정된 제목을 저장'하기 : 'repository의 자동저장 메소드 save' 사용하여 저장함.
		this.questionRepository.save(q6); //
		//===================================================================================================



		//===================================================================================================
		//- [ 데이터 삭제하기 ]
		//: 첫 번째 질문을 삭제하기
		assertEquals(2, this.questionRepository.count()); //'질문 entity'의 '총 데이터 건수'를 리턴해줌
		Optional<Question> oq3 = this.questionRepository.findById(1); //첫 번째 질문의 id값인 1을 repository에 넣어줘서
																	  //그 repository가 db에 접근하여 해당 1번 질문글의
																	  //상세 내역을 가져오라고 시키는 것
		assertTrue(oq3.isPresent()); //'클래스 Optional의 메소드 isPresent': db에 접근하여 해당하는 값이 있는 경우 true,
									 // 								  해당하는 값이 없으면 false를 반환
									 //즉, 'Optional 객체'가 '값을 포함하고 있는지 여부'를 확인해주는 메소드
		Question q7 = oq3.get(); //'클래스 Optional의 메소드 get': db에 해당 값이 존재하면, 해당 값을 리턴하고
								 //								db에 해당 값이 없으면, '예외 발생'시킴
		this.questionRepository.delete(q7); //'1번 질문글'을 '삭제'함
		assertEquals(1, this.questionRepository.count()); //이제 삭제했으니, 총 남아있는 게시글 수는 1개임
																  //즉, 실제 데이터는 1개가 있는 것임
		//===================================================================================================



		//===================================================================================================
		//[ 답변 데이터 생성 후 조회하기 ]





		//===================================================================================================



		//===================================================================================================
		//[ 답변 조회하기 ]



		//===================================================================================================



		//===================================================================================================
		//[ 답변에 연결된 질문 찾기 vs 질문에 달린 답변 찾기 ]


		//===================================================================================================





		//테스트용으로, db에 아래 데이터 총 2개를 넣어봄.
		/*
		Question q1 = new Question(); //'질문 entity의 q1 엔티티 객체' 생성
		q1.setSubject("sbb가 무엇인가요?");
		q1.setContent("sbb에 대해서 알고 싶습니다");
		q1.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q1); //'QuestionRepository'를 이용하여, 위 값들을 db에 저장(repository의 메소드 save)
		)
		Question q2 = new Question(); //'질문 entity의 q2 엔티티 객체' 생성
		q2.setSubject("스프링부트 모델 질문입니다");
		q2.setContent("id는 자동으로 생성되나요?");
		q2.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q2); //'QuestionRepository'를 이용하여, 위 값들을 db에 저장(repository의 메소드 save)
		*/




	}

	

}
