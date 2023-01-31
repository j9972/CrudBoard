package Crud.CrudBoard.board.service;

import Crud.CrudBoard.board.dto.BoardDTO;
import Crud.CrudBoard.board.entity.BaseEntity;
import Crud.CrudBoard.board.entity.BoardEntity;
import Crud.CrudBoard.board.entity.BoardFileEntity;
import Crud.CrudBoard.board.repository.BoardFileRepository;
import Crud.CrudBoard.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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
    private final BoardFileRepository boardFileRepository;
    public void save(BoardDTO boardDTO) throws IOException {
        // 파일 첨부 여부에 따라 로직 분리
        // BoardEntity boardEntity = null;
        if (boardDTO.getBoardFile().isEmpty()) {
            // 첨부 파일 없음
            // DTO -> Entity로 옮겨 담음
            BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO);
            boardRepository.save(boardEntity); // 이렇게하면 insert 코드가 나가게 된다.
        } else {
            // 첨부 파일 있음
            /*
                1. DTO에 담긴 파일을 꺼냄 -> boardFile
                2. 파일의 이름 가져옴 -> originalFilename
                3. 서버 저장용 이름을 만듦 -> storedFileName
                // 내사진.jpa => 123543566(난수)_내사진.jpa
                4. 저장 경로 설정 -> savePath
                5. 해당 경로에 파일 저장 -> boardFile.transferTo(new File(savePath));
                6. board_table에 해당 데이터 save 처리
                7. board_file_table에 해당 데이터 save 처리
             */
            // DB에 데이터 저장하는 단계 ( 다중 파일은 부모 데이터 먼저 저장이 필요하기에 이 부분 먼저 실행 )
            BoardEntity boardEntity = BoardEntity.toSaveFileEntity(boardDTO);
            Long saveId = boardRepository.save(boardEntity).getId();
            BoardEntity board = boardRepository.findById(saveId).get();

            for( MultipartFile boardFile : boardDTO.getBoardFile()) { // 반복문으로 DTO를 꺼냄

                // MultipartFile boardFile = boardDTO.getBoardFile(); -> DTO를 꺼내는 코드
                String originalFilename = boardFile.getOriginalFilename();
                String storedFileName = System.currentTimeMillis() + "_" + originalFilename; // 내사진.jpa => 123543566(난수)_내사진.jpa
                String savePath = "/Users/jungsuyoung/springBoot/CrudBoard_img/" + storedFileName;
                boardFile.transferTo(new File(savePath));

                BoardFileEntity boardFileEntity = BoardFileEntity.toBoardFileEntity(board, originalFilename, storedFileName);
                boardFileRepository.save(boardFileEntity);
            }
        }
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

    public Page<BoardDTO> paging(Pageable pageable) {
        // pageable.getPageNumber(); // 몇 페이지가 호출됬는지 알 수 있다
        int page = pageable.getPageNumber() -1;
        int pageLimit = 3; // 한 페이지에 보여줄 글 갯수
        // 한 페이지에 보여줄 글 갯수를 사용자가 정하게끔 하려면 pageLimit 를 parameter 로 받아와서 짐용하면 된다

        // 한페이지당 3개씩 글을 보여주고 정렬 기준은 id 기준으로 내림파순 정렬
        // page 위치에 있는 값을 0부터 시작
        // Page<BoardEntity> 의 Page는 아래의 데이터들을 전달해준다.
        Page<BoardEntity> boardEntities =
                boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        // Page 객체가 return 을 페이지 기준으로 하고, 아래는 page 객체가 보여주는 값들이다.
        System.out.println("boardEntities.getContent() = " + boardEntities.getContent()); // 요청 페이지에 해당하는 글
        System.out.println("boardEntities.getTotalElements() = " + boardEntities.getTotalElements()); // 전체 글갯수
        System.out.println("boardEntities.getNumber() = " + boardEntities.getNumber()); // DB로 요청한 페이지 번호
        System.out.println("boardEntities.getTotalPages() = " + boardEntities.getTotalPages()); // 전체 페이지 갯수
        System.out.println("boardEntities.getSize() = " + boardEntities.getSize()); // 한 페이지에 보여지는 글 갯수
        System.out.println("boardEntities.hasPrevious() = " + boardEntities.hasPrevious()); // 이전 페이지 존재 여부
        System.out.println("boardEntities.isFirst() = " + boardEntities.isFirst()); // 첫 페이지 여부
        System.out.println("boardEntities.isLast() = " + boardEntities.isLast()); // 마지막 페이지 여부

        // 목록에서 보여줘야 할 데이터 : id, writer, title, hits, createdTime
        Page<BoardDTO> boardDTOS = boardEntities.map(
                // board 는 Entity 이다. ( map을 사용해서 Entity인 board를 DTO로 변환시켜줄 것 )
                board -> new BoardDTO(board.getId(),
                        board.getBoardWriter(),
                        board.getBoardTitle(),
                        board.getBoardHits(),
                        board.getCreatedTime()));
        return boardDTOS;
    }
}
