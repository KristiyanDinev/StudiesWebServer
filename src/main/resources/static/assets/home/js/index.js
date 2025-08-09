
// Add event listeners for pagination buttons
document.addEventListener('DOMContentLoaded', function() {
    document.querySelectorAll('.page-link[data-page]').forEach(button => {
        button.addEventListener('click', function() {
            const currentPage = parseInt(this.dataset.current);
            const nextPage = parseInt(this.dataset.page);

            let elements = document.getElementsByClassName("page-row")
                if (elements.length < 10 && nextPage > currentPage) {
                    alert("You are already on the last page.")
                    return
                }

                window.location.href = window.location.pathname +
                        `?${new URLSearchParams({ page: nextPage }).toString()}`
        });
    });

    const homeNavBarElement = document.getElementById('home-navbar')
    if (!document.getElementById('home-navbar')) {
        return
    }
    if (document.getElementById('search-study')) {
        document.querySelectorAll('.page-row').forEach(item => {
                item.addEventListener('click', function() {
                    window.open(`/file/study/${item.id}?download=false`, '_blank')
                })
            })
    }

    if (document.getElementById('search-song')) {
            document.querySelectorAll('.page-row').forEach(item => {
                    item.addEventListener('click', function() {
                        window.open(`/file/song/${item.id}?download=false`, '_blank')
                    })
                })
    }

    if (document.getElementById('search-sermon')) {
                document.querySelectorAll('.page-row').forEach(item => {
                        item.addEventListener('click', function() {
                            window.open(`/file/sermon/${item.id}?download=false`, '_blank')
                        })
                    })
     }
});