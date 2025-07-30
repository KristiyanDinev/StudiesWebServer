const searchInput = document.getElementById('song-search');
const dropdown = document.getElementById('song-dropdown');
const errorDiv = document.getElementById('error');
const uploadBtn = document.getElementById('upload');

let searchTimeout;
let selectedSong = null;

// Function to show error message
function showError(message) {
    errorDiv.textContent = message;
    errorDiv.classList.remove('d-none', 'alert-success');
    errorDiv.classList.add('alert-danger');
}

// Function to hide error message
function hideError() {
    errorDiv.classList.add('d-none');
}

// Function to search songs
async function searchSongs(query) {
    query = query.trim()
    if (query.length < 2) {
        dropdown.classList.add('d-none');
        dropdown.style.display = 'none';
        return;
    }

    let formData = new FormData()
    formData.append('alike_song', query)
    try {
        const response = await fetch('/playlists/song/alike', {
            method: 'POST',
            body: formData
        });

        if (!response.ok) {
            throw new Error('Failed to fetch songs');
        }

        const songs = await response.json();
        displaySuggestions(songs);
    } catch (error) {
        showError('Failed to search songs. Please try again later.');
        dropdown.classList.add('d-none');
        dropdown.style.display = 'none';
    }
}

// Function to display suggestions in dropdown
function displaySuggestions(songs) {
    dropdown.innerHTML = '';

    if (!songs || songs.length === 0) {
        dropdown.innerHTML = `
            <div class="p-4 text-center text-muted">
                <i class="bi bi-music-note me-2"></i>
                <div>No songs found</div>
                <small class="text-muted">Try a different search term</small>
            </div>`;
    } else {
        songs.forEach((song, index) => {
            const item = document.createElement('div');
            item.className = 'song-item';

            const songName = escapeHtml(song.name || 'Unknown Song');
            const duration = formatDuration(song.duration);

            item.innerHTML = `
                <div class="song-content">
                    <div class="song-icon">
                        <i class="bi bi-music-note-beamed"></i>
                    </div>
                    <div class="song-details">
                        <div class="song-name">${songName}</div>
                        <div class="song-duration">
                            <i class="bi bi-clock me-1"></i>${duration}
                        </div>
                    </div>
                    <div class="song-action">
                        <i class="bi bi-plus-circle"></i>
                    </div>
                </div>
            `;

            item.addEventListener('click', function() {
                selectSong(song);
            });

            dropdown.appendChild(item);
        });

        // Add scroll indicator if many songs
        if (songs.length > 5) {
            const scrollIndicator = document.createElement('div');
            scrollIndicator.className = 'scroll-indicator';
            scrollIndicator.innerHTML = `
                <div class="text-center py-2">
                    <small class="text-muted">
                        <i class="bi bi-chevron-double-down me-1"></i>
                        ${songs.length} songs - scroll for more
                    </small>
                </div>
            `;
            dropdown.appendChild(scrollIndicator);
        }
    }

    // Apply custom styles
    dropdown.style.cssText = `
        max-height: 320px;
        overflow-y: auto;
        background: white;
        border: 1px solid #e0e0e0;
        border-radius: 12px;
        box-shadow: 0 8px 25px rgba(0, 0, 0, 0.15);
        z-index: 1050;
        top: 100%;
        left: 0;
        margin-top: 8px;
        backdrop-filter: blur(10px);
    `;

    // Add custom CSS for song items
    if (!document.getElementById('dropdown-styles')) {
        const style = document.createElement('style');
        style.id = 'dropdown-styles';
        style.textContent = `
            .song-item {
                padding: 0;
                cursor: pointer;
                transition: all 0.2s ease;
                border-bottom: 1px solid #f5f5f5;
            }

            .song-item:last-child {
                border-bottom: none;
            }

            .song-item:hover {
                background: linear-gradient(90deg, #f8f9ff 0%, #f0f4ff 100%);
                transform: translateX(4px);
            }

            .song-content {
                display: flex;
                align-items: center;
                padding: 16px 20px;
                gap: 16px;
            }

            .song-icon {
                width: 40px;
                height: 40px;
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                border-radius: 10px;
                display: flex;
                align-items: center;
                justify-content: center;
                color: white;
                font-size: 18px;
                flex-shrink: 0;
            }

            .song-details {
                flex: 1;
                min-width: 0;
            }

            .song-name {
                font-weight: 600;
                color: #2d3748;
                font-size: 15px;
                margin-bottom: 4px;
                line-height: 1.3;
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: nowrap;
            }

            .song-duration {
                color: #718096;
                font-size: 13px;
                display: flex;
                align-items: center;
            }

            .song-action {
                color: #a0aec0;
                font-size: 20px;
                transition: all 0.2s ease;
                opacity: 0.6;
            }

            .song-item:hover .song-action {
                color: #667eea;
                opacity: 1;
                transform: scale(1.1);
            }

            .song-item:hover .song-icon {
                transform: scale(1.05);
                box-shadow: 0 4px 12px rgba(102, 126, 234, 0.3);
            }

            .scroll-indicator {
                background: linear-gradient(to bottom, transparent, #f8f9fa);
                border-top: 1px solid #e9ecef;
                position: sticky;
                bottom: 0;
            }

            #song-dropdown::-webkit-scrollbar {
                width: 6px;
            }

            #song-dropdown::-webkit-scrollbar-track {
                background: #f1f3f4;
                border-radius: 3px;
                margin: 8px 0;
            }

            #song-dropdown::-webkit-scrollbar-thumb {
                background: linear-gradient(45deg, #667eea, #764ba2);
                border-radius: 3px;
            }

            #song-dropdown::-webkit-scrollbar-thumb:hover {
                background: linear-gradient(45deg, #5a67d8, #6b46c1);
            }
        `;
        document.head.appendChild(style);
    }

    dropdown.classList.remove('d-none');
    dropdown.style.display = 'block';
}

