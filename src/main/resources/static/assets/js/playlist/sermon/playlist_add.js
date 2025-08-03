const searchInput = document.getElementById('sermon-search');
const dropdown = document.getElementById('sermon-dropdown');
const errorDiv = document.getElementById('error');
const uploadBtn = document.getElementById('upload');
const playlistElement = document.getElementById('playlist')

let selectedSermon = null;

function showError(message) {
    errorDiv.textContent = message;
    errorDiv.className = 'alert alert-danger';
}

function hideError() {
    errorDiv.className = 'alert d-none';
}

async function searchSermons(query) {
    query = query.trim();
    if (query.length < 2) {
        dropdown.classList.add('d-none');
        return;
    }

    const playlist = playlistElement.value
    if (!playlist) {
        return
    }

    const formData = new FormData();
    formData.append('alike_sermon', query);

    try {
        const response = await fetch('/admin/playlists/sermon/alike', {
            method: 'POST',
            body: formData
        });

        if (!response.ok) throw new Error('Failed to fetch');

        const sermons = await response.json();
        displaySuggestions(sermons);
    } catch (error) {
        showError('Failed to search sermons. Please try again.');
        dropdown.classList.add('d-none');
    }
}

function displaySuggestions(sermons) {
    dropdown.innerHTML = '';

    if (!sermons || sermons.length === 0) {
        dropdown.innerHTML = '<div class="rounded fs-3 border border-3 border-dark p-3 mt-3 mb-3 me-3">No sermons found</div>';
    } else {
        sermons.forEach(sermon => {
            const item = document.createElement('button');
            item.className = 'dropdown-item';
            item.type = 'button';
            item.innerHTML = `
                <div class="d-flex justify-content-between align-items-center flex-column flex-wrap border border-3 border-dark rounded p-3 mt-3 mb-3 me-3">
                    <span class="fw-bold fs-4">${escapeHtml(sermon.name || 'Unknown Sermon')}</span>
                    <div class="flex-row">
                        <span class="fs-5">${formatDuration(sermon.duration)}s</span>
                        <i class="bi bi-plus-circle fs-4 text-success"></i>
                    </div>
                </div>
            `;

            item.addEventListener('click', () => selectSermon(sermon));
            dropdown.appendChild(item);
        });
    }

    dropdown.classList.remove('d-none');
}

function selectSermon(sermon) {
    selectedSermon = sermon;
    searchInput.value = sermon.name || 'Unknown Sermon';
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
    selectedSermon = null;
    searchSermons(this.value);
});

searchInput.addEventListener('focus', async function() {
    if (this.value.trim().length >= 2) {
        searchSermons(this.value);
    }
});

document.addEventListener('click', function(event) {
    if (!searchInput.contains(event.target) && !dropdown.contains(event.target)) {
        dropdown.classList.add('d-none');
    }
});

uploadBtn.addEventListener('click', async function() {
    const playlist = playlistElement.value;

    if (!selectedSermon) {
        showError('Please select a sermon from the dropdown');
        return;
    }
    if (!playlist) {
        showError('Please specify the playlist name');
        return;
    }

    if (!confirm(`Add "${selectedSermon.name}" to "${playlist}"?`)) {
        return;
    }

    const formData = new FormData();
    formData.append('sermon', selectedSermon.name);
    formData.append('playlist', playlist);

    try {
        const response = await fetch('/admin/playlists/sermon/add', {
            method: 'POST',
            body: formData
        });

        if (response.ok) {
            window.location.pathname = '/admin/sermons';
        } else {
            throw new Error('Upload failed');
        }
    } catch {
        showError("Couldn't add sermon to playlist. Please try again.");
    }
});