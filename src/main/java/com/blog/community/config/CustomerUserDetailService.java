package com.blog.community.config;

import com.blog.community.dto.member.request.CustomUserDetails;
import com.blog.community.entity.MemberEntity;
import com.blog.community.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;
	public CustomerUserDetailService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return memberRepository.findByEmail(email)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));
    }

    private UserDetails createUserDetails(MemberEntity memberEntity) {
        return new CustomUserDetails(memberEntity.getMemberId(), memberEntity.getMemberEmail(), memberEntity.getMemberPassword());
    }


}