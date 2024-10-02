package likelion.demo.service;

import jakarta.transaction.Transactional;
import likelion.demo.domain.Article;
import likelion.demo.domain.Comment;
import likelion.demo.domain.Member;
import likelion.demo.dto.request.CommentCreateRequestDto;
import likelion.demo.dto.request.CommentUpdateRequestDto;
import likelion.demo.dto.response.ArticleResponseDto;
import likelion.demo.dto.response.CommentResponseDto;
import likelion.demo.repository.ArticleJpaRepository;
import likelion.demo.repository.CommentJpaRepository;
import likelion.demo.repository.MemberJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    CommentJpaRepository commentRepository;
    @Autowired
    ArticleJpaRepository articleRepository;
    @Autowired
    MemberJpaRepository memberRepository;

    @Transactional
    public Long createComment(CommentCreateRequestDto requestDto) {
        Article article = articleRepository.findById(requestDto.getArticleId())
                .orElseThrow(() -> new RuntimeException("해당 ID를 가진 게시글이 존재하지 않습니다."));

        Member member = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(() -> new RuntimeException("해당 ID를 가진 회원이 존재하지 않습니다."));

        Comment comment = Comment.builder()
                .content(requestDto.getContent())
                .article(article)
                .member(member)
                .build();

        commentRepository.save(comment);
        return comment.getId();
    }

    @Transactional
    public List<CommentResponseDto> findCommentsByArticleId(Long articleId) {
        List<Comment> comments = commentRepository.findByArticleId(articleId);
        return comments.stream()
                .map(comment -> new CommentResponseDto(comment.getId(), comment.getContent()))
                .collect(Collectors.toList());
    }

    @Transactional
    public Long updateComment(CommentUpdateRequestDto requestDto) {
        Comment comment = commentRepository.findById(requestDto.getCommentId())
                .orElseThrow(() -> new RuntimeException("해당 ID를 가진 댓글이 존재하지 않습니다."));
        comment.setContent(requestDto.getContent());
        return comment.getId();
    }

    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("해당 ID를 가진 댓글이 존재하지 않습니다."));

        commentRepository.delete(comment);
    }
}
