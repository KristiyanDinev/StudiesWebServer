let clearStudyBtn = document.getElementById('clear-search-study')
let clearSongBtn = document.getElementById('clear-search-song')
let clearSermonBtn = document.getElementById('clear-search-sermon')

var selectedSeries = []

var selectedCategories = []
var selectedPlaylists = []


async function clearStudySearch() {
    hideError()
    const text = clearStudyBtn.innerHTML
    try {
        clearStudyBtn.disabled = true
        clearStudyBtn.innerHTML = 'Clearing Study Search'

        const res = await fetch('/search/study/clear', {
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

        const res = await fetch('/search/song/clear', {
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

        const res = await fetch('/search/sermon/clear', {
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
        const res = await fetch('/studies/series', {
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
        const res = await fetch('/song/categories', {
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
        const res = await fetch('/song/playlists', {
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

document.addEventListener('DOMContentLoaded', async function() {
    if (clearStudyBtn) {
        await getSeries()
        clearStudyBtn.addEventListener('click', clearStudySearch)
    }
    if (clearSongBtn) {
        await getSongCategories()
        await getSongPlaylists()
        clearSongBtn.addEventListener('click', clearSongSearch)
    }
    if (clearSermonBtn) {
        clearSermonBtn.addEventListener('click', clearSermonSearch)
    }
})
