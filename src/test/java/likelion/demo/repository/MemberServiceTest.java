package likelion.demo.repository;

import likelion.demo.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    public void testPrintMembersByPage() {
        memberService.printMembersByPage(0, 10);    // 첫 페이지의 10개 회원 출력
    }
}