// Function to handle song selection
function selectSong(song) {
    selectedSong = song;
    searchInput.value = song.name || 'Unknown Song';
    dropdown.classList.add('d-none');
    dropdown.style.display = 'none';
    hideError();

    // Add visual feedback
    searchInput.style.borderColor = '#28a745';
    searchInput.style.boxShadow = '0 0 0 0.2rem rgba(40, 167, 69, 0.25)';

    // Show success message briefly
    showSuccessMessage(`Selected: ${song.name}`);

    setTimeout(() => {
        searchInput.style.borderColor = '';
        searchInput.style.boxShadow = '';
    }, 2000);
}

// Function to show success message
function showSuccessMessage(message) {
    // Remove any existing success message
    const existingSuccess = document.querySelector('.temp-success-msg');
    if (existingSuccess) {
        existingSuccess.remove();
    }

    const successMsg = document.createElement('div');
    successMsg.className = 'alert alert-success mt-2 temp-success-msg';
    successMsg.innerHTML = `<i class="bi bi-check-circle-fill me-2"></i>${message}`;

    const searchContainer = searchInput.closest('.position-relative') || searchInput.parentNode;
    searchContainer.appendChild(successMsg);

    setTimeout(() => {
        if (successMsg.parentNode) {
            successMsg.remove();
        }
    }, 2000);
}

// Function to format duration (assuming duration is in seconds)
function formatDuration(seconds) {
    // Convert to number if it's a string, default to 0 if invalid
    const totalSeconds = parseInt(seconds) || 0;

    if (totalSeconds <= 0) {
        return '0:00';
    }

    const minutes = Math.floor(totalSeconds / 60);
    const remainingSeconds = totalSeconds % 60;
    return `${minutes}:${remainingSeconds.toString().padStart(2, '0')}`;
}

// Function to escape HTML
function escapeHtml(text) {
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}

// Search input event listeners
searchInput.addEventListener('input', function() {
    const query = this.value;

    // Clear previous timeout
    clearTimeout(searchTimeout);

    // Reset selected song if user is typing
    selectedSong = null;

    // Debounce search requests
    searchTimeout = setTimeout(() => {
        searchSongs(query);
    }, 300);
});

// Handle focus and blur events
searchInput.addEventListener('focus', function() {
    if (this.value.trim().length >= 2) {
        searchSongs(this.value);
    }
});

// Hide dropdown when clicking outside
document.addEventListener('click', function(event) {
    if (!searchInput.contains(event.target) && !dropdown.contains(event.target)) {
        dropdown.classList.add('d-none');
        dropdown.style.display = 'none';
    }
});

// Handle keyboard navigation
searchInput.addEventListener('keydown', function(event) {
    const items = dropdown.querySelectorAll('.dropdown-item:not(.dropdown-item-text)');
    let activeItem = dropdown.querySelector('.dropdown-item.bg-light');
    let activeIndex = Array.from(items).indexOf(activeItem);

    switch(event.key) {
        case 'ArrowDown':
            event.preventDefault();
            if (activeItem) activeItem.classList.remove('bg-light');
            activeIndex = (activeIndex + 1) % items.length;
            if (items[activeIndex]) {
                items[activeIndex].classList.add('bg-light');
                items[activeIndex].scrollIntoView({ block: 'nearest' });
            }
            break;

        case 'ArrowUp':
            event.preventDefault();
            if (activeItem) activeItem.classList.remove('bg-light');
            activeIndex = activeIndex <= 0 ? items.length - 1 : activeIndex - 1;
            if (items[activeIndex]) {
                items[activeIndex].classList.add('bg-light');
                items[activeIndex].scrollIntoView({ block: 'nearest' });
            }
            break;

        case 'Enter':
            event.preventDefault();
            if (activeItem && !activeItem.classList.contains('dropdown-item-text')) {
                activeItem.click();
            }
            break;

        case 'Escape':
            dropdown.classList.add('d-none');
            searchInput.blur();
            break;
    }
});

// Add hover effect for dropdown items
document.addEventListener('mouseover', function(event) {
    if (event.target.closest('.dropdown-item') && !event.target.closest('.dropdown-item').classList.contains('dropdown-item-text')) {
        // Remove active class from all items
        dropdown.querySelectorAll('.dropdown-item.bg-light').forEach(item => {
            item.classList.remove('bg-light');
        });
        // Add active class to hovered item
        event.target.closest('.dropdown-item').classList.add('bg-light');
    }
});

// Upload button handler
uploadBtn.addEventListener('click', async function() {
    const playlist = document.getElementById('playlist').value;
    if (!selectedSong) {
        showError('Please select a song from the dropdown');
        return;
    }
    if (!playlist) {
        showError('Please specify the name of the playlist');
        return;
    }

    let formData = new FormData()
    formData.append('song', selectedSong.name)
    formData.append('playlist', playlist)

    try {
        const res = await fetch('/playlists/song/add', {
            method: 'POST',
            body: formData
        })

        if (res.ok) {
            window.location.pathname = '/songs'
            return
        }
        throw new Exception()
    } catch {
        showError("Couldn't add this song to the playlist. Please try again later.");
    }
});
