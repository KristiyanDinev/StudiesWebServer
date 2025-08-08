

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


async function searchSong() {
    hideError()

    let songName = document.getElementById('songName')
    songName.classList.remove('is-invalid')

     try {
        const song = songName.value.trim()
        if (song && selectedCategories.size == 0 && selectedPlaylists.size == 0) {
            songName.classList.add('is-invalid')
            return
        }

        let formData = new FormData()
        formData.append('alike_song', song)
        for (let p of selectedCategories) {
            formData.append('categories', p)
        }
        for (let p of selectedPlaylists) {
            formData.append('playlists', p)
        }

        const res = await fetch('/admin/search/songs', {
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
    let searchStudyBtn = document.getElementById('search-study')
    if (searchStudyBtn) {
        searchStudyBtn.addEventListener('click', async function() {
            await searchStudy()
        })
    }

    let searchSongBtn = document.getElementById('search-song')
    if (searchSongBtn) {
        searchSongBtn.addEventListener('click', async function() {
                await searchSong()
        })
    }
})
