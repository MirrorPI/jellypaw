package a201.boardview.repository;

import a201.boardview.data.entity.ImageView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageViewRepository extends JpaRepository<ImageView, Long> {

    void deleteByBoardView_Id(Long boardId);

    List<ImageView> findByBoardView_IdIn(List<Long> boardIds);
}
