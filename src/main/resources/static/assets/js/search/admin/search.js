
var selectedSeries = []

async function getSeries() {
    try {
        const res = await fetch('/studies/series', {
            method: 'GET'
        })

        if (!res.ok) {
           return
        }
            const series = await res.json()
            let selectElement = document.getElementById('seriesSelect')
            selectElement.innerHTML = ''
            series.forEach(s => {
                var _div = document.createElement('div');
                _div.id = `search,${s.id}`;
                _div.className = 'card mb-2 cursor-pointer shadow-sm';
                _div.innerHTML = `
                                    <div class="card-body">
                                        <h5 class="card-title">${s.series_name}</h5>
                                    </div>
                     `;
                _div.addEventListener('click', function () {
                        if (selectedSeries.includes(s.series_name)) {
                            selectedSeries.pop(s.series_name)
                            _div.classList.remove('bg-primary','text-white')

                        } else {
                            selectedSeries.push(s.series_name)
                            _div.classList.add('bg-primary', 'text-white')
                        }
                });
                selectElement.appendChild(_div);
            });



    } catch {
    }
}

async function searchStudy() {
    try {

        let formData = new FormData()
        formData.append('alike_study', document.getElementById('studyName').value.trim())
        for (let s of selectedSeries) {
            formData.append('series', s)
        }

        const res = await fetch('/admin/search/studies', {
            method: 'POST',
            body: formData
        })

        if (!res.ok) {
            return []
        }

        return await res.json()

    } catch {
        return []
    }
}

function displayResults(results) {
    let table_bodyElement = document.getElementById('table_body')
    for (let study of results) {
        let studyElement = document.createElement('tr');
        studyElement.className = 'page-row'
        studyElement.innerHTML = ``


        table_bodyElement.appendChild(studyElement)
    }
}

document.addEventListener('DOMContentLoaded', async function() {

    await getSeries()

    let searchBtn = document.getElementById('search')
    searchBtn.addEventListener('click', async function() {
        const res = await searchStudy()
        displayResults(res)
    })

})
