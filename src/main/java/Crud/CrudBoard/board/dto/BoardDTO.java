package Crud.CrudBoard.board.dto;

import Crud.CrudBoard.board.entity.BoardEntity;
import lombok.*;

import java.time.LocalDateTime;

/*
    DTO = VO = Bean ( != Entity )
 */
@Getter @Setter
@ToString // 필드값 확인할 때 사용 ( 필수는 아님 )
@NoArgsConstructor // 기본생성자
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성
public class BoardDTO {
    private Long id;
    private String boardWriter;
    private String boardPass;
    private String boardTitle;
    private String boardContents;
    private int boardHits; // 조회수
    private LocalDateTime boardCreatedTime; // 작성시간 -> 시간 관련은 별도 파일
    private LocalDateTime boardUpdatedTime; // 수정시간
    
    // Entity -> DTO 로 옮겨 담기
    public static BoardDTO toBoardDTO(BoardEntity boardEntity) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(boardEntity.getId());
        boardDTO.setBoardWriter(boardEntity.getBoardWriter());
        boardDTO.setBoardPass(boardEntity.getBoardPass());
        boardDTO.setBoardTitle(boardEntity.getBoardTitle());
        boardDTO.setBoardContents(boardEntity.getBoardContents());
        boardDTO.setBoardHits(boardEntity.getBoardHits());
        boardDTO.setBoardCreatedTime(boardEntity.getCreatedTime());
        boardDTO.setBoardUpdatedTime(boardEntity.getUpdatedTime());

//        if (boardEntity.getFileAttached() == 0) {
//            boardDTO.setFileAttached(boardEntity.getFileAttached()); // 0
//        } else {
//            boardDTO.setFileAttached(boardEntity.getFileAttached()); // 1
//            // 파일 이름을 가져가야 함.
//            // orginalFileName, storedFileName : board_file_table(BoardFileEntity)
//            // join
//            // select * from board_table b, board_file_table bf where b.id=bf.board_id
//            // and where b.id=?
//            boardDTO.setOriginalFileName(boardEntity.getBoardFileEntityList().get(0).getOriginalFileName());
//            boardDTO.setStoredFileName(boardEntity.getBoardFileEntityList().get(0).getStoredFileName());
//        }

        return boardDTO;
    }
}
