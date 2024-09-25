package likelion.demo.repository;

import jakarta.transaction.Transactional;
import likelion.demo.domain.Member;
import likelion.demo.domain.Playlist;
import likelion.demo.domain.Song;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class PlaylistRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private SongRepository songRepository;

    @Test
    public void makePlaylist() {
        // 우선 멤버를 만듦
        Member member = Member.builder()
                .name("Chaelin 1")
                .email("Chaelin@mutsa.com")
                .build();
        memberRepository.save(member);

        Playlist playlist = Playlist.builder()
                .name("Chaelin's Playlist")
                .build();

        // 플레이리스트를 만듦
        playlistRepository.save(playlist);

        // 플레이리스트가 잘 만들어졌는지 확인
        Playlist foundPlaylist = playlistRepository.findById(playlist.getId()).get();
        assertEquals("Chaelin's Playlist", foundPlaylist.getName());
    }


    @Test
    public void makePlaylistFail() {
        // 멤버를 만듦
        Member member = Member.builder()
                .name("Chaelin 2")
                .email("chaelin@mutsa.com")
                .build();
        memberRepository.save(member);

        // 플레이리스트 생성
        Playlist playlist1 = Playlist.builder()
                .name("My Playlist")
                .build();
        playlistRepository.save(playlist1);

        Playlist playlist2 = Playlist.builder()
                .name("My Playlist") // 같은 이름의 플레이리스트 생성
                .build();

        // 테스트
        assertThrows(DataIntegrityViolationException.class, () -> {
            playlistRepository.save(playlist2);
        });
    }

    @Test
    public void successAddSong() {
        // 플레이리스트 생성
        Playlist playlist = Playlist.builder()
                .name("Chaelin 3")
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

    @Test
    public void failAddSong() {
        // 플레이리스트 생성
        Playlist playlist = Playlist.builder()
                .name("Chaelin 4")
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