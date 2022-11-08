package com.example.ssbyujong.answer;

import com.example.ssbyujong.question.Question;
import com.example.ssbyujong.question.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

//컨트롤러에서는 서비스를 호출하고
//서비스에서는 리포지터리를 호출하고
//리포지터리는 db에 접근하여 데이터를 가져온다

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {

    //의존성주입. 여기 'AnswerController 객체' 안에서 이제 'QuestionService 객체'의 내용물을 사용할 수 있게 됨.
    private final QuestionService questionService;

    //- [ 질문에 대한 답변을 만드는 부분 ]
    @PostMapping("/create/{id}") //'POST 요청 URL'에만 반응하여 받아들인다.
    public String createAnswer(@PathVariable("id") Integer id, @RequestParam String content, Model model){
        //*** RequestParam 확실히 공부하기!

        Question question = this.questionService.getQuestion(id); //일단 먼저, '어떤 질문'인지를 알아야 하기 때문에 
                                                                  //그 '질문 데이터'를 'db'로부터 조회하여 가져옴


        //문자열 포맷팅 https://wikidocs.net/205#_3
        return String.format("redirect:/question/detail/%s", id); //'변수 id의 실제 int값'을 '%s'에 입력시켜주어서
                                                                  //그 값을 그대로 출력해줌. 여기서는 만약, id=3 이면,
                                                                  //리턴값: redirect:/question/detail/3 으로 된다!

    }
}
