for (let deleteBtn of document.getElementsByClassName('delete')) {

    deleteBtn.addEventListener('click', async function (e) {
        const songName = deleteBtn.name
        if (!confirm(`Are you sure you want to delete ${songName} ?`)) return

        let error = document.getElementById('error')
        error.className = 'alert d-none'
        error.innerHTML = ''

        const originalText = deleteBtn.innerHTML
        deleteBtn.disabled = true
        deleteBtn.innerHTML = '<i class="bi bi-trash3 me-1"></i> Deleting...'

        let formData = new FormData()
        formData.append('song', songName)

        try {
            const res = await fetch('/songs/delete', {
                method: 'POST',
                body: formData
            })

            if (res.ok) {
                window.location.reload()
                return
            }

        } catch (error) {
            console.error('Error while deleting song:', error)
        }

        error.className = 'alert alert-danger m-2'
        error.innerHTML = '<i class="bi bi-x-circle-fill me-2"></i> Error while deleting song: '+songName

        deleteBtn.innerHTML = originalText
        deleteBtn.disabled = false
    })
}

// {'123': ['nice', 'cool', ....], '124': [...]}
var categories = {}
var displayNone = 'd-none'

for (let editBtn of document.getElementsByClassName('edit')) {
    let id = editBtn.id.replace('submit_', '')
    let categoryList = []
    for (let span of document.getElementById('category_display_' + id)
                       .getElementsByTagName('span')) {
            let name = span.textContent.trim()
            if (name !== 'No categories added') {
                categoryList.push(name);
            }
     }

    categories[id] = categoryList
    function updateCategoryDisplay() {
        const categoryDisplay = document.getElementById('category_display_'+id)
        if (categories) {
            const categoryList = categories[id].filter(cat => cat.trim() !== '');
            categoryDisplay.innerHTML = categoryList.map(cat =>
                `<span class="badge bg-primary me-1">${cat.trim()}</span>`
            ).join('');
        } else {
            categoryDisplay.innerHTML = '<span class="text-muted">No categories added</span>';
        }
    }

    document.getElementById('add_category_'+id).addEventListener('click', function (e) {
            e.preventDefault();
            const categoryInput = document.getElementById('category_input_'+id)
            const newCategory = categoryInput.value.trim();
                if (newCategory === '') return;

                let categoryList = categories ? categories[id] : [];
            if (!categoryList.includes(newCategory)) {
                    categoryList.push(newCategory);
                    categories[id] = categoryList;
                    updateCategoryDisplay();
            }
            categoryInput.value = '';
    })

    document.getElementById('remove_category_'+id).addEventListener('click', function (e) {
        e.preventDefault();
        const categoryInput = document.getElementById('category_input_'+id)
        const categoryToRemove = categoryInput.value.trim();
            if (categoryToRemove === '') return;

            let categoryList = categories ? categories[id] : [];

            // Filter out the category
            categoryList = categoryList.filter(cat => cat.trim() !== categoryToRemove);
            categories[id] = categoryList;
            updateCategoryDisplay();

            categoryInput.value = ''; // Clear input
    })

    document.getElementById('toggle_' + id).addEventListener('click', function (e) {
        e.preventDefault();
        let editBtn = document.getElementById('submit_' + id); // Make sure this exists
        let _cat = document.getElementById('edit_cat_' + id);
        if (editBtn.classList.contains(displayNone)) {
            editBtn.classList.remove(displayNone);
            _cat.classList.remove(displayNone);
        } else {
            editBtn.classList.add(displayNone);
            _cat.classList.add(displayNone);
        }
    });


    editBtn.addEventListener('click', async function (e) {
        const songName = editBtn.name
        if (!confirm(`Are you sure you want to edit ${songName} ?`)) return

        let error = document.getElementById('error')
        error.className = 'alert '+displayNone
        error.innerHTML = ''

        const originalText = editBtn.innerHTML
        editBtn.disabled = true
        editBtn.innerHTML = '<i class="bi bi-bookmark-check-fill me-1"></i> Submitting...'

        let formData = new FormData()
        formData.append('song', songName)
        formData.append('categories', categories[id].join(';'))

        try {
            const res = await fetch('/songs/edit', {
                method: 'POST',
                body: formData
            })

            if (res.ok) {
                window.location.reload()
                return
            }

        } catch (error) {
            console.error('Error while editing song:', error)
        }

        error.className = 'alert alert-danger m-2'
        error.innerHTML = '<i class="bi bi-x-circle-fill me-2"></i> Error while editing song: '+songName

        deleteBtn.innerHTML = originalText
        deleteBtn.disabled = false
    })
}


