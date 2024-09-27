package likelion.demo.service;

import likelion.demo.domain.Member;
import likelion.demo.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberJpaRepository memberJpaRepository;

    // 특정 페이지 단위로 페이징 처리하여 조회
    public Page<Member> getMemberByPage(int page, int size) {
        // PageRequest 객체를 생성하여 페이징 정보와 정렬 조건(오름차순)을 지정
        PageRequest pageable = PageRequest.of(page, size, Sort.by("username").ascending());
        return memberJpaRepository.findAll(pageable);
    }

    public void printMembersByPage(int page, int size) {
        Page<Member> memberPage = getMemberByPage(page, size);
        List<Member> members = memberPage.getContent();

        for (Member member : members) {
            System.out.println("ID: " + member.getId() + ", Username: " + member.getUsername());
        }
    }

    // 나이가 20 이상인 멤버들을 username 기준으로 오름차순 정렬하여 페이징 결과 반환
    public Page<Member> getMembersByAge(int page, int size) {
        // 페이지 요청 객체 생성 (page: 페이지 번호, size: 페이지 크기, 정렬 기준: username 오름차순)
        Pageable pageable = PageRequest.of(page, size, Sort.by("username").ascending());

        // 나이가 20 이상인 멤버 조회
        return memberJpaRepository.findByAgeGreaterThanEqual(20, pageable);
    }

    // 페이징된 멤버 목록을 출력하는 메서드
    public void printMembersByAge(int page, int size) {
        Page<Member> memberPage = getMembersByAge(page, size);
        List<Member> members = memberPage.getContent();

        for (Member member : members) {
            System.out.println("ID: " + member.getId() + ", Username: " + member.getUsername());
        }
    }

    // 이름이 특정 문자열로 시작하는 멤버를 페이징하여 조회하는 메서드
    public Page<Member> getMembersByUsernamePrefix(int page, int size, String prefix) {
        // 페이지 번호, 페이지 크기, 정렬 기준(이름 오름차순) 설정
        Pageable pageable = PageRequest.of(page, size, Sort.by("username").ascending());
        return memberJpaRepository.findByUsernameStartingWith(prefix, pageable);
    }

    // 페이징된 멤버 목록을 출력하는 메서드
    public void printMembersByUsernamePrefix(int page, int size, String prefix) {
        // 페이징된 결과 조회
        Page<Member> memberPage = getMembersByUsernamePrefix(page, size, prefix);
        List<Member> members = memberPage.getContent();

        for (Member member : members) {
            System.out.println("ID: " + member.getId() + ", Username: " + member.getUsername());
        }
    }
}
