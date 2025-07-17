package com.petthegarden.petthegarden.showoff.dto;

import com.petthegarden.petthegarden.entity.Pet;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ShowRegDto {

//    @NotNull(message = "회원 ID는 필수입니다.")
    private Integer memberId;

//    @NotNull(message = "펫 ID는 필수입니다.")
    private Integer petId;

    @NotBlank(message = "제목을 입력해주세요.")
    @Size(max = 200, message = "제목은 200자 이내로 입력해주세요.")
    private String subject;

    @NotBlank(message = "내용을 입력해주세요.")
    @Size(max = 4000, message = "내용은 4000자 이내로 입력해주세요.")
    private String content;

    private String image;

    private String youtubeLink;

    public ShowRegDto() {}

    public ShowRegDto(Integer memberId, Integer petId, String petName, String subject, String content, String image, String youtubeLink) {
        this.memberId = memberId;
        this.petId = petId;
        this.subject = subject;
        this.content = content;
        this.image = image;
        this.youtubeLink = youtubeLink;
    }

    // Getters and Setters
    public Integer getMemberId() { return memberId; }
    public void setMemberId(Integer memberId) { this.memberId = memberId; }

    public Integer getPetId() { return petId; }
    public void setPetId(Integer petId) { this.petId = petId; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getYoutubeLink() { return youtubeLink; }
    public void setYoutubeLink(String youtubeLink) { this.youtubeLink = youtubeLink; }

    @Override
    public String toString() {
        return "ShowRegDto{" +
                "memberId=" + memberId +
                ", petId=" + petId +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                ", image='" + image + '\'' +
                ", youtubeLink='" + youtubeLink + '\'' +
                '}';
    }
}
