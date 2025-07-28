$(function() {
    const tabMap = {
        myProfile: 0,
        talent: 1,
        freeboard: 2,
        pet: 3,
        follow: 4,
        qna: 5
    };

    function loadFreeboardPage(pageNum = 0) {
        fetch(`/mypage/myboards?page=${pageNum}`)
            .then(res => res.text())
            .then(html => {
                const parser = new DOMParser();
                const doc = parser.parseFromString(html, "text/html");

                const newBody = doc.querySelector("#subscribeRap");
                const newPagination = doc.querySelector("#freeboard-pagination");

                if (newBody) $("#subscribeRap").html(newBody.innerHTML);
                if (newPagination) $("#freeboard-pagination").html(newPagination.innerHTML);

                attachPageEvent();
            });
    }

    function attachPageEvent() {
        $("#freeboard-pagination .page-link").off("click").on("click", function(e) {
            e.preventDefault();
            const page = $(this).data("page");
            loadFreeboardPage(page);
        });
    }

    // 초기 탭 세팅 (URL 쿼리 기반)
    const urlParams = new URLSearchParams(window.location.search);
    const activeTab = urlParams.get("tab") || "myProfile";
    showTab(activeTab);

    // 탭 클릭 이벤트
    $(".list1 > li").click(function() {
        const clickedIndex = $(this).index();
        const tabKey = Object.keys(tabMap)[clickedIndex];
        showTab(tabKey);
    });

    function showTab(tabKey) {
        const index = tabMap[tabKey] ?? 0;

        $(".list1 > li").removeClass("l_on").eq(index).addClass("l_on");
        $(".content").hide().eq(index).show();

        if (tabKey === "freeboard") {
            loadFreeboardPage(0);
        } else if (tabKey === "talent") {
            loadShowOffPage(0);
        }

        history.replaceState(null, "", "?tab=" + tabKey);
    }
    function loadShowOffPage(pageNum = 0) {
        fetch(`/mypage/myshowoffs?page=${pageNum}`)
            .then(res => res.text())
            .then(html => {
                const parser = new DOMParser();
                const doc = parser.parseFromString(html, "text/html");

                const newBody = doc.querySelector("#subscribeRap2");
                const newPagination = doc.querySelector("#talent-pagination");

                if (newBody) $("#subscribeRap2").html(newBody.innerHTML);
                if (newPagination) $("#subscribePagination2").html(newPagination.innerHTML);

                attachShowOffPageEvent();
            });
    }
    function attachShowOffPageEvent() {
        $("#talent-pagination .page-link").off("click").on("click", function (e) {
            e.preventDefault();
            const page = $(this).data("page");
            loadShowOffPage(page);
        });
    }
});