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
});