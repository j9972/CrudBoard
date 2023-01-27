package Crud.CrudBoard.board.entity;

import Crud.CrudBoard.board.dto.BoardDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// DB 테이블 역할을 하는 클래스
@Entity
@Getter @Setter
@Table(name = "board_table")
public class BoardEntity extends  BaseEntity{ // 시간 관련 정보를 상속 받음
    @Id // Id 컬럼 -> pk 컬럼 지정 ( 필수 )
    @GeneratedValue
    private Long id;

    @Column(length = 20, nullable = false) // 크기 20, not null
    private String boardWriter;

    @Column // default : 크기 255, null 가능
    private String boardPass;

    @Column
    private String boardTitle;

    @Column(length = 500)
    private String boardContents;

    @Column
    private int boardHits;

    // 옮겨 담는 작업
    public static BoardEntity toSaveEntity(BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
        boardEntity.setBoardPass(boardDTO.getBoardPass());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardHits(0); // 기본값은 0이기 때문에
        return boardEntity;
    }
}
