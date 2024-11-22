$(document).ready(function() {
    // 전체 선택 체크박스 클릭 시
    $('#selectAll').change(function() {
        // 전체 체크박스가 체크되었는지 여부 확인
        let isChecked = $(this).prop('checked');
        // 모든 메시지 체크박스를 선택하거나 해제
        $('.message-checkbox').prop('checked', isChecked);
    });
	
 	// 선택한 메시지 삭제
    $('#deleteSelected').click(function() {
    	let selectedIds = [];
        // 체크된 메시지의 ID를 배열에 추가
        $('.message-checkbox:checked').each(function() {
            selectedIds.push($(this).val());
        });

		if (selectedIds.length > 0) {
            // 삭제 로직 (여기서는 ID들을 서버로 전송)
            $.ajax({
                url: '/message/deletesent', // 서버의 삭제 요청 URL
                type: 'POST',
                data: { 
                    messageIds: selectedIds 
                },
                success: function(response) {
                    alert('선택한 메시지가 삭제되었습니다.');
                    location.reload(); // 페이지 새로 고침 (메시지 목록을 업데이트하려면)
                },
                error: function(xhr, status, error) {
                    alert('삭제 실패: 다시 시도해 주세요.');
                }
            });
        } else {
            alert('삭제할 메시지를 선택해주세요.');
        }
		
		
    });

    // 검색 버튼 클릭 시
    $('#searchButton').click(function() {
    	let filter = $('#searchFilter').val();
    	let query = $('#searchText').val();
        // 실제 검색 처리 로직이 필요하면 여기에 AJAX를 사용하여 서버와 연동할 수 있습니다.
		
		//let url = window.location.href;
		//let url = window.location.origin + "/message/sent/search";  // 기본 URL
		let url = "/message/sent/search";  // 기본 URL
	    url += "?filter="+encodeURIComponent(filter)+"&query="+encodeURIComponent(query);
	    //url += "?filter="+filter+"&query="+query;
		window.location.href = url;

	    // 4. URL 호출 (AJAX)
		/*$.ajax({
	        url: url,            // 만들어진 URL
	        type: 'GET',         // GET 요청
	        //dataType: 'json',    // 응답 데이터 타입 (JSON)
	        success: function(response) {
	            // 서버로부터 응답을 받은 후 처리
	            console.log('검색 결과:', response);
	            // 여기에서 UI를 업데이트 하거나 데이터를 표시하는 로직을 추가할 수 있습니다.
	        },
	        error: function(xhr, status, error) {
	            // 에러 처리
	            console.error('검색 중 오류 발생:', error);
	        }
	    });*/
    });
	
	//컬럼 헤더 클릭시 정렬되도록
	let sortOrder = {}; // 동적으로 정렬 상태를 저장

    // 초기화: 테이블 열의 갯수를 기반으로 동적 설정
    $("th").each(function(index) {
        sortOrder[index] = true; // 초기 정렬 상태를 ascending으로 설정
    });

    // Header click event
    $("th").click(function() {
		//debugger;
        const index = $(this).index();
        const rows = $(".message-table tbody tr").get();

        const ascending = sortOrder[index];

        // Sort rows
        rows.sort(function(rowA, rowB) {
            const cellA = $(rowA).children("td").eq(index).text();
            const cellB = $(rowB).children("td").eq(index).text();

            let comparison = 0;
            if ($.isNumeric(cellA) && $.isNumeric(cellB)) {
                comparison = parseFloat(cellA) - parseFloat(cellB);
            } else {
                comparison = cellA.localeCompare(cellB);
            }

            return ascending ? comparison : -comparison;
        });

        // Append sorted rows to the table
        $.each(rows, function(index, row) {
            $(".message-table tbody").append(row);
        });

        // Toggle the sort order for the next click
        sortOrder[index] = !ascending;
    });
    
 	// 메시지 목록 갱신 함수
    window.updateMessageTable = function() {
		location.reload();
		/*
        $.ajax({
            url: '/message/sent', // 보낸 메시지 목록 URL
            type: 'GET',
            success: function(response) {
            	 // 새로운 메시지 목록을 테이블에 적용
                let newTableBody = $($.parseHTML(response)).find("#messageTableBody");

                // 새로운 테이블에 id를 현재 페이지의 id와 동일하게 설정
                // newTableBody.attr('id', 'messageTableBody');

                // 기존 #messageTableBody를 새로 불러온 테이블로 교체
                $('#messageTableBody').replaceWith(newTableBody);
            	
                // (이전 코드)새로운 메시지 목록을 테이블에 적용
                // let table = $($.parseHTML(response)).filter("#messageTableBody");
                // $('#messageTableBody').html(table); // 받은 HTML로 테이블 바꾸기
            },
            error: function(xhr, status, error) {
                alert("메시지 목록을 불러오는 데 실패했습니다.");
            }
        });*/
    }
});

//메시지 작성 창을 새 팝업으로 열기
function openComposeMessageWindow() {
    //window.open('/message/compose', 'Compose Message', 'width=600,height=400,resizable=yes,scrollbars=yes');
    let width = 600;
    let height = 400;
    let windowWidth = $(window).width();
    let windowHeight = $(window).height();
    let left = (windowWidth / 2) - (width / 2);
    let top = (windowHeight / 2) - (height / 2);
    
    window.open('/message/popupsend', 'Compose Message', `width=${width}, height=${height}, top=${top}, left=${left}, resizable=yes, scrollbars=yes`);
}