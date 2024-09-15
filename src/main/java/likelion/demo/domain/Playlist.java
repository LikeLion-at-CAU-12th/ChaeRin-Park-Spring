package likelion.demo.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "playlist_id")
    private Long id;

    @Column(unique = true)      // 중복을 방지
    private String name;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)      // 플리와 멤버 다대일 관계, 지연 로딩 (필요할 때만 Member 가져옴)
    @JoinColumn(name = "member_id")     // member_id 컬럼을 통해 멤버와 연결
    private Member owner;

    @ManyToMany     // 플레이리스트와 음악은 다대다 관계 (하나의 플리는 여러 곡 포함, 하나의 곡은 여러 플리에)
    @JoinTable(     // 다대다 관계를 표현하기 위한 중간 테이블 설정
            name = "playlist_song",     // 중간 테이블 이름
            joinColumns = @JoinColumn(name = "playlist_id"),    // 중간 테이블의 playlist_id 열
            inverseJoinColumns = @JoinColumn(name = "song_id")      // 중간 테이블의 song_id 열
    )
    @Builder.Default
    private List<Song> songs = new ArrayList<>();       // 플리에 포함된 곡들을 관리하는 리스트 (객체 생성 시 빈 리스트로 초기화)

    // 곡을 플리에 추가
    public void addSong(Song song) {
        if (song == null || song.getId() == null) {
            throw new IllegalArgumentException("유효하지 않은 곡입니다.");
        }
        this.songs.add(song);
    }

    // 곡을 플리에서 제거
    public void removeSong(Song song) {
        this.songs.remove(song);
    }
}
