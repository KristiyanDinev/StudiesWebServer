@param String song
@param String type

@template.layouts._header(title = song)

<audio controls autoplay>
    <source src="/file/song/download/${song}" type="${type}">
    Your browser doesn't support this audio player
</audio>

<a href="/file/song/download/${song}" class="btn btn-success m-2"><i class="bi bi-cloud-arrow-down-fill me-1"></i> Download</a>

@template.layouts._footer()

