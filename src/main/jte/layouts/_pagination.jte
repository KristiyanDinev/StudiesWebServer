@param int page

    <nav aria-label="Page navigation" class="mt-4">
        <ul class="pagination justify-content-center">
            <!-- Previous Button -->
            @if(page > 1)
                <li class="page-item">
                    <button class="page-link" data-page="${page - 1}" data-current="${page}" aria-label="Previous">
                        <i class="bi bi-chevron-left"></i>
                        <span class="sr-only">Previous</span>
                    </button>
                </li>
            @else
                <li class="page-item disabled">
                    <button class="page-link" disabled aria-label="Previous">
                        <i class="bi bi-chevron-left"></i>
                        <span class="sr-only">Previous</span>
                    </button>
                </li>
            @endif

            <!-- First Page -->
            @if(page > 3)
                <li class="page-item">
                    <button class="page-link" data-page="1" data-current="${page}">1</button>
                </li>
                @if(page > 4)
                    <li class="page-item disabled">
                        <span class="page-link">...</span>
                    </li>
                @endif
            @endif



            <!-- Next Button -->
                <li class="page-item">
                    <button class="page-link" data-page="${page + 1}" data-current="${page}" aria-label="Next">
                        <span class="sr-only">Next</span>
                        <i class="bi bi-chevron-right"></i>
                    </button>
                </li>
        </ul>
    </nav>

    <!-- Page Info -->
    <div class="d-flex justify-content-center mt-2 mb-2">
        <span class="fw-bold">
            Page ${page}
        </span>
    </div>
