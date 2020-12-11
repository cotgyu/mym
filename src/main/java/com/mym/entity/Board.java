package com.mym.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Board extends BaseEntity{

    @Id @GeneratedValue
    private Long seq;

    private String title;

    private String content;

    @ManyToOne
    @JoinColumn(name = "id")
    private Member member;

    @Builder
    public Board(String title, String content, Member member){
        this.title = title;
        this.content = content;
        this.member = member;
    }

}
