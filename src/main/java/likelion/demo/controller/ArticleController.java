package likelion.demo.controller;

import likelion.demo.dto.request.ArticleCreateRequestDto;
import likelion.demo.dto.request.ArticleUpdateRequestDto;
import likelion.demo.dto.response.ArticleResponseDto;
import likelion.demo.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    // api/v1/articles
    @PostMapping("")
    public ResponseEntity<Long> createArticle(@RequestBody ArticleCreateRequestDto requestDto) {
        Long articleId = articleService.createArticle(requestDto);
        return new ResponseEntity<>(articleId, HttpStatus.CREATED);
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<ArticleResponseDto>> getArticlesByMemberId(@PathVariable Long memberId) {
        List<ArticleResponseDto> articles = articleService.findArticlesByMemberId(memberId);
        if (articles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(articles);
    }

    @PutMapping("")
    public ResponseEntity<Long> putArticle(@RequestBody ArticleUpdateRequestDto requestDto) {
        Long article = articleService.updateArticle(requestDto);
        return ResponseEntity.ok(article);
    }

    @DeleteMapping("/{articleId}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long articleId) {
        articleService.deleteArticle(articleId);
        return ResponseEntity.noContent().build();
    }
}
