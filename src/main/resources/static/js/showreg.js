let editor;

document.addEventListener('DOMContentLoaded', function() {
    initializeCKEditor();
    initializeFormValidation();
});

function initializeCKEditor() {
    ClassicEditor
        .create(document.querySelector('#content'), {
            // toolbar: {
            //     items: [
            //         'heading', '|', 'bold', 'italic', '|', 'link',
            //         'bulletedList', 'numberedList', '|', 'outdent', 'indent', '|',
            //         'imageUpload', 'blockQuote', 'insertTable', '|', 'mediaEmbed', '|',
            //         'undo', 'redo'
            //     ]
            // },
            language: 'ko',
            image: {
                toolbar: [
                    'imageTextAlternative', 'imageStyle:inline', 'imageStyle:block', 'imageStyle:side'
                ]
            },
            table: {
                contentToolbar: [
                    'tableColumn', 'tableRow', 'mergeTableCells'
                ]
            },
            mediaEmbed: {
                previewsInData: true,
                // providers: [
                //     {
                //         name: 'youtube',
                //         url: /^(?:m\.)?youtube\.com\/watch\?v=([\w-]+)/,
                //         html: match => {
                //             const id = match[1];
                //             return (
                //                 '<div style="position:relative;padding-bottom:56.25%;height:0;">' +
                //                 `<iframe src="https://www.youtube.com/embed/${id}" frameborder="0" allowfullscreen style="position:absolute;width:100%;height:100%;"></iframe>` +
                //                 '</div>'
                //             );
                //         }
                //     },
                //     {
                //         name: 'youtu.be',
                //         url: /^youtu\.be\/([\w-]+)/,
                //         html: match => {
                //             const id = match[1];
                //             return (
                //                 '<div style="position:relative;padding-bottom:56.25%;height:0;">' +
                //                 `<iframe src="https://www.youtube.com/embed/${id}" frameborder="0" allowfullscreen style="position:absolute;width:100%;height:100%;"></iframe>` +
                //                 '</div>'
                //             );
                //         }
                //     }
                // ]
            },
            simpleUpload: {
                uploadUrl: '/showoff/upload-image'
            }
        })
        .then(newEditor => {
            editor = newEditor;
            newEditor.ui.view.editable.element.style.height = '400px';

            // 안전하게 이미지 업로드 버튼 클릭 감지
            const imageUploadButton = editor.ui.componentFactory.create('imageUpload');
            if (imageUploadButton) {
                imageUploadButton.on('execute', () => {
                    console.log('이미지 업로드 버튼 클릭됨');
                });
            }
        })
        .catch(error => {
            console.error('CKEditor 초기화 실패:', error);
            showMessage('에디터 초기화에 실패했습니다.', 'error');
        });
}

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

function validateForm() {
    const subject = document.getElementById('subject').value.trim();
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
        // <p> → <ul><li> 변환하여 hidden input에 넣기
        let listItems = content.replace(/<p>(.*?)<\/p>/g, '<li>$1</li>');
        let convertedContent = `<ul>${listItems}</ul>`;
        document.getElementById('content').value = convertedContent;
    }
    return true;
}

function submitForm() {
    const submitBtn = document.getElementById('submitBtn');
    const btnText = submitBtn.querySelector('.btn-text');
    const btnSpinner = submitBtn.querySelector('.btn-spinner');
    submitBtn.disabled = true;
    btnText.style.display = 'none';
    btnSpinner.style.display = 'inline-block';
    document.getElementById('showoffForm').submit();
}

function showMessage(message, type = 'info') {
    const existingMessage = document.querySelector('.alert');
    if (existingMessage) {
        existingMessage.remove();
    }
    const alertDiv = document.createElement('div');
    alertDiv.className = `alert alert-${type}`;
    alertDiv.textContent = message;
    const form = document.querySelector('.registration-form');
    form.insertBefore(alertDiv, form.firstChild);
    setTimeout(() => {
        alertDiv.remove();
    }, 3000);
}
