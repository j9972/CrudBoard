package Crud.CrudBoard.board.service;

import Crud.CrudBoard.board.dto.BoardDTO;
import Crud.CrudBoard.board.entity.BaseEntity;
import Crud.CrudBoard.board.entity.BoardEntity;
import Crud.CrudBoard.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// 서비스 클래스에서 하는 작업
// DTO -> Entity ( Entity class 에서 할 것 )
// Entity -> DTO ( DTO class 에서 할 것 )
// 컨트롤러로부터 데이터를 넘겨받을 때에는 DTO 로 넘겨 받는다
// Repository 로 넘겨줄때는 Entity
// DB를 조회할 때는 repository 로 부터 Entity 로 받아옴 , 컨트롤러로 넘겨줄때는 이걸 DTO 로 전환해서 보냄

/*
    Entity 는 view 로 노출하면 안된다 ( entity 는 service 에서만 사용하기 )
    Entity - Repository
    DTO - Controller
 */

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    public void save(BoardDTO boardDTO) {
        BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO);
        boardRepository.save(boardEntity); // 이렇게하면 insert 코드가 나가게 된다.
    }

    public List<BoardDTO> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        List<BoardDTO> boardDTOList = new ArrayList<>();

        // Entity -> DTO 로 옮겨 담기
    }
}
