package com.example.ssbyujong.question;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//컨트롤러에서는 서비스를 호출하고
//서비스에서는 리포지터리를 호출하고
//리포지터리는 db에 접근하여 데이터를 가져온다

@RequestMapping("/question") //앞으로 여기 'QuestionController'에 매핑되는 URL요청은 무조건
                                //'www.localhost:8080/question/'으로 들어오는 것에만 이 컨트롤러가 매핑된다!
@RequiredArgsConstructor
@Controller
public class QuestionController {



//========================================================================================================

    //< '생성자를 작성'하여 'QuestionRepository 객체'를 'QuestionController 객체'에 의존성주입(DI) 시키는 방법 >
    //1.아래에 'QuestionRepository 객체'를 대변해줄 수 있는 '필드'를 선언하고
    //2.저 위에 '롬복 @RequiredArgsConstructor'를 추가시켜준다.
    //-----끝-----. 이제 DI 완료됨.
    //private final QuestionRepository questionRepository; //final로 해도 되고, 안 해도 됨. '생성자 주입'일 때만 'final 가능'함.
    //3.만약 @ReuqiredArgsConstructor를 붙히지 않았다면, '생성자 활용 DI'하기 위해 아래처럼 '이 컨트롤러'의 생성자도 만들어줬어야 함.
    //그런데, 롬복 @ReuqiredArgsConstructor를 써줌으로써, 아래 과정조차 생략해서, 이제 위에처럼 DI 의존하고 싶은 객체만
    //가져와서 여기서 필드로 선언만 해주면 DI 끝남 이제 ㄷㄷ..
    //
    //@Autowired(단, 이 컨트롤러에서 단일(기본)생성자만 만들 경우에는, @Autowired를 생략 가능함.
    //public QuestionController(QuestionRepository questionRepository){
    //  this.questionRepository = questionRepository
    //}
    //-----어쨌든 DI 끝-----


    //< '생성자를 작성'하여 'QuestionService 객체'를 'QuestionController 객체'에 의존성주입(DI) 시키는 방법 >
    //바로 위 내용과 원리 동일함.
    private final QuestionService questionService;

//========================================================================================================




//========================================================================================================

    //- [ 데이터를 '조회'하여, '뷰 페이지'에 전달하기 ]
    //'뷰 페이지에서 질문 목록을 조회'하여 출력해보기.
    //'뷰 페이지에서 질문 목록을 조회'하기 위해서는, '질문 repository'를 사용해야 함.
    //1.'질문 repository'로 '질문 목록을 조회'한 후,
    //2.이를 'model 객체'를 사용하여, '뷰 페이지'에 전달함.


    //'model 객체'는 '자바 클래스'와 '뷰 페이지(템플릿)' 간의 '연결고리' 역할을 함.
    //'model 객체'에 '데이터값'을 담아 두면, 이제 '그 템플릿'에서 '그 데이터값'을 사용할 수 있음

        /*
        List<Question> questionList = this.questionRepository.findAll();//'repository의 메소드 findAll'을 사용하여
                                                                        //'db로부터 가져온 데이터값'을
                                                                        //'질문 목록 데이터를 뜻하는 변수 questionList'
                                                                        //에 담아둠.

        model.addAttribute("questionList", questionList);//'reopository'를 통해,
                                                         // db로부터 가져온 '실제 데이터값 questionList(우)'를
                                                         //"변수명 questionList(좌)"라는 이름으로
                                                         //이제 '뷰 question_list'에서 사용할 수 있게 된다.

        return "question_list";

        cf) 'repository 객체'와 'entity 객체'는 '컨트롤러'에서 바로 사용할 수 없게끔 하는 것이 좋다!
            왜냐하면, 이 둘은 'db'와 직접 맞닿아있어서, 컨트롤러나 타임리프 같은 탬플릿 엔진에 전달하여 사용하는 것은 좋지 않기 때문
            컨트롤러나 타임리프에서 사용하는 데이터 객체는, 그 속성을 변경하여 비즈니스적인 요구를 처리해야 하는 경우가 많은데,
            'entity'를 직접 사용하여 속성을 변경한다면, 이로 인해 'db의 테이블 칼럼'이 '변경되어 엉망'이 될 수도 있기 때문

            이러한 이유로, 'entity 클래스'는 '컨트롤러'에서 사용할 수 없게끔 설계해야 한다.
            그러기 위해서는, 'entity 클래스 대신 사용할 DTO 클래스'가 필요함.
            그리고, 그 'entity 객체'를 'DTO 객체'로 변환시켜주는 작업도 필요함.
            이 변환 작업을 바로 'service 클래스'에서 담당한다.
            'service 클래스'는 '컨트롤러'와 'repository'의 '중간자 입장'에서,
            'entity 객체'와 'DTO 객체'를 서로 변환하여, 양쪽 모두('컨트롤러'와 'repository')에 전달해주는 역할임

            이제, '컨트롤러 --->  서비스 ---> 리포지터리'의 구조로 데이터 처리가 이뤄짐
         */


    
        //'Paging 관련 코드 작성 전'에 '데이터를 '조회'하여, '뷰 페이지'에 전달하기' 위해,
        // 최초에 작성한 코드. 그런데, 'Paging 관련 코드'를 이 코드 바탕으로 작성하기 때문에,
        //이 최초의 '데이토 조회' 코드는 주석처리해줌.
        /*
        @RequestMapping("/list")
        public String list(Model model){
        //위와 같은 이유로, 위에서 먼저 작성했던 repository 통한 DI는 삭제하고, 'service 객체'를 통해 DI를 진행한다 앞으로.
        List<Question> questionList = this.questionService.getList(); //'questionSerivce의 메소드 getList'
                                                                      //:'repository의 메소드 findAll'을 사용하여
                                                                      //'db로부터 가져온 데이터값'을
                                                                      //'질문 목록 데이터 집합체'로 '메소드 getList'에 저장.
                                                                      //그리고, 여기서는 '변수 questionList'에 그
                                                                      //'질문 목록 데이터 집합체가 담긴 메소드 getList'를
                                                                      //집어넣음.

        model.addAttribute("questionList", questionList);//바로 위 과정을 통해,
                                                                     //db로부터 가져온 '실제 데이터값 questionList(우)'를
                                                                     //"변수명 questionList(좌)"라는 이름으로
                                                                     //이제 '뷰 question_list'에서 사용할 수 있게 된다.
        return "question_list";
    }
         */

//========================================================================================================




//========================================================================================================

