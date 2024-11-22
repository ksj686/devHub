$(document).ready(function() {
    // 폼 제출 시 AJAX 요청으로 메시지 전송
    $('#composeForm').submit(function(event) {
        event.preventDefault(); // 폼 기본 동작 방지

        // 전송할 데이터 준비
        let receiverId = $('#receiverId').val();
        let text = $('#messageText').val();

        $.ajax({
            url: '/message/insert', // 메시지 전송 URL
            type: 'POST',
            data: {
                receiverId: receiverId,
                text: text
            },
            complete:function(){
				console.log("전송완료");
            	alert("메시지가 전송되었습니다.");
                // 부모 창에서 테이블 갱신 함수 호출
                if (window.opener && window.opener.updateMessageTable) {
                    window.opener.updateMessageTable(); // 부모 창에서 테이블 갱신
                }
				window.close(); // 팝업 창 닫기
            }
            /* success: function(response) {
                // 메시지 전송 성공 후 팝업창 닫기
                alert("메시지가 전송되었습니다.");
                window.close(); // 팝업 창 닫기
                
                // 부모 창에서 테이블 갱신 함수 호출
                if (window.opener && window.opener.updateMessageTable) {
                    window.opener.updateMessageTable(); // 부모 창에서 테이블 갱신
                }
            },
            error: function(xhr, status, error) {
                alert("메시지 전송에 실패했습니다. 다시 시도해주세요.");
            } */
        });
    });
});
        
// 팝업 창 닫기 버튼 (선택 사항)
function closeWindow() {
    window.close();
}