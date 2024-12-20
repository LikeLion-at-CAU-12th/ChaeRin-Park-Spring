package likelion.demo.service;

import jakarta.transaction.Transactional;
import likelion.demo.domain.*;
import likelion.demo.dto.request.ArticleCreateRequestDto;
import likelion.demo.dto.request.ArticleUpdateRequestDto;
import likelion.demo.dto.response.ArticleResponseDto;
import likelion.demo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final MemberJpaRepository memberRepository;
    private final ArticleJpaRepository articleRepository;
    private final CategoryArticleJpaRepository categoryArticleRepository;
    private final ArticleLogJpaRepository articleLogRepository;
    private final CategoryJpaRepository categoryRepository;

    @Transactional
    public Long createArticle(ArticleCreateRequestDto requestDto) {
        Member member = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(() -> new RuntimeException("해당 아이디를 가진 회원이 존재하지 않습니다."));
        Article article = Article.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .member(member)
                .comments(new ArrayList<>())
                .build();
        articleRepository.save(article);

        ArticleLog articleLog = ArticleLog.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .article(article)
                .build();
        articleLogRepository.save(articleLog);

        List<Long> categoryIds = requestDto.getCategoryIds();
        if (categoryIds != null && !categoryIds.isEmpty()) {
            for (Long categoryId : categoryIds) {
                Category category = categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new RuntimeException(("해당 ID를 가진 카테고리가 존재하지 않습니다.")));

                CategoryArticle categoryArticle = CategoryArticle.builder()
                        .category(category)
                        .article(article)
                        .build();

                categoryArticleRepository.save(categoryArticle);
            }
        }
        return article.getId();
    }

    public List<ArticleResponseDto> findArticlesByMemberId(Long memberId) {
        List<Article> articles = articleRepository.findByMemberId(memberId);
        return articles.stream()
                .map(article -> new ArticleResponseDto(article.getId(), article.getTitle(), article.getContent()))
                .collect(Collectors.toList());
    }

    // Update 구현
    @Transactional
    public Long updateArticle(ArticleUpdateRequestDto requestDto) {
        // 기존 Article 조회
        Article article = articleRepository.findById(requestDto.getArticleId())
                .orElseThrow(() -> new RuntimeException("해당 아이디를 가진 글이 존재하지 않습니다."));

        // Article 필드 수정
        article.setTitle(requestDto.getTitle());
        article.setContent(requestDto.getContent());

        // Article 저장 (JPA는 영속성 컨텍스트 내에서 필드만 수정해도 자동으로 update가 반영됨)
        articleRepository.save(article);

        // 이전 ArticleLog의 createdAt을 복사
        ArticleLog previousLog = articleLogRepository.findTopByArticleOrderByCreatedAtDesc(article)
                .orElse(null);

        ArticleLog newLog = ArticleLog.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .article(article)
                .build();

        // 새로운 ArticleLog의 createdAt을 이전 로그의 createdAt으로 설정
        if (previousLog != null) {
            newLog.setCreatedAt(previousLog.getCreatedAt());
        } else {
            newLog.setCreatedAt(LocalDateTime.now()); // 첫 번째 로그인 경우 현재 시간 설정
        }
        // 수동으로 updatedAt 설정
        newLog.setUpdatedAt(LocalDateTime.now());
        articleLogRepository.save(newLog);

        // 카테고리 업데이트 로직 (기존의 연관된 카테고리 제거 후 새로 추가)
        List<CategoryArticle> existingCategoryArticles = categoryArticleRepository.findByArticle(article);
        categoryArticleRepository.deleteAll(existingCategoryArticles);

        List<Long> categoryIds = requestDto.getCategoryIds();
        if (categoryIds != null && !categoryIds.isEmpty()) {
            for (Long categoryId : categoryIds) {
                Category category = categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new RuntimeException("해당 ID를 가진 카테고리가 존재하지 않습니다."));

                CategoryArticle categoryArticle = CategoryArticle.builder()
                        .category(category)
                        .article(article)
                        .build();

                categoryArticleRepository.save(categoryArticle);
            }
        }

        return article.getId();
    }


    @Transactional
    public void deleteArticle(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("해당 아이디를 가진 글이 존재하지 않습니다."));

        List<CategoryArticle> categoryArticles = categoryArticleRepository.findByArticle(article);

        if (!categoryArticles.isEmpty()) {
            categoryArticleRepository.deleteAll(categoryArticles);
        }

        articleLogRepository.deleteByArticle(article);

        articleRepository.delete(article); // Article 삭제
    }
}