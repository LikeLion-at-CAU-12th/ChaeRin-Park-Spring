package likelion.demo.repository;

import jakarta.transaction.Transactional;
import likelion.demo.domain.Member;
import likelion.demo.domain.Playlist;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class PlaylistRepositoryTest1 {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PlaylistRepository playlistRepository;

    @Test
    public void makePlaylist() {
        // 우선 멤버를 만듦
        Member member = Member.builder()
                .name("Chaelin")
                .email("Chaelin@mutsa.com")
                .build();
        memberRepository.save(member);

        Playlist playlist = Playlist.builder()
                .name("Chaelin's Playlist")
                .owner(member)
                .build();

        // 플레이리스트를 만듦
        playlistRepository.save(playlist);

        // 플레이리스트가 잘 만들어졌는지 확인
        Playlist foundPlaylist = playlistRepository.findById(playlist.getId()).get();
        assertEquals("Chaelin's Playlist", foundPlaylist.getName());
        assertEquals(member.getId(), foundPlaylist.getOwner().getId());
    }
}