package com.example.ssbyujong.answer;


import com.example.ssbyujong.question.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;



    //[ 답변 생성 후, 저장하기 ]: '2-11. 답변 등록'
    //'답변 생성'을 위해 '메소드 create'를 추가함.
    //이제, '컨트롤러'에서 이 '메소드 create'를 사용할 수 있게 됨.
    public void create(Question question, String content){ //'답변 생성'을 위해 '메소드 create'를 추가함.
                                                           //인자값으로 받은 question 객체, content 객체를
                                                           //사용하여, 'Answer 객체'를 생성 후, 저장함.
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion((question));
        this.answerRepository.save(answer); //'Answer 객체'를 생성 후, '저장'함.
    }

}
