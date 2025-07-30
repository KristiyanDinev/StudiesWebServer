<header>
    @template.layouts._header(title = "Playlist add Song")
</header>
<body class="bg-light">
@template.layouts._navbar()

<div class="container-fluid py-5" style="min-height: calc(100vh - 140px);">
    <div class="row justify-content-center">
        <div class="col-12 col-sm-10 col-md-8 col-lg-6 col-xl-5">
            <!-- Main Card -->
            <div class="card border-0 shadow-lg">
                <!-- Card Header -->
                <div class="card-header bg-primary text-white border-0 py-4">
                    <div class="row align-items-center">
                        <div class="col">
                            <h3 class="card-title mb-0 fw-bold">
                                <i class="bi bi-music-note-list me-3 fs-2"></i>
                                Add Song to Playlist
                            </h3>
                        </div>
                    </div>
                </div>

                <!-- Card Body -->
                <div class="card-body p-4 p-md-5">
                    <!-- Info Alert -->
                    <div class="alert alert-info border-0 mb-4" role="alert">
                        <div class="row align-items-center">
                            <div class="col-auto">
                                <div class="bg-info bg-opacity-25 rounded-circle p-2">
                                    <i class="bi bi-info-circle-fill text-info fs-5"></i>
                                </div>
                            </div>
                            <div class="col">
                                <div class="fw-semibold mb-1">How it works</div>
                                <small class="text-muted">
                                    This adds a song to your playlist. It doesn't matter if that song is already in the playlist or not.
                                    If the playlist doesn't exist, it will be created with this one song you just added.
                                </small>
                            </div>
                        </div>
                    </div>

                    <!-- Search Section -->
                    <div class="mb-4">
                        <label class="form-label fw-semibold text-dark mb-3">
                            <i class="bi bi-search me-2"></i>Search for a song
                        </label>

                        <div class="position-relative">
                            <div class="form-floating">
                                <input type="text"
                                       class="form-control form-control-lg border-2 rounded-3"
                                       id="song-search"
                                       placeholder="Start typing to search for a song..."
                                       autocomplete="off"
                                       style="padding-right: var(--bs-spacer-5);">
                                <label for="song-search" class="text-muted">
                                    <i class="bi bi-music-note me-2"></i>Choose a song to add
                                </label>
                            </div>

                            <!-- Search Icon -->
                            <div class="position-absolute top-50 end-0 translate-middle-y pe-4">
                                <i class="bi bi-search text-muted fs-5"></i>
                            </div>

                            <!-- Dropdown for recommendations -->
                            <div id="song-dropdown"
                                 class="position-absolute w-100 d-none"
                                 style="z-index: var(--bs-zindex-dropdown);">
                                <!-- Recommendations will be populated here -->
                            </div>
                        </div>
                    </div>

                    <!-- Error message container -->
                    <div class="alert d-none border-0" id="error" role="alert"></div>

                    <!-- Action Buttons -->
                    <div class="d-grid gap-3 mt-4">
                        <button id="upload"
                                type="button"
                                class="btn btn-success btn-lg py-3 border-0 rounded-3 fw-semibold">
                            <div class="row align-items-center justify-content-center">
                                <div class="col-auto">
                                    <i class="bi bi-plus-circle-fill fs-4"></i>
                                </div>
                                <div class="col-auto">
                                    Add Song to Playlist
                                </div>
                            </div>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

@template.layouts._footer()

<!-- Custom Bootstrap CSS Variables and Enhancements -->
<style>
    :root {
        --bs-primary-rgb: 13, 110, 253;
        --bs-success-rgb: 25, 135, 84;
        --bs-info-rgb: 13, 202, 240;
        --bs-spacer-5: 3rem;
        --bs-zindex-dropdown: 1000;
    }

    .card {
        transition: transform var(--bs-transition-base);
        border-radius: var(--bs-border-radius-xl) !important;
    }

    .card:hover {
        transform: translateY(-2px);
    }

    .card-header {
        background: linear-gradient(135deg, rgb(var(--bs-primary-rgb)) 0%, rgb(13, 90, 213) 100%) !important;
        border-radius: var(--bs-border-radius-xl) var(--bs-border-radius-xl) 0 0 !important;
    }

    .form-control:focus {
        border-color: rgb(var(--bs-primary-rgb));
        box-shadow: 0 0 0 0.25rem rgba(var(--bs-primary-rgb), 0.25);
    }

    .btn-success {
        background: linear-gradient(135deg, rgb(var(--bs-success-rgb)) 0%, rgb(20, 108, 67) 100%);
        transition: all var(--bs-transition-base);
    }

    .btn-success:hover {
        transform: translateY(-1px);
        box-shadow: 0 0.5rem 1rem rgba(var(--bs-success-rgb), 0.3);
        background: linear-gradient(135deg, rgb(20, 108, 67) 0%, rgb(var(--bs-success-rgb)) 100%);
    }

    .btn-outline-secondary:hover {
        transform: translateY(-1px);
    }

    .alert-info {
        background: linear-gradient(135deg, rgba(var(--bs-info-rgb), 0.1) 0%, rgba(var(--bs-info-rgb), 0.05) 100%);
        border-left: 4px solid rgb(var(--bs-info-rgb));
    }

    /* Responsive Typography */
    @media (max-width: 576px) {
        .card-header h3 {
            font-size: 1.25rem;
        }
        .card-body {
            padding: var(--bs-spacer-3) !important;
        }
    }

    /* Custom scrollbar for potential dropdown */
    .custom-scrollbar::-webkit-scrollbar {
        width: 6px;
    }

    .custom-scrollbar::-webkit-scrollbar-track {
        background: var(--bs-gray-100);
        border-radius: var(--bs-border-radius);
    }

    .custom-scrollbar::-webkit-scrollbar-thumb {
        background: linear-gradient(45deg, rgb(var(--bs-primary-rgb)), rgb(var(--bs-info-rgb)));
        border-radius: var(--bs-border-radius);
    }
</style>

<script src="/assets/js/playlist/playlist_add.js"></script>
</body>