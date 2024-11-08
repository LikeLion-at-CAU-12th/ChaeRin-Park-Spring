package likelion.demo.repository;

import likelion.demo.domain.Member;
import likelion.demo.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;
import java.util.stream.IntStream;

@SpringBootTest
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberJpaRepository memberJpaRepository;

    private final Random random = new Random();

    // 테스트 실행 전, Member 100개 생성하는 코드입니다.
    @BeforeEach
    public void setUp() {
        IntStream.range(0, 100).forEach(i -> {
            String username = "user" + random.nextInt(10000);
            String email = "user" + random.nextInt(10000) + "@example.com";
            int age = random.nextInt(60 - 18 + 1) + 18;
            Member member = Member.builder()
                    .username(username)
                    .email(email)
                    .age(age)
                    .build();
            memberJpaRepository.save(member);
        });
    }

    @Test
    public void testPrintMembersByPage() {
        // 0번째 페이지의 10개 데이터를 가져옴
        memberService.printMembersByPage(0, 10);
    }

    @Test
    public void testPrintMembersByAge() {
        memberService.printMembersByAge(0, 50);
    }

    @Test
    public void testPrintMembersByUsernamePrefix() {
        memberService.printMembersByUsernamePrefix(0, 10, "u");
    }
}