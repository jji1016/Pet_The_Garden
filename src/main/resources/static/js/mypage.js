$(document).ready(function () {
    $(".list1>li").removeClass("l_on");
    $(".list2>li").removeClass("l_on");
    $(".list1>li").eq(0).addClass("l_on");

    $(".list1>li, .list2>li").click(function () {
        $(".list1>li, .list2>li").removeClass("l_on");
        $(this).addClass("l_on");
    });

    $(".list1>li").click(function () {
        let index = $(this).index();
        $(".content").hide();
        $(".content").eq(index).show();
    });

    $(".list2>li").click(function () {
        let index = $(this).index();
        $(".content").hide();
        $(".content").eq(index + 3).show();
    });
});