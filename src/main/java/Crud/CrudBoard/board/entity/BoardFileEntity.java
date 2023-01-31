package Crud.CrudBoard.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "board_file_table")
public class BoardFileEntity extends BaseEntity{
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String originalFileName;

    @Column
    private String storedFileName;

    // Board랑 BoardFile의 관계를 생각해야함 ( 하나의 게시글에는 여러 파일이 올 수 있다.)
    // 따라서 BoardFileEntity는 자식 Entity이다. ( N 쪽이 주인이다. )
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;

    public static BoardFileEntity toBoardFileEntity(BoardEntity boardEntity, String originalFileName, String storedFileName) {
        BoardFileEntity boardFileEntity = new BoardFileEntity();
        boardFileEntity.setOriginalFileName(originalFileName);
        boardFileEntity.setStoredFileName(storedFileName);
        boardFileEntity.setBoardEntity(boardEntity);
        return boardFileEntity;
    }
}
