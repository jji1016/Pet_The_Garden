const tabMap = {
    profile: 0,
    talent: 1,
    freeboard: 2,
    pet: 3,
    follow: 4,
    qna: 5
};

$(document).ready(function () {
    // 현재 탭 파악 (URL에서)
    const urlParams = new URLSearchParams(window.location.search);
    const activeTab = urlParams.get("tab") || "profile";
    const index = tabMap[activeTab] || 0;

    $(".list1 > li").removeClass("l_on").eq(index).addClass("l_on");
    $(".content").hide().eq(index).show();

    // 탭 클릭 시
    $(".list1 > li").click(function () {
        const clickedIndex = $(this).index();
        $(".list1 > li").removeClass("l_on");
        $(this).addClass("l_on");

        $(".content").hide().eq(clickedIndex).show();
        history.replaceState(null, "", "?tab=" + Object.keys(tabMap)[clickedIndex]);
        // URL은 바뀌지만 새로고침 없이 처리됨
    });
});
