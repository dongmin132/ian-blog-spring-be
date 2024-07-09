package com.blog.community.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false)
    private String memberEmail;

    @Column(nullable = false)
    private String memberPassword;

    @Column(nullable = false)
    private String memberNickname;

    @Column
    private String memberProfileImage;

    public static MemberEntity createMember(String memberEmail, String memberPassword, String memberNickname, BCryptPasswordEncoder bCryptPasswordEncoder, String memberProfileImage) {
        return new MemberEntity(null, memberEmail, bCryptPasswordEncoder.encode(memberPassword), memberNickname, memberProfileImage);
    }
}

