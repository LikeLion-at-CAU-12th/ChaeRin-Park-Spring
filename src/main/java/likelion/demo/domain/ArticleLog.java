package likelion.demo.domain;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleLog extends BaseTimeEntity {
    @Id @GeneratedValue
    @Column(name = "article_log_id")
    private Long id;
    private String title;
    private String content;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @Builder
    public ArticleLog(String title, String content, Article article) {
        this.title = title;
        this.content = content;
        this.article = article;
    }
}
