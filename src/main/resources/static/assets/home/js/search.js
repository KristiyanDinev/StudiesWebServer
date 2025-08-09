let clearStudyBtn = document.getElementById('clear-search-study')
let clearSongBtn = document.getElementById('clear-search-song')
let clearSermonBtn = document.getElementById('clear-search-sermon')

if (!error) {
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
}
function hideError() {
    error.classList.add('d-none')
    error.classList.remove('alert-danger')
}
var selectedSeries = []
var selectedCategories = []
var selectedPlaylists = []

async function clearStudySearch() {
    hideError()
    const text = clearStudyBtn.innerHTML
    try {
        clearStudyBtn.disabled = true
        clearStudyBtn.innerHTML = 'Clearing Study Search'

        const res = await fetch('/home/search/study/clear', {
            method: 'POST'
        })

        clearStudyBtn.innerHTML = text
        clearStudyBtn.disabled = false

        if (!res.ok) {
            throw new Error()
        }

        window.location.pathname = window.location.pathname

    } catch {
        clearStudyBtn.innerHTML = text
        clearStudyBtn.disabled = false
        showError('<i class="bi bi-x-circle-fill me-2"></i> Couldn\'t clear out the search. Please try again.')
    }
}

async function clearSongSearch() {
    hideError()
    const text = clearSongBtn.innerHTML
    try {
        clearSongBtn.disabled = true
        clearSongBtn.innerHTML = 'Clearing Song Search'

        const res = await fetch('/home/search/song/clear', {
            method: 'POST'
        })

        clearSongBtn.innerHTML = text
        clearSongBtn.disabled = false

        if (!res.ok) {
            throw new Error()
        }

        window.location.pathname = window.location.pathname

    } catch {
        clearSongBtn.innerHTML = text
        clearSongBtn.disabled = false
        showError('<i class="bi bi-x-circle-fill me-2"></i> Couldn\'t clear out the search. Please try again.')
    }
}

async function clearSermonSearch() {
    hideError()
    const text = clearSermonBtn.innerHTML
    try {
        clearSermonBtn.disabled = true
        clearSermonBtn.innerHTML = 'Clearing Sermon Search'

        const res = await fetch('/home/search/sermon/clear', {
            method: 'POST'
        })

        clearSermonBtn.innerHTML = text
        clearSermonBtn.disabled = false

        if (!res.ok) {
            throw new Error()
        }

        window.location.pathname = window.location.pathname

    } catch {
        clearSermonBtn.innerHTML = text
        clearSermonBtn.disabled = false
        showError('<i class="bi bi-x-circle-fill me-2"></i>Couldn\'t clear out the search. Please try again.')
    }
}

async function getSeries() {
    hideError()
    try {
        const res = await fetch('/home/studies/series', {
            method: 'GET'
        })

        if (!res.ok) {
           throw new Error()
        }
            const series = await res.json()
            let selectElement = document.getElementById('seriesSelect')
            selectElement.innerHTML = ''
            series.forEach(s => {
                var _div = document.createElement('div');
                _div.className = 'mb-1 cursor-pointer shadow-sm p-1 border rounded';
                _div.innerHTML = `<small>${s}</small>`;
                _div.addEventListener('click', function () {
                        if (selectedSeries.includes(s)) {
                            selectedSeries.pop(s)
                            _div.classList.remove('bg-primary','text-white')

                        } else {
                            selectedSeries.push(s)
                            _div.classList.add('bg-primary', 'text-white')
                        }
                });
                selectElement.appendChild(_div);
            });



    } catch {
        showError('<i class="bi bi-x-circle-fill me-2"></i> Can\'t get the study series. Please try again.')
    }
}

async function getSongCategories() {
    hideError()
    try {
        const res = await fetch('/home/song/categories', {
            method: 'GET'
        })

        if (!res.ok) {
           throw new Error()
        }
            const categories = await res.json()
            let selectElement = document.getElementById('categoriesSelect')
            selectElement.innerHTML = ''
            categories.forEach(s => {
                var _div = document.createElement('div');
                _div.className = 'mb-1 cursor-pointer shadow-sm p-1 border rounded';
                _div.innerHTML = `<small>${s}</small>`;
                _div.addEventListener('click', function () {
                        if (selectedCategories.includes(s)) {
                            selectedCategories.pop(s)
                            _div.classList.remove('bg-primary','text-white')

                        } else {
                            selectedCategories.push(s)
                            _div.classList.add('bg-primary', 'text-white')
                        }
                });
                selectElement.appendChild(_div);
            });
    } catch {
        showError('<i class="bi bi-x-circle-fill me-2"></i> Can\'t get the song categories. Please try again.')
    }
}

