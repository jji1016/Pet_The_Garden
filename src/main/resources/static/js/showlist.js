/**
 * showlist.js - 상세 페이지 이동만 하는 단순화 버전
 */

// 테이블 행 클릭 시 상세 페이지로 이동
function showPetDetail(petId) {
    // 상세 페이지 URL로 이동 (예시: /showoff/detail/1)
    window.location.href = '/showoff/showdetail/' + petId;
}

// 페이지 로드 후 테이블 행 호버 효과 (확대 효과 없이 배경색만 변경)
document.addEventListener('DOMContentLoaded', function() {
    const petRows = document.querySelectorAll('.pet-row');
    petRows.forEach(row => {
        // 행에 마우스 올리면 배경색만 바뀌게 (transform 제거)
        row.addEventListener('mouseenter', function() {
            this.style.backgroundColor = '#f8f9fa';
        });
        row.addEventListener('mouseleave', function() {
            this.style.backgroundColor = '';
        });
    });
});
