package likelion.demo.repository;

import likelion.demo.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// JPA가 인터페이스를 기반으로 기본적인 CRUD 기능 구현 -> 런타임에 이 인터페이스를 구현하는 클래스 자동 생성
public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByEmail(String email);     // 특정 email 값을 가진 Member 객체를 조회하는 쿼리 자동 생성
}
