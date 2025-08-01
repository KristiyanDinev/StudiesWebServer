<header>
    @template.layouts._header(title = "Playlist remove Song")
</header>
<body>
@template.layouts._navbar()

<div class="container-fluid py-5">
    <div class="row justify-content-center">
        <div class="col-12 col-sm-10 col-md-8 col-lg-6 col-xl-5">
            <!-- Main Card -->
            <div class="card border-0 shadow-lg">
                <!-- Card Header -->
                <div class="card-header bg-danger text-white border-0 py-4">
                    <div class="row align-items-center">
                        <div class="col">
                            <h3 class="card-title mb-0 fw-bold">
                                <i class="bi bi-dash-circle-fill me-3 fs-2"></i>
                                Remove Song from Playlist
                            </h3>
                        </div>
                    </div>
                </div>

                <!-- Card Body -->
                <div class="card-body p-4 p-md-5">
                    <!-- Info Alert -->
                    <div class="alert alert-warning border-0 mb-4" role="alert">
                        <div class="row align-items-center">
                            <div class="col-auto">
                                <div class="bg-warning bg-opacity-25 rounded-circle p-2">
                                    <i class="bi bi-exclamation-triangle-fill text-warning fs-5"></i>
                                </div>
                            </div>
                            <div class="col">
                                <div class="fw-semibold mb-1">Removal Notice</div>
                                <small class="text-muted">
                                    This will permanently remove the selected song from your playlist.
                                    The song will still be available in your library.
                                </small>
                            </div>
                        </div>
                    </div>

                    <div class="mb-4">
                        <label class="form-label fw-semibold text-dark mb-3" for="playlist">
                            <i class="bi bi-tag me-2"></i>Name of Playlist
                        </label>
                        <input type="text" id="playlist" placeholder="My Playlist"
                               class="form-control form-control-lg border-2 rounded p-3">
                    </div>

                    <!-- Search Section -->
                    <div class="mb-4">
                        <div class="position-relative">
                            <div class="form-floating">
                                <input type="text"
                                       class="form-control form-control-lg border-2 rounded-3"
                                       id="song-search"
                                       placeholder="Start typing to search for a song..."
                                       autocomplete="off"
                                       style="padding-right: var(--bs-spacer-5);">
                                <label for="song-search" class="text-muted">
                                    <i class="bi bi-dash-circle me-2"></i>Choose a song to remove
                                </label>
                            </div>

                            <!-- Dropdown for recommendations -->
                            <div id="song-dropdown"
                                 class="position-relative w-100 d-none"
                                 style="z-index: var(--bs-zindex-dropdown);">
                                <!-- Recommendations will be populated here -->
                            </div>
                        </div>
                    </div>

                    <!-- Error message container -->
                    <div class="alert d-none border-0" id="error" role="alert"></div>

                    <!-- Action Buttons -->
                    <div class="d-grid gap-3 mt-4 position-relative">
                        <button id="remove"
                                type="button"
                                class="btn btn-danger btn-lg py-3 border-0 rounded-3 fw-semibold">
                            <div class="row align-items-center justify-content-center">
                                <div class="col-auto">
                                    <i class="bi bi-dash-circle-fill fs-4"></i>
                                </div>
                                <div class="col-auto">
                                    Remove Song from Playlist
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
<script src="/assets/js/playlist/song/playlist_remove.js"></script>
</body>