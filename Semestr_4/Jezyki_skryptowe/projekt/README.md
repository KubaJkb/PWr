# PDF/DOCX Search Engine

Aplikacja do wyszukiwania fraz w wielu dokumentach PDF i DOCX na raz. Obsługuje dopasowania dokładne oraz przybliżone z konfigurowalnym progiem zgodności. Posiada interfejs graficzny oparty na bibliotece tkinter.

## Struktura projektu

projekt/  
├── data/ # Przykładowe pliki do testowania ręcznego  
│   ├── test.docx  
│   └── test.pdf  
├── src/ # Kod źródłowy aplikacji  
│   ├── main.py # Punkt wejścia – uruchamia GUI  
│   ├── gui.py # Interfejs graficzny  
│   ├── search_engine.py # Logika wyszukiwania  
│   └── file_loader.py # Ekstrakcja tekstu z plików  
├── tests/ # Testy jednostkowe  
│   ├── test_data/  
│   │   ├── sample.docx  
│   │   └── sample.pdf  
│   ├── test_file_loader.py  
│   └── test_search_engine.py  

## Wymagania

Wymagany Python 3.8 lub wyższy.

Instalacja zależności:
```bash
pip install -r requirements.txt
```

## Uruchamianie

Aby uruchomić aplikację:
```bash
  python src/main.py
```

## Funkcjonalność

- Wybór lub przeciąganie plików PDF/DOCX
- Lista załadowanych dokumentów z możliwością usuwania
- Wyszukiwanie fraz w trybie:
  - Dokładnym (uwzględniającym znaki specjalne i spacje)
  - Przybliżonym (pomijającym znaki diakrytyczne i spacje)
- Ustawianie minimalnego progu zgodności (%)
- Podgląd trafień z podświetleniem frazy


## Testowanie

Uruchomienie testów jednostkowych:
```bash
  pytest tests/ -v
```
