package com.example.ssbyujong.question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


//https://wikidocs.net/160890
//< repository >
//'entity'만으로는 'db의 테이블'에 '저장'하거나, '조회'할 수 없다. 데이터 처리를 위해서는, 실제 db에 접근하여 연동하는
//'DB Layer 접근자 repository'가 필요함.
//'repository'는 'entity에 의해 생성된 db 테이블'에 '접근하는 메소드들(e.g: 메소드 findAll, 메소드 save ..)을 사용하기 위한
//인터페이스'임. '데이터 처리'를 위해서는, 'db 테이블'에 어떤 값을 넣거나, 값을 조회하는 등의 CRUD가 필요하다.
//이 때, 이러한 CRUD를 어떻게 처리할지 정의하는 계층이 바로 repository임.
//이제, 'QuestionRepository'를 통해 'db의 question 테이블'에 '데이터를 저장, 조회'할 수 있음

//========================================================================================================
//여기에, '메소드 findBy + entity 클래스에 작성된 속성명('답변 entity'의 경우는 id, question, content, create_date)'을
//메소드 형식으로 작성해주면, 이제 그 해당 속성의 값으로 db로부터 데이터를 조회할 수 있음.
//'repository의 메소드명(e.g: findByAll, findById, findBySubject ...)'은 '데이터를 조회하는 쿼리문의 where 조건을 결정하는 역할'임.
//매우 많은 조합을 가질 수 있음.
/*
< 항목 >         :                           < 예제 >                                      :       < 설명 >
And             :   findBySubjectAndContent(String subject, String content)              :  여러 컬럼을 and로 검색
Or              :   findBySubjectOrContent(String subject, String content)               :  여러 컬럼을 or로 검색
Between         :   findByCreateDateBetween(LocalDateTime fromDate, LocalDateTimetoDate) :  컬럼을 between으로 검색
LessThan        :   findByLessThan(Integer id)                                           :  작은 항목 검색
GreaterThanEqual:   findByIdGreaterThanEqual(Integer id)                                 :  크거나 같은 항목 검색
Like            :   findBySubjectLike(String subject)                                    :  like 검색
In              :   findBySubjectIn(String[] subjects)                                   :  여러 값들 중에 하나인 항목 검색
OrderBy         :   findBySubjectOrderByCreateDateAsc(String subject)                    :  검색 결과를 정렬하여 전달

cf) 응답 결과가 여러 건인 경우에는, repository 메소드의 리턴타입을 Question이 아닌 'List<Question>'으로 해야 한다.
보다 자세한 쿼리 생성 규칙은, 여기 공식 문서 참조
https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
 */
//========================================================================================================

//제네릭으로  < 해당 entity의 타입(Answer), 해당 entity의 pk 속성 타입(Long) > 이렇게 지정해줘야 함
public interface QuestionRepository extends JpaRepository<Question, Integer> {


    //'질문 entity의 '제목(subject)' 속성'으로 '데이터를 조회'하려면, 'repository의 메소드 findBySubject'를 사용해야 함.
    //그런데, '질문 repository'는 '메소드 findBySubject'를 기본적으로 제공하지 않는다.
    //따라서, '메소드 findBySubject'를 사용하려면, '인터페이스 QuestionRepository'의 내부에 아래 코드들을 넣는다
    Question findBySubject(String subject);


    //'질문 entity의 '제목(subject 속성)'과 '내용(content 속성)'' '함께' 조회하기.
    //'And 조건'으로 조회함. 아래와 같은 'repository 메소드' 추가해주기
    Question findBySubjectAndContent(String subject, String content);


    // - [
    //'질문 entity의 '제목' 속성'에 '특정 문자열'이 포함되어 있는 데이터를 조회하기.
    List<Question> findBySubjectLike(String subject);



    //- [ 페이징 구현하기 ] : '3-02. 페이징'
    //1. < Repository >
    //: 'QuestionRepository의 '메소드 findAll'에 '인자값'으로 '인터페이스 Pageable'을 넣어준다.
    //   이를 통해, 이제 '페이징'을 사용할 수 있음
    //(='Pageable 객체'를 매개변수로 입력으로 받아서, 'Page<Question> 타입 객체'를 리턴하는 '메소드 findAll'을
    // 생성함.)
    //2. < Service >
    //: 'QuestionService'의 '메소드 getList'를 아래처럼 작성함.
    //3. < Controller >
    //: 이에 맞춰서 작성해줌
    Page<Question> findAll(Pageable pageable); //JPA 관련 라이브러리에, 이미 페이징을 위한 패키지들이 들어있음


}
