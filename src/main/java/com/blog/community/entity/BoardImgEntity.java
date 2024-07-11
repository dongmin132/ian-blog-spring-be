package com.blog.community.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="board_image")
public class BoardImgEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardImgId;

    private String boardImageName;
    private String boardImageUrl;
    private String repImgYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity board;
}
