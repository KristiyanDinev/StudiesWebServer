const searchInput = document.getElementById("sermon-search")
const dropdown = document.getElementById('sermon-dropdown')
const errorDiv = document.getElementById('error')
const removeBtn = document.getElementById("remove")
const playlistInput = document.getElementById('playlist')

selectedSermon = null

function hideError() {
    errorDiv.className = 'alert d-none'
}

function showError(message) {
    errorDiv.textContent = message
    errorDiv.className = 'alert alert-danger'
}

function showSuccess(message) {
    errorDiv.textContent = message
    errorDiv.className = 'alert alert-success'
}


async function getSermonsAlike(query) {
    query = query.trim();
    if (query.length < 1) return [];

    const playlist = playlistInput.value.trim()
    if (!playlist) {
        return []
    }

    let formData = new FormData()
    formData.append('alike_sermon', query)
    formData.append('playlist', playlist)

    try {
        const res = await fetch('/admin/playlists/sermon/alike_by_playlist', {
            method: "POST",
            body: formData
        })
        
        if (!res.ok) {
            showError("Can't get sermons")
            return []
        }
        
        return await res.json()
    } catch {
        showError("Can't get sermons")
        return []
    }
}

function displaySuggestions(sermons) {
    dropdown.innerHTML = '';

    if (!sermons || sermons.length === 0) {
        dropdown.innerHTML = '<div class="rounded border border-3 border-dark p-3 mt-3 mb-3 me-3">No sermons found</div>';
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
                    <i class="bi bi-dash-circle fs-4 text-danger"></i>
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

    if (selectedSermon && selectedSermon.name !== query) {
        selectedSermon = null;
    }

    const sermons_alike = await getSermonsAlike(query)
    displaySuggestions(sermons_alike);
});

searchInput.addEventListener('focus', async function() {
    if (this.value.trim().length >= 1) {
        const sermons_alike = await getSermonsAlike(query)
        displaySuggestions(sermons_alike);
    }
});

document.addEventListener('click', function(event) {
    if (!searchInput.contains(event.target) && !dropdown.contains(event.target)) {
        dropdown.classList.add('d-none');
    }
});

removeBtn.addEventListener('click', async function() {
    const playlist = playlistInput.value.trim();

    if (!selectedSermon) {
        showError('Please select a sermon to remove');
        return;
    }
    if (!playlist) {
        showError('Please specify the playlist name');
        return;
    }

    if (!confirm(`Remove "${selectedSermon.name}" from "${playlist}"?`)) {
        return;
    }

    const formData = new FormData();
    formData.append('sermon', selectedSermon.name);
    formData.append('playlist', playlist);
    hideError()
    try {
        const response = await fetch('/admin/playlists/sermon/remove', {
            method: 'POST',
            body: formData
        });

        if (!response.ok) {
            throw new Error();
        }
        window.location.pathname = '/admin/sermons';
    } catch {
        showError("Couldn't remove sermon from playlist. Please try again.");
    }
});