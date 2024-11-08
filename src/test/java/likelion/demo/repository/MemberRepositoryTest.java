package likelion.demo.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import likelion.demo.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class MemberRepositoryTest {
    @Autowired MemberRepository memberRepository;
    @Test
    @Transactional
    void save() {
        Member member = Member.builder()
                .username("memberA")
                .age(23)
                .email("polarpheno@gmail.com")
                .build();

        Long savedId = memberRepository.save(member);
        Member findMember = memberRepository.findOne(savedId);

        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember).isEqualTo(member);

    }

    @Test
    void findOne() {
    }

    @Test
    void findAll() {
    }

    @Test
    void findByUsername() {
    }
}