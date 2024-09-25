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
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "artist_id")
    private Long id;

    private String name;        // 아티스트명

    private String genre;       // 장르

    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL, orphanRemoval = true)        // 아티스트와 음악은 일대다 관계
    private List<Song> songs = new ArrayList<>();       // 아티스트의 음악 목록

    // 아티스트의 음악 목록에 음악을 추가
    public void addSong(Song song) {
        this.songs.add(song);
        song.setArtist(this);
    }

    // 아티스트의 음악 목록에서 음악을 삭제
    public void removeSong(Song song) {
        this.songs.remove(song);
        song.setArtist(null);
    }
}
