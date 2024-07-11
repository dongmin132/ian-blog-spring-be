package com.blog.community.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "board")
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    private String boardTitle;

    private String boardContent;

    private int boardViewCount;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @OneToMany(mappedBy="board", cascade = CascadeType.ALL)
    private List<BoardImgEntity> boardImages;
}
