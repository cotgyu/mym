package com.mym.service;

import com.mym.dto.MemberRequestDto;
import com.mym.dto.MemberSaveDto;
import com.mym.entity.Member;
import com.mym.entity.MemberRoles;
import com.mym.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    private final ModelMapper modelMapper;

    private PasswordEncoder passwordEncoder;

    public List<MemberRequestDto> findAllMember(){

        return memberRepository.findAll()
                .stream()
                .map(MemberRequestDto::new)
                .collect(Collectors.toList());
    }

    public long addMember(MemberSaveDto memberSaveDto){

        return memberRepository.save(memberSaveDto.toEntity()).getId();
    }

    public long updateMember(long id, MemberSaveDto memberSaveDto){

        Optional<Member> findMember = memberRepository.findById(id);

        if(!findMember.isPresent()){
            return -1;
        }

        Member member = findMember.get();
        modelMapper.map(memberSaveDto, member);

        memberRepository.save(member);

        return member.getId();
    }

    public MemberRequestDto getMemberByUsername(String username){

        Optional<Member> findMember = memberRepository.findByUsername(username);

        if(!findMember.isPresent()){
            return null;
        }

        MemberRequestDto returnDto = new MemberRequestDto();

        modelMapper.map(returnDto, findMember.get());

        return returnDto;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Member> byUsername = memberRepository.findByUsername(username);

        Member member = byUsername.get();

        return new User(member.getUsername(), member.getPassword(), authorities(member.getRoles()));
    }


    private Collection<? extends GrantedAuthority> authorities(Set<MemberRoles> roles) {

        return roles.stream().map(r -> {
            return new SimpleGrantedAuthority("ROLE_"+ r.name());
        }).collect(Collectors.toSet());
    }
}
