package likelion.demo.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "song_id")
    private Long id;

    private String title;       // 노래 제목

    private LocalDateTime releaseDate;      // 발매일

    private int streamCount;        // 스트리밍 횟수

    private int likeCount;      // 곡이 받은 좋아요 수

    @ManyToOne(fetch = FetchType.LAZY)      // 음악과 아티스트의 다대일 관계
    @JoinColumn(name = "artist_id")     // artist_id로 아티스트와 연결
    private Artist artist;

    @ManyToOne(fetch = FetchType.LAZY)      // 음악과 앨범은 다대일 관계
    @JoinColumn(name = "album_id")      // album_id로 앨범과 연결
    private Album album;

    @ManyToMany     // 음악과 장르는 다대다 관계
    @JoinTable(     // 음악과 장르의 중간 테이블
            name = "song_genre",        // 중간 테이블 이름
            joinColumns = @JoinColumn(name = "song_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres = new ArrayList<>();     // song 엔티티가 여러 개의 장르와 관련될 수 있으므로 리스트로 설정

    @OneToMany(mappedBy = "song", cascade = CascadeType.ALL, orphanRemoval = true)      // 음악과 좋아요는 일대다 관계
    private List<Like> likes = new ArrayList<>();

    // 장르 추가
    public void addGenre(Genre genre) {
        this.genres.add(genre);
        genre.getSongs().add(this);
    }

    // 장르 삭제
    public void removeGenre(Genre genre) {
        this.genres.remove(genre);
        genre.getSongs().remove(this);
    }

    public void addLike(Like like) {
        this.likes.add(like);
        like.setSong(this);
        this.likeCount++;
    }

    public void removeLike(Like like) {
        this.likes.remove(like);
        like.setSong(null);
        this.likeCount--;
    }
}
