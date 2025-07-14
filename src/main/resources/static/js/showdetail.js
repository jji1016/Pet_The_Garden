function likeShowOff() {
    alert("추천되었습니다!");
    // 실제 추천 기능은 Ajax 등으로 구현
}

function submitComment(event) {
    event.preventDefault();
    alert("댓글이 등록되었습니다! (실제 저장 기능은 추후 구현)");
    event.target.reset();
    return false;
}
