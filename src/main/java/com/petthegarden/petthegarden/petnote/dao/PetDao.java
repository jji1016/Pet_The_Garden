package com.petthegarden.petthegarden.petnote.dao;

import com.petthegarden.petthegarden.petnote.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PetDao {
    private final PetRepository petRepository;


}
