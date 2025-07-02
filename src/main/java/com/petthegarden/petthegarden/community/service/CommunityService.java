package com.petthegarden.petthegarden.community.service;

import com.petthegarden.petthegarden.community.dao.CommunityDao;
import com.petthegarden.petthegarden.community.dao.CommunityDao;
import com.petthegarden.petthegarden.community.dto.BoardDto;
import com.petthegarden.petthegarden.community.repository.CommunityRepository;
import com.petthegarden.petthegarden.entity.Board;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CommunityService {
    private final CommunityRepository communityRepository;
    @Value("${file.path}board/")
    String boardPath;

    private final CommunityDao communityDao;

    public List<Board> getBoardList() {
        return communityDao.findAllBoards();
    }

    public Board getBoardById(Integer id) {
        return communityDao.findById(id);
    }

    public void saveBoard(BoardDto boardDto, MultipartFile file) {
        String fileName = null;

        if (file != null && !file.isEmpty()) {
            try {
                String originalFilename = file.getOriginalFilename();
                String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String baseName = originalFilename.substring(0, originalFilename.lastIndexOf("."));
                String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                fileName = baseName + "_" + timestamp + extension;

                File saveDir = new File(boardPath);
                if (!saveDir.exists()) {
                    saveDir.mkdirs();
                }

                file.transferTo(new File(boardPath + fileName));
            } catch (Exception e) {
                log.error("반려동물 이미지 업로드 실패", e);
                throw new RuntimeException("이미지 업로드 중 오류가 발생했습니다.");
            }
            Board board = boardDto.toEntity();
            board.setImage(fileName);
            board.setRegDate(LocalDateTime.now());
            communityRepository.save(board);
        }

    }
    public void updateBoard(Integer id, BoardDto boardDto, MultipartFile file){
        Board board = getBoardById(id);

        board.setSubject(boardDto.getSubject());
        board.setContent(boardDto.getContent());
        board.setModifyDate(LocalDateTime.now());
        String fileName = null;

        if (file != null && !file.isEmpty()) {
            try {
                String originalFilename = file.getOriginalFilename();
                String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String baseName = originalFilename.substring(0, originalFilename.lastIndexOf("."));
                String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                fileName = baseName + "_" + timestamp + extension;

                File saveDir = new File(boardPath);
                if (!saveDir.exists()) {
                    saveDir.mkdirs();
                }

                file.transferTo(new File(boardPath + fileName));
            } catch (Exception e) {
                log.error("반려동물 이미지 업로드 실패", e);
                throw new RuntimeException("이미지 업로드 중 오류가 발생했습니다.");
            }
        }
    }

    public Map<String, Object> uploadImage(MultipartFile file) {
        Map<String, Object> response = new HashMap<>();

        if (file == null || file.isEmpty()) {
            response.put("uploaded", 0);
            Map<String, String> error = new HashMap<>();
            error.put("message", "파일이 없습니다.");
            response.put("error", error);
            return response;
        }

        try {

            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String baseName = originalFilename.substring(0, originalFilename.lastIndexOf("."));
            String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String fileName = baseName + "_" + timestamp + extension;

            File saveDir = new File(boardPath);
            if (!saveDir.exists()) {
                saveDir.mkdirs();
            }

            file.transferTo(new File(boardPath + fileName));

            response.put("uploaded", 1);
            response.put("fileName", "preview-image.jpg");
            response.put("url", "\"/PTGUpload/board/\" + fileName"); //

        } catch (Exception e) {
            response.put("uploaded", 0);
            Map<String, String> error = new HashMap<>();
            error.put("message", "업로드 실패: " + e.getMessage());
            response.put("error", error);
        }
        return response;
    }
}