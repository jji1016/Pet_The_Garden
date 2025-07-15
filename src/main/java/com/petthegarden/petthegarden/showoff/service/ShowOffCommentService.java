package com.petthegarden.petthegarden.showoff.service;

import com.petthegarden.petthegarden.entity.ShowOff;
import com.petthegarden.petthegarden.entity.ShowOffComment;
import com.petthegarden.petthegarden.entity.Member;
import com.petthegarden.petthegarden.showoff.repository.ShowDetailRepository;
import com.petthegarden.petthegarden.showoff.repository.ShowOffCommentRepository;
import com.petthegarden.petthegarden.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowOffCommentService {

    private final ShowOffCommentRepository showOffCommentRepository;
    private final ShowDetailRepository showDetailRepository;
    private final MemberRepository memberRepository;

    public List<ShowOffComment> findByShowOffId(Integer showoffId) {
        return showOffCommentRepository.findByShowOff_IdOrderByRegDateDesc(showoffId);
    }

    public void saveComment(Integer showoffId, String content, UserDetails principal) {
        if (principal == null) throw new RuntimeException("로그인이 필요합니다.");
        ShowOff showOff = showDetailRepository.findById(showoffId).orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        Member member = memberRepository.findByUserID(principal.getUsername()).orElseThrow(() -> new RuntimeException("회원 정보를 찾을 수 없습니다."));
        ShowOffComment comment = ShowOffComment.builder()
                .content(content)
                .regDate(LocalDateTime.now())
                .showOff(showOff)
                .member(member)
                .build();
        showOffCommentRepository.save(comment);
    }
}
