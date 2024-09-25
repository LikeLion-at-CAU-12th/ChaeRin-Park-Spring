package likelion.demo.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_id")
    private Long id;

    private String title;       // 앨범 이름

    private LocalDate releaseDate;      // 출시일

    @ManyToOne(fetch = FetchType.LAZY)      // 앨범과 아티스트는 다대일 관계
    @JoinColumn(name = "artist_id")
    private Artist artist;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true)     // 앨범과 음악은 일대다 관계
    private List<Song> songs = new ArrayList<>();       // 수록곡 모음

    // 앨범에 음악을 추가
    public void addSong(Song song) {
        this.songs.add(song);
        song.setAlbum(this);
    }

    // 앨범에서 음악을 제거
    public void removeSong(Song song) {
        this.songs.remove(song);
        song.setAlbum(null);
    }
}
