package likelion.demo.repository;

import likelion.demo.domain.Article;
import likelion.demo.domain.CategoryArticle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryArticleJpaRepository extends JpaRepository<CategoryArticle, Long> {
    List<CategoryArticle> findByArticle(Article article);
}
