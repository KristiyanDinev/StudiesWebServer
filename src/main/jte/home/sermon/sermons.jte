@import project.kristiyan.WebServer.models.PaginationModel
@import project.kristiyan.WebServer.models.SermonModel
@import project.kristiyan.database.entities.sermon.SermonCategoryEntity
@import project.kristiyan.database.entities.sermon.SermonPlaylistEntity

@param PaginationModel<SermonModel> sermons

<header>
    @template.layouts._header(title = "Sermons")
</header>
<body>
@template.layouts._navbar()

<!-- Hero Section -->
<div class="py-5 mb-5">
    <div class="container">
        <div class="row align-items-center">
            <div class="col-lg-8">
                <h1 class="display-4 fw-bold mb-3">
                    Sermons
                </h1>
            </div>
        </div>
    </div>
</div>

<div class="container">
    <!-- Search Section -->
    <div class="row mb-5">
        <div class="col-12">
            <div class="card border-0 shadow-sm">
                <div class="card-body p-4">
                    @template.layouts.search._searchSermon()
                </div>
            </div>
        </div>
    </div>

    <!-- Pagination -->
    @template.layouts._pagination(page = sermons.currentPage)

    <!-- Alert Container -->
    <div class="row mb-4">
        <div class="col-12">
            <div class="alert alert-danger d-none" id="error" role="alert">
                <i class="bi bi-exclamation-triangle-fill me-2"></i>
                <span id="error-message"></span>
            </div>
        </div>
    </div>

    <!-- Empty State -->
    @if(sermons.items.isEmpty())
        <div class="col-12">
            <div class="text-center py-5">
                <div class="mb-4">
                    <i class="bi bi-file-earmark-x text-muted display-1"></i>
                </div>
                <h3 class="text-muted mb-3">No Sermons Found</h3>
            </div>
        </div>
    @endif

    <!-- Sermons Grid -->
    <div class="row g-4 mb-5">
        @for(SermonModel sermon : sermons.items)
            <div class="col-6 page-row" style="cursor:pointer" id="${sermon.sermonEntity.name}">
                <div class="card h-100 border-0 shadow">
                    <!-- Card Header -->
                    <div class="card-header bg-light border-0">
                        <div class="d-flex align-items-center">
                            <div class="flex-shrink-0">
                                <div class="bg-warning bg-opacity-10 rounded-3 p-3">
                                    <i class="bi bi-mic-fill text-warning fs-2"></i>
                                </div>
                            </div>
                            <div class="flex-grow-1 ms-3">
                                <h5 class="card-title text-dark fw-bold mb-1">${sermon.sermonEntity.name}</h5>
                                <small class="text-muted">
                                    <i class="bi bi-calendar3 me-1"></i>
                                    ${sermon.uploadedDate}
                                </small>
                            </div>
                        </div>
                    </div>

                    <!-- Card Body -->
                    <div class="card-body">
                        <!-- Category Tags -->
                        <div class="mb-3">
                            <h6 class="text-muted mb-2 fw-semibold">
                                <i class="bi bi-collection me-1"></i>
                                Categories
                            </h6>
                            @if(sermon.sermonCategoryEntities.isEmpty())
                                <span class="badge bg-secondary">
                                    <i class="bi bi-dash-circle me-1"></i>
                                    Not in any categories
                                </span>
                            @else
                                @for(SermonCategoryEntity categoryEntity : sermon.sermonCategoryEntities)
                                    <span class="badge bg-primary me-2 mb-2">
                                        <i class="bi bi-tag-fill me-1"></i>
                                        ${categoryEntity.name}
                                    </span>
                                @endfor
                            @endif
                        </div>

                        <!-- Playlist Tags -->
                        <div class="mb-3">
                            <h6 class="text-muted mb-2 fw-semibold">
                                <i class="bi bi-collection me-1"></i>
                                Playlists
                            </h6>
                            @if(sermon.sermonPlaylistEntities.isEmpty())
                                <span class="badge bg-secondary">
                                    <i class="bi bi-dash-circle me-1"></i>
                                    Not in any playlists
                                </span>
                            @else
                                @for(SermonPlaylistEntity playlistEntity : sermon.sermonPlaylistEntities)
                                    <span class="badge bg-success me-2 mb-2">
                                        <i class="bi bi-tag-fill me-1"></i>
                                        ${playlistEntity.name}
                                    </span>
                                @endfor
                            @endif
                        </div>

                        <!-- File Info -->
                        <div class="d-flex align-items-center justify-content-between mb-3">
                            <div class="d-flex align-items-center">
                                <i class="bi bi-hdd text-primary me-2"></i>
                                <span class="text-muted fw-medium">${sermon.fileSize} MB</span>
                            </div>
                        </div>
                    </div>

                    <!-- Card Footer -->
                    <div class="card-footer bg-transparent border-0">
                        <div class="d-grid">
                            <a href="/file/sermon/download/${sermon.sermonEntity.name}"
                               class="btn btn-success btn-lg">
                                <i class="bi bi-cloud-arrow-down-fill me-2"></i>
                                Download
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        @endfor
    </div>
</div>

@template.layouts._footer()

<script src="/assets/home/js/search.js"></script>

</body>