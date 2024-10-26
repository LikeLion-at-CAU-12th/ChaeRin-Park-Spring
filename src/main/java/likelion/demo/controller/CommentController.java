package likelion.demo.controller;

import likelion.demo.dto.request.CommentCreateRequestDto;
import likelion.demo.dto.request.CommentUpdateRequestDto;
import likelion.demo.dto.response.ArticleResponseDto;
import likelion.demo.dto.response.CommentResponseDto;
import likelion.demo.service.ArticleService;
import likelion.demo.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("")
    public ResponseEntity<Long> createComment(@RequestBody CommentCreateRequestDto requestDto) {
        Long commentId = commentService.createComment(requestDto);
        return new ResponseEntity<>(commentId, HttpStatus.CREATED);
    }

    @GetMapping("/article/{articleId}")
    public ResponseEntity<List<CommentResponseDto>> getCommentsByArticleId(@PathVariable Long articleId) {
        List<CommentResponseDto> comments = commentService.findCommentsByArticleId(articleId);
        if (comments.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(comments);
    }

    @PutMapping("")
    public ResponseEntity<Long> updateComment(@RequestBody CommentUpdateRequestDto requestDto) {
        Long commentId = commentService.updateComment(requestDto);
        return ResponseEntity.ok(commentId);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}

