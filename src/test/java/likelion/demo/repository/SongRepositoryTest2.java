package likelion.demo.repository;

import jakarta.transaction.Transactional;
import likelion.demo.domain.Song;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class SongRepositoryTest2 {

    @Autowired
    private SongRepository songRepository;

    @Test
    public void failSearchTitle() {
        // 테스트 데이터 없음

        // 제목 검색
        List<Song> foundSongs = songRepository.findByTitle("Nonexistent Song");

        // 존재하지 않음 -> 검색 실패
        assertTrue(foundSongs.isEmpty());       // 빈 리스트이면 True
    }
}
