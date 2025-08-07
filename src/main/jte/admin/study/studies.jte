@import project.kristiyan.WebServer.models.PaginationModel
@import project.kristiyan.WebServer.models.StudyModel
@import project.kristiyan.database.entities.study.StudySeriesEntity

@param PaginationModel<StudyModel> studies

<header>
    @template.layouts._header(title = "Studies")
</header>
<body>
@template.layouts._admin_navbar()

<h1 class="text-center text-dark fw-bold mb-4">Manage Studies</h1>

@template.layouts.search._searchStudy()

<a class="btn btn-primary mb-2" href="/admin/studies/upload">
    <i class="bi bi-cloud-arrow-up-fill me-1"></i> Upload Study
</a>

@template.layouts._pagination(page = studies.getCurrentPage())

<p class="alert d-none container mx-4" id="error"></p>

<div class="table-responsive">
    <table class="table table-bordered table-striped text-center">
        <thead class="thead-dark">
        <tr>
            <th>Name</th>
            <th>Series</th>
            <th>Uploaded Date</th>
            <th>Size (MB)</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody id="table_body">
        @for(StudyModel study : studies.getItems())
            <tr class="page-row">
                <td>${study.getStudyName()}</td>
                <td class="p-2">
                    @if(study.getStudySeriesEntities().isEmpty())
                        <span class="text-muted">Not in any series yet</span>
                    @endif
                    @for(StudySeriesEntity studySeriesEntity : study.getStudySeriesEntities())
                        <span class="badge bg-info text-muted me-2 mb-2">${studySeriesEntity.series_name}</span>
                    @endfor
                </td>
                <td>${study.getUploadedDate()}</td>
                <td>${study.getFileSize()}MB</td>
                <td>
                    <a href="/file/study/${study.getStudyName()}?download=false" class="btn btn-success mb-2 mx-2"><i class="bi bi-cloud-arrow-down-fill me-1"></i> Download</a>
                    <button type="button" id="${study.getStudyName()}" class="delete btn btn-sm btn-danger mb-2 mx-2"><i class="bi bi-trash3 me-1"></i> Delete</button>
                </td>
            </tr>
        @endfor
        </tbody>
    </table>
</div>

@template.layouts._footer()

<script src="/assets/js/studies.js"></script>
<script src="/assets/js/search/search.js"></script>
<script src="/assets/js/search/admin/search.js"></script>
</body>