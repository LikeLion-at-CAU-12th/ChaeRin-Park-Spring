package likelion.demo.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genre_id")
    private Long id;

    private String name;        // 장르명

    @ManyToMany(mappedBy = "genres")        // 장르와 음악은 다대다 관계
    private List<Song> songs = new ArrayList<>();       // 특정 장르의 음악 모음

    // 특정 장르에 음악을 추가
    public void addSong(Song song) {
        this.songs.add(song);
        song.getGenres().add(this);
    }

    // 특정 장르에서 음악을 제거
    public void removeSong(Song song) {
        this.songs.remove(song);
        song.getGenres().remove(this);
    }
}
