package com.example.checkcheck.service;

import com.example.checkcheck.dto.requestDto.CommentRequestDto;
import com.example.checkcheck.dto.responseDto.CommentResponseDto;
import com.example.checkcheck.dto.responseDto.ResponseDto;
import com.example.checkcheck.model.Member;
import com.example.checkcheck.model.articleModel.Article;
import com.example.checkcheck.model.commentModel.Comment;
import com.example.checkcheck.repository.ArticleRepository;
import com.example.checkcheck.repository.CommentRepository;
import com.example.checkcheck.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;
    private final ArticleService articleService;

    // 댓글 작성
    @Transactional
    public ResponseDto<?> createComment(Long articlesId, CommentRequestDto requestDto, Member member) {

        // 게시글 확인
        Article article = articleService.isPresentArticle(articlesId);
        if (null == article) {
            return ResponseDto.fail("NOT_FOUND", "게시물이 존재하지 않습니다.");
        }

        Comment comment = Comment.builder()
                .comment(requestDto.getComment())
                .article(article)
                .member(member)
                .type(requestDto.getType())
                .build();
        commentRepository.save(comment);

        return ResponseDto.success(
            CommentResponseDto.builder()
                .commentId(comment.getCommentId())
                .type(comment.getType())
                .comment(comment.getComment())
                .createdAt(comment.getCreatedAt())
                .build()
        );
    }

    // 모든 댓글 조회
    @Transactional
    public ResponseDto<?> readAllComment(Long articlesId) {
        // 게시글 확인
        Article article = articleService.isPresentArticle(articlesId);
        if (null == article) {
            return ResponseDto.fail("NOT_FOUND", "게시물이 존재하지 않습니다.");
        }

        List<Comment> commentList = commentRepository.findAllByArticle(article);
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();

        for (Comment comment : commentList) {
            commentResponseDtoList.add(
                    CommentResponseDto.builder()
                            .commentId(comment.getCommentId())
                            .type(comment.getType())
                            .comment(comment.getComment())
                            .createdAt(comment.getCreatedAt())
//                            .isSelected(comment.getIsSelected())
                            .build()
            );
        }
        return ResponseDto.success(commentResponseDtoList);
    }

    // 댓글 채택


    // 댓글 삭제
    @Transactional
    public ResponseDto<?> deleteComment(Long commentsId, Member member) {

        Comment comment = isPresentComment(commentsId);
        if (null == comment) {
            return ResponseDto.fail("NOT_FOUND", "댓글이 존재하지 않습니다.");
        }

        if (comment.getMember().equals(member)) {
            return ResponseDto.fail("BAD_REQUEST", "작성자만 삭제 할 수 있습니다.");
        }

        commentRepository.delete(comment);
        return ResponseDto.success("success");
    }

    // 댓글 번호 확인
    @Transactional
    public Comment isPresentComment(Long id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        return optionalComment.orElse(null);
    }

}
