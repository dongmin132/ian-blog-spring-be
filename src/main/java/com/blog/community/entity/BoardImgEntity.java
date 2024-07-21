package com.blog.community.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@Table(name="board_image")
@AllArgsConstructor
@NoArgsConstructor
public class BoardImgEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_image_id")
    private Long boardImgId;

    private String boardImageName;
    private String boardImageUrl;

    @Column
    private String boardImageRepYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity board;

    public static BoardImgEntity createBoardImg(String boardImageName, String boardImageUrl, String repImgYn, BoardEntity board) {
        return new BoardImgEntity(null, boardImageName, boardImageUrl, repImgYn, board);
    }

}
