

<div>

    <div>
        <div class="container">
            <div class="search-container">
                <h2 class="mb-4">Study Search</h2>

                <div id="studySearchForm">
                    <!-- Study Name Search Input -->
                    <div class="mb-3">
                        <label for="studyName" class="form-label">Study Name</label>
                        <input
                                type="text"
                                class="form-control"
                                id="studyName"
                                placeholder="Study name..."
                                minlength="2"
                                required>
                    </div>

                    <!-- Series Multi-Select Dropdown -->
                    <div class="mb-3">
                        <span>Select Series</span>
                        <div id="seriesSelect"
                                class="form-control">
                        </div>
                        <div class="form-text">
                            Select one or more series to search within. Leave empty to search all series.
                        </div>
                    </div>

                    <!-- Search Button -->
                    <div class="mb-3">
                        <button type="button" class="btn btn-primary" id="search">
                            <span class="search-text">Search Studies</span>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="/assets/js/search/admin/search.js"></script>