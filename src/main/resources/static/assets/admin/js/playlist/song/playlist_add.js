const searchInput = document.getElementById('song-search');
const dropdown = document.getElementById('song-dropdown');
const errorDiv = document.getElementById('error');
const uploadBtn = document.getElementById('upload');
const playlistElement = document.getElementById('playlist')

let selectedSong = null;

function showError(message) {
    errorDiv.textContent = message;
    errorDiv.className = 'alert alert-danger';
}

function hideError() {
    errorDiv.className = 'alert d-none';
}

async function searchSongs(query) {
    query = query.trim();
    if (query.length < 2) {
        dropdown.classList.add('d-none');
        return;
    }

    const formData = new FormData();
    formData.append('alike_song', query);

    try {
        const response = await fetch('/admin/playlists/song/alike', {
            method: 'POST',
            body: formData
        });

        if (!response.ok) throw new Error('Failed to fetch');

        const songs = await response.json();
        displaySuggestions(songs);
    } catch (error) {
        showError('Failed to search songs. Please try again.');
        dropdown.classList.add('d-none');
    }
}

function displaySuggestions(songs) {
    dropdown.innerHTML = '';

    if (!songs || songs.length === 0) {
        dropdown.innerHTML = '<div class="rounded fw-3 border border-3 border-dark p-3 mt-3 mb-3 me-3">No songs found</div>';
    } else {
        songs.forEach(song => {
            const item = document.createElement('button');
            item.className = 'dropdown-item';
            item.type = 'button';
            item.innerHTML = `
                <div class="d-flex justify-content-between align-items-center flex-column flex-wrap border border-3 border-dark rounded p-3 mt-3 mb-3 me-3">
                    <span class="fw-bold fs-4">${escapeHtml(song.name || 'Unknown Song')}</span>
                    <div>
                        <span class="fs-5">${formatDuration(song.duration)}s</span>
                        <i class="bi bi-plus-circle fs-4 text-success"></i>
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

    // Brief success feedback
    searchInput.classList.add('is-valid');
    setTimeout(() => searchInput.classList.remove('is-valid'), 2000);
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

// Event listeners
searchInput.addEventListener('input', async function() {
    selectedSong = null;
    await searchSongs(this.value);
});

searchInput.addEventListener('focus', async function() {
    if (this.value.trim().length >= 2) {
        await searchSongs(this.value);
    }
});

document.addEventListener('click', function(event) {
    if (!searchInput.contains(event.target) && !dropdown.contains(event.target)) {
        dropdown.classList.add('d-none');
    }
});

uploadBtn.addEventListener('click', async function() {
    const playlist = playlistElement.value.trim();

    if (!selectedSong) {
        showError('Please select a song from the dropdown');
        return;
    }
    if (!playlist) {
        showError('Please specify the playlist name');
        return;
    }

    if (!confirm(`Add "${selectedSong.name}" to "${playlist}"?`)) {
        return;
    }

    const formData = new FormData();
    formData.append('song', selectedSong.name);
    formData.append('playlist', playlist);
    hideError()
    try {
        const response = await fetch('/admin/playlists/song/add', {
            method: 'POST',
            body: formData
        });

        if (!response.ok) {
            throw new Error();
        }
        window.location.pathname = '/admin/songs';
    } catch {
        showError("Couldn't add song to playlist. Please try again.");
    }
});