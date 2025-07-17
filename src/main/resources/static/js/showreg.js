document.addEventListener('DOMContentLoaded', function() {
    initializeImageUpload();
    initializeYoutubePreview();
    addCharacterCounter();
});

// 이미지 업로드 및 미리보기
function initializeImageUpload() {
    const imageFile = document.getElementById('imageFile');
    const imagePreview = document.getElementById('imagePreview');
    const previewImg = document.getElementById('previewImg');
    const removeBtn = document.querySelector('.remove-image');
    const imageInput = document.getElementById('image');

    if (imageFile) {
        imageFile.addEventListener('change', function(e) {
            const file = e.target.files[0];
            if (file) {
                // 파일 크기 제한 (5MB)
                if (file.size > 5 * 1024 * 1024) {
                    alert('파일 크기가 5MB를 초과합니다.');
                    this.value = '';
                    return;
                }
                // 이미지 파일 타입 확인
                if (!file.type.startsWith('image/')) {
                    alert('이미지 파일만 업로드 가능합니다.');
                    this.value = '';
                    return;
                }
                // 미리보기 표시
                const reader = new FileReader();
                reader.onload = function(e) {
                    previewImg.src = e.target.result;
                    imagePreview.style.display = 'block';
                };
                reader.readAsDataURL(file);

                // 서버에 업로드
                const formData = new FormData();
                formData.append('image', file);
                const imageContainer = document.querySelector('.image-upload-container');
                imageContainer.classList.add('loading');
                fetch('/showoff/upload-image', {
                    method: 'POST',
                    body: formData
                })
                    .then(response => response.json())
                    .then(data => {
                        imageContainer.classList.remove('loading');
                        if (data.success && data.url) {
                            imageInput.value = data.url;
                        } else {
                            alert('이미지 업로드에 실패했습니다: ' + (data.message || '서버 오류'));
                            imageInput.value = '';
                            imageFile.value = '';
                            previewImg.src = '';
                            imagePreview.style.display = 'none';
                        }
                    })
                    .catch(error => {
                        imageContainer.classList.remove('loading');
                        alert('이미지 업로드 중 오류가 발생했습니다.');
                        imageInput.value = '';
                        imageFile.value = '';
                        previewImg.src = '';
                        imagePreview.style.display = 'none';
                    });
            } else {
                previewImg.src = '';
                imagePreview.style.display = 'none';
            }
        });
    }

    if (removeBtn) {
        removeBtn.addEventListener('click', function() {
            imageFile.value = '';
            imageInput.value = '';
            previewImg.src = '';
            imagePreview.style.display = 'none';
        });
    }
}

// 유튜브 미리보기
function initializeYoutubePreview() {
    const youtubeLink = document.getElementById('youtubeLink');
    const youtubePreview = document.getElementById('youtubePreview');
    const youtubeFrame = document.getElementById('youtubeFrame');

    if (youtubeLink) {
        youtubeLink.addEventListener('input', function() {
            const url = youtubeLink.value.trim();
            const regExp = /^.*(?:youtu.be\/|v\/|u\/\w\/|embed\/|watch\?v=|\&v=)([^#\&\?]*).*/;
            const match = url.match(regExp);
            if (match && match[1].length === 11) {
                youtubeFrame.src = 'https://www.youtube.com/embed/' + match[1];
                youtubePreview.style.display = 'block';
            } else {
                youtubeFrame.src = '';
                youtubePreview.style.display = 'none';
            }
        });
    }
}

// 글자 수 카운터 (선택사항)
function addCharacterCounter() {
    const subjectInput = document.getElementById('subject');
    const contentInput = document.getElementById('content');
    if (subjectInput) {
        addCounterToElement(subjectInput, 200);
    }
    if (contentInput) {
        addCounterToElement(contentInput, 4000);
    }
}

function addCounterToElement(element, maxLength) {
    const counter = document.createElement('div');
    counter.className = 'character-counter';
    counter.style.cssText = 'text-align: right; font-size: 0.8rem; color: #666; margin-top: 0.25rem;';
    const updateCounter = () => {
        const current = element.value.length;
        counter.textContent = `${current}/${maxLength}`;
        counter.style.color = current > maxLength ? '#dc3545' : '#666';
    };
    element.addEventListener('input', updateCounter);
    element.parentNode.appendChild(counter);
    updateCounter();
}
