


async function getSeries() {
    try {
        const res = await fetch('/studies/series', {
            method: 'GET'
        })

        if (res.ok) {
            const series = await res.json()
            let selectElement = document.getElementById('seriesSelect')
            selectElement.innerHTML = ''
            series.forEach(s => {
                const opt = document.createElement('option');
                opt.value = s.series_name;
                opt.textContent = s.series_name;
                selectElement.appendChild(opt);
            });


        }
    } catch {
    }
}

document.addEventListener('DOMContentLoaded', async function() {

    await getSeries()

    document.getElementById('clearButton').addEventListener('click', function () {
        const select = document.getElementById('seriesSelect');
        for (let option of select.options) {
            option.selected = false;
        }

        // Optional: trigger change event if needed
        select.dispatchEvent(new Event('change'));
    });

})