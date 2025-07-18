function toggleDropdown() {
    const dropdown = document.getElementById('monthDropdown');
    const arrow = document.querySelector('.best-hof-arrow');

    if (dropdown.classList.contains('show')) {
        dropdown.classList.remove('show');
        arrow.style.transform = 'rotate(0deg)';
    } else {
        dropdown.classList.add('show');
        arrow.style.transform = 'rotate(180deg)';
    }
}

function selectMonth(month) {
    const currentUrl = new URL(window.location.href);
    currentUrl.searchParams.set('month', month);
    window.location.href = currentUrl.toString();
}

function goToDetail(element) {
    const postId = element.getAttribute('data-post-id');
    if (postId) {
        window.location.href = `/showoff/showdetail/${postId}`;
    }
}

document.addEventListener('click', function (event) {
    const dropdown = document.getElementById('monthDropdown');
    const monthSelector = document.querySelector('.best-hof-month-selector');

    if (monthSelector && !monthSelector.contains(event.target)) {
        dropdown.classList.remove('show');
        const arrow = document.querySelector('.best-hof-arrow');
        if (arrow) {
            arrow.style.transform = 'rotate(0deg)';
        }
    }
});

function handleImageError(img) {
    img.style.display = 'none';
    const placeholder = document.createElement('div');
    placeholder.className = 'best-hof-no-image';
    placeholder.innerHTML = '<i class="fas fa-image"></i>';
    img.parentNode.appendChild(placeholder);
}

function addLoadAnimation() {
    const cards = document.querySelectorAll('.best-hof-card');
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

function addHoverEffects() {
    const cards = document.querySelectorAll('.best-hof-card');
    cards.forEach(card => {
        card.addEventListener('mouseenter', function () {
            this.style.transform = 'translateY(-8px) scale(1.02)';
        });

        card.addEventListener('mouseleave', function () {
            this.style.transform = 'translateY(0) scale(1)';
        });
    });
}

function setupKeyboardNavigation() {
    const cards = document.querySelectorAll('.best-hof-card');
    cards.forEach((card, index) => {
        card.setAttribute('tabindex', '0');
        card.addEventListener('keydown', function (e) {
            if (e.key === 'Enter' || e.key === ' ') {
                e.preventDefault();
                card.click();
            }
        });
    });
}

function initBestPage() {
    addLoadAnimation();
    addHoverEffects();
    setupKeyboardNavigation();

    const images = document.querySelectorAll('.best-hof-card-image img');
    images.forEach(img => {
        img.addEventListener('error', function () {
            handleImageError(this);
        });
    });
}

window.addEventListener('load', function () {
    initBestPage();
});

document.addEventListener('DOMContentLoaded', function () {
    const monthSelector = document.querySelector('.best-hof-month-selector');
    if (monthSelector) {
        monthSelector.addEventListener('click', function (e) {
            e.stopPropagation();
        });
    }
});

