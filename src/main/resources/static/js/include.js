$(document).ready(function() {
    // 페이지 로드 시 세션에 저장된 다크모드 상태 확인
    if (sessionStorage.getItem('dark-mode') === 'enabled') {
        $('body').addClass('dark-mode');
        $('.mode-toggle-btn').addClass('dark-mode').text('🌞'); // 다크모드 활성화 상태로 버튼 텍스트 변경
    }

    // 버튼 클릭 시 다크모드, 라이트모드 전환
    $('#mode-toggle').click(function() {
        $('body').toggleClass('dark-mode');
        $('.mode-toggle-btn').toggleClass('dark-mode');

        // 버튼 텍스트 변경
        if ($('body').hasClass('dark-mode')) {
            $(this).text('🌞'); // 다크모드 -> 라이트모드로 변경
            sessionStorage.setItem('dark-mode', 'enabled'); // 세션에 다크모드 상태 저장
        } else {
            $(this).text('🌙'); // 라이트모드 -> 다크모드로 변경
            sessionStorage.removeItem('dark-mode'); // 세션에서 다크모드 상태 삭제
        }
    });
});
