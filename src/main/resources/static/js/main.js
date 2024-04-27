var swiper = new Swiper(".popular-content", {
    slidesPerView: 1,
    spaceBetween: 10,
    autoplay: {
        delay: 5500,
        disableOnInteraction: false,
    },
    pagination: {
        el: ".swiper-pagination",
        clickable: true,
    },
    navigation: {
        nextEl: ".swiper-button-next",
        prevEl: ".swiper-button-prev",
    },

    breakpoints: {
        320: {
            slidesPerView: 2,
            spaceBetween: 10
        },

        510: {
            slidesPerView: 2,
            spaceBetween: 10
        },

        758: {
            slidesPerView: 3,
            spaceBetween: 15
        },

        900: {
            slidesPerView: 4,
            spaceBetween: 20
        }
    },
});

//  show video
let playBtn = document.querySelector('.play-movie');
let videoContainer = document.querySelector('.video-container');
let closeBtn = document.querySelector('.close-video');

playBtn.onclick = () => {
    videoContainer.classList.add('show-video');
}

closeBtn.onclick = () => {
    videoContainer.classList.remove('show-video');
}

// Función para mostrar u ocultar el menú desplegable
function toggleDropdown() {
    var dropdown = document.getElementById("userDropdownContent");
    dropdown.classList.toggle("show");
}