/////////////////////// 글 삭제 스크립트///////////////////////
document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll('.delete-button').forEach(button => {
        button.addEventListener('click', function () {
            const contentId = this.getAttribute('data-id'); // 글 ID
			
			// 경고 팝업 메시지
			const confirmDelete = confirm('정말로 이 글을 삭제하시겠습니까? 삭제된 글은 복구할 수 없습니다.');
			if (!confirmDelete) {
			    return; // 취소 버튼을 누르면 삭제 중단
			}
			
            // 삭제 요청
            fetch(`/contents/${contentId}`, {
                method: 'DELETE'
            })
            .then(response => {
                if (response.ok) {
                    alert('글 삭제가 완료되었습니다.');
                    window.location.href = '/contents'; // 삭제 후 목록 페이지로 이동
                } else if (response.status === 403) {
                    alert('작성자가 아니므로 글을 삭제할 수 없습니다.');
                } else if (response.status === 401) {
                    alert('로그인 후 글을 삭제할 수 있습니다.');
                } else {
                    alert('글 삭제에 실패했습니다. 다시 시도해주세요.');
                }
            })
            .catch(error => {
                console.error('Error occurred while deleting content:', error);
                alert('오류가 발생했습니다. 다시 시도해주세요.');
            });
        });
    });
});


///////////////////댓글 작성 스크립트/////////////////////
document.querySelector('#comment-form').addEventListener('submit', function (e) {
    e.preventDefault();

    // 현재 폼에서 data-id 속성 가져오기
    const contentId = this.getAttribute('data-id');
    if (!contentId) {
        alert('Content ID not found!');
        return;
    }

    const text = this.querySelector('textarea[name="text"]').value;

    fetch(`/contents/${contentId}/comments`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ text })
    }).then(response => {
        if (response.ok) {
            loadComments(contentId); // 댓글 목록 갱신
        } else {
            alert('Failed to add comment');
        }
    }).catch(error => {
        console.error('Error:', error);
        alert('An error occurred while adding the comment.');
    });
});


//////////////////댓글 삭제 스크립트////////////////////
document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll('.delete-comment-button').forEach(function (button) {
        button.addEventListener('click', function () {
            const commentId = this.getAttribute('data-comment-id');
            const contentId = this.getAttribute('data-content-id');

            fetch(`/contents/${contentId}/comments/${commentId}`, {
                method: 'DELETE'
            })
            .then(response => {
                if (response.ok) {
                    // 댓글 삭제 성공
                    const commentElement = this.closest('li');
                    commentElement.remove(); // 댓글 UI에서 제거
                } else if (response.status === 403) {
                    alert('댓글 삭제 권한이 없습니다.');
                } else if (response.status === 404) {
                    alert('댓글을 찾지 못했습니다.');
                } else {
                    alert('댓글 삭제에 실패하였습니다.');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('An error occurred while deleting the comment.');
            });
        });
    });
});
//////////////댓글 목록 갱신 스크립트//////////////////
function loadComments(contentId) {
    fetch(`/contents/${contentId}/comments`)
        .then(response => response.json())
        .then(comments => {
            const commentsSection = document.getElementById('comments-section');
            const commentsList = commentsSection.querySelector('ul');
            commentsList.innerHTML = ''; // 기존 댓글 초기화

            comments.forEach(comment => {
                const li = document.createElement('li');
                li.innerHTML = `
                    <strong>${comment.userId}</strong>: ${comment.text}
                    <small>${new Date(comment.dateCreated).toLocaleString()}</small>
                `;
                commentsList.appendChild(li);
            });
        });
}

/////////////////추천수 증가 스크립트/////////////
document.getElementById('recommend-button').addEventListener('click', function () {
    const contentId = this.getAttribute('data-id');

    // 추천 수 증가 요청
    fetch(`/contents/${contentId}/recommend`, {
        method: 'POST'
    }).then(response => {
        if (response.ok) {
            updateRecommendCount(contentId); // 추천 수 업데이트
        } else {
            alert('Failed to recommend the content.');
        }
    });
});

////////////////추천수 조회 스크립트////////
function updateRecommendCount(contentId) {
    // 추천 수 조회 요청
    fetch(`/contents/${contentId}/recommend`)
        .then(response => response.json())
        .then(data => {
            document.getElementById('recommend-count').innerText = data; // 추천 수 업데이트
        });
}



//////////////textarea 자동 조정 스크립트///////

document.addEventListener('DOMContentLoaded', function () {
    const textarea = document.querySelector('.main-area'); // 클래스 'main-area'를 가진 textarea 선택

    // 초기 크기 조정
    adjustTextareaHeight(textarea);

    // 내용 변경 시 크기 조정
    textarea.addEventListener('input', function () {
        adjustTextareaHeight(textarea);
    });
});

// 크기 조정 함수
function adjustTextareaHeight(textarea) {
    textarea.style.height = 'auto'; // 높이를 초기화
    textarea.style.height = textarea.scrollHeight + 'px'; // 내용에 맞는 높이로 설정
}