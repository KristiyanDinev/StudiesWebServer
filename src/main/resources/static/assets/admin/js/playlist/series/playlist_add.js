const searchInput = document.getElementById('study-search');
const dropdown = document.getElementById('study-dropdown');
const errorDiv = document.getElementById('error');
const uploadBtn = document.getElementById('upload');
const seriesElement = document.getElementById('series')

let selectedStudy = null;

function showError(message) {
    errorDiv.textContent = message;
    errorDiv.className = 'alert alert-danger';
}

function hideError() {
    errorDiv.className = 'alert d-none';
}

async function getStudiesAlike(query) {
    query = query.trim();
    if (query.length < 2) {
        dropdown.classList.add('d-none');
        return;
    }

    const formData = new FormData();
    formData.append('alike_study', query);

    try {
        const response = await fetch('/admin/playlists/study/alike', {
            method: 'POST',
            body: formData
        });

        if (!response.ok) throw new Error();

        const studies = await response.json();
        displaySuggestions(studies);
    } catch (error) {
        showError('Failed to search studies. Please try again.');
        dropdown.classList.add('d-none');
    }
}

function displaySuggestions(studies) {
    dropdown.innerHTML = '';

    if (!studies || studies.length === 0) {
        dropdown.innerHTML = '<div class="rounded fs-3 border border-3 border-dark p-3 mt-3 mb-3 me-3">No studies found</div>';
    } else {
        studies.forEach(study => {
            const item = document.createElement('button');
            item.className = 'dropdown-item';
            item.type = 'button';
            item.innerHTML = `
                <div class="d-flex justify-content-between align-items-center flex-column flex-wrap border border-3 border-dark rounded p-3 mt-3 mb-3 me-3">
                    <span class="fw-bold fs-4">${escapeHtml(study.study_name || 'Unknown Study')}</span>
                </div>
            `;

            item.addEventListener('click', () => selectStudy(study));
            dropdown.appendChild(item);
        });
    }

    dropdown.classList.remove('d-none');
}

function selectStudy(study) {
    selectedStudy = study;
    searchInput.value = study.study_name || 'Unknown Study';
    dropdown.classList.add('d-none');
    hideError();

    // Brief success feedback
    searchInput.classList.add('is-valid');
    setTimeout(() => searchInput.classList.remove('is-valid'), 2000);
}


function escapeHtml(text) {
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}

// Event listeners
searchInput.addEventListener('input', async function() {
    selectedStudy = null;
    await getStudiesAlike(this.value);
});

searchInput.addEventListener('focus', async function() {
    if (this.value.trim().length >= 2) {
        await getStudiesAlike(this.value);
    }
});

document.addEventListener('click', function(event) {
    if (!searchInput.contains(event.target) && !dropdown.contains(event.target)) {
        dropdown.classList.add('d-none');
    }
});

uploadBtn.addEventListener('click', async function() {
    const series = seriesElement.value.trim();

    if (!selectedStudy) {
        showError('Please select a study from the dropdown');
        return;
    }
    if (!series) {
        showError('Please specify the series name');
        return;
    }

    if (!confirm(`Add ${selectedStudy.study_name} to ${series}`)) {
        return
    }

    const formData = new FormData();
    formData.append('study', selectedStudy.study_name);
    formData.append('series', series);
    hideError()
    try {
        const response = await fetch('/admin/playlists/study/add', {
            method: 'POST',
            body: formData
        });

        if (!response.ok) {
            throw new Error();
        }
        window.location.pathname = '/admin/studies';
    } catch {
        showError("Couldn't add study to series. Please try again.");
    }
});