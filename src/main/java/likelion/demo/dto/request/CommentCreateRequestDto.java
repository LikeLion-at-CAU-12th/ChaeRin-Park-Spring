package likelion.demo.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentCreateRequestDto {
    private String content;
    private Long articleId;
    private Long memberId;
}
