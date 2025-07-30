const searchInput = document.getElementById('song-search');
const dropdown = document.getElementById('song-dropdown');
const errorDiv = document.getElementById('error');
const removeBtn = document.getElementById('remove');
const playlistInput = document.getElementById('playlist');

let selectedSong = null;
let allSongs = [];

function showError(message) {
    errorDiv.textContent = message;
    errorDiv.className = 'alert alert-danger';
}

function hideError() {
    errorDiv.className = 'alert d-none';
}

async function loadPlaylistSongs(playlist) {
    if (!playlist) {
        allSongs = [];
        dropdown.classList.add('d-none');
        return;
    }

    try {
        const formData = new FormData();
        formData.append('playlist', playlist);

        const response = await fetch('/playlists/song', {
            method: 'POST',
            body: formData
        });

        if (response.ok) {
            allSongs = await response.json();
        } else {
            allSongs = [];
        }
    } catch (error) {
        allSongs = [];
        showError('Failed to load playlist songs');
    }
}

function filterSongs(query) {
    query = query.trim().toLowerCase();
    if (query.length < 1) return [];

    return allSongs.filter(song =>
        song.name.toLowerCase().includes(query)
    ).slice(0, 10);
}

function displaySuggestions(songs) {
    dropdown.innerHTML = '';

    if (!songs || songs.length === 0) {
        dropdown.innerHTML = '<div class="dropdown-item-text text-muted">No songs found</div>';
    } else {
        songs.forEach(song => {
            const item = document.createElement('button');
            item.className = 'dropdown-item d-flex justify-content-between align-items-center';
            item.type = 'button';

            const songName = escapeHtml(song.name || 'Unknown Song');
            const duration = formatDuration(song.duration);

            item.innerHTML = `
                <span>${songName}</span>
                <div>
                    <small class="text-muted me-2">${duration}</small>
                    <i class="bi bi-dash-circle text-danger"></i>
                </div>
            `;

            item.addEventListener('click', () => selectSong(song));
            dropdown.appendChild(item);
        });

        if (songs.length >= 10) {
            const indicator = document.createElement('div');
            indicator.className = 'dropdown-item-text text-muted small text-center';
            indicator.textContent = 'Showing first 10 results - keep typing to refine';
            dropdown.appendChild(indicator);
        }
    }

    dropdown.classList.remove('d-none');
}

function selectSong(song) {
    selectedSong = song;
    searchInput.value = song.name || 'Unknown Song';
    dropdown.classList.add('d-none');
    hideError();

    // Visual feedback for removal selection
    searchInput.classList.add('border-danger');
    setTimeout(() => searchInput.classList.remove('border-danger'), 3000);
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
playlistInput.addEventListener('input', function() {
    const playlist = this.value.trim();
    allSongs = [];
    selectedSong = null;
    searchInput.value = '';
    dropdown.classList.add('d-none');

    if (playlist) {
        loadPlaylistSongs(playlist);
    }
});

searchInput.addEventListener('input', function() {
    const query = this.value;

    if (selectedSong && selectedSong.name !== query) {
        selectedSong = null;
    }

    const filteredSongs = filterSongs(query);
    displaySuggestions(filteredSongs);
});

searchInput.addEventListener('focus', function() {
    if (this.value.trim().length >= 1) {
        const filteredSongs = filterSongs(this.value);
        displaySuggestions(filteredSongs);
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

    try {
        const response = await fetch('/playlists/song/remove', {
            method: 'POST',
            body: formData
        });

        if (response.ok) {
            window.location.pathname = '/songs';
        } else {
            throw new Error('Remove failed');
        }
    } catch {
        showError("Couldn't remove song from playlist. Please try again.");
    }
});