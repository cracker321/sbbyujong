package com.example.ssbyujong.question;


import com.example.ssbyujong.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service //스프링의 서비스로 만들기 위해서는, '서비스 클래스명 위'에 '@Service'를 붙여주면 된다.
         //이제 스프링은 '@Service'가 붙은 클래스를, '서비스 클래스'로 인식함
public class QuestionService {


    //========================================================================================================

    //'서비스 QuestionService'는 이제 '질문 repository'를 '생성자 활용 DI'를 통해 의존함.
    private final QuestionRepository questionRepository;



    //========================================================================================================

    //- [ 'service'가 'repository'에게 명령하여,
    //     db로부터 '질문 목록을 조회'하여, '질문 목록 데이터집합들'을 리턴하라고 해주는 메소드 작성 ] : '2-09. 서비스'
    //    이것을, 페이징 구현하기 위한 메소드로 바꿨음 저~ 아래에서 이제.
    //    그래서, 기존 메소드인 아래 것은 주석처리해줌

/*
    public List<Question> getList{

        return this.questionRepository.findAll();
    }
 */

    //========================================================================================================




    //========================================================================================================

    //https://devlog-wjdrbs96.tistory.com/414   :paging 구현 순서 얘기해줌
    //https://junhyunny.github.io/spring-boot/jpa/junit/jpa-paging/
    //http://ojc.asia/bbs/board.php?bo_table=LecJpa&wr_id=281
    //https://sharekim-dev.tistory.com/29     :주요 객체 및 메소드 설명

    //- [ 페이징 구현하기 ] : '3-02. 페이징'
    //          +
    //- [ 질문글 작성일시 최신 순(=역순)으로 조회하기 ] : '3-02. 페이징' 하단
    //   : 일반적으로, 게시판은 가장 최신글이 가장 먼저 보인다.

    //1. < Repository >
    //: 'QuestionRepository의 '메소드 findAll'에 '인자값'으로 '인터페이스 Pageable'을 넣어준다.
    //   이를 통해, 이제 '페이징'을 사용할 수 있음
    //(='Pageable 객체'를 매개변수로 입력으로 받아서, 'Page<Question> 타입 객체'를 리턴하는 '메소드 findAll'을
    // 생성함.)
    //2. < Service >
    //: 'QuestionService'의 '메소드 getList'를 아래처럼 작성함.
    //3. < Controller >
    //: 이에 맞춰서 작성해줌
    public Page<Question> getList(int page){ //'getter' 아님!
                                             //'int 타입의 페이지번호를 뜻하는 변수 page'를 입력받아서,
                                             //'해당 페이지의 데이터집합들'을 리턴하는 메소드로 변경했음.

        //[ 질문글 작성일시 최신 순(=역순)으로 조회하기 ]
        //: 게시물을 역순으로 조회하기 위해서는 위와 같이 PageRequest.of 메서드의 세번째 파라미터로 Sort 객체를 전달해야 한다.
        //Sort.Order 객체로 구성된 리스트에 Sort.Order 객체를 추가하고 Sort.by(소트리스트)로 소트 객체를 생성할 수 있다.
        //작성일시(createDate)를 역순(Desc)으로 조회하려면 Sort.Order.desc("createDate") 같이 작성한다.
        //만약 작성일시 외에 추가로 정렬조건이 필요할 경우에는 sorts 리스트에 추가하면 된다.
        //이렇게 수정하고 첫번째 페이지를 조회하면 이제 가장 최근에 등록된 순으로 게시물이 출력되는 것을 확인한 수 있다.
        List<Sort.Order> sorts = new ArrayList<>(); //
        sorts.add(Sort.Order.desc("createDate"));

        //[ 페이징 구현하기 ]
        Pageable pageable = PageRequest.of(page,10, Sort.by(sorts));//'인터페이스 Pageable의 내장클래스 PageRequest'.
                                                                         //'변수 page': '조회할 페이지의 번호'
                                                                         //'10': 한 페이지에 보여줄 게시물의 개수
                                                                         //이제, 데이터 전체를 조회하지 않고도,
                                                                         //해당 페이지의 데이터만 조회하도록 쿼리가 변경됨.
        return this.questionRepository.findAll(pageable);
    }
    //========================================================================================================




    //========================================================================================================

    //- [ 'service'가 'repository'에게 명령하여,
    //     db로부터 '질문의 제목과 내용 조회'하여, '질문의 제목과 내용 데이터들'을 리턴하라고 해주는 메소드 작성 ]
    public Question getQuestion(Integer id){
        Optional<Question> question = this.questionRepository.findById(id);
        if(question.isPresent()){ //'클래스 Optional의 메소드 isPresent': db에 접근하여 위에 들어온 id값에 해당하는 값이 있는 경우 true,
                                  // 								    db에 접근하여 위에 들어온 id값에 해당하는 값이 없으면 false를 반환
                                  // 즉, 'Optional 객체'가 '값을 포함하고 있는지 여부'를 확인해주는 메소드
            return question.get();//'클래스 Optional의 메소드 get': db에 접근하여 위에 들어온 id값에 해당 값이 존재하면, 해당 값을 리턴하고
                                  //							  db에 접근하여 위에 들어오해당 값이 없으면, '예외 발생'시킴
        } else{
            throw new DataNotFoundException("question not found"); //'클래스 DataNotFoundException'을 따로 만들어줘야함.
        }
    }

//========================================================================================================



//========================================================================================================

    //- [ 질문 게시글을 등록 ] : '2-15. 질문 등록과 폼'
    //1. < Controller >
    //
    //2. < Service >
    //
    //3. < Repository >
    //

}
