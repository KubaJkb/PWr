import os
from typing import List, Dict, Any, Optional
import tkinter as tk
from tkinter import filedialog, ttk
from tkinterdnd2 import TkinterDnD, DND_FILES
from search_engine import search_in_documents
from file_loader import load_document


class SearchApp(TkinterDnD.Tk):
    def __init__(self):
        super().__init__()
        self.title("Wyszukiwarka PDF/DOCX")
        self.geometry("1000x600")
        self.files: List[str] = []
        self.results: List[Dict[str, Any]] = []

        self.create_widgets()

    def create_widgets(self):
        # ================== Wybór plików poprzez DragAndDrop lub wybór z eksplorera ==========================
        top_frame = tk.Frame(self)
        top_frame.pack(fill="x", padx=10, pady=5)

        self.drop_area = tk.Label(top_frame, text="Przeciągnij pliki PDF/DOCX tutaj", relief="sunken", height=2)
        self.drop_area.pack(side="left", fill="x", expand=True, padx=(0, 5))

        self.drop_area.drop_target_register(DND_FILES)
        self.drop_area.dnd_bind("<<Drop>>", self.on_drop)

        self.add_button = tk.Button(top_frame, text="Wybierz pliki PDF/DOCX", command=self.add_files, height=2)
        self.add_button.pack(side="left", fill="x", expand=True, padx=(5, 0))

        # ================================= Lista wybranych plików =========================================
        file_frame = tk.Frame(self)
        file_frame.pack(fill="x", padx=10, pady=(0, 5))

        self.file_listbox = tk.Listbox(file_frame, height=4)
        self.file_listbox.pack(side="left", fill="x", expand=True)

        file_scrollbar = tk.Scrollbar(file_frame, orient="vertical", command=self.file_listbox.yview)
        file_scrollbar.pack(side="right", fill="y")
        self.file_listbox.config(yscrollcommand=file_scrollbar.set)

        self.file_menu = tk.Menu(self, tearoff=0)
        self.file_menu.add_command(label="Usuń", command=self.remove_selected_file)

        self.file_listbox.bind("<Button-3>", self.show_context_menu)  # Windows/Linux
        self.file_listbox.bind("<Button-2>", self.show_context_menu)  # macOS

        # ***************** Główna ramka zawierająca: Wyniki wyszukania i Menu wyszukiwania *********************
        main_frame = tk.Frame(self)
        main_frame.pack(fill="both", expand=True, padx=10, pady=10)

        # ============================= Lista wybieralnych wyników wyszukania ================================
        left_frame = tk.Frame(main_frame, width=750)
        left_frame.pack(side="left", fill="both", expand=True)

        tree_frame = tk.Frame(left_frame)
        tree_frame.pack(fill="both", expand=True)

        self.result_tree = ttk.Treeview(tree_frame, columns=("lp", "file", "index", "snippet"), show="headings")
        self.result_tree.heading("lp", text="Lp.")
        self.result_tree.heading("file", text="Plik")
        self.result_tree.heading("index", text="Wynik w pliku")
        self.result_tree.heading("snippet", text="Fragment")
        self.result_tree.column("lp", width=30, anchor="center")
        self.result_tree.column("file", width=120, anchor="w")
        self.result_tree.column("index", width=100, anchor="center")
        self.result_tree.column("snippet", width=500, anchor="w")
        self.result_tree.pack(side="left", fill="both", expand=True)

        tree_scrollbar = tk.Scrollbar(tree_frame, orient="vertical", command=self.result_tree.yview)
        tree_scrollbar.pack(side="right", fill="y")
        self.result_tree.configure(yscrollcommand=tree_scrollbar.set)

        # ================================ Menu z opcjami wyszukiwania ====================================
        right_frame = tk.Frame(main_frame, width=250)
        right_frame.pack(side="right", fill="y")

        tk.Label(right_frame, text="Szukana fraza:").pack(pady=5)
        self.search_entry = tk.Entry(right_frame, width=30)
        self.search_entry.pack(pady=5)

        self.exact_var = tk.BooleanVar()
        self.exact_checkbox = tk.Checkbutton(right_frame, text="Dokładne wyszukiwanie", variable=self.exact_var)
        self.exact_checkbox.pack(pady=5)

        tk.Label(right_frame, text="Minimalna zgodność (%)").pack()
        self.similarity_entry = tk.Entry(right_frame)
        self.similarity_entry.insert(0, "100")
        self.similarity_entry.pack(pady=5)

        self.search_button = tk.Button(right_frame, text="Szukaj", command=self.search)
        self.search_button.pack(pady=10)

        self.preview_button = tk.Button(right_frame, text="Podgląd zaznaczonego wyniku", command=self.open_preview)
        self.preview_button.pack(pady=10)

    def add_files(self):
        filetypes = [("Dokumenty", "*.pdf *.docx")]
        selected = filedialog.askopenfilenames(title="Wybierz pliki", filetypes=filetypes)
        for file in selected:
            if file not in self.files:
                self.files.append(file)
                self.file_listbox.insert(tk.END, os.path.basename(file))

    def on_drop(self, event):
        paths = self.tk.splitlist(event.data)
        for path in paths:
            path = path.strip()
            if os.path.isfile(path) and path.lower().endswith((".pdf", ".docx")):
                if path not in self.files:
                    self.files.append(path)
                    self.file_listbox.insert(tk.END, os.path.basename(path))

    def show_context_menu(self, event):
        try:
            index = self.file_listbox.nearest(event.y)
            self.file_listbox.selection_clear(0, tk.END)
            self.file_listbox.selection_set(index)
            self.file_menu.tk_popup(event.x_root, event.y_root)
        finally:
            self.file_menu.grab_release()

    def remove_selected_file(self):
        selection = self.file_listbox.curselection()
        if not selection:
            return
        index = selection[0]
        self.file_listbox.delete(index)
        del self.files[index]

    def search(self):
        phrase = self.search_entry.get().strip()
        for item in self.result_tree.get_children():
            self.result_tree.delete(item)

        if not phrase or not self.files:
            return

        try:
            similarity = int(self.similarity_entry.get().strip())
            similarity = min(100, similarity)
        except ValueError:
            similarity = 100

        exact = self.exact_var.get()
        self.results = search_in_documents(self.files, phrase, exact_match=exact, similarity=similarity)

        file_result_counts = {}
        for r in self.results:
            file_result_counts.setdefault(r['file_path'], []).append(r)

        for i, res in enumerate(self.results):
            index_in_file = file_result_counts[res["file_path"]].index(res) + 1
            total_in_file = len(file_result_counts[res["file_path"]])
            display_index = f"{index_in_file}/{total_in_file}"

            self.result_tree.insert("", "end", iid=i, values=(i + 1, res['file'], display_index, res['snippet']))

    def open_preview(self):
        selection = self.result_tree.selection()
        if not selection:
            return
        index = int(selection[0])
        result = self.results[index]
        phrase = self.search_entry.get().strip()
        PreviewWindow(
            self,
            result["file_path"],
            phrase,
            match_index=result["match_index"],
            match_length=result.get("match_length")
        )


class PreviewWindow(tk.Toplevel):
    def __init__(self, parent: tk.Tk, file_path: str, phrase: str, match_index: Optional[int] = None, match_length: Optional[int] = None):
        super().__init__(parent)
        self.title(f"Podgląd - {file_path.split('/')[-1]}")
        self.geometry("800x600")

        full_text: str = load_document(file_path)

        text_widget = tk.Text(self, wrap="word")
        text_widget.pack(fill="both", expand=True)

        text_widget.insert("1.0", full_text)
        text_widget.tag_config("highlight", background="yellow")
        text_widget.tag_config("focus-highlight", background="orange")

        text_widget.config(state='disabled')

        if phrase:
            idx = "1.0"
            while True:
                idx = text_widget.search(phrase, idx, nocase=True, stopindex=tk.END)
                if not idx:
                    break
                lastidx = f"{idx}+{len(phrase)}c"
                text_widget.tag_add("highlight", idx, lastidx)
                idx = lastidx

        if match_index is not None:
            length = match_length if match_length else len(phrase)
            start = f"1.0+{match_index}c"
            end = f"{start}+{length}c"
            text_widget.tag_add("focus-highlight", start, end)
            text_widget.see(start)
