const fileInput = document.getElementById('file');
const filenameInput = document.getElementById('filename');
let error = document.getElementById('error');
const MAX_SIZE_BYTES = 5 * 1024 * 1024 * 1024; // 5 GB

var isInvalid = 'is-invalid'

fileInput.addEventListener('change', function () {
        const file = this.files[0];
        if (!file) return;

        error.innerHTML = ''
        fileInput.classList.remove(isInvalid)
        if (file.type !== "application/pdf") {
            fileInput.classList.add(isInvalid)
            error.innerHTML = '<i class="bi bi-x-circle-fill me-2"></i> Only PDF files are allowed'
            this.value = "";
            filenameInput.value = "";
            return;
        }

        if (file.size > MAX_SIZE_BYTES) {
            fileInput.classList.add(isInvalid)
            error.innerHTML = '<i class="bi bi-x-circle-fill me-2"></i> File size exceeds 5 GB limit'
            this.value = "";
            filenameInput.value = "";
            return;
        }

        // Autofill filename input (without .pdf extension)
        filenameInput.value = file.name.replace(/\.pdf$/i, '');
});


let uploadBtn = document.getElementById('upload')
uploadBtn.addEventListener('click', async function (e) {
        const file = fileInput.files[0];
        const name = filenameInput.value.trim();

        error.innerHTML = ''
        error.className = ''
        fileInput.classList.remove(isInvalid)
        if (!file) {
            error.className = 'alert alert-danger'
            fileInput.classList.add(isInvalid)
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
        formData.append("filename", name)
        formData.append("file", file)

        try {
            const res = await fetch('/admin/studies/upload', {
                method: 'POST',
                body: formData
            })

            if (!res.ok) {
                throw new Error()
            }

                error.innerHTML = ""
                error.className = ""
                window.location.pathname = '/admin/studies'

        } catch {
            uploadBtn.innerHTML = originalText
            uploadBtn.disabled = false

            error.className = 'alert alert-danger'
            error.innerHTML = '<i class="bi bi-x-circle-fill me-2"></i> Can\'t upload your study'
        }
});