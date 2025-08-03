for (let deleteBtn of document.getElementsByClassName('delete')) {

    deleteBtn.addEventListener('click', async function (e) {
        const studyName = deleteBtn.id
        if (!confirm(`Are you sure you want to delete ${studyName} ?`)) return

        let error = document.getElementById('error')
        error.className = 'alert d-none'
        error.innerHTML = ''

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

            if (res.ok) {
                window.location.reload()
                return
            }

        } catch (error) {
            console.error('Error while deleting study:', error)
        }

        error.className = 'alert alert-danger m-2'
        error.innerHTML = '<i class="bi bi-x-circle-fill me-2"></i> Error while deleting study: '+studyName

        deleteBtn.innerHTML = originalText
        deleteBtn.disabled = false
    })
}