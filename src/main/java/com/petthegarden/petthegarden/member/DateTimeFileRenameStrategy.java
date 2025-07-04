package com.petthegarden.petthegarden.member;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeFileRenameStrategy implements FileRenameStrategy {
    public String renameFile(String originalFileName) {
        String extension  = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        String fileName = originalFileName.substring(0, originalFileName.lastIndexOf("."));
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return fileName + '_' +now + '_' + extension;
    }
}
