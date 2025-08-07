const seriesElement = document.getElementById('series')
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
    const series = seriesElement.value.trim()
    seriesElement.classList.remove('is-invalid')

    if (!series) {
        seriesElement.classList.add('is-invalid')
        return
    }

    if (!confirm(`Delete ${series}`)) {
            return
        }

    let formData = new FormData()
    formData.append('series', series)

    hideError()
    try {
        const res = await fetch('/admin/playlists/study/delete', {
            method: 'POST',
            body: formData
        })

        if (!res.ok) {
            throw new Error()
        }

        window.location.pathname = '/admin/studies'
    } catch {
        showError("Couldn't delete that series. Please try again later.")
    }
})

