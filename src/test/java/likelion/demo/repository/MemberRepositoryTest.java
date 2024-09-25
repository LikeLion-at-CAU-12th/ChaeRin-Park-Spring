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
@Transactional      // 테스트 메서드 실행 시 모든 DB 작업을 하나의 트랜잭션으로 묶어줌
public class MemberRepositoryTest {

    @Autowired      // 스프링의 의존성 주입 -> MemberRepository 인스턴스를 자동으로 주입
    private MemberRepository memberRepository;

    @Autowired
    private PlaylistRepository playlistRepository;

    @Test       // 해당 메서드가 테스트 메서드임을 나타냄
    public void joinFailIfEmailExist() {
        // 첫 번째 멤버
        Member member1 = Member.builder()
                .name("Chaelin")
                .email("chaelin@mutsa.com")
                .build();

        // 첫 번째 멤버와 이메일이 겹침
        Member member2 = Member.builder()
                .name("Chaelin")
                .email("chaelin@mutsa.com")
                .build();

        memberRepository.save(member1);

        assertThrows(DataIntegrityViolationException.class, () -> {
            memberRepository.save(member2);
        });
    }

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
}