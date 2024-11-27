document.getElementById('searchButton').addEventListener('click', function () {
    const filter = document.getElementById('searchFilter').value; // 필터 (제목 또는 작성자)
    const query = document.getElementById('searchText').value; // 검색어

    if (!query.trim()) {
        alert('검색어를 입력하세요.');
        return;
    }

    const queryParams = new URLSearchParams({ filter, query });

    fetch(`/contents/search?${queryParams.toString()}`)
        .then(response => {
            if (!response.ok) {
                throw new Error(`Search request failed with status ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            updateContentsTable(data); // 검색 결과를 표시
        })
        .catch(error => {
            console.error('Error during search:', error);
            alert('검색 중 오류가 발생했습니다.');
        });
});