async function getSongPlaylists() {
    hideError()
    try {
        const res = await fetch('/home/song/playlists', {
            method: 'GET'
        })

        if (!res.ok) {
           throw new Error()
        }
            const playlists = await res.json()
            let selectElement = document.getElementById('playlistSelect')
            selectElement.innerHTML = ''
            playlists.forEach(s => {
                var _div = document.createElement('div');
                _div.className = 'mb-1 cursor-pointer shadow-sm p-1 border rounded';
                _div.innerHTML = `<small>${s}</small>`;
                _div.addEventListener('click', function () {
                        if (selectedPlaylists.includes(s)) {
                            selectedPlaylists.pop(s)
                            _div.classList.remove('bg-success','text-white')

                        } else {
                            selectedPlaylists.push(s)
                            _div.classList.add('bg-success', 'text-white')
                        }
                });
                selectElement.appendChild(_div);
            });
    } catch {
        showError('<i class="bi bi-x-circle-fill me-2"></i> Can\'t get the song playlists. Please try again.')
    }
}

async function getSermonCategories() {
    hideError()
    try {
        const res = await fetch('/home/sermon/categories', {
            method: 'GET'
        })

        if (!res.ok) {
           throw new Error()
        }
            const categories = await res.json()
            let selectElement = document.getElementById('categoriesSelect')
            selectElement.innerHTML = ''
            categories.forEach(s => {
                var _div = document.createElement('div');
                _div.className = 'mb-1 cursor-pointer shadow-sm p-1 border rounded';
                _div.innerHTML = `<small>${s}</small>`;
                _div.addEventListener('click', function () {
                        if (selectedCategories.includes(s)) {
                            selectedCategories.pop(s)
                            _div.classList.remove('bg-primary','text-white')

                        } else {
                            selectedCategories.push(s)
                            _div.classList.add('bg-primary', 'text-white')
                        }
                });
                selectElement.appendChild(_div);
            });
    } catch {
        showError('<i class="bi bi-x-circle-fill me-2"></i> Can\'t get the sermon categories. Please try again.')
    }
}

async function getSermonPlaylists() {
    hideError()
    try {
        const res = await fetch('/home/sermon/playlists', {
            method: 'GET'
        })

        if (!res.ok) {
           throw new Error()
        }
            const playlists = await res.json()
            let selectElement = document.getElementById('playlistSelect')
            selectElement.innerHTML = ''
            playlists.forEach(s => {
                var _div = document.createElement('div');
                _div.className = 'mb-1 cursor-pointer shadow-sm p-1 border rounded';
                _div.innerHTML = `<small>${s}</small>`;
                _div.addEventListener('click', function () {
                        if (selectedPlaylists.includes(s)) {
                            selectedPlaylists.pop(s)
                            _div.classList.remove('bg-success','text-white')

                        } else {
                            selectedPlaylists.push(s)
                            _div.classList.add('bg-success', 'text-white')
                        }
                });
                selectElement.appendChild(_div);
            });
    } catch {
        showError('<i class="bi bi-x-circle-fill me-2"></i> Can\'t get the sermon playlists. Please try again.')
    }
}


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

        const res = await fetch('/home/search/studies', {
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

        const res = await fetch('/home/search/songs', {
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

async function searchSermon() {
    hideError()

    let sermonName = document.getElementById('sermonName')
    sermonName.classList.remove('is-invalid')

     try {
        const sermon = sermonName.value.trim()
        if (sermon && selectedCategories.size == 0 && selectedPlaylists.size == 0) {
            sermonName.classList.add('is-invalid')
            return
        }

        let formData = new FormData()
        formData.append('alike_sermon', sermon)
        for (let p of selectedCategories) {
            formData.append('categories', p)
        }
        for (let p of selectedPlaylists) {
            formData.append('playlists', p)
        }

        const res = await fetch('/home/search/sermons', {
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

    let searchSermonBtn = document.getElementById('search-sermon')
    if (searchSermonBtn) {
         searchSermonBtn.addEventListener('click', async function() {
                    await searchSermon()
         })
    }
})

document.addEventListener('DOMContentLoaded', async function() {
    if (clearStudyBtn) {
        await getSeries()
        clearStudyBtn.addEventListener('click', clearStudySearch)
        document.getElementById('search-study').addEventListener('click', searchStudy)
    }
    if (clearSongBtn) {
        await getSongCategories()
        await getSongPlaylists()
        clearSongBtn.addEventListener('click', clearSongSearch)
        document.getElementById('search-song').addEventListener('click', searchSong)
    }
    if (clearSermonBtn) {
        await getSermonCategories()
        await getSermonPlaylists()
        clearSermonBtn.addEventListener('click', clearSermonSearch)
        document.getElementById('search-sermon').addEventListener('click', searchSermon)
    }
})
