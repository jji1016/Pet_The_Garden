function increaseLike(showOffId) {
    fetch(`/showoff/like/${showOffId}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' }
    })
        .then(r => r.json())
        .then(d => {
            if (d.success) {
                document.querySelector('.showdetail-meta span:last-child').textContent = d.likeCount;
            } else {
                alert(d.message || '에러');
            }
        })
        .catch(() => alert('본인 작성 글에는 좋아요를 누를 수 없습니다.'));
}
