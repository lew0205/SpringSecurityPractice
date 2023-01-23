package study.securityPrac.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.securityPrac.domain.member.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
}
