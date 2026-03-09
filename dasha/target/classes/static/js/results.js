// src/main/resources/static/js/results.js

async function runExperiment() {
    // Показываем загрузчик, скрываем таблицу и ошибку
    const loader = document.getElementById('loader');
    const table = document.getElementById('resultTable');
    const errorDiv = document.getElementById('errorMsg');

    loader.style.display = 'block';
    table.style.display = 'none';
    errorDiv.style.display = 'none';
    errorDiv.innerText = '';

    try {
        const response = await fetch('/api/experiment');
        if (!response.ok) {
            throw new Error(`HTTP ${response.status}: ${response.statusText}`);
        }
        const data = await response.json();
        displayResults(data);
    } catch (err) {
        errorDiv.innerText = 'Ошибка: ' + err.message;
        errorDiv.style.display = 'block';
    } finally {
        loader.style.display = 'none';
    }
}

function displayResults(data) {
    const sizes = data.sizes; // например [100, 1000, 5000]
    const times = data.times; // объект с данными

    const tableHead = document.getElementById('tableHead');
    const tableBody = document.getElementById('tableBody');

    // Заголовок
    let headHtml = '<tr><th>Алгоритм</th>';
    sizes.forEach(size => {
        headHtml += `<th>${size} элементов</th>`;
    });
    headHtml += '</tr>';
    tableHead.innerHTML = headHtml;

    // Строки
    let bodyHtml = '';
    for (const [algoName, timeArray] of Object.entries(times)) {
        bodyHtml += '<tr>';
        bodyHtml += `<td>${algoName}</td>`;
        timeArray.forEach(t => {
            bodyHtml += `<td>${t.toFixed(6)} мс</td>`;
        });
        bodyHtml += '</tr>';
    }
    tableBody.innerHTML = bodyHtml;

    document.getElementById('resultTable').style.display = 'table';
}

// Делаем функцию доступной глобально
window.runExperiment = runExperiment;