import sqlite3
import tkinter as tk
from tkinter import ttk, messagebox, filedialog

class WRMStatisticsApp:
    def __init__(self, master):
        self.master = master
        master.title("Statystyki WRM")

        # --- Krok 1: Wybór pliku bazy danych ---
        self.db_path = None
        self.select_db_button = ttk.Button(master, text="Wybierz plik bazy danych", command=self.choose_database)
        self.select_db_button.grid(row=0, column=0, columnspan=2, padx=10, pady=10, sticky="ew")

        # Etykieta wyświetlająca nazwę wybranego pliku lub komunikat
        self.db_label = ttk.Label(master, text="Nie wybrano bazy danych")
        self.db_label.grid(row=1, column=0, columnspan=2, padx=10, pady=(0,10), sticky="w")

        # --- Krok 2: Combobox z listą stacji ---
        self.station_label = ttk.Label(master, text="Wybierz stację:")
        self.station_label.grid(row=2, column=0, padx=(10,0), pady=5, sticky="w")

        self.station_var = tk.StringVar()
        self.station_combobox = ttk.Combobox(master, textvariable=self.station_var, state="disabled", width=40)
        self.station_combobox.grid(row=2, column=1, padx=(0,10), pady=5, sticky="ew")

        # --- Krok 3: Przycisk generujący statystyki ---
        self.run_button = ttk.Button(master, text="Wyświetl statystyki", state="disabled", command=self.calculate_stats)
        self.run_button.grid(row=3, column=0, columnspan=2, padx=10, pady=10, sticky="ew")

        # --- Krok 4: Pole tekstowe (lub etykiety) do wyświetlenia wyników ---
        self.results_text = tk.Text(master, width=60, height=10, state="disabled", wrap="word")
        self.results_text.grid(row=4, column=0, columnspan=2, padx=10, pady=(0,10), sticky="nsew")

        # Umożliwienie skalowania w pionie i poziomie
        master.grid_columnconfigure(1, weight=1)
        master.grid_rowconfigure(4, weight=1)

        # Bufor na listę (tupla) (station_name, station_id)
        self.stations_list = []

    def choose_database(self):
        path = filedialog.askopenfilename(
            title="Wybierz plik bazy WRM",
            filetypes=[("SQLite3 Database", "*.sqlite3"), ("Wszystkie pliki", "*.*")]
        )
        if not path:
            return

        self.db_path = path
        self.db_label.config(text=f"Baza: {self.db_path}")

        try:
            conn = sqlite3.connect(self.db_path)
            cursor = conn.cursor()
            cursor.execute("SELECT station_id, station_name FROM Stations ORDER BY station_name COLLATE NOCASE;")
            rows = cursor.fetchall()
            conn.close()
        except Exception as e:
            messagebox.showerror("Błąd", f"Nie udało się otworzyć bazy danych:\n{e}")
            return

        if not rows:
            messagebox.showwarning("Uwaga", "Tabela Stations jest pusta lub nie istnieje.")
            return

        self.stations_list = rows
        station_names = [row[1] for row in rows]  # lista samych nazw
        self.station_combobox.config(values=station_names, state="readonly")
        self.station_combobox.set(station_names[0])  # domyślnie pierwsza
        self.run_button.config(state="normal")

    def calculate_stats(self):
        selected_name = self.station_var.get().strip()
        if not selected_name:
            messagebox.showwarning("Uwaga", "Proszę wybrać stację z listy.")
            return

        # Znajdź ID wybranej stacji
        station_id = None
        for sid, name in self.stations_list:
            if name == selected_name:
                station_id = sid
                break

        if station_id is None:
            messagebox.showerror("Błąd", "Nie znaleziono wybranej stacji w bazie.")
            return

        # Otwórz połączenie
        try:
            conn = sqlite3.connect(self.db_path)
            cursor = conn.cursor()
        except Exception as e:
            messagebox.showerror("Błąd", f"Nie udało się połączyć z bazą:\n{e}")
            return

        # --- Zapytanie a. Średni czas trwania przejazdu rozpoczynanego na danej stacji ---
        cursor.execute(
            """
            SELECT AVG(duration) 
            FROM Rentals 
            WHERE rental_station_id = ?;
            """,
            (station_id,)
        )
        avg_start = cursor.fetchone()[0]  # może być None, jeśli brak przejazdów
        avg_start_display = f"{avg_start:.2f} min" if avg_start is not None else "brak danych"

        # --- Zapytanie b. Średni czas trwania przejazdu kończącego na danej stacji ---
        cursor.execute(
            """
            SELECT AVG(duration)
            FROM Rentals
            WHERE return_station_id = ?;
            """,
            (station_id,)
        )
        avg_end = cursor.fetchone()[0]
        avg_end_display = f"{avg_end:.2f} min" if avg_end is not None else "brak danych"

        # --- Zapytanie c. Liczba różnych rowerów zwróconych (parkowanych) na danej stacji ---
        cursor.execute(
            """
            SELECT COUNT(DISTINCT bike_number)
            FROM Rentals
            WHERE return_station_id = ?;
            """,
            (station_id,)
        )
        distinct_bikes = cursor.fetchone()[0]  # zwróci 0 nawet jeśli brak
        bikes_display = str(distinct_bikes)

        # --- Zapytanie d. Nietrywialne własne: najbardziej popularna stacja zwrotu dla przejazdów startujących stąd ---
        cursor.execute(
            """
            SELECT s.station_name, COUNT(*) AS cnt
            FROM Rentals r 
            JOIN Stations s ON r.return_station_id = s.station_id
            WHERE r.rental_station_id = ?
            GROUP BY r.return_station_id
            ORDER BY cnt DESC
            LIMIT 1;
            """,
            (station_id,)
        )
        top_return = cursor.fetchone()
        if top_return:
            most_popular_return_name, cnt_trips = top_return
            top_return_display = f"{most_popular_return_name} ({cnt_trips} przejazdów)"
        else:
            top_return_display = "brak danych (brak przejazdów startujących tam)"

        conn.close()

        # --- Wyświetlenie wyników w polu tekstowym ---
        self.results_text.config(state="normal")
        self.results_text.delete("1.0", tk.END)

        self.results_text.insert(tk.END, f"Statystyki dla stacji: {selected_name}\n\n")
        self.results_text.insert(tk.END, f"a) Średni czas trwania przejazdu (start): {avg_start_display}\n")
        self.results_text.insert(tk.END, f"b) Średni czas trwania przejazdu (koniec):  {avg_end_display}\n")
        self.results_text.insert(tk.END, f"c) Liczba różnych rowerów zwróconych na stacji: {bikes_display}\n")
        self.results_text.insert(tk.END, "d) Najpopularniejsza stacja zwrotu (dla przejazdów startujących tu):\n")
        self.results_text.insert(tk.END, f"   → {top_return_display}\n")

        self.results_text.config(state="disabled")


def main():
    root = tk.Tk()
    app = WRMStatisticsApp(root)
    root.mainloop()

if __name__ == "__main__":
    main()