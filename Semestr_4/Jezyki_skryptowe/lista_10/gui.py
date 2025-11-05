import sqlite3
import sys
import os
import tkinter as tk
from tkinter import ttk, messagebox


class WRMStatisticsApp:
    def __init__(self, master, db_path):
        self.master = master
        self.db_path = db_path
        master.title("Statystyki WRM")

        # Etykieta wyświetlająca ścieżkę wybranej bazy danych
        self.db_label = ttk.Label(master, text=f"Baza danych: {os.path.abspath(self.db_path)}")
        self.db_label.grid(row=0, column=0, columnspan=2, padx=10, pady=(10,5), sticky="w")

        # Combobox z listą stacji
        self.station_label = ttk.Label(master, text="Wybierz stację:")
        self.station_label.grid(row=1, column=0, padx=(10,0), pady=5, sticky="w")

        self.station_var = tk.StringVar()
        self.station_combobox = ttk.Combobox(master, textvariable=self.station_var, state="disabled", width=40)
        self.station_combobox.grid(row=1, column=1, padx=(0,10), pady=5, sticky="ew")

        # Przycisk generujący statystyki
        self.run_button = ttk.Button(master, text="Wyświetl statystyki", state="disabled", command=self.calculate_stats)
        self.run_button.grid(row=2, column=0, columnspan=2, padx=10, pady=10, sticky="ew")

        # Pole tekstowe do wyświetlenia wyników
        self.results_text = tk.Text(master, width=60, height=10, state="disabled", wrap="word")
        self.results_text.grid(row=3, column=0, columnspan=2, padx=10, pady=(0,10), sticky="nsew")

        # Umożliwienie skalowania w pionie i poziomie
        master.grid_columnconfigure(1, weight=1)
        master.grid_rowconfigure(3, weight=1)

        self.stations_list = []
        self.load_stations()

    def load_stations(self):
        if not os.path.exists(self.db_path):
            messagebox.showerror("Błąd", f"Plik bazy danych nie istnieje:\n{self.db_path}")
            self.master.destroy()
            return

        try:
            conn = sqlite3.connect(self.db_path)
            cursor = conn.cursor()
            cursor.execute("""
                           SELECT station_id, station_name 
                           FROM Stations 
                           ORDER BY station_name COLLATE NOCASE;
                           """)
            rows = cursor.fetchall()
            conn.close()
        except Exception as e:
            messagebox.showerror("Błąd", f"Nie udało się otworzyć bazy danych:\n{e}")
            self.master.destroy()
            return

        if not rows:
            messagebox.showwarning("Uwaga", "Tabela Stations jest pusta lub nie istnieje.")
            self.master.destroy()
            return

        self.stations_list = rows
        station_names = [row[1] for row in rows]
        self.station_combobox.config(values=station_names, state="readonly")
        self.station_combobox.set(station_names[0])
        self.run_button.config(state="normal")

    def calculate_stats(self):
        selected_name = self.station_var.get().strip()

        station_id = None
        for sid, name in self.stations_list:
            if name == selected_name:
                station_id = sid
                break
        if station_id is None:
            messagebox.showerror("Błąd", "Nie znaleziono wybranej stacji w bazie.")
            return

        try:
            conn = sqlite3.connect(self.db_path)
            cursor = conn.cursor()
        except Exception as e:
            messagebox.showerror("Błąd", f"Nie udało się połączyć z bazą:\n{e}")
            return

        # a. Średni czas trwania przejazdu rozpoczynanego na danej stacji
        cursor.execute(
            """
            SELECT AVG(duration) 
            FROM Rentals 
            WHERE rental_station_id = ?;
            """,
            (station_id,)
        )
        avg_start = cursor.fetchone()[0]
        avg_start_display = f"{avg_start:.2f} min" if avg_start is not None else "brak danych"

        # b. Średni czas trwania przejazdu kończącego na danej stacji
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

        # c. Liczba różnych rowerów zwróconych (parkowanych) na danej stacji
        cursor.execute(
            """
            SELECT COUNT(DISTINCT bike_number)
            FROM Rentals
            WHERE return_station_id = ?;
            """,
            (station_id,)
        )
        distinct_bikes = cursor.fetchone()[0]
        bikes_display = str(distinct_bikes)

        # d. Najbardziej popularna stacja zwrotu dla przejazdów startujących stąd
        cursor.execute(
            """
            SELECT 
                s.station_name, 
                COUNT(*) AS cnt
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

        # Wyświetlenie wyników w polu tekstowym
        self.results_text.config(state="normal")
        self.results_text.delete("1.0", tk.END)

        self.results_text.insert(tk.END, f"Statystyki dla stacji: {selected_name}\n\n")
        self.results_text.insert(tk.END, f"a) Średni czas trwania przejazdu (start): {avg_start_display}\n")
        self.results_text.insert(tk.END, f"b) Średni czas trwania przejazdu (koniec):  {avg_end_display}\n")
        self.results_text.insert(tk.END, f"c) Liczba różnych rowerów zwróconych na stacji: {bikes_display}\n")
        self.results_text.insert(tk.END, "d) Najpopularniejsza stacja zwrotu:\n")
        self.results_text.insert(tk.END, f"   -> {top_return_display}\n")

        self.results_text.config(state="disabled")

def main():
    if len(sys.argv) != 2:
        print("Użycie: python gui_wrm_stats.py <nazwa_bazy.sqlite3>")
        sys.exit(1)

    db_name = sys.argv[1]
    if not db_name.endswith(".sqlite3"):
        db_name = f"{db_name}.sqlite3"

    if not os.path.exists(db_name):
        print(f"Błąd: plik bazy danych nie istnieje: {db_name}")
        sys.exit(1)

    root = tk.Tk()
    app = WRMStatisticsApp(root, db_name)
    root.mainloop()

if __name__ == "__main__":
    main()