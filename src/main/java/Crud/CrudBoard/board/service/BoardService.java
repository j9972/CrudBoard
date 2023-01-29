package Crud.CrudBoard.board.service;

import Crud.CrudBoard.board.dto.BoardDTO;
import Crud.CrudBoard.board.entity.BaseEntity;
import Crud.CrudBoard.board.entity.BoardEntity;
import Crud.CrudBoard.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        // DTO -> Entity로 옮겨 담는
        BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO);
        boardRepository.save(boardEntity); // 이렇게하면 insert 코드가 나가게 된다.
    }

    @Transactional
    public List<BoardDTO> findAll() {
        // Entity ( BoardEntity ) -> DTO ( BoardDTO ) 로 옮겨 담기
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        List<BoardDTO> boardDTOList = new ArrayList<>();
        for (BoardEntity boardEntity: boardEntityList) {
            boardDTOList.add(BoardDTO.toBoardDTO(boardEntity));
        }
        return boardDTOList;
    }

    // 조회수 증가 method
    @Transactional // 별도의 method를 사용하는 경우는 @Transactional 이 필요하다
    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }

    @Transactional
    public BoardDTO findById(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            BoardDTO boardDTO = BoardDTO.toBoardDTO(boardEntity);
            return boardDTO;
        } else {
            return null;
        }
    }

    public BoardDTO update(BoardDTO boardDTO) {
        // toUpdateEntity 는 Entity 파일에 만들어 준다
        BoardEntity boardEntity = BoardEntity.toUpdateEntity(boardDTO);
        boardRepository.save(boardEntity);
        return  findById(boardEntity.getId());
    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }
}
