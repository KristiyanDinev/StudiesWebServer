const searchInput = document.getElementById("sermon-search")
const dropdown = document.getElementById('sermon-dropdown')
const errorDiv = document.getElementById('error')
const removeBtn = document.getElementById("remove")
const playlistInput = document.getElementById('playlist')

selectedSermon = null

allSermons = []

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

async function loadSermonsByPlaylist(playlist) {
    if (!playlist) {
        allSermons = []
        return
    }

    try {
        let formData = new FormData()
        formData.append('playlist', playlist)

        const res = await fetch('/playlists/sermon', {
            method: 'POST',
            body: formData
        })

        if (res.ok) {
            allSermons = await res.json()
            return
        }
        allSermons = []
        showError("Couldn't load sermons for that playlist")
    } catch {
        allSermons = []
        showError("Couldn't load sermons for that playlist")
    }
}



function filterSermons(query) {
    query = query.trim().toLowerCase();
    if (query.length < 1) return [];

    return allSermons.filter(sermon =>
        sermon.name.toLowerCase().includes(query)
    ).slice(0, 10);
}

function displaySuggestions(sermons) {
    dropdown.innerHTML = '';

    if (!sermons || sermons.length === 0) {
        dropdown.innerHTML = '<div class="dropdown-item-text text-muted">No sermons found</div>';
    } else {
        sermons.forEach(sermon => {
            const item = document.createElement('button');
            item.className = 'dropdown-item d-flex justify-content-between align-items-center';
            item.type = 'button';

            const sermonName = escapeHtml(sermon.name || 'Unknown Sermon');
            const duration = formatDuration(sermon.duration);

            item.innerHTML = `
                <span>${sermonName}</span>
                <div>
                    <small class="text-muted me-2">${duration}</small>
                    <i class="bi bi-dash-circle text-danger"></i>
                </div>
            `;

            item.addEventListener('click', () => selectSermon(sermon));
            dropdown.appendChild(item);
        });

        if (sermons.length >= 10) {
            const indicator = document.createElement('div');
            indicator.className = 'dropdown-item-text text-muted small text-center';
            indicator.textContent = 'Showing first 10 results - keep typing to refine';
            dropdown.appendChild(indicator);
        }
    }

    dropdown.classList.remove('d-none');
}

function selectSermon(sermon) {
    selectedSermon = sermon;
    searchInput.value = sermon.name || 'Unknown Sermon';
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
    allSermons = [];
    selectedSermon = null;
    searchInput.value = '';
    dropdown.classList.add('d-none');

    if (playlist) {
        loadSermonsByPlaylist(playlist);
    }
});

searchInput.addEventListener('input', function() {
    const query = this.value;

    if (selectedSermon && selectedSermon.name !== query) {
        selectedSermon = null;
    }

    const filteredSermons = filterSermons(query);
    displaySuggestions(filteredSermons);
});

searchInput.addEventListener('focus', function() {
    if (this.value.trim().length >= 1) {
        const filteredSermons = filterSermons(this.value);
        displaySuggestions(filteredSermons);
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

    try {
        const response = await fetch('/playlists/sermon/remove', {
            method: 'POST',
            body: formData
        });

        if (response.ok) {
            window.location.pathname = '/sermons';
        } else {
            throw new Error('Remove failed');
        }
    } catch {
        showError("Couldn't remove sermon from playlist. Please try again.");
    }
});