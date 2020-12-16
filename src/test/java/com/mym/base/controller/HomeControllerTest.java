package com.mym.base.controller;

import com.mym.base.entity.Member;
import com.mym.base.entity.MemberRoles;
import com.mym.base.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


@Transactional
@Rollback(false)
class HomeControllerTest extends BaseControllerTest{

    @PersistenceContext
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;


    @Test
    public void jpaTest(){

        Member testMember =  Member.builder()
                .username("testMember")
                .password(("pass"))
                .roles(Set.of(MemberRoles.USER))
                .build();

        memberRepository.save(testMember);


        em.flush();
        em.clear();


        Member findMember = memberRepository.findById(testMember.getId()).get();

        assertThat(testMember.getId()).isEqualTo(findMember.getId());


    }

}