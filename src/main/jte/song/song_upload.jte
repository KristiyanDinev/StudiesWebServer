
<header>
    @template.layouts._header(title = "Song Upload")
</header>
<body>
@template.layouts._navbar()

<div class="p-4 border rounded shadow-sm bg-light" style="max-width: 600px; margin: auto;">
    <h4 class="mb-4">Upload a Song</h4>

    <!-- File input -->
    <div class="mb-3">
        <label for="file" class="form-label">Select Audio File <span class="text-danger">*</span></label>
        <input type="file" class="form-control" id="file" name="file" accept="audio/*" required>
        <div class="form-text">Only audio files are allowed. Max size: 3 GB.</div>
    </div>

    <!-- Editable filename input -->
    <div class="mb-3">
        <label for="filename" class="form-label">File Name (editable)</label>
        <p class="alert alert-warning m-2"><i class="bi bi-exclamation-triangle-fill me-2"></i> You can't edit the name of the file after uploading</p>
        <input type="text" class="form-control" id="filename" name="filename" placeholder="Enter file name">
    </div>

    <div class="mb-3">
        <label for="category_input" class="form-label">Song Categories</label>
        <div class="input-group mb-2">
            <input type="text" id="category_input" class="form-control" placeholder="Enter category name">
        </div>
        <div class="mb-2">
            <button type="button" id="add_category" class="btn btn-outline-primary mr-2">Add</button>
            <button type="button" id="remove_category" class="btn btn-outline-danger mx-2">Remove</button>
        </div>
    </div>

    <div class="mb-3">
        <label class="form-label">Categories:</label>
        <div id="category_display" class="border rounded p-2 bg-light">
            <span class="text-muted">No categories added</span>
        </div>
    </div>

    <div class="alert d-none" id="error">
    </div>

    <button id="upload" type="button" class="btn btn-success">Upload</button>
</div>
@template.layouts._footer()

<script src="/assets/js/song_upload.js"></script>
</body>