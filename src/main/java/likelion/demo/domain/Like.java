package likelion.demo.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)      // 좋아요와 멤버는 다대일 관계
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)      // 좋아요와 음악은 다대일 관게
    @JoinColumn(name = "song_id")
    private Song song;

    private LocalDateTime likedAt;

    // 좋아요의 소유자 설정
    public void setMember(Member member) {
        this.member = member;
        member.getLikes().add(this);        // 멤버의 좋아요 목록에도 추가
    }

    // 음악 좋아요 설정
    public void setSong(Song song) {
        this.song = song;
        song.getLikes().add(this);      // 음악의 좋아요 목록에도 추가
    }
}
