package com.petthegarden.petthegarden.member;

import com.petthegarden.petthegarden.constant.Role;
import com.petthegarden.petthegarden.entity.Member;
import com.petthegarden.petthegarden.member.dto.LoginDto;
import com.petthegarden.petthegarden.member.dto.MemberDto;
import com.petthegarden.petthegarden.member.util.DateTimeFileRenameStrategy;
import com.petthegarden.petthegarden.member.util.FileRenameStrategy;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    @Value("${file.path}")
    private String upload;
    private final MemberDao memberDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public void save(MemberDto memberDto) {
        String encodedPassword = bCryptPasswordEncoder.encode(memberDto.getUserPW());

        LocalDateTime now = LocalDateTime.now();
        String dateFolder = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        Path uploadFolder = Paths.get(upload, dateFolder);
        try {
            Files.createDirectories(uploadFolder);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        memberDto.setUserPW(encodedPassword);
//        memberDto.setOriginalImage("");
//        memberDto.setImage("");
        memberDto.setRole(Role.ROLE_USER);


        if (memberDto.getImageProfile() != null && !memberDto.getImageProfile().isEmpty()) {

            String originalProfileName = memberDto.getImageProfile().getOriginalFilename();
            FileRenameStrategy fileRenameStrategy = new DateTimeFileRenameStrategy();
            String renameProfileName = fileRenameStrategy.renameFile(originalProfileName);
            Path saveProfile = uploadFolder.resolve(renameProfileName);

            try {
                memberDto.getImageProfile().transferTo(saveProfile);
                Thumbnails.of(saveProfile.toFile())
                        .size(100, 100)
                        .keepAspectRatio(true)
                        .toFile(saveProfile.toFile());

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            memberDto.setOriginalImage(dateFolder + "/" + originalProfileName);
            memberDto.setImage(dateFolder + "/" + renameProfileName);
        }

        Member savedMember = memberDto.toMember();
        memberDao.save(savedMember);
    }

    public boolean existsByUserID(String userID) {
        return memberDao.existsByUserID(userID);
    }

    public boolean existsByUserName(String userName) {
        return memberDao.existsByUserName(userName);
    }

    public Optional<Member> findByUserID(String userID) {
        return memberDao.findByUserID(userID);
    }

    public boolean checkPassword(Member member, String rawPassword) {
        return bCryptPasswordEncoder.matches(rawPassword, member.getUserPW());
    }
}
