package Crud.CrudBoard.board.controller;

import Crud.CrudBoard.board.dto.CommentDTO;
import Crud.CrudBoard.board.repository.CommentRepository;
import Crud.CrudBoard.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;
    /*
        @ResponseBody 가 필요한 이유는 ajax로부터 데이터를 받아오기 때문이다.
        -> 정적 파일로 이동하는게 아니라, 문자 그대로를 출력해준다

        @ResponseBody => ResponseEntity 사용
     */
    @PostMapping("/save")
    public ResponseEntity save(@ModelAttribute CommentDTO commentDTO) {
        System.out.println("commentDTO = " + commentDTO);
        Long saveResult = commentService.save(commentDTO);
        if (saveResult != null) {
            // 작성 성공하면 댓글 목록을 가져와서 리턴 ( 다시 전체 댓글을 가지고 와서 화면에 뿌려주는게 맞다 )
            // 댓글 목록 : 해당 게시글의 댓글 전체
            List<CommentDTO> commentDTOList = commentService.findAll(commentDTO.getBoardId());
            return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("해당 게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }
    }
}
