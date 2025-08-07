let error = document.getElementById('error')

function showError(message) {
    error.innerHTML = message
    error.classList.add('alert-danger')
    error.classList.remove('d-none')
}

function hideError() {
    error.classList.add('d-none')
    error.classList.remove('alert-danger')
}

for (let deleteBtn of document.getElementsByClassName('delete')) {
    deleteBtn.addEventListener('click', async function (e) {
        const studyName = deleteBtn.id
        if (!confirm('Are you sure you want to delete '+studyName+'?')) return

        hideError()

        const originalText = deleteBtn.innerHTML
        deleteBtn.disabled = true
        deleteBtn.innerHTML = '<i class="bi bi-trash3 me-1"></i> Deleting...'

        let formData = new FormData()
        formData.append('study', studyName)

        try {
            const res = await fetch('/admin/studies/delete', {
                method: 'POST',
                body: formData
            })

            if (!res.ok) {
                throw new Error()
            }

            window.location.reload()

        } catch {
            deleteBtn.innerHTML = originalText
            deleteBtn.disabled = false
            showError('<i class="bi bi-x-circle-fill me-2"></i> Error while deleting study: '+studyName)
        }
    })
}