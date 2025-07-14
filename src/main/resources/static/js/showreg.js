// CKEditor 설정 및 초기화
let editor;

document.addEventListener('DOMContentLoaded', function() {
    initializeCKEditor();
    initializeFormValidation();
    initializeImageUpload();
});

/**
 * CKEditor 초기화
 */
function initializeCKEditor() {
    ClassicEditor
        .create(document.querySelector('#content'), {
            toolbar: {
                items: [
                    'heading',
                    '|',
                    'bold', 'italic', 'underline',
                    '|',
                    'link', 'bulletedList', 'numberedList',
                    '|',
                    'outdent', 'indent',
                    '|',
                    'imageUpload', 'blockQuote', 'insertTable',
                    '|',
                    'mediaEmbed',
                    '|',
                    'undo', 'redo'
                ]
            },
            language: 'ko',
            image: {
                toolbar: [
                    'imageTextAlternative',
                    'imageStyle:inline',
                    'imageStyle:block',
                    'imageStyle:side'
                ]
            },
            table: {
                contentToolbar: [
                    'tableColumn',
                    'tableRow',
                    'mergeTableCells'
                ]
            },
            mediaEmbed: {
                previewsInData: true,
                providers: [
                    {
                        name: 'youtube',
                        url: /^(?:m\.)?youtube\.com\/watch\?v=([\w-]+)/,
                        html: match => {
                            const id = match[1];
                            return (
                                '<div style="position: relative; padding-bottom: 56.25%; height: 0; overflow: hidden; max-width: 100%; height: auto;">' +
                                `<iframe src="https://www.youtube.com/embed/${id}" ` +
                                'style="position: absolute; top: 0; left: 0; width: 100%; height: 100%;" ' +
                                'frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen>' +
                                '</iframe>' +
                                '</div>'
                            );
                        }
                    },
                    {
                        name: 'youtu.be',
                        url: /^youtu\.be\/([\w-]+)/,
                        html: match => {
                            const id = match[1];
                            return (
                                '<div style="position: relative; padding-bottom: 56.25%; height: 0; overflow: hidden; max-width: 100%; height: auto;">' +
                                `<iframe src="https://www.youtube.com/embed/${id}" ` +
                                'style="position: absolute; top: 0; left: 0; width: 100%; height: 100%;" ' +
                                'frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen>' +
                                '</iframe>' +
                                '</div>'
                            );
                        }
                    }
                ]
            },
            // 이미지 업로드 어댑터 설정
            simpleUpload: {
                uploadUrl: '/showoff/upload-image',
                withCredentials: true,
                headers: {
                    'X-Requested-With': 'XMLHttpRequest'
                }
            }
        })
        .then(newEditor => {
            editor = newEditor;
            console.log('CKEditor가 성공적으로 초기화되었습니다.');

            // 에디터 높이 설정
            newEditor.ui.view.editable.element.style.height = '400px';

            // 이미지 업로드 오류 처리
            newEditor.plugins.get('FileRepository').createUploadAdapter = (loader) => {
                return new CustomUploadAdapter(loader);
            };
        })
        .catch(error => {
            console.error('CKEditor 초기화 실패:', error);
            showMessage('에디터 초기화에 실패했습니다.', 'error');
        });
}

/**
 * 커스텀 업로드 어댑터
 */
class CustomUploadAdapter {
    constructor(loader) {
        this.loader = loader;
    }

    upload() {
        return this.loader.file
            .then(file => new Promise((resolve, reject) => {
                this._initRequest();
                this._initListeners(resolve, reject, file);
                this._sendRequest(file);
            }));
    }

    abort() {
        if (this.xhr) {
            this.xhr.abort();
        }
    }

    _initRequest() {
        const xhr = this.xhr = new XMLHttpRequest();
        xhr.open('POST', '/showoff/upload-image', true);
        xhr.responseType = 'json';
    }

