package com.example.ssbyujong.question;


import com.example.ssbyujong.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service //스프링의 서비스로 만들기 위해서는, '서비스 클래스명 위'에 '@Service'를 붙여주면 된다.
         //이제 스프링은 '@Service'가 붙은 클래스를, '서비스 클래스'로 인식함
public class QuestionService {

    //'서비스 QuestionService'는 이제 '질문 repository'를 '생성자 활용 DI'를 통해 의존함.
    private final QuestionRepository questionRepository;



    //- [ 'service'가 'repository'에게 명령하여,
    //     db로부터 '질문 목록을 조회'하여, '질문 목록 데이터들'을 리턴해주는 메소드 작성 ]
    public List<Question> getList(){ //'getter' 아님!

        return this.questionRepository.findAll();
    }


    //- [ 'service'가 'repository'에게 명령하여,
    //     db로부터 '질문의 제목과 내용 조회'하여, '질문의 제목과 내용 데이터들'을 리턴해주는 메소드 작성 ]
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
}
