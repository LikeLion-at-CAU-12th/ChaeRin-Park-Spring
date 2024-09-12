package likelion.demo.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity     // 이 클래스가 JPA 엔티티임을 나타냄 -> 데이터베이스의 테이블과 매핑
@Getter     // 모든 필드에 대해 getter 메서드를 자동 생성
@Setter     // 모든 필드에 대해 setter 메서드를 자동으로 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED)      // 기본 생성자를 만들고, PROTECTED 접근제어자 -> 외부에서 호출 불가
@AllArgsConstructor     // 모든 필드를 인자로 받는 생성자를 자동으로 생성
@Builder        // 이 클래스를 빌더 패턴으로 생성할 수 있게 함 -> 복잡한 객체를 단계별로 생성
public class Member {
    @Id     // 엔티티의 고유 식별자
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // 자동으로 고유 식별자 값 생성
    @Column(name = "member_id")     // 엔티티 클래스의 필드를 DB 컬럼과 매핑할 때 사용
    private Long id;

    private String name;

    @Column(unique = true)      // 중복을 방지
    private String email;

    private LocalDateTime createdAt;

    // 멤버와 플레이리스트의 일대다 관계
    // mappedBy -> Playlist 엔티티에서 owner 필드를 통해 이 관계가 매핑됨
    // cascade -> 멤버가 삭제되거나 변경될 때 관련된 Playlist도 함께 처리
    // orphanRemoval -> 멤버와 연결된 플리가 더 이상 사용되지 않으면 자동으로 삭제
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Playlist> playlists = new ArrayList<>();

    // 멤버와 좋아요의 일대다 관계 (플리와 동일한 방식으로 처리)
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    // 사용자의 플레이리스트 목록에 새로운 플레이리스트를 추가 -> 해당 플레이리스트의 사용자를 현재 사용자로 설정
    public void addPlaylist(Playlist playlist) {
        this.playlists.add(playlist);
        playlist.setOwner(this);
    }

    // 사용자의 플레이리스트 목록에서 특정 플레이리스트를 삭제 -> 해당 플레이리스트의 소유자를 null로 설정
    public void removePlaylist(Playlist playlist) {
        this.playlists.remove(playlist);
        playlist.setOwner(null);
    }


    // 사용자가 좋아요를 누른 곡 목록에 새로운 좋아요를 추가하고, 해당 좋아요 소유자를 현재 사용자로 설정
    public void addLike(Like like) {
        this.likes.add(like);
        like.setMember(this);
    }

    // 좋아요를 제거함
    public void removeLike(Like like) {
        this.likes.remove(like);
        like.setMember(null);
    }
}
