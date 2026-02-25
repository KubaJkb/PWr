document.addEventListener('DOMContentLoaded', function() {
    
    // Zadanie 1: Tabliczka mnożenia
    const generateButton = document.getElementById('generate-table');
    const tableSizeInput = document.getElementById('table-size');
    const messageDiv = document.getElementById('message');
    const tableContainer = document.getElementById('table-container');
    
    generateButton.addEventListener('click', generateMultiplicationTable);
    
    function generateMultiplicationTable() {
        // Pobranie wartości n od użytkownika
        let n = parseInt(tableSizeInput.value);
        const defaultN = 10;
        
        // Walidacja wartości n
        if (isNaN(n) || n < 5 || n > 20) {
            n = defaultN;
            messageDiv.textContent = `Podane dane były niewłaściwe, więc przyjęto n=${defaultN}.`;
            messageDiv.className = 'message error';
        } else {
            messageDiv.textContent = '';
            messageDiv.className = 'message';
        }
        
        // Generowanie losowych liczb dla wierszy i kolumn
        const rowHeaders = generateRandomNumbers(n);
        const colHeaders = generateRandomNumbers(n);
        
        // Tworzenie tabeli
        const table = document.createElement('table');
        table.className = 'multiplication-table';
        
        // Tworzenie nagłówka tabeli
        const thead = document.createElement('thead');
        const headerRow = document.createElement('tr');
        
        // Pusta komórka w lewym górnym rogu
        const emptyHeader = document.createElement('th');
        headerRow.appendChild(emptyHeader);
        
        // Nagłówki kolumn
        for (let i = 0; i < n; i++) {
            const th = document.createElement('th');
            th.textContent = colHeaders[i];
            headerRow.appendChild(th);
        }
        
        thead.appendChild(headerRow);
        table.appendChild(thead);
        
        // Tworzenie ciała tabeli
        const tbody = document.createElement('tbody');
        
        for (let i = 0; i < n; i++) {
            const row = document.createElement('tr');
            
            // Nagłówek wiersza
            const rowHeader = document.createElement('th');
            rowHeader.textContent = rowHeaders[i];
            row.appendChild(rowHeader);
            
            // Komórki z wynikami mnożenia
            for (let j = 0; j < n; j++) {
                const cell = document.createElement('td');
                const result = rowHeaders[i] * colHeaders[j];
                cell.textContent = result;
                
                // Dodanie klasy w zależności od parzystości wyniku
                if (result % 2 === 0) {
                    cell.className = 'even';
                } else {
                    cell.className = 'odd';
                }
                
                cell.addEventListener('click', function() {
                    // Oblicz sumę całego wiersza
                    const rowCells = row.querySelectorAll('td');
                    let rowSum = 0;
                    
                    for (let k = 0; k < rowCells.length; k++) {
                        rowSum += parseInt(rowCells[k].textContent);
                    }
                    
                    alert(`Suma wartości w wierszu: ${rowSum}`);
                });
                
                row.appendChild(cell);
            }
            
            tbody.appendChild(row);
        }
        
        table.appendChild(tbody);
        
        // Usunięcie poprzedniej tabeli i dodanie nowej
        tableContainer.innerHTML = '';
        tableContainer.appendChild(table);
    }
    
    function generateRandomNumbers(count) {
        const numbers = [];
        for (let i = 0; i < count; i++) {
            numbers.push(Math.floor(Math.random() * 99) + 1);
        }
        return numbers;
    }
    

    // Zadanie 2: Canvas z liniami do kursora
    const canvasElements = document.querySelectorAll('.drawingX');
    
    canvasElements.forEach(canvas => {
        const ctx = canvas.getContext('2d');
        let isMouseInside = false;
        
        // Obsługa zdarzenia najechania myszką na canvas
        canvas.addEventListener('mouseenter', function() {
            isMouseInside = true;
        });
        
        // Obsługa zdarzenia opuszczenia canvas przez myszkę
        canvas.addEventListener('mouseleave', function() {
            isMouseInside = false;
            // Wyczyść canvas
            ctx.clearRect(0, 0, canvas.width, canvas.height);
        });
        
        // Obsługa ruchu myszką nad canvas
        canvas.addEventListener('mousemove', function(event) {
            if (!isMouseInside) return;
            
            // Pobierz pozycję kursora względem canvas
            const rect = canvas.getBoundingClientRect();
            const mouseX = event.clientX - rect.left;
            const mouseY = event.clientY - rect.top;
            
            // Wyczyść canvas
            ctx.clearRect(0, 0, canvas.width, canvas.height);
            
            // Narysuj linie od rogów do pozycji myszki
            drawLineFromCorner(ctx, 0, 0, mouseX, mouseY); // Lewy górny róg
            drawLineFromCorner(ctx, canvas.width, 0, mouseX, mouseY); // Prawy górny róg
            drawLineFromCorner(ctx, 0, canvas.height, mouseX, mouseY); // Lewy dolny róg
            drawLineFromCorner(ctx, canvas.width, canvas.height, mouseX, mouseY); // Prawy dolny róg
        });
        
        function drawLineFromCorner(ctx, cornerX, cornerY, mouseX, mouseY) {
            ctx.beginPath();
            ctx.moveTo(cornerX, cornerY);
            ctx.lineTo(mouseX, mouseY);
            ctx.strokeStyle = '#3498db';
            ctx.lineWidth = 2;
            ctx.stroke();
        }
    });
});