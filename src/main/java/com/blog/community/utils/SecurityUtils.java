//package com.blog.community.utils;
//
//import com.blog.community.dto.member.request.CustomUserDetails;
//import com.blog.community.exception.member.CustomMemberException;
//import com.blog.community.exception.member.MemberException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//
//
//public class SecurityUtils {
//    private SecurityUtils() {
//
//    }
//
//    public static Long getCurrentMemberId() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if(authentication == null || authentication.getPrincipal() == null) {
//            throw new CustomMemberException(MemberException.MEMBER_NOT_FOUND);
//        }
//
//        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
//        Long memberId = customUserDetails.getMemberId(); // getMemberId 메서드를 사용하여 memberId를 가져옵니다.
//        return memberId;
//    }
//
//}
