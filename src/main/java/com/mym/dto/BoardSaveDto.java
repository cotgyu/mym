package com.mym.dto;

import com.mym.entity.Board;
import com.mym.entity.Member;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardSaveDto {

    private String title;
    private String content;
    private Member member;


    @Builder
    public BoardSaveDto(String title, String content, Member member){

        this.title = title;
        this.content = content;
        this.member = member;

    }

    public Board toEntity(){
        return Board.builder()
                .title(title)
                .content(content)
                .member(member)
                .build();
    }


}
