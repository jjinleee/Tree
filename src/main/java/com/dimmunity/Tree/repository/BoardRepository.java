package com.dimmunity.Tree.repository;

import com.dimmunity.Tree.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<BoardEntity,Long> {
    //<> 안에 넣을 내용만 신경써주면 된다.

    //게시글 조회 쿼리
    //update board_table set board_hits=board_his+1 where id=?
    //기존의 조회수에서 하나를 올리는 과정
    @Modifying
    @Query(value = "update BoardEntity b set b.boardHits=b.boardHits+1 where b.id=:id")
    void updateHits(@Param("id") Long id);
}