
<header>
    @template.layouts._header(title = "Playlist add Song")
</header>
<body>
@template.layouts._navbar()

<div class="p-4 border rounded shadow-sm bg-light" style="max-width: 600px; margin: auto;">
    <h4 class="mb-4">Add Song to Playlist</h4>

        <p class="alert alert-info"><i class="bi bi-info-circle-fill me-1"></i>
            This adds a song to playlist.
            It doesn't matter if that song is already in the playlist or not.
            If the playlist doesn't exist, it will be created with this one song you just added.</p>

    <div class="form-floating">
        <label for="song">Chose a song to add</label>
        <select class="form-select" id="song">
            <option value="">test</option>
        </select>
    </div>

    <div class="alert d-none" id="error">
    </div>

    <button id="upload" type="button" class="btn btn-success">Add</button>
</div>
@template.layouts._footer()

<script src="/assets/js/playlist/playlist_add.js"></script>
</body>