let editor = null;
let originalContent = '';

// 수정 모드 토글
function toggleEditMode() {
    const viewMode = document.getElementById('view-mode');
    const editMode = document.getElementById('edit-mode');

    // 원본 내용 저장
    originalContent = document.querySelector('.content-body').innerHTML;

    // 모드 전환
    viewMode.style.display = 'none';
    editMode.style.display = 'block';

    // CKEditor 초기화
    initializeEditor();
}

// 수정 취소
function cancelEdit() {
    const viewMode = document.getElementById('view-mode');
    const editMode = document.getElementById('edit-mode');

    // CKEditor 제거
    if (editor) {
        editor.destroy();
        editor = null;
    }

    // 모드 전환
    editMode.style.display = 'none';
    viewMode.style.display = 'block';

    // 원본 내용 복원
    document.querySelector('.content-body').innerHTML = originalContent;
}

// CKEditor 초기화
function initializeEditor() {
    ClassicEditor
        .create(document.querySelector('#edit-editor'), {
            ckfinder: {
                uploadUrl: '/showoff/upload-image'
            }
        })
        .then(editorInstance => {
            editor = editorInstance;
        })
        .catch(error => {
            console.error('CKEditor 초기화 실패:', error);
        });
}

// 좋아요 증가
function increaseLike(showOffId) {
    fetch(`/showoff/like/${showOffId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        }
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                // 좋아요 수 업데이트
                const likeElement = document.querySelector('.likes span');
                if (likeElement) {
                    likeElement.textContent = data.likeCount;
                }
            } else {
                alert('좋아요 처리 중 오류가 발생했습니다.');
            }
        })
        .catch(error => {
            console.error('좋아요 요청 실패:', error);
            alert('좋아요 처리 중 오류가 발생했습니다.');
        });
}

// 페이지 로드 시 실행
document.addEventListener('DOMContentLoaded', function() {
    // 초기화 작업이 필요하면 여기에 추가
});
