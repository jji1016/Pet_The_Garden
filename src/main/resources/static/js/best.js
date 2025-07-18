// 드롭다운 메뉴 토글 함수
function toggleDropdown() {
    const dropdown = document.getElementById('monthDropdown');
    const arrow = document.querySelector('.arrow-drop-down');

    if (dropdown.classList.contains('show')) {
        dropdown.classList.remove('show');
        arrow.style.transform = 'rotate(0deg)';
    } else {
        dropdown.classList.add('show');
        arrow.style.transform = 'rotate(180deg)';
    }
}

// 월 선택 함수
function selectMonth(month) {
    // 현재 페이지 URL에 month 파라미터 추가해서 리다이렉트
    const currentUrl = new URL(window.location.href);
    currentUrl.searchParams.set('month', month);
    window.location.href = currentUrl.toString();
}

// 게시글 상세 페이지로 이동
function goToDetail(element) {
    const postId = element.getAttribute('data-post-id');
    if (postId) {
        window.location.href = `/showoff/showdetail/${postId}`;
    }
}

// 클릭 이벤트로 드롭다운 외부를 클릭하면 닫기
document.addEventListener('click', function(event) {
    const dropdown = document.getElementById('monthDropdown');
    const dropdownContainer = document.querySelector('.overlap-3');

    if (!dropdownContainer.contains(event.target)) {
        dropdown.classList.remove('show');
        document.querySelector('.arrow-drop-down').style.transform = 'rotate(0deg)';
    }
});

// 이미지 로드 오류 처리
function handleImageError(img) {
    img.style.display = 'none';
    // 기본 이미지를 표시하거나 다른 처리
    const placeholder = document.createElement('div');
    placeholder.className = 'image-placeholder';
    placeholder.innerHTML = '<i class="fas fa-image"></i>';
    img.parentNode.insertBefore(placeholder, img);
}

// 애니메이션 효과
function addLoadAnimation() {
    const cards = document.querySelectorAll('.overlap-4, .overlap, .overlap-2');
    cards.forEach((card, index) => {
        card.style.opacity = '0';
        card.style.transform = 'translateY(50px)';

        setTimeout(() => {
            card.style.transition = 'all 0.6s ease-out';
            card.style.opacity = '1';
            card.style.transform = 'translateY(0)';
        }, index * 200);
    });
}

// 페이지 로드 시 애니메이션 실행
window.addEventListener('load', function() {
    addLoadAnimation();
});

// 페이지 로드 시 이미지 오류 처리 설정
document.addEventListener('DOMContentLoaded', function() {
    const images = document.querySelectorAll('.element img, .element-2 img, .img img');
    images.forEach(img => {
        img.addEventListener('error', function() {
            handleImageError(this);
        });
    });
});
