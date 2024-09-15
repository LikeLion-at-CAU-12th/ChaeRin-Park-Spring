package likelion.demo.repository;

import likelion.demo.domain.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SongRepository extends JpaRepository<Song, Long> {
    List<Song> findByTitle(String title); // 제목으로 곡 검색
}
