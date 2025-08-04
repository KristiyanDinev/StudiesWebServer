const playlistElement = document.getElementById('playlist')
const error = document.getElementById('error')
const deleteBtn = document.getElementById('remove')

function hideError() {
    error.className = "alert d-none"
}

function showError(message) {
    error.textContent = message
    error.className = "alert alert-danger"
}


deleteBtn.addEventListener('click', async function() {
    const playlistValue = playlistElement.value.trim()
    playlistElement.classList.remove('is-invalid')

    if (!playlistValue) {
        playlistElement.classList.add('is-invalid')
        return
    }

    if (!confirm(`Delete "${playlistValue}"?`)) {
            return;
     }

    let formData = new FormData()
    formData.append('playlist', playlistValue)

    hideError()
    try {
        const res = await fetch('/admin/playlists/song/delete', {
            method: 'POST',
            body: formData
        })

        if (res.ok) {
            window.location.pathname = '/admin/songs'
            return
        }
    } catch {}

    showError("Couldn't delete that playlist. Please try again later.")
})

