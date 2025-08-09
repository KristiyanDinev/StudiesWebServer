


        <div class="container">
            <div class="search-container">
                <h2 class="mb-4">Sermon Search</h2>

                <div>
                    <div class="mb-3">
                        <label for="sermonName" class="form-label">Sermon Name</label>
                        <input
                                type="text"
                                class="form-control"
                                id="sermonName"
                                placeholder="Sermon name..."
                                minlength="2">
                    </div>

                    <!-- Series Multi-Select Dropdown -->
                    <div class="mb-3">
                        <span>Select Categories</span>
                        <div id="categoriesSelect"
                                class="form-control">
                        </div>
                        <div class="form-text">
                            Select one or more categories to search within. Leave empty to search all categories.
                        </div>
                    </div>


                    <!-- Series Multi-Select Dropdown -->
                    <div class="mb-3">
                        <span>Select Playlists</span>
                        <div id="playlistSelect"
                             class="form-control">
                        </div>
                        <div class="form-text">
                            Select one or more playlists to search within. Leave empty to search all playlists.
                        </div>
                    </div>

                    <!-- Search Button -->
                    <div class="mb-3">
                        <button type="button" class="btn btn-primary" id="search-sermon">
                            <span class="search-text">Search Sermon</span>
                        </button>
                    </div>
                    <div class="mb-3">
                        <button type="button" class="btn btn-secondary" id="clear-search-sermon">
                            <span>Clear Search</span>
                        </button>
                    </div>
                </div>
            </div>
        </div>

