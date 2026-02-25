// Globalne zmienne
let isLoggedIn = false;
let currentBalance = 0;
let checkInterval = null;

// Inicjalizacja
document.addEventListener('DOMContentLoaded', () => {
    startStatusCheck();
    setupEventListeners();
});

// Sprawdzanie statusu co sekunde
function startStatusCheck() {
    checkStatus();
    checkInterval = setInterval(checkStatus, 1000);
}

// Sprawdz status logowania
async function checkStatus() {
    try {
        const response = await fetch('/api/status');
        const data = await response.json();

        if (data.logged_in && !isLoggedIn) {
            // Nowo zalogowany
            isLoggedIn = true;
            currentBalance = data.balance;
            showGamesPanel();
            updateBalance(data.balance);
            loadHistory();
        } else if (data.logged_in) {
            // Juz zalogowany - aktualizuj balans
            if (currentBalance !== data.balance) {
                currentBalance = data.balance;
                updateBalance(data.balance);
            }
        } else if (!data.logged_in && isLoggedIn) {
            // Wylogowany
            isLoggedIn = false;
            showLoginPanel();
        }
    } catch (error) {
        console.error('Blad sprawdzania statusu:', error);
    }
}

// Pokaz panel gier
function showGamesPanel() {
    document.getElementById('login-panel').classList.add('hidden');
    document.getElementById('games-panel').classList.remove('hidden');
    document.getElementById('user-info').classList.remove('hidden');
    document.getElementById('history-panel').classList.remove('hidden');
}

// Pokaz panel logowania
function showLoginPanel() {
    document.getElementById('login-panel').classList.remove('hidden');
    document.getElementById('games-panel').classList.add('hidden');
    document.getElementById('user-info').classList.add('hidden');
    document.getElementById('history-panel').classList.add('hidden');
}

// Aktualizuj wyswietlany balans
function updateBalance(balance) {
    const balanceEl = document.getElementById('balance');
    balanceEl.textContent = balance;

    // Animacja zmiany
    balanceEl.style.transform = 'scale(1.2)';
    setTimeout(() => {
        balanceEl.style.transform = 'scale(1)';
    }, 200);
}

// Setup event listeners
function setupEventListeners() {
    // Wylogowanie
    document.getElementById('logout-btn').addEventListener('click', logout);
}

// Wylogowanie
async function logout() {
    try {
        await fetch('/api/logout', { method: 'POST' });
        isLoggedIn = false;
        showLoginPanel();
    } catch (error) {
        console.error('Blad wylogowania:', error);
    }
}

// Aktualizuj opcje zakladu ruletki
function updateBetOptions() {
    const betType = document.getElementById('bet-type').value;
    const betValue = document.getElementById('bet-value');

    let options = '';

    switch (betType) {
        case 'color':
            options = `
                <option value="czerwony">Czerwony</option>
                <option value="czarny">Czarny</option>
            `;
            break;
        case 'even_odd':
            options = `
                <option value="parzyste">Parzyste</option>
                <option value="nieparzyste">Nieparzyste</option>
            `;
            break;
        case 'half':
            options = `
                <option value="1-18">1-18</option>
                <option value="19-36">19-36</option>
            `;
            break;
    }

    betValue.innerHTML = options;
}

// Pokaz overlay gry
function showGameOverlay() {
    const overlay = document.getElementById('game-overlay');
    const animation = document.getElementById('game-animation');
    const result = document.getElementById('game-result');

    overlay.classList.remove('hidden');
    animation.classList.remove('hidden');
    result.classList.add('hidden');
}

// Pokaz wynik gry
function showGameResult(won, amount, newBalance) {
    const animation = document.getElementById('game-animation');
    const result = document.getElementById('game-result');
    const icon = document.getElementById('result-icon');
    const title = document.getElementById('result-title');
    const amountEl = document.getElementById('result-amount');

    animation.classList.add('hidden');
    result.classList.remove('hidden');

    if (won) {
        result.className = 'game-result win';
        icon.textContent = '🎉';
        title.textContent = 'WYGRANA!';
        amountEl.textContent = `+${amount}$ (Saldo: ${newBalance}$)`;
        amountEl.style.color = '#4ecdc4';
    } else {
        result.className = 'game-result lose';
        icon.textContent = '😢';
        title.textContent = 'PRZEGRANA';
        amountEl.textContent = `Saldo: ${newBalance}$`;
        amountEl.style.color = '#e74c3c';
    }

    updateBalance(newBalance);
}

