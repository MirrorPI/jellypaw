package a201.boardview.repository;

import a201.boardview.data.entity.BoardView;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BoardViewRepository extends JpaRepository<BoardView, Long> {
    List<BoardView> findAllByUserId_Id(Long userIdId);
    List<BoardView> findAllByUserId_nickname(String nickname);
    List<BoardView> findAllByPlaceId(Long placeId);


    @Query("""
        select b
        from BoardView b
        where (
            :cursorCreatedAt is null
            or b.createdAt < :cursorCreatedAt
            or (b.createdAt = :cursorCreatedAt and b.id < :cursorId)
        )
        order by b.createdAt desc, b.id desc
        """)
    Slice<BoardView> findSliceByCursor(
            @Param("cursorCreatedAt") LocalDateTime cursorCreatedAt,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );

    @Query("""
        select b
        from BoardView b
            join fetch b.userId u
            left join fetch b.comment c
            left join fetch b.likeCount lc
            left join fetch b.viewCount vc
        where (
            :cursorCreatedAt is null
            or b.createdAt < :cursorCreatedAt
            or (b.createdAt = :cursorCreatedAt and b.id < :cursorId)
        )
        order by b.createdAt desc, b.id desc
        """)
    Slice<BoardView> findSliceByCursorWithFetchJoin(
            @Param("cursorCreatedAt") LocalDateTime cursorCreatedAt,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );

}
