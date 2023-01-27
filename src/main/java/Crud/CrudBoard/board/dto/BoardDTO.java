package Crud.CrudBoard.board.dto;

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
}
