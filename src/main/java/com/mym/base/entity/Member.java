package com.mym.base.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor @AllArgsConstructor
public class Member extends BaseEntity{

    @Id @GeneratedValue
    private Long id;

    private String username;

    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<MemberRoles> roles;


}
