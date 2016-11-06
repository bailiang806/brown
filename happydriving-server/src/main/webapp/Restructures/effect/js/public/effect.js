/**
 * Created by gaoying on 15/10/26.
 */
function initswiper(){
    var swiper = new Swiper('.swiper-container', {
        pagination: '.swiper-pagination',
        paginationElement : 'li',
        paginationClickable :true,
        spaceBetween:0,
        centeredSlides: true,
        autoplay: 8000,
        loop : true,
        autoplayDisableOnInteraction: false

    });
}