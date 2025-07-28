package com.petthegarden.petthegarden.index;

import com.petthegarden.petthegarden.entity.Board;
import com.petthegarden.petthegarden.index.dto.RecentBoardDto;
import com.petthegarden.petthegarden.index.repository.RecentBoard;
import com.petthegarden.petthegarden.index.repository.ShowOffMainRepository;
import com.petthegarden.petthegarden.index.repository.Top3PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MainDao {
    private final ShowOffMainRepository showOffMainRepository;
    private final Top3PetRepository top3PetRepository;
    private final RecentBoard recentBoard;

    public List<Object[]> getTop3pet() {
       return  top3PetRepository.findTop3PetFollowerRaw();
    }

    public List<Board> findRecentBoards() {
        return recentBoard.findRecentBoards();
    }
}
