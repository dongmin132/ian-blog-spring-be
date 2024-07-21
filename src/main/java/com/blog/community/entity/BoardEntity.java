package com.blog.community.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "board")
@Getter
@ToString(exclude = {"member", "boardImages"})
@NoArgsConstructor
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

    @OneToMany(mappedBy="board", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<BoardImgEntity> boardImages = new ArrayList<>();

    private BoardEntity(String boardTitle, String boardContent, int boardViewCount, MemberEntity member) {
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.boardViewCount = boardViewCount;
        this.member = member;
    }

    public static BoardEntity createBoard(String boardTitle, String boardContent, MemberEntity member) {
        return new BoardEntity(boardTitle, boardContent, 0, member);
    }

    public void addBoardImage(BoardImgEntity boardImage) {
        this.boardImages.add(boardImage);
        boardImage.setBoard(this);
    }

    public void updateContent(String content) {
        this.boardContent = content;
    }

    public void removeBoardImage(BoardImgEntity boardImage) {
        this.boardImages.remove(boardImage);
        boardImage.setBoard(null);
    }

}
