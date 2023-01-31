package Crud.CrudBoard.board.service;

import Crud.CrudBoard.board.dto.CommentDTO;
import Crud.CrudBoard.board.entity.BoardEntity;
import Crud.CrudBoard.board.entity.CommentEntity;
import Crud.CrudBoard.board.repository.BoardRepository;
import Crud.CrudBoard.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    
    public Long save(CommentDTO commentDTO) {
        /* 부모 엔티티(boardEntity) 조회 */
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(commentDTO.getBoardId());
        if(optionalBoardEntity.isPresent()){
            BoardEntity boardEntity = optionalBoardEntity.get();
            // DTO로 받아온것을 Entity로 바꿔줘야 한다 ( for Entity는 보호해줘야 한다 )
            // DTO로 받아온것을 Entity로 바꿔주는 방법 말고 builder 라는 방법 또한 있다
            CommentEntity commentEntity = CommentEntity.toSaveEntity(commentDTO,boardEntity);
            return commentRepository.save(commentEntity).getId();
        } else {
            // 부모엔티티 조회 안되면 null 반환
            return null;
        }
    }

    public List<CommentDTO> findAll(Long boardId) {
        BoardEntity boardEntity = boardRepository.findById(boardId).get();
        List<CommentEntity> commentEntityList = commentRepository.findAllByBoardEntityOrderByIdDesc(boardEntity);
        /* EntityList -> DTOList */
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (CommentEntity commentEntity: commentEntityList) {
            CommentDTO commentDTO = CommentDTO.toCommentDTO(commentEntity, boardId);
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
    }
}
