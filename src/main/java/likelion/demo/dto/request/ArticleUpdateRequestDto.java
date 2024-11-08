package likelion.demo.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ArticleUpdateRequestDto {
    private Long articleId;
    private String title;
    private String content;
    private List<Long> categoryIds;
}