    //- [ 질문 게시글을 등록 ] : '2-15. 질문 등록과 폼'

    @GetMapping("/create") //'질문 등록하기'의 URL 요청에 대한 Http Method의 REST API 방식은 'GET 요청'이기에
                              //컨트롤러에서도 '@GetMapping'을 사용하여 받아줌
    public String questionCreate(){

        return "question_form"; //'메소드 questionCreate'은 '뷰 question_form'을 '렌더링'하여 출력해줌.
    }

    //'질문 게시글 생성'을 'POST 방식'으로 처리할 수 있도록 아래를 작성해줌
    //바로 위 메소드를 오버라이딩 했음(매개변수의 형태가 다른 경우에 가능)
    //'질문을 등록하는 템플릿'인 '뷰 question_form' 내부의 필드인 '변수 subject(질문의 제목)'와 '변수 content(질문의 내용)'를
    //여기 '메소드 questionCreate의 매개변수'로 받는다.
    @PostMapping("/create")
    public String questionCreate(@RequestParam String subject, @RequestParam String content){



        //이제, 입력으로 받은 '질문의 제목 subject'와 '질문의 내용 content'를 '저장'하자!
        
        return "redirect:/question/list"; //'질문 게시글 저장'이 완료되면, 사용자에게 '질문 목록 페이지'를 보여줌
    }
//========================================================================================================




//========================================================================================================

    //- [ 페이징 구현하기 ] : '3-02. 페이징'
    //1. < Repository >
    //: 'QuestionRepository의 '메소드 findAll'에 '인자값'으로 '인터페이스 Pageable'을 넣어준다.
    //  이를 통해, 이제 '페이징'을 사용할 수 있음
    //(='Pageable 객체'를 매개변수로 입력으로 받아서, 'Page<Question> 타입 객체'를 리턴하는 '메소드 findAll'을
    // 생성함.)
    //2. < Service >
    //: 'QuestionService'의 '메소드 getList'를 아래처럼 작성함.
    //3. < Controller >
    //: 이에 맞춰서 작성해줌

    //이클립스 RequestParam01 파일의 '방식 3'과 동일함. 참조하기.
    // '@RequestParam(value="page", defaultValue="0") int page'라는 매개변수를 '메소드 list'에 추가해줌.
    //왜냐하면, 'http://localhost:8080/question/list?page=0' 처럼 GET방식으로 요청된 URL에서 'page값'을 가져오기 위해서.
    //URL에 '페이지 파라미터인 page'가 전달되지 않을 경우에는 디폴트 값으로 0이 되도록 설정한 것임.
    //왜냐하면, 스프링부트 페이징의 첫페이지 번호는 1이 아닌 0이기 때문이다.
    @RequestMapping("/list")
    public String list(Model model, @RequestParam(value="page", defaultValue = "0") int page){

        Page<Question> paging = this.questionService.getList(page); //'인터페이스 Page'를 구현하는 'Page 객체'

        model.addAttribute("paging", paging);

        return "question_list";
    }
//========================================================================================================




//========================================================================================================

    //- [ 질문 상세 화면 보여주기 ]
    //'질문 목록의 제목을 클릭했을 때, 상세화면이 보여지도록' 하는 것.
    @RequestMapping("detail/{id}")
    public String detail(@PathVariable("id") Integer id, Model model){
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);

        return "question_detail";
    }

//========================================================================================================




//========================================================================================================







}
