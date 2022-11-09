package com.example.ssbyujong.answer;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.example.ssbyujong.question.Question;
import com.example.ssbyujong.user.SiteUser;
import lombok.Getter;
import lombok.Setter;

//기본적으로 '답변' 엔티티에는 최소한 아래와 같은 속성이 필요함
//<  속성명    :     설명   >
//id          : 답변의 고유 번호
//question    : 질문(어떤 질문의 답변인지 알아야 하므로, 질문 속성이 필요함)
//content     : 답변의 내용
//create_date : 답변을 작성한 일시
@Getter
@Setter //원래라면, 'entity 객체'에서는 절.대. 'Setter'를 만들지 않는다!
@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;



    //'저 건너편 질문 entity의 변수 question'은 여기 '답변 entity'에서 '질문 entity'를 참조하기 위해 추가했음
    //예를 들어, '답변 객체(e.g: answer 객체)'를 통해, '질문 객체(e.g: question)'의 제목을 알고 싶다면,
    //'answer.getQuestion().getSubject()'처럼 접근할 수 있다.
    //하지만, 아래처럼 이렇게 속성만 딱 추가하면 안되고, '답변 entity'와 '질문 entity'가 '연결된 속성'이라는 것을
    //명시적으로 표시해줘야 한다.
    //즉, 아래와 같이 '변수 question' 위에 '@ManyToOne'을 추가해줘야 한다.
    //왜냐하면, '1개의 질문'에 'N개(여러 개)의 답변'이 달릴 수 있는 구조이기 때문.
    //따라서, '질문은 One(하나)'이 되고, '답변은 Many(많은 것)'이 된다. 즉, N(답변의 개수):1(질문의 개수) 관계임.
    //이렇게 '@ManyToOne'을 추가해주면, '질문 entity'와 '답변 entity'는 서로 연결됨.
    //(실제 DB에선느 'ForeignKey관계'가 생성됨)
    @ManyToOne
    private Question question;


    //- [ Answer에 속성 추가하기 ] : '3-08. entity 변경'
    //: '답변 entity'에 'author 속성'을 추가하기
    //'여러 개(N개)의 질문'이 '1명의 사용자'에게 작성될 수 있으므로, '@ManyToOne관계'가 성립됨.
    @ManyToOne
    private SiteUser author; //'SiteUser 엔티티'를 새롭게 'Answer 테이블'에 추가함
}
