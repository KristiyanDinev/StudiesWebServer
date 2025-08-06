

<div>
    <style>
        .search-container {
            max-width: 800px;
            margin: 2rem auto;
        }
        .study-results {
            margin-top: 2rem;
        }
        .custom-select-list {
    display: flex;
    flex-direction: column;
    max-height: 200px;
    overflow-y: auto;
    border: 1px solid #ccc;
    padding: 4px;
}

.custom-option {
    padding: 6px 10px;
    cursor: pointer;
    border-radius: 4px;
}

.custom-option:hover {
    background-color: #eee;
}

.custom-option.selected {
    background-color: #007bff;
    color: white;
}
    </style>

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
                                name="studyName"
                                placeholder="Study name..."
                                minlength="2"
                                required>
                    </div>

                    <!-- Series Multi-Select Dropdown -->
                    <div class="mb-3">
                        <label for="seriesSelect" class="form-label">Select Series</label>
                        <select
                                id="seriesSelect"
                                name="seriesIds"
                                class="selectpicker form-control"
                                multiple
                                data-live-search="true"
                                data-actions-box="true"
                                data-selected-text-format="count > 3"
                                title="Series">
                        </select>
                        <div class="form-text">
                            Select one or more series to search within. Leave empty to search all series.
                        </div>
                        <button id="clearButton" type="button">Clear</button>
                    </div>

                    <!-- Search Button -->
                    <div class="mb-3">
                        <button type="submit" class="btn btn-primary">
                            <span class="search-text">Search Studies</span>
                            <span class="loading-spinner spinner-border spinner-border-sm ms-2" role="status">
                                <span class="visually-hidden">Loading...</span>
                            </span>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="/assets/js/search/search.js"></script>