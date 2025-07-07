package com.petthegarden.petthegarden.petnote;

import com.petthegarden.petthegarden.entity.Member;
import com.petthegarden.petthegarden.member.MemberDao;
import com.petthegarden.petthegarden.petnote.dao.PetDao;
import com.petthegarden.petthegarden.petnote.dao.PetnoteDao;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class PetnoteService {
    private final PetnoteDao petnoteDao;
    private final MemberDao memberDao;
    private final PetDao petDao;

    public void saveDiary(DiaryDto diaryDto, Integer memberId) {
        Member member = memberDao.findById(memberId)
                .orElseThrow(() -> new RuntimeException("회원 정보가 없습니다."));

//        Pet pet = petDao.findFirstByMemberId(memberId)
//                .orElseThrow(() -> new RuntimeException("등록된 반려동물이 없습니다."));

        // 2. Diary 엔티티로 변환 후 저장
//        Diary diary = diaryDto.toDiary(member, pet);
//        petnoteDao.save(diary);
    }

}
