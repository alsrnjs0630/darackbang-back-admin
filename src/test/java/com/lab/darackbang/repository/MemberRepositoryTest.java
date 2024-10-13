package com.lab.darackbang.repository;

import com.lab.darackbang.entity.Member;
import com.lab.darackbang.entity.MemberRole;
import com.lab.darackbang.entity.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest // 통합 테스트 어노테이션. Application 모든 빈 로드
@Slf4j
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberRoleRepository memberRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void insertTest(){
        for(int i=1; i<=10 ;i++ ){

            Member member = new Member();
            member.setUserEmail("user"+String.valueOf(i)+"@darackbang.com");
            member.setPassword(passwordEncoder.encode("1234"));
            member.setName("user"+String.valueOf(i));
            member.setBirthday("20240302");
            member.setAgeGroup("20");
            member.setGender("F");
            member.setMobileNo("01028810137");
            member.setMileage(0);
            member.setIsBlacklist(false);
            member.setIsDeleted(false);
            member.setMemberState("01");


            List<MemberRole> memberRoles = new ArrayList<>();

            // 유저 권한 생성 및 추가
            MemberRole userRole = new MemberRole();
            userRole.setRole(Role.USER);
            userRole.setMember(member); // 회원 추가
            memberRoles.add(userRole);

            // i >= 5일 경우 관리자 권한 추가
            if (i >= 5) {
                MemberRole managerRole = new MemberRole();
                managerRole.setRole(Role.MANAGER);
                managerRole.setMember(member);
                memberRoles.add(managerRole);
            }

            // i >= 8일 경우 어드민 권한 추가
            if (i >= 8) {
                MemberRole adminRole = new MemberRole();
                adminRole.setRole(Role.ADMIN);
                adminRole.setMember(member);
                memberRoles.add(adminRole);
            }

            // 회원과 권한 리스트 연결
            member.setMemberRoles(memberRoles);

            memberRepository.save(member);
        }
    }

    @Test
    void read(){

        Member member = memberRepository.findById(23L).orElseThrow();

        log.info("사용자 이메일 {}",member.toString());

        log.info("사용자 롤 정보 {}", member.getMemberRoles().toString());

    }

    @Test
    void update(){ // 회원 수정 삭제

        Member member = memberRepository.findById(3L).orElseThrow();

        member.setIsDeleted(true);
        memberRepository.save(member);

    }


}
