package likelion.demo.repository;

import jakarta.transaction.Transactional;
import likelion.demo.domain.Playlist;
import likelion.demo.domain.Song;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class PlaylistRepositoryTest3 {

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private SongRepository songRepository;

    @Test
    public void successAddSong() {
        // 플레이리스트 생성
        Playlist playlist = Playlist.builder()
                .name("My Playlist")
                .build();
        playlistRepository.save(playlist);

        // 음악 생성
        Song song = Song.builder()
                .title("Test Song")
                .build();
        songRepository.save(song);

        // 음악을 플레이리스트에 담기
        playlist.addSong(song);
        playlistRepository.save(playlist);

        // 잘 담겼는지 확인
        Playlist foundPlaylist = playlistRepository.findById(playlist.getId()).get();
        assertEquals(1, foundPlaylist.getSongs().size());
        assertEquals("Test Song", foundPlaylist.getSongs().get(0).getTitle());
    }
}
