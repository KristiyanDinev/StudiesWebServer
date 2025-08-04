const searchInput = document.getElementById("study-search")
const dropdown = document.getElementById('study-dropdown')
const errorDiv = document.getElementById('error')
const removeBtn = document.getElementById("remove")
const series = document.getElementById('series')

selectedStudy = null

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


async function getStudiesAlike(query) {
    query = query.trim();
    if (query.length < 1) return [];

    const seriesValue = series.value.trim()
    if (!seriesValue) {
        return []
    }


    let formData = new FormData()
    formData.append('alike_study', query)
    formData.append('series', seriesValue)

    try {
        const res = await fetch('/admin/playlists/study/alike_by_series', {
            method: 'POST',
            body: formData
        })
        if (!res.ok) {
            showError("Can't get studies")
            return []
        }
        return await res.json()

    } catch {
        showError("Error while getting the studies")
        return []
    }
}

function displaySuggestions(studies) {
    dropdown.innerHTML = '';

    if (!studies || studies.length === 0) {
        dropdown.innerHTML = '<div class="rounded border border-3 border-dark p-3 mt-3 mb-3 me-3">No studies found</div>';
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

    // Visual feedback for removal selection
    searchInput.classList.add('is-valid');
    setTimeout(() => searchInput.classList.remove('is-valid'), 3000);
}

function escapeHtml(text) {
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}

searchInput.addEventListener('input', async function() {
    const query = this.value;

    if (selectedStudy && selectedStudy.name !== query) {
        selectedStudy = null;
    }

    const studies_alike = await getStudiesAlike(query);
    displaySuggestions(studies_alike);
});

searchInput.addEventListener('focus', async function() {
    if (this.value.trim().length >= 1) {
        const studies_alike = await getStudiesAlike(this.value);
        displaySuggestions(studies_alike);
    }
});

document.addEventListener('click', function(event) {
    if (!searchInput.contains(event.target) && !dropdown.contains(event.target)) {
        dropdown.classList.add('d-none');
    }
});

removeBtn.addEventListener('click', async function() {
    const seriesValue = series.value.trim();

    if (!selectedStudy) {
        showError('Please select a study to remove');
        return;
    }
    if (!seriesValue) {
        showError('Please specify the series name');
        return;
    }

    if (!confirm(`Remove "${selectedStudy.study_name}" from "${seriesValue}"?`)) {
        return;
    }

    const formData = new FormData();
    formData.append('study', selectedStudy.study_name);
    formData.append('series', seriesValue);

    try {
        const response = await fetch('/admin/playlists/study/remove', {
            method: 'POST',
            body: formData
        });

        if (!response.ok) {
            throw new Error()
        }
        window.location.pathname = '/admin/studies';

    } catch {
        showError("Couldn't remove study from series. Please try again.");
    }
});