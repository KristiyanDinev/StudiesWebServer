@import project.kristiyan.WebServer.models.PaginationModel
@import project.kristiyan.WebServer.models.SongModel
@import project.kristiyan.database.entities.SongCategoryEntity
@import project.kristiyan.database.entities.SongPlaylistEntity
@import java.io.IOException
@import java.nio.file.Files
@import java.nio.file.attribute.BasicFileAttributes
@import java.time.ZoneId
@import java.time.format.DateTimeFormatter

@param PaginationModel<SongModel> songs

!{var dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");}

<header>
    @template.layouts._header(title = "Songs")
</header>
<body>
@template.layouts._navbar()

<h1 class="text-center text-dark fw-bold mb-4">Manage Songs</h1>

<a class="btn btn-primary mb-2 mx-2" href="/songs/upload">
    <i class="bi bi-cloud-arrow-up-fill me-1"></i> Upload Song
</a>

@template.layouts._pagination(page = songs.getCurrentPage(),
                            totalPages = songs.getTotalPages())

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
        @for(SongModel song : songs.getItems())
            !{
                BasicFileAttributes attrs = null;
                String uploadDate = "Unknown";
                try {
                    uploadDate = Files.readAttributes(song.getFile().toPath(), BasicFileAttributes.class)
                        .creationTime()
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .format(dateFormatter);
                } catch (IOException e) {
                    uploadDate = "Error reading date";
                }
            }
            <tr class="page-row">
                <td>${song.getFile().getName()}</td>
                <td>${song.getSongEntity().duration}s</td>
                <td class="mb-3">
                    <div id="category_display_${song.getSongEntity().id}" class="p-2">
                        @if(song.getSongCategoryEntities().isEmpty())
                            <span class="text-muted">No categories added</span>
                        @endif
                        @for(SongCategoryEntity songCategoryEntity : song.getSongCategoryEntities())
                            <span class="badge bg-primary me-2 mb-2">${songCategoryEntity.name.trim()}</span>
                        @endfor
                    </div>
                    <div class="d-none" id="edit_cat_${song.getSongEntity().id}">
                        <input type="text" id="category_input_${song.getSongEntity().id}" class="form-control mb-2" placeholder="Enter category name">
                        <div class="mb-2">
                            <button type="button" id="add_category_${song.getSongEntity().id}" class="btn btn-outline-primary mr-2">Add</button>
                            <button type="button" id="remove_category_${song.getSongEntity().id}" class="btn btn-outline-danger mx-2">Remove</button>
                        </div>
                    </div>
                </td>
                <td class="mb-3">
                    @for(SongPlaylistEntity songPlaylistEntity : song.getSongPlaylistEntities())
                        <span class="badge bg-success me-2 mb-2">${songPlaylistEntity.name.trim()}</span>
                    @endfor
                </td>
                <td>${uploadDate}</td>
                <td>${String.format("%.2f", song.getFile().length() / (1024.0 * 1024.0))}MB</td>
                <td>
                    <a href="/file/song/${song.getFile().getName()}" class="btn btn-success mb-2 mx-2"><i class="bi bi-cloud-arrow-down-fill me-1"></i> Download</a>
                    <button type="button" id="${song.getSongEntity().id}" name="${song.getFile().getName()}" class="delete btn btn-sm btn-danger mb-2 mx-2"><i class="bi bi-trash3 me-1"></i> Delete</button>
                    <button type="button" id="toggle_${song.getSongEntity().id}" class="btn btn-sm btn-primary mb-2 mx-2"><i class="bi bi-pencil-square me-1"></i> Edit Categories</button>
                    <button type="button" id="submit_${song.getSongEntity().id}" name="${song.getFile().getName()}" class="edit btn btn-sm btn-success mb-2 mx-2 d-none"><i class="bi bi-bookmark-check-fill me-1"></i> Submit</button>
                </td>
            </tr>
        @endfor
        </tbody>
    </table>
</div>

@template.layouts._footer()
<script src="/assets/js/songs.js"></script>
</body>
