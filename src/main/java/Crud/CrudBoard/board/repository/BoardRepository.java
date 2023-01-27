package Crud.CrudBoard.board.repository;

import Crud.CrudBoard.board.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    // 조회수 증가 -> DB 기준으로 update board_table set board_hits=board_hits+1 where id=?
    @Modifying // update, delete 쿼리를 쓸 때는 필수 어노테이션
    @Query(value = "update BoardEntity b set b.boardHits=b.boardHits + 1 where b.id = :id")
    void updateHits(@Param("id") Long id); // :id(계속 바뀌는 부분) 는 param의 id를 생각하면 된다
}
