package likelion.demo.repository;

import jakarta.transaction.Transactional;
import likelion.demo.domain.Playlist;
import likelion.demo.domain.Song;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class PlaylistRepositoryTest4 {

    @Autowired
    private PlaylistRepository playlistRepository;

    @Test
    public void failAddSong() {
        // 플레이리스트 생성
        Playlist playlist = Playlist.builder()
                .name("My Playlist")
                .build();
        playlistRepository.save(playlist);

        Song song = Song.builder()
                .title(null)  // 필수 필드가 null로 설정된 곡
                .build();

        // 예외 발생하지 않으면 테스트 실패
        assertThrows(IllegalArgumentException.class, () -> {
            playlist.addSong(song);
        });
    }
}
