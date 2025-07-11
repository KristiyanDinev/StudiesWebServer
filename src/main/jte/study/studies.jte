@import project.kristiyan.WebServer.models.PaginationModel
@import java.nio.file.Files
@import java.nio.file.attribute.BasicFileAttributes
@import java.io.File
@import java.time.ZoneId
@import java.time.format.DateTimeFormatter
@import java.io.IOException

@param PaginationModel<File> studies

!{var dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");}

<header>
    @template.layouts._header(title = "Studies")
</header>
<body>
@template.layouts._navbar()

<h1 class="text-center text-dark fw-bold mb-4">Manage Studies</h1>

@template.layouts._pagination(page = studies.getCurrentPage(),
                            totalPages = studies.getTotalPages())

<div class="table-responsive">
    <table class="table table-bordered table-striped text-center">
        <thead class="thead-dark">
        <tr>
            <th>Name</th>
            <th>Uploaded Date</th>
            <th>Size (MB)</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        @for(File study : studies.getItems())
            !{
                BasicFileAttributes attrs = null;
                String uploadDate = "Unknown";
                try {
                    uploadDate = Files.readAttributes(study.toPath(), BasicFileAttributes.class)
                        .creationTime()
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .format(dateFormatter);
                } catch (IOException e) {
                    uploadDate = "Error reading date";
                }
            }
            <tr class="page-row">
                <td>${study.getName()}</td>
                <td>${uploadDate}</td>
                <td>${String.format("%.3f", study.length() / (1024.0 * 1024.0))}</td>
                <td>
                    <a href="/file/study/${study.getName()}" class="btn btn-primary mb-2 mx-2">Download</a>
                    <button type="button" id="delete" class="btn btn-sm btn-danger mb-2 mx-2">Delete</button>
                </td>
            </tr>
        @endfor
        </tbody>
    </table>
</div>

@template.layouts._footer()
</body>