// Zamknij wynik
function closeResult() {
    document.getElementById('game-overlay').classList.add('hidden');
    loadHistory();
}

// Wylacz wszystkie przyciski gry
function disableAllPlayButtons(disabled) {
    document.querySelectorAll('.btn-play').forEach(btn => {
        btn.disabled = disabled;
    });
}

// GRAJ W SLOTY
async function playSlots() {
    if (!isLoggedIn) return;

    if (currentBalance < 10) {
        alert('Za mało środków! Potrzebujesz 10$');
        return;
    }

    showGameOverlay();
    disableAllPlayButtons(true);

    try {
        const response = await fetch('/api/play/slots', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' }
        });

        const data = await response.json();

        if (data.error) {
            alert(data.error);
            document.getElementById('game-overlay').classList.add('hidden');
        } else {
            showGameResult(
                data.result === 'win',
                data.win_amount,
                data.new_balance
            );
        }
    } catch (error) {
        console.error('Blad gry:', error);
        document.getElementById('game-overlay').classList.add('hidden');
    }

    disableAllPlayButtons(false);
}

// GRAJ W RULETKE
async function playRoulette() {
    if (!isLoggedIn) return;

    if (currentBalance < 20) {
        alert('Za mało środków! Potrzebujesz 20$');
        return;
    }

    const betType = document.getElementById('bet-type').value;
    const betValue = document.getElementById('bet-value').value;

    showGameOverlay();
    disableAllPlayButtons(true);

    try {
        const response = await fetch('/api/play/roulette', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                bet_type: betType,
                bet_value: betValue
            })
        });

        const data = await response.json();

        if (data.error) {
            alert(data.error);
            document.getElementById('game-overlay').classList.add('hidden');
        } else {
            showGameResult(
                data.result === 'win',
                data.win_amount,
                data.new_balance
            );
        }
    } catch (error) {
        console.error('Blad gry:', error);
        document.getElementById('game-overlay').classList.add('hidden');
    }

    disableAllPlayButtons(false);
}

// GRAJ W MNOZNIK
async function playMultiplier() {
    if (!isLoggedIn) return;

    if (currentBalance < 15) {
        alert('Za mało środków! Potrzebujesz 15$');
        return;
    }

    showGameOverlay();
    disableAllPlayButtons(true);

    try {
        const response = await fetch('/api/play/multiplier', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' }
        });

        const data = await response.json();

        if (data.error) {
            alert(data.error);
            document.getElementById('game-overlay').classList.add('hidden');
        } else {
            showGameResult(
                data.result === 'win',
                data.win_amount,
                data.new_balance
            );
        }
    } catch (error) {
        console.error('Blad gry:', error);
        document.getElementById('game-overlay').classList.add('hidden');
    }

    disableAllPlayButtons(false);
}

// Zaladuj historie gier
async function loadHistory() {
    if (!isLoggedIn) return;

    try {
        const response = await fetch('/api/history');
        const data = await response.json();

        if (data.history) {
            renderHistory(data.history);
        }
    } catch (error) {
        console.error('Blad ladowania historii:', error);
    }
}

// Wyrenderuj historie
function renderHistory(history) {
    const list = document.getElementById('history-list');

    if (history.length === 0) {
        list.innerHTML = '<p style="color: #aaa; text-align: center;">Brak historii gier</p>';
        return;
    }

    const gameNames = {
        'slots': '🎰 Sloty',
        'roulette': '🎡 Ruletka',
        'multiplier': '✖️ Mnożnik'
    };

    list.innerHTML = history.map(item => {
        const isWin = item.result === 'win';
        const amount = isWin ? item.win_amount : -item.bet_amount;
        const amountClass = isWin ? 'positive' : 'negative';
        const sign = isWin ? '+' : '';

        return `
            <div class="history-item ${item.result}">
                <span class="history-game">${gameNames[item.game_type] || item.game_type}</span>
                <span class="history-amount ${amountClass}">${sign}${amount}$</span>
            </div>
        `;
    }).join('');
}
