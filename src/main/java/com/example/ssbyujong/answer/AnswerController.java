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

    //'생성자 작성 통한 의존성주입'. 
    //여기 'AnswerController 객체' 안에서 이제 'QuestionService 객체'의 내용물을 사용할 수 있게 됨.
    //'생성자 작성'은 컨트롤러 위의 '롬복 @RequiredArgsConstructor'가 대신해줌
    private final QuestionService questionService;

    //'생성자 작성 통한 의존성주입'
    //여기 'AnswerController 객체' 안에서 이제 'AnswerSerive 객체'의 내용물을 사용할 수 있게 됨.
    //'생성자 작성'은 컨트롤러 위의 '롬복 @RequiredArgsConstructor'가 대신해줌.
    private final AnswerService answerService;

    
    
    //========================================================================================================
    
    //- [ 질문에 대한 답변을 만드는 부분 ]. '2-11. 답변 등록'
    //- [ 답변 생성 후, 저장하기 ]: '2-11. 답변 등록'
    @PostMapping("/create/{id}") //'POST 요청 URL'에만 반응하여 받아들인다.
    public String createAnswer(@PathVariable("id") Integer id, @RequestParam String content, Model model){
        //< @RequestParam String content >
        //'뷰 question_detail'에서 '답변으로 입력한 내용(content)'을 얻기 위해 추가됨
        //'해당 답변 내용에 해당하는 textarea의 name 속성명'이 'content'이 때문에, '변수명도 content'를 사용해야 함
       /* 아래는 '뷰 question_detail'의 '답변 내용' 관련 부분
        <form th:action="@{|/answer/create/${question.id}|}" method="post">
          <textarea name="content" id="content" rows="15"></textarea>
          <input type="submit" value="답변등록">
        </form>
*/

        Question question = this.questionService.getQuestion(id); //일단 먼저, '어떤 질문'인지를 알아야 하기 때문에 
                                                                  //그 '질문 데이터'를 'db'로부터 조회하여 가져옴

        this.answerService.create(question, content); //'AnswerService 객체의 메소드 create'를 호출하여
                                                      //이제 '답변을 생성 후, 저장'할 수 있게 했다.

        //문자열 포맷팅 https://wikidocs.net/205#_3
        return String.format("redirect:/question/detail/%s", id); //'변수 id의 실제 int값'을 '%s'에 입력시켜주어서
                                                                  //그 값을 그대로 출력해줌. 여기서는 만약, id=3 이면,
                                                                  //리턴값: redirect:/question/detail/3 으로 된다!
    }
//========================================================================================================




//========================================================================================================





//========================================================================================================




//========================================================================================================
    //- [ 답변에 작성자 저장하기 ] : '3-08. 엔티티 변경'
    //Answer과 Question에 이후에 추가된 속성값 'author 속성'이 있으므로, '질문'과 '답변' 저장 시에, 'author'도 함께 저장할 수 있음







}
