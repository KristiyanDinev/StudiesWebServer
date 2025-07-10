@import project.kristiyan.WebServer.dto.SongDto
@import java.io.IOException
@import java.nio.file.Files
@import java.nio.file.attribute.BasicFileAttributes
@import java.util.List
@import java.io.File
@import java.time.ZoneId
@import java.time.format.DateTimeFormatter

@param List<SongDto> songs

!{var dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");}

<header>
    @template.layouts._header(title = "Songs")
</header>
<body>
@template.layouts._navbar()

<h1 class="text-center text-dark fw-bold mb-4">Manage Songs</h1>

<div class="table-responsive">
    <table class="table table-bordered table-striped text-center">
        <thead class="thead-dark">
        <tr>
            <th>Name</th>
            <th>Duration (seconds)</th>
            <th>Uploaded Date</th>
            <th>Size (MB)</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        @for(SongDto song : songs)
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
            <tr>
                <td>${song.getFile().getName()}</td>
                <td>${song.getSongEntity().duration}s</td>
                <td>${uploadDate}</td>
                <td>${String.format("%.2f", song.getFile().length() / (1024.0 * 1024.0))}</td>
                <td>
                    <a href="/file/${song.getFile().getName()}" class="btn btn-primary mb-2 mx-2">Download</a>
                    <button type="button" id="delete" class="btn btn-sm btn-danger mb-2 mx-2">Delete</button>
                </td>
            </tr>
        @endfor
        </tbody>
    </table>
</div>

@template.layouts._footer()
</body>
