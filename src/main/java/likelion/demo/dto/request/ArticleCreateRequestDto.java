package likelion.demo.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ArticleCreateRequestDto {
    private Long memberId;
    private String title;
    private String content;
    private List<Long> categoryIds;
}
