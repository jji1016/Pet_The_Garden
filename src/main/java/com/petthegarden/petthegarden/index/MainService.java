package com.petthegarden.petthegarden.index;

import com.petthegarden.petthegarden.entity.Board;
import com.petthegarden.petthegarden.entity.ShowOff;
import com.petthegarden.petthegarden.index.dto.RecentBoardDto;
import com.petthegarden.petthegarden.index.dto.Top3PetDto;
import com.petthegarden.petthegarden.index.dto.WeeklyTop3Dto;
import com.petthegarden.petthegarden.index.repository.ShowOffMainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional
public class MainService {
    private final ShowOffMainRepository showOffMainRepository;
    private final MainDao mainDao;


    public List<WeeklyTop3Dto> getWeeklyTop3() {
        LocalDate now = LocalDate.now();
        Locale locale = Locale.KOREA;
        WeekFields weekFields = WeekFields.of(locale);

        LocalDate startOfWeek = now.with(weekFields.dayOfWeek(), 1);
        LocalDate endOfWeek = now.with(weekFields.dayOfWeek(), 7);

        LocalDateTime startDateTime = startOfWeek.atStartOfDay();
        LocalDateTime endDateTime = endOfWeek.atTime(LocalTime.MAX);

        Pageable top3 = PageRequest.of(0, 3);

            List<ShowOff> list = showOffMainRepository.findTop3ByThisWeek(startDateTime, endDateTime, top3); // 예시

        return list.stream()
                .map(showOff -> WeeklyTop3Dto.builder()
                        .id(showOff.getId())
                        .title(showOff.getSubject())
                        .petName(showOff.getPet().getPetName())
                        .image(showOff.getImage())
                        .content(showOff.getContent())
                        .regDate(showOff.getRegDate().toLocalDate())
                        .likes(showOff.getShowOffLike())
                        .build())
                .toList();
        }



    public List<Top3PetDto> getTop3pet() {
        List<Object[]> rawData = mainDao.getTop3pet();

        return rawData.stream()
                .map(row -> Top3PetDto.builder()
                        .id((Integer) row[0])
                        .image((String) row[1])
                        .petName((String) row[2])
                        .species((String) row[3])
                        .userName((String) row[4])
                        .followers(((Number) row[5]).intValue())
                        .build())
                .toList();
    }

    public List<RecentBoardDto> findRecentBoards() {
        List<Board> recentBoards = mainDao.findRecentBoards();

        return recentBoards.stream()
                .map(board -> RecentBoardDto.builder()
                        .Id(board.getId())
                        .title(board.getSubject())
                        .regDate(board.getRegDate().toLocalDate())
                        .writer(board.getMember().getUserName())
                        .userID(board.getMember().getUserID())
                        .build())
                .toList();
    }

}


