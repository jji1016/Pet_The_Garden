package com.petthegarden.petthegarden.showoff.service;

import com.petthegarden.petthegarden.entity.Member;
import com.petthegarden.petthegarden.entity.ShowOff;
import com.petthegarden.petthegarden.showoff.dto.CorrectDto;
import com.petthegarden.petthegarden.showoff.repository.CorrectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CorrectService {

    private final CorrectRepository correctRepository;

    /* 게시글 단건 조회 */
    @Transactional(readOnly = true)
    public ShowOff getShowOffById(Integer id) {
        return correctRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("장기자랑을 찾을 수 없습니다. id=" + id));
    }

    /* 수정 로직 */
    public ShowOff updateShowOff(Integer id, CorrectDto dto, Member member) {

        ShowOff origin = getShowOffById(id);

        /* 작성자 본인인지 검증 */
        if (!origin.getMember().getId().equals(member.getId())) {
            throw new RuntimeException("본인 게시글만 수정 가능합니다.");
        }

        origin = ShowOff.builder()
                .Id(origin.getId())
                .subject(dto.getSubject())
                .content(dto.getContent())
                .image(dto.getImage())
                .regDate(origin.getRegDate())
                .modifyDate(java.time.LocalDateTime.now())
                .member(member)
                .pet(origin.getPet())
                .showOffLike(origin.getShowOffLike())
                .build();

        return correctRepository.save(origin);
    }
}
