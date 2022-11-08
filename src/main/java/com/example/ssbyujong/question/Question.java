package com.example.ssbyujong.question;


import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


import com.example.ssbyujong.answer.Answer;
import lombok.Getter;
import lombok.Setter;

//https://wikidocs.net/161165
//https://jobc.tistory.com/120
//https://doorbw.tistory.com/227


//@Entity: 데이터베이스에 저장하기 위해 사용자가 정의한 클래스. '객체' 그 자체. 'DB 테이블'과 '매핑'되는 '자바 클래스'.
//일반적으로, 'DB에서의 Table들을 객체화시킨 것'.
//'질문'과 '답변'을 할 수 있는 게시판 서비스를 만드려면, '질문'과 '답변'에 해당하는 'entity'가 있어야 함.

//'@Entity'를 붙여주어야, 이제 DB가 이 'entity 객체'를 인식하여 db에 저장시켜줄 수 있게 됨
//'Entity 객체' 내용 자체는 'dto 객체' 내용과 거의 유사함


import lombok.Getter;
import lombok.Setter;

@Setter //- 일반적으로 'entity 객체'를 작성할 때는, '메소드 Setter'를 구현하지 않기를 권함.
        //왜냐하면, 'entity'는 'db'와 바로 연결되어 있으므로, 'db를 자유롭게 변경할 수 있는 메소드 setter'를 허용하는 것은
        //안전하지 않다고 판단하기 때문.
        //- '메소드 Setter' 없이 entity'에 값을 저장할 수 있는 이유 :
        //'enttity를 생성할 경우'에는 '롬복의 @Builder'를 통한 빌드패턴을 사용하고,
        //'데이터를 변경해야 할 경우'에는 '그에 해당된느 메소드를 entity에 추가'하여 데이터를 변경하면 됨.
        //다만, 이 프로젝트파일에서는 복잡도를 낮추고, 원활한 설명을 위해 'entity'에 '메소드 setter'를 추가하여 진행하고 있음.
@Getter
@Entity
public class Question {

//기본적으로 '질문' 엔티티에는 최소한 아래와 같은 속성이 필요함
//<  속성명    :     설명   >
// id         : 질문의 고유 번호
//subject     : 질문의 제목
//content     : 질문의 내용
//create_date : 질문을 작성한 일시
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //고유번호 id 자동생성
    private Integer id;


    //'@Column' 속성
    //'entity의 속성'은 'DB 테이블의 컬럼명'과 일치하는데, 컬럼의 세부 설정을 위해 '@Column'을 사용함
    //'length'는 '컬럼의 길이를 제한할 때' 사용
    //'coulumnDefinition'은 '컬럼의 속성을 정의할 때' 사용
    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT") //'columnDefinitno~~'는 '내용'처럼 '글자 수를 제한할 수 없는 경우'에 사용됨.
    private String content;

    private LocalDateTime createDate;

    //'1개의 질문'에 'N개(여러 개)의 답변'이 달리는 구조이므로, 여기 '클래스  answer'에서도 'question 객체'를 참조할 수 있음
    //이 경우에는, 당연히 1:N 관계이므로, '@OneToMany'를 달아준다.
    //'1개의 질문'에 'N개(여러 개)의 답변'이기에 '답변의 구조는 List 형식'으로 구성해야 한다.

    //'mappedBy': '답변 entity(저기 건너편)'에서 참조한 entity인 '질문 entity(여기 클래스)'의 '속성명인 question'을
    //mappedBy에 전달해줘야 함
    //'cascade=CascadeType.REMOVE': '1개의 질문'에는 'N개(여러 개)의 답변'이 작성될 수 있음. 이 때, 질문을 삭제하면,
    //그에 달린 'N개(여러 개)의 답변들'도 모두 함께 삭제하기 위해, '@OneToMany'의 속성으로 'cascade=CascadeType.REMOVE'를 입력해줌
    @OneToMany(mappedBy = "question", cascade=CascadeType.REMOVE)
    private List<Answer> answerList;
}