    _initListeners(resolve, reject, file) {
        const xhr = this.xhr;
        const loader = this.loader;
        const genericErrorText = '이미지를 업로드할 수 없습니다.';

        xhr.addEventListener('error', () => reject(genericErrorText));
        xhr.addEventListener('abort', () => reject());
        xhr.addEventListener('load', () => {
            const response = xhr.response;

            if (!response || response.error) {
                return reject(response && response.error && response.error.message ?
                    response.error.message : genericErrorText);
            }

            resolve({
                default: response.url
            });
        });

        if (xhr.upload) {
            xhr.upload.addEventListener('progress', evt => {
                if (evt.lengthComputable) {
                    loader.uploadTotal = evt.total;
                    loader.uploaded = evt.loaded;
                }
            });
        }
    }

    _sendRequest(file) {
        const data = new FormData();
        data.append('upload', file);
        this.xhr.send(data);
    }
}

/**
 * 폼 유효성 검사 초기화
 */
function initializeFormValidation() {
    const form = document.getElementById('showoffForm');
    const submitBtn = document.getElementById('submitBtn');

    if (form && submitBtn) {
        form.addEventListener('submit', function(e) {
            e.preventDefault();

            if (validateForm()) {
                submitForm();
            }
        });
    }
}

/**
 * 폼 유효성 검사
 */
function validateForm() {
    const petId = document.getElementById('petId').value;
    const subject = document.getElementById('subject').value.trim();

    // 반려동물 선택 확인
    if (!petId) {
        showMessage('반려동물을 선택해주세요.', 'error');
        document.getElementById('petId').focus();
        return false;
    }

    // 제목 확인
    if (!subject) {
        showMessage('제목을 입력해주세요.', 'error');
        document.getElementById('subject').focus();
        return false;
    }

    if (subject.length > 200) {
        showMessage('제목은 200자 이내로 입력해주세요.', 'error');
        document.getElementById('subject').focus();
        return false;
    }

    // CKEditor 내용 확인
    if (editor) {
        const content = editor.getData().trim();
        if (!content) {
            showMessage('내용을 입력해주세요.', 'error');
            editor.editing.view.focus();
            return false;
        }

        if (content.length > 4000) {
            showMessage('내용은 4000자 이내로 입력해주세요.', 'error');
            editor.editing.view.focus();
            return false;
        }

        // hidden input에 CKEditor 내용 설정
        document.getElementById('content').value = content;
    }

    return true;
}

/**
 * 폼 제출
 */
function submitForm() {
    const submitBtn = document.getElementById('submitBtn');
    const btnText = submitBtn.querySelector('.btn-text');
    const btnSpinner = submitBtn.querySelector('.btn-spinner');

    // 버튼 상태 변경
    submitBtn.disabled = true;
    btnText.style.display = 'none';
    btnSpinner.style.display = 'inline-block';

    // 실제 폼 제출
    document.getElementById('showoffForm').submit();
}

/**
 * 이미지 업로드 관련 초기화
 */
function initializeImageUpload() {
    // 드래그 앤 드롭 지원은 CKEditor에서 자동으로 처리됨
    console.log('이미지 업로드 기능이 초기화되었습니다.');
}

/**
 * 메시지 표시
 */
function showMessage(message, type = 'info') {
    // 기존 메시지 제거
    const existingMessage = document.querySelector('.alert');
    if (existingMessage) {
        existingMessage.remove();
    }

    // 새 메시지 생성
    const alertDiv = document.createElement('div');
    alertDiv.className = `alert alert-${type}`;
    alertDiv.textContent = message;

    // 폼 위에 메시지 삽입
    const form = document.querySelector('.registration-form');
    form.insertBefore(alertDiv, form.firstChild);

    // 3초 후 자동 제거
    setTimeout(() => {
        alertDiv.remove();
    }, 3000);
}

/**
 * 취소 버튼 처리
 */
function cancelForm() {
    if (confirm('작성 중인 내용이 있습니다. 정말 취소하시겠습니까?')) {
        history.back();
    }
}
