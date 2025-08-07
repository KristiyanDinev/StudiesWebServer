const fileInput = document.getElementById('file');
const filenameInput = document.getElementById('filename');
const categoryInput = document.getElementById('category_input');
const categoryDisplay = document.getElementById('category_display');
let error = document.getElementById('error');

const MAX_SIZE_BYTES = 3 * 1024 * 1024 * 1024; // 3 GB
var categories = ''
var isInvalid = 'is-invalid'

fileInput.addEventListener('change', function () {
        const file = this.files[0];
        if (!file) return;

        error.innerHTML = ''
        fileInput.classList.remove(isInvalid)
        if (!file.type.startsWith("audio/")) {
            fileInput.classList.add(isInvalid)
            error.innerHTML = '<i class="bi bi-x-circle-fill me-2"></i> Only Audio files are allowed'
            this.value = "";
            filenameInput.value = "";
            return;
        }

        if (file.size > MAX_SIZE_BYTES) {
            fileInput.classList.add(isInvalid)
            error.innerHTML = '<i class="bi bi-x-circle-fill me-2"></i> File size exceeds 3 GB limit'
            this.value = "";
            filenameInput.value = "";
            return;
        }

        // Autofill filename input (remove any common audio extension)
        filenameInput.value = file.name.replace(/\.(mp3|wav|flac|ogg|aac|m4a)$/i, '');
});

function updateCategoryDisplay() {
    if (categories) {
        const categoryList = categories.split(';').filter(cat => cat.trim() !== '');
        categoryDisplay.innerHTML = categoryList.map(cat =>
            `<span class="badge bg-primary me-1">${cat.trim()}</span>`
        ).join('');
    } else {
        categoryDisplay.innerHTML = '<span class="text-muted">No categories added</span>';
    }
}

document.getElementById('add_category').addEventListener('click', function (e) {
    e.preventDefault();
        const newCategory = categoryInput.value.trim();
            if (newCategory === '') return;

            let categoryList = categories ? categories.split(';') : [];
        if (!categoryList.includes(newCategory)) {
                categoryList.push(newCategory);
                categories = categoryList.join(';');
                updateCategoryDisplay();
        }
        categoryInput.value = '';
})

document.getElementById('remove_category').addEventListener('click', function (e) {
    e.preventDefault();
    const categoryToRemove = categoryInput.value.trim();
        if (categoryToRemove === '') return;

        let categoryList = categories ? categories.split(';') : [];

        // Filter out the category
        categoryList = categoryList.filter(cat => cat.trim() !== categoryToRemove);
        categories = categoryList.join(';');
        updateCategoryDisplay();

        categoryInput.value = ''; // Clear input
})


let uploadBtn = document.getElementById('upload')
uploadBtn.addEventListener('click', async function (e) {
        const file = fileInput.files[0];
        const name = filenameInput.value.trim();

        error.innerHTML = ''
        error.className = ''
        fileInput.classList.remove(isInvalid)
        if (!file) {
            fileInput.classList.add(isInvalid)
            error.className = 'alert alert-danger'
            error.innerHTML = '<i class="bi bi-x-circle-fill me-2"></i> Please select a file'
            e.preventDefault();
            return;
        }

        if (!name) {
            fileInput.classList.add(isInvalid)
            error.className = 'alert alert-danger'
            error.innerHTML = '<i class="bi bi-x-circle-fill me-2"></i> Please provide a file name'
            e.preventDefault();
            return;
        }

        const originalText = uploadBtn.innerHTML
                uploadBtn.innerHTML = '<i class="bi bi-arrow-clockwise me-2"></i>Uploading...'
                uploadBtn.disabled = true

                let formData = new FormData()
                formData.append("fileUpload.filename", name)
                formData.append("fileUpload.file", file)
                formData.append("categories", categories)

                try {
                    const res = await fetch('/admin/songs/upload', {
                        method: 'POST',
                        body: formData
                    })

                    if (!res.ok) {
                        throw new Error()
                    }

                    error.innerHTML = ""
                     error.className = ""
                    window.location.pathname = '/admin/songs'

                } catch {
                    uploadBtn.innerHTML = originalText
                    uploadBtn.disabled = false

                    error.className = 'alert alert-danger'
                    error.innerHTML = '<i class="bi bi-x-circle-fill me-2"></i> Can\'t upload your song'
                }
});
