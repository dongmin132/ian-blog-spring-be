package com.blog.community.dto;

import com.blog.community.entity.MemberEntity;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@ToString
public class CustomUserDetails implements UserDetails {
    private final Long memberId;
    private final String email;
    private final String password;
//    private final List<String> roles;     지금은 사용안함

    public CustomUserDetails(MemberEntity memberEntity) {
        this.memberId = memberEntity.getMemberId();
        this.email = memberEntity.getMemberEmail();
        this.password = memberEntity.getMemberPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public Long getMemberId() {
        return memberId;
    }

    @Override  
    public boolean isAccountNonExpired() {  
        return true;  
    }  
  
    @Override  
    public boolean isAccountNonLocked() {  
        return true;  
    }  
  
    @Override  
    public boolean isCredentialsNonExpired() {  
        return true;  
    }  
  
    @Override  
    public boolean isEnabled() {  
        return true;  
    }  
}