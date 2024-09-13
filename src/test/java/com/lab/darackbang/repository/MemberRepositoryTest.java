package com.lab.darackbang.repository;

import com.lab.darackbang.entity.Member;
import com.lab.darackbang.entity.MemberRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RequiredArgsConstructor
@Slf4j
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void insertTest(){

        for(int i=1; i<=10 ;i++ ){

            Member member = Member.builder()
                    .userEmail("user"+String.valueOf(i)+"@test.com")
                    .password("1234")
                    .name("user"+String.valueOf(i))
                    .birthday("20240302")
                    .ageGroup("20")
                    .gender("F")
                    .mobileNo("01028810137")
                    .mileage(0)
                    .createdDate(LocalDate.now())
                    .updatedDate(LocalDate.now())
                    .build();

            List<MemberRole> memberRoles = new ArrayList<>();

            memberRoles.add(MemberRole.builder().role("1").member(member).build());
            member.setMemberRoles(memberRoles);

            if(i>=5){
                memberRoles.add(MemberRole.builder().role("2").member(member).build());
                member.setMemberRoles(memberRoles);
            }

            if(i>=8){
                memberRoles.add(MemberRole.builder().role("3").member(member).build());
                member.setMemberRoles(memberRoles);
            }

            memberRepository.save(member);
        }
    }

    @Test
    @Transactional
    void read(){

        Member member = memberRepository.findById(53L).orElseThrow();

        log.info("사용자 이메일 {}",member.toString());

        log.info("사용자 롤 정보 {}", member.getMemberRoles().toString());

    }

    @Test
    void update(){

        Member member = memberRepository.findById(53L).orElseThrow();

        member.setIsDeleted(true);
        memberRepository.save(member);

    }


}
