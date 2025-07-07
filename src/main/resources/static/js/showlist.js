/**
 * showlist.js - 단순화된 펫 장기자랑 페이지용 스크립트
 */

/**
 * 펫 상세 정보를 보여주는 함수 (예시 데이터)
 */
function showPetDetail(petId) {
    // 예시 데이터 (실제로는 서버에서 가져와야 함)
    const sampleData = {
        1: {
            name: "백덤블링 하는 원숭이",
            species: "고리조팡나무",
            owner: "고리조팡나무",
            regDate: "2025.06.25",
            content: "우리 원숭이가 백덤블링을 하는 모습입니다!",
            image: "/images/pets/sample1.jpg"
        },
        2: {
            name: "목 돌리는 아기 기린",
            species: "준민처름",
            owner: "준민처름",
            regDate: "2025.06.26",
            content: "목을 돌리는 귀여운 아기 기린이에요!",
            image: "/images/pets/sample2.jpg"
        },
        3: {
            name: "배다리 물어다 주는 호랑이",
            species: "생각치호의 해방분명",
            owner: "생각치호의 해방분명",
            regDate: "2025.06.27",
            content: "배다리를 물어다 주는 착한 호랑이입니다!",
            image: "/images/pets/sample3.jpg"
        },
        4: {
            name: "주인 가족 안부 물어주는 앵무새",
            species: "이옷집 또텔러",
            owner: "이옷집 또텔러",
            regDate: "2025.06.28",
            content: "가족들 안부를 묻는 똑똑한 앵무새예요!",
            image: "/images/pets/sample4.jpg"
        },
        5: {
            name: "다트 하는 다람쥐",
            species: "중고린 알라서점",
            owner: "중고린 알라서점",
            regDate: "2025.06.29",
            content: "다트를 하는 재주가 있는 다람쥐입니다!",
            image: "/images/pets/sample5.jpg"
        }
    };

    const pet = sampleData[petId];
    if (!pet) return;

    // 모달에 정보 설정
    document.getElementById('modalPetImage').src = pet.image;
    document.getElementById('modalPetImage').alt = pet.name;
    document.getElementById('modalPetName').textContent = pet.name;
    document.getElementById('modalPetSpecies').textContent = pet.species;
    document.getElementById('modalMemberNickname').textContent = pet.owner;
    document.getElementById('modalRegDate').textContent = pet.regDate;
    document.getElementById('modalPetContent').textContent = pet.content;

    // 모달 표시
    const modal = new bootstrap.Modal(document.getElementById('petDetailModal'));
    modal.show();
}

/**
 * 페이지 로드 완료 후 초기화
 */
document.addEventListener('DOMContentLoaded', function() {
    console.log('ShowOff page initialized');

    // 테이블 행 호버 효과
    const petRows = document.querySelectorAll('.pet-row');
    petRows.forEach(row => {
        row.addEventListener('mouseenter', function() {
            this.style.transform = 'scale(1.02)';
            this.style.transition = 'transform 0.2s ease';
        });

        row.addEventListener('mouseleave', function() {
            this.style.transform = 'scale(1)';
        });
    });
});
