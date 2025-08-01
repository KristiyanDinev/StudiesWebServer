<header>
    @template.layouts._header(title = "Playlist Options")
</header>
<body>
@template.layouts._navbar()


    <div class="d-flex flex-row">
        <div class="container m-4">
            <h3 class="fw-bold">Songs</h3>
            <div>
                <a class="btn btn-primary mb-2 mx-2" href="/playlists/song/add">
                    <i class="bi bi-plus-circle-fill me-1"></i> Add Song to Playlist
                </a>
            </div>

            <div>
                <a class="btn btn-danger btn-sm mb-2 mx-2" href="/playlists/song/remove">
                    <i class="bi bi-dash-circle-fill me-1"></i> Remove Song from Playlist
                </a>
            </div>

            <div>
                <a class="btn btn-danger btn-sm mb-2 mx-2" href="/playlists/song/delete">
                    <i class="bi bi-trash3-fill me-1"></i> Delete Song Playlist
                </a>
            </div>
        </div>

        <div class="container m-4">
            <h3 class="fw-bold">Sermons</h3>
            <div>
                <a class="btn btn-primary mb-2 mx-2" href="/playlists/sermon/add">
                    <i class="bi bi-plus-circle-fill me-1"></i> Add Sermon to Playlist
                </a>
            </div>

            <div>
                <a class="btn btn-danger btn-sm mb-2 mx-2" href="/playlists/sermon/remove">
                    <i class="bi bi-dash-circle-fill me-1"></i> Remove Sermon from Playlist
                </a>
            </div>

            <div>
                <a class="btn btn-danger btn-sm mb-2 mx-2" href="/playlists/sermon/delete">
                    <i class="bi bi-trash3-fill me-1"></i> Delete Sermon Playlist
                </a>
            </div>
        </div>

        <div class="container m-4">
            <h3 class="fw-bold">Study Series</h3>
            <div>
                <a class="btn btn-primary mb-2 mx-2" href="/playlists/series/add">
                    <i class="bi bi-plus-circle-fill me-1"></i> Add Study to Series
                </a>
            </div>

            <div>
                <a class="btn btn-danger btn-sm mb-2 mx-2" href="/playlists/series/remove">
                    <i class="bi bi-dash-circle-fill me-1"></i> Remove Study from Series
                </a>
            </div>

            <div>
                <a class="btn btn-danger btn-sm mb-2 mx-2" href="/playlists/series/delete">
                    <i class="bi bi-trash3-fill me-1"></i> Delete Series
                </a>
            </div>
        </div>
    </div>


@template.layouts._footer()
</body>