package likelion.demo.repository;

import jakarta.transaction.Transactional;
import likelion.demo.domain.Song;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class SongRepositoryTest1 {

    @Autowired
    private SongRepository songRepository;

    @Test
    public void searchTitleSuccess() {
        // Test Song을 만들어 DB에 저장
        Song song = Song.builder()
                .title("Test Song")
                .build();

        songRepository.save(song);

        // 위에서 만든 Test Song을 검색
        List<Song> foundSongs = songRepository.findByTitle("Test Song");

        // 두 값이 같으면 테스트 성공
        assertEquals(1, foundSongs.size());     // 검색된 곡의 개수가 1개인지 확인
        assertEquals("Test Song", foundSongs.get(0).getTitle());        // 검색된 곡의 제목이 일치하는지 확인
    }
}
