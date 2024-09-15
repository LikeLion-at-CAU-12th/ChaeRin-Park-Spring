package likelion.demo.repository;

import jakarta.transaction.Transactional;
import likelion.demo.domain.Member;
import likelion.demo.domain.Playlist;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class PlaylistRepositoryTest2 {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PlaylistRepository playlistRepository;

    @Test
    public void makePlaylistFail() {
        // 멤버를 만듦
        Member member = Member.builder()
                .name("Chaelin")
                .email("chaelin@mutsa.com")
                .build();
        memberRepository.save(member);

        // 플레이리스트 생성
        Playlist playlist1 = Playlist.builder()
                .name("My Playlist")
                .owner(member)
                .build();
        playlistRepository.save(playlist1);

        Playlist playlist2 = Playlist.builder()
                .name("My Playlist") // 같은 이름의 플레이리스트 생성
                .owner(member)
                .build();

        // 테스트
        assertThrows(DataIntegrityViolationException.class, () -> {
            playlistRepository.save(playlist2);
        });
    }
}
