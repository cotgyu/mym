package com.mym.dto;

import com.mym.entity.Board;
import com.mym.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Getter @NoArgsConstructor @AllArgsConstructor
public class BoardRequestDto {

    private Long seq;
    private String title;
    private String content;

    private Member member;

    private String lastModifiedDate;
    private String lastModifiedBy;


    public BoardRequestDto(Board board) {
        seq = board.getSeq();
        title = board.getTitle();
        content = board.getContent();

        member = board.getMember();

        lastModifiedDate = toStringDateTime(board.getLastModifiedDate());
        lastModifiedBy = board.getLastModifiedBy();

    }

    private String toStringDateTime(LocalDateTime localDateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:SS");

        return Optional.ofNullable(localDateTime)
                .map(formatter::format)
                .orElse("");
    }


}
