package com.petthegarden.petthegarden.showoff.service;

import com.petthegarden.petthegarden.entity.Member;
import com.petthegarden.petthegarden.entity.Pet;
import com.petthegarden.petthegarden.entity.ShowOff;
import com.petthegarden.petthegarden.showoff.dto.ShowRegDto;
import com.petthegarden.petthegarden.showoff.repository.ShowRegRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ShowRegService {

    private final ShowRegRepository showRegRepository;

    public void createShowReg(ShowRegDto dto, Member member, Pet pet) {
        ShowOff showOff = ShowOff.builder()
                .subject(dto.getSubject())
                .content(dto.getContent())
                .member(member)
                .pet(pet)
                .regDate(LocalDateTime.now())
                .showOffLike(0)
                .build();

        showRegRepository.save(showOff);
    }
}
