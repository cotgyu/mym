package com.mym.base.repository;

import com.mym.base.entity.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.mym.base.entity.QMember.member;


@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Member> findAllCustomMember() {
        return queryFactory.selectFrom(member).fetch();
    }
}
