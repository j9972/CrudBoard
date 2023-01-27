package Crud.CrudBoard.board.controller;

import Crud.CrudBoard.board.dto.BoardDTO;
import Crud.CrudBoard.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/save")
    public String saveForm() {
        return "save";
    }

    /*
        DTO = VO = Entity != Entity
        DTO 클래스에 담에 save.html 에 있는 데이터들을 한번에 넘겨준다
        save.html 에 있는 데이터 : boardWriter, boardPass 이렇게 5개
    */
    @PostMapping("/save")
    // @ModelAttribute BoardDTO boardDTO -> setter 로 데이터를 자동으로 넣어줌
    public String save(@ModelAttribute BoardDTO boardDTO) {
        System.out.println("boardDTO = " + boardDTO);
        boardService.save(boardDTO);

        return "index";
    }

    @GetMapping("/")
    public String findAll(Model model) {
        // DB에서 전체 게시글을 가져와서 list.html에 뿌려준다
        List<BoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("boardList", boardDTOList);
        return "list";
    }
}
