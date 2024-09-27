package likelion.demo.repository;

import likelion.demo.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;


@Repository
public interface MemberJpaRepository extends JpaRepository<Member, Long> {
    List<Member> findByUsername(String username);

    // 나이가 20 이상인 멤버를 조회하고, 페이징 결과를 반환하는 메서드
    Page<Member> findByAgeGreaterThanEqual(int age, Pageable pageable);

    // username이 특정 문자열로 시작하는 경우를 필터링하고, 페이징된 결과를 반환하는 메서드
    Page<Member> findByUsernameStartingWith(String prefix, Pageable pageable);
}
