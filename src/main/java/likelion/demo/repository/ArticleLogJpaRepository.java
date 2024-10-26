package likelion.demo.repository;

import likelion.demo.domain.Article;
import likelion.demo.domain.ArticleLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleLogJpaRepository extends JpaRepository<ArticleLog, Long> {
    Optional<ArticleLog> findByArticle(Article article);
    void deleteByArticle(Article article);
    Optional<ArticleLog> findTopByArticleOrderByCreatedAtDesc(Article article);
}
