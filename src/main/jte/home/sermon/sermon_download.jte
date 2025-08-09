@param String sermon
@param String type

@template.layouts._header(title = sermon)

<audio controls autoplay>
    <source src="/file/sermon/download/${sermon}" type="${type}">
    Your browser doesn't support this audio player
</audio>

<a href="/file/sermon/download/${sermon}" class="btn btn-success m-2"><i class="bi bi-cloud-arrow-down-fill me-1"></i> Download</a>

@template.layouts._footer()

