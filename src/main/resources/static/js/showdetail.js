// 좋아요
function increaseLike(showOffId) {
    fetch(`/showoff/like/${showOffId}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' }
    })
        .then(r => r.json())
        .then(d => {
            if (d.success) {
                document.querySelector('.showdetail-meta span:last-child').textContent = d.likeCount;
            } else alert('에러');
        })
        .catch(() => alert('에러'));
}
