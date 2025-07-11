@import project.kristiyan.WebServer.models.PaginationModel
@import project.kristiyan.WebServer.models.SongModel
@import project.kristiyan.database.entities.SongCategoryEntity
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

@template.layouts._pagination(page = songs.getCurrentPage(),
                            totalPages = songs.getTotalPages())

<div class="table-responsive">
    <table class="table table-bordered table-striped text-center">
        <thead class="thead-dark">
        <tr>
            <th>Name</th>
            <th>Duration (seconds)</th>
            <th>Categories</th>
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
                    @if(song.getSongCategoryEntities().isEmpty())
                        <span class="text-muted">No categories added</span>
                    @endif
                    @for(SongCategoryEntity songCategoryEntity : song.getSongCategoryEntities())
                        <span class="badge bg-primary me-2 mb-2">${songCategoryEntity.name.trim()}</span>
                    @endfor
                </td>
                <td>${uploadDate}</td>
                <td>${String.format("%.2f", song.getFile().length() / (1024.0 * 1024.0))}</td>
                <td>
                    <a href="/file/song/${song.getFile().getName()}" class="btn btn-primary mb-2 mx-2">Download</a>
                    <button type="button" id="delete" class="btn btn-sm btn-danger mb-2 mx-2">Delete</button>
                </td>
            </tr>
        @endfor
        </tbody>
    </table>
</div>

@template.layouts._footer()
</body>
