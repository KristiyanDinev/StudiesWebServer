const searchInput = document.getElementById('song-search');
const dropdown = document.getElementById('song-dropdown');
const errorDiv = document.getElementById('error');
const removeBtn = document.getElementById('remove');
const playlistInput = document.getElementById('playlist');

let selectedSong = null;

function showError(message) {
    errorDiv.textContent = message;
    errorDiv.className = 'alert alert-danger';
}

function hideError() {
    errorDiv.className = 'alert d-none';
}


async function getSongsAlike(query) {
    query = query.trim();
    if (query.length < 1) return [];

    const playlistValue = playlistInput.value.trim()
    if (!playlistValue) {
        return []
    }

    let formData = new FormData()
    formData.append('playlist', playlistValue)
    formData.append('alike_song', query)

    try {
        const res = await fetch('/admin/playlists/song/alike_by_playlist', {
            method: 'POST',
            body: formData
        })

        if (!res.ok) {
            showError("Can't get songs")
            return []
        }

        return await res.json()
    } catch {
        showError("Can't get songs")
        return []
    }
}

function displaySuggestions(songs) {
    dropdown.innerHTML = '';

    if (!songs || songs.length === 0) {
        dropdown.innerHTML = '<div class="rounded border border-3 border-dark p-3 mt-3 mb-3 me-3">No songs found</div>';
    } else {
        songs.forEach(song => {
            const item = document.createElement('button');
            item.className = 'dropdown-item';
            item.type = 'button';

            item.innerHTML = `
            <div class="d-flex justify-content-between align-items-center flex-column flex-wrap border border-3 border-dark rounded p-3 mt-3 mb-3 me-3">
                <span class="fw-bold fs-4">${escapeHtml(song.name || 'Unknown Song')}</span>
                <div class="flex-row">
                    <span class="fs-5">${formatDuration(song.duration)}s</span>
                    <i class="bi bi-dash-circle fs-4 text-danger"></i>
                </div>
            </div>
            `;

            item.addEventListener('click', () => selectSong(song));
            dropdown.appendChild(item);
        });
    }

    dropdown.classList.remove('d-none');
}

function selectSong(song) {
    selectedSong = song;
    searchInput.value = song.name || 'Unknown Song';
    dropdown.classList.add('d-none');
    hideError();

    // Visual feedback for removal selection
    searchInput.classList.add('is-valid');
    setTimeout(() => searchInput.classList.remove('is-valid'), 3000);
}

function formatDuration(seconds) {
    const totalSeconds = parseInt(seconds) || 0;
    if (totalSeconds <= 0) return '0:00';

    const minutes = Math.floor(totalSeconds / 60);
    const remainingSeconds = totalSeconds % 60;
    return `${minutes}:${remainingSeconds.toString().padStart(2, '0')}`;
}

function escapeHtml(text) {
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}


searchInput.addEventListener('input', async function() {
    const query = this.value;
    if (selectedSong && selectedSong.name !== query) {
        selectedSong = null;
    }
    const songs_alike = await getSongsAlike(query)
    displaySuggestions(songs_alike);
});

searchInput.addEventListener('focus', async function() {
    if (this.value.trim().length >= 1) {
        const songs_alike = await getSongsAlike(this.value)
        displaySuggestions(songs_alike);
    }
});

document.addEventListener('click', function(event) {
    if (!searchInput.contains(event.target) && !dropdown.contains(event.target)) {
        dropdown.classList.add('d-none');
    }
});

removeBtn.addEventListener('click', async function() {
    const playlist = playlistInput.value.trim();

    if (!selectedSong) {
        showError('Please select a song to remove');
        return;
    }
    if (!playlist) {
        showError('Please specify the playlist name');
        return;
    }

    if (!confirm(`Remove "${selectedSong.name}" from "${playlist}"?`)) {
        return;
    }

    const formData = new FormData();
    formData.append('song', selectedSong.name);
    formData.append('playlist', playlist);
    hideError()
    try {
        const response = await fetch('/admin/playlists/song/remove', {
            method: 'POST',
            body: formData
        });

        if (!response.ok) {
            throw new Error('Remove failed');
        }
        window.location.pathname = '/admin/songs';

    } catch {
        showError("Couldn't remove song from playlist. Please try again.");
    }
});