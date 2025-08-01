
<header>
    @template.layouts._header(title = "Study Upload")
</header>
<body>
@template.layouts._navbar()

<div class="p-4 border rounded shadow-sm">
    <h4 class="mb-4">Upload PDF Study</h4>

    <!-- File input -->
    <div class="mb-3">
        <label for="file" class="form-label">Select PDF File <span class="text-danger">*</span></label>
        <p class="alert alert-warning m-2"><i class="bi bi-exclamation-triangle-fill me-2"></i> You can't edit the name of the file after uploading</p>
        <input type="file" class="form-control" id="file" name="file" accept="application/pdf">
        <div class="form-text">Only PDF files are allowed. Max size: 5 GB.</div>
    </div>

    <!-- Editable filename input -->
    <div class="mb-3">
        <label for="filename" class="form-label">File Name (editable)</label>
        <input type="text" class="form-control" id="filename" name="filename" placeholder="Enter file name">
    </div>

    <div class="alert d-none" id="error">
    </div>

    <button id="upload" type="button" class="btn btn-success">Upload</button>
</div>
@template.layouts._footer()

<script src="/assets/js/study_upload.js"></script>
</body>