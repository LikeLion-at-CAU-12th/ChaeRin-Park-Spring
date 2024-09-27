package likelion.demo.service;

import likelion.demo.domain.Member;
import likelion.demo.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberJpaRepository memberJpaRepository;

    public Page<Member> getMemberByPage(int page, int size) {
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
}
