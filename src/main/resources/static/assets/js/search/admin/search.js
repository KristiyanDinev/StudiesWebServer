

async function searchStudy() {
    hideError()

    let studyName = document.getElementById('studyName')
    studyName.classList.remove('is-invalid')

     try {
        const study = studyName.value.trim()
        if (study && selectedSeries.size == 0) {
            studyName.classList.add('is-invalid')
            return
        }

        let formData = new FormData()
        formData.append('alike_study', study)
        for (let s of selectedSeries) {
            formData.append('series', s)
        }

        const res = await fetch('/admin/search/studies', {
            method: 'POST',
            body: formData
        })

        if (!res.ok) {
            throw new Error()
        }

        window.location.pathname = window.location.pathname

    } catch {
         showError('<i class="bi bi-x-circle-fill me-2"></i>Can\'t get your search results.')
    }
}


document.addEventListener('DOMContentLoaded', async function() {
    let searchBtn = document.getElementById('search')
    searchBtn.addEventListener('click', async function() {
        await searchStudy()
    })

})
