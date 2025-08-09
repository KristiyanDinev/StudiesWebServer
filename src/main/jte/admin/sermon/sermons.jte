@import project.kristiyan.WebServer.models.PaginationModel
@import project.kristiyan.WebServer.models.SermonModel
@import project.kristiyan.database.entities.sermon.SermonCategoryEntity
@import project.kristiyan.database.entities.sermon.SermonPlaylistEntity

@param PaginationModel<SermonModel> sermons


<header>
    @template.layouts._header(title = "Admin - Sermons")
</header>
<body>
@template.layouts._admin_navbar()

<h1 class="text-center text-dark fw-bold mb-4">Manage Sermons</h1>

@template.layouts.search._searchSermon()

<a class="btn btn-primary mb-2" href="/admin/sermons/upload">
    <i class="bi bi-cloud-arrow-up-fill me-1"></i> Upload Sermon
</a>

@template.layouts._pagination(page = sermons.currentPage)

<p class="alert d-none" id="error"></p>

<div class="table-responsive">
    <table class="table table-bordered table-striped text-center">
        <thead class="thead-dark">
        <tr>
            <th>Name</th>
            <th>Duration (seconds)</th>
            <th>Categories</th>
            <th>Playlists</th>
            <th>Uploaded Date</th>
            <th>Size (MB)</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        @for(SermonModel sermon : sermons.items)
            <tr class="page-row">
                <td>${sermon.sermonEntity.name}</td>
                <td>${sermon.sermonEntity.duration}s</td>
                <td class="mb-3">
                    <div id="category_display_${sermon.sermonEntity.id}" class="p-2">
                        @if(sermon.sermonCategoryEntities.isEmpty())
                            <span class="text-muted">No categories added</span>
                        @endif
                        @for(SermonCategoryEntity sermonCategoryEntity : sermon.sermonCategoryEntities)
                            <span class="badge bg-primary me-2 mb-2">${sermonCategoryEntity.name.trim()}</span>
                        @endfor
                    </div>
                    <div class="d-none" id="edit_cat_${sermon.sermonEntity.id}">
                        <input type="text" id="category_input_${sermon.sermonEntity.id}" class="form-control mb-2" placeholder="Enter category name">
                        <div class="mb-2">
                            <button type="button" id="add_category_${sermon.sermonEntity.id}" class="btn btn-outline-primary mr-2">Add</button>
                            <button type="button" id="remove_category_${sermon.sermonEntity.id}" class="btn btn-outline-danger mx-2">Remove</button>
                        </div>
                    </div>
                </td>
                <td class="mb-3">
                    @if(sermon.sermonPlaylistEntities.isEmpty())
                        <span class="text-muted">Not in any playlist yet</span>
                    @endif
                    @for(SermonPlaylistEntity sermonPlaylistEntity : sermon.sermonPlaylistEntities)
                        <span class="badge bg-success me-2 mb-2">${sermonPlaylistEntity.name.trim()}</span>
                    @endfor
                </td>
                <td>${sermon.uploadedDate}</td>
                <td>${sermon.fileSize}MB</td>
                <td>
                    <a href="/file/sermon/${sermon.sermonEntity.name}" class="btn btn-success mb-2 mx-2"><i class="bi bi-cloud-arrow-down-fill me-1"></i> Download</a>
                    <button type="button" id="${sermon.sermonEntity.id}" name="${sermon.sermonEntity.name}" class="delete btn btn-sm btn-danger mb-2 mx-2"><i class="bi bi-trash3 me-1"></i> Delete</button>
                    <button type="button" id="toggle_${sermon.sermonEntity.id}" class="btn btn-sm btn-primary mb-2 mx-2"><i class="bi bi-pencil-square me-1"></i> Edit Categories</button>
                    <button type="button" id="submit_${sermon.sermonEntity.id}" name="${sermon.sermonEntity.name}" class="edit btn btn-sm btn-success mb-2 mx-2 d-none"><i class="bi bi-bookmark-check-fill me-1"></i> Submit</button>
                </td>
            </tr>
        @endfor
        </tbody>
    </table>
</div>

@template.layouts._footer()
<script src="/assets/admin/js/sermons.js"></script>
<script src="/assets/home/js/search.js"></script>
</body>
