package com.mym.dto;

import com.mym.entity.Member;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberSaveDto {

    private String username;

    private String password;

    @Builder
    public MemberSaveDto(String username, String password){

        this.username = username;
        this.password = password;
    }

    public Member toEntity(){
        return Member.builder()
                .username(username)
                .password(password)
                .build();
    }

}
