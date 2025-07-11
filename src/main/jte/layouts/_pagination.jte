@param int page
@param int totalPages

@if(totalPages > 1)
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

            <!-- Page Numbers -->
            @for(int i = Math.max(1, page - 2); i <= Math.min(totalPages, page + 2); i++)
                @if(i == page)
                    <li class="page-item active">
                        <button class="page-link" disabled>${i}</button>
                    </li>
                @else
                    <li class="page-item">
                        <button class="page-link" data-page="${i}" data-current="${page}">${i}</button>
                    </li>
                @endif
            @endfor

            <!-- Last Page -->
            @if(page < totalPages - 2)
                @if(page < totalPages - 3)
                    <li class="page-item disabled">
                        <span class="page-link">...</span>
                    </li>
                @endif
                <li class="page-item">
                    <button class="page-link" data-page="${totalPages}" data-current="${page}">${totalPages}</button>
                </li>
            @endif

            <!-- Next Button -->
            @if(page < totalPages)
                <li class="page-item">
                    <button class="page-link" data-page="${page + 1}" data-current="${page}" aria-label="Next">
                        <span class="sr-only">Next</span>
                        <i class="bi bi-chevron-right"></i>
                    </button>
                </li>
            @else
                <li class="page-item disabled">
                    <button class="page-link" disabled aria-label="Next">
                        <span class="sr-only">Next</span>
                        <i class="bi bi-chevron-right"></i>
                    </button>
                </li>
            @endif
        </ul>
    </nav>

    <!-- Page Info -->
    <div class="d-flex justify-content-center mt-2">
        <small class="text-muted">
            Page ${page} of ${totalPages}
        </small>
    </div>
@endif