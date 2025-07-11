package com.petthegarden.petthegarden.showoff.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ShowRegDto {

    @NotNull(message = "회원 ID는 필수입니다.")
    private Integer memberId;

    @NotBlank(message = "제목을 입력해주세요.")
    @Size(max = 200, message = "제목은 200자 이내로 입력해주세요.")
    private String subject;

    @NotBlank(message = "내용을 입력해주세요.")
    @Size(max = 4000, message = "내용은 4000자 이내로 입력해주세요.")
    private String content;

    public ShowRegDto() {}

    public ShowRegDto(Integer memberId, String subject, String content) {
        this.memberId = memberId;
        this.subject = subject;
        this.content = content;
    }

    public Integer getMemberId() { return memberId; }
    public void setMemberId(Integer memberId) { this.memberId = memberId; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    @Override
    public String toString() {
        return "ShowRegDto{" +
                "memberId=" + memberId +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
