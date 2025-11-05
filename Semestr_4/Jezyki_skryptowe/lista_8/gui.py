from datetime import timezone
from PySide6.QtWidgets import (
    QWidget, QListWidget, QLabel, QVBoxLayout, QHBoxLayout,
    QPushButton, QFileDialog, QDateTimeEdit, QFormLayout
)
from PySide6.QtCore import QDateTime, Qt
from log_parser import parse_log_file


class LogViewer(QWidget):
    def __init__(self):
        super().__init__()
        self.setWindowTitle("Przeglądarka logów HTTP")
        self.resize(800, 600)

        self.all_entries = []
        self.filtered_entries = []
        self.current_index = -1

        self.build_gui()

    def build_gui(self):
        layout = QVBoxLayout()
        layout.setContentsMargins(20, 20, 20, 20)  # left, top, right, bottom
        layout.setSpacing(15)

        # ======= WCZYTYWANIE PLIKU =======
        file_bar_layout = QHBoxLayout()

        self.loaded_file_label = QLabel("No file chosen")

        load_button = QPushButton("Open log file")
        load_button.clicked.connect(self.load_logs)

        file_bar_layout.addWidget(self.loaded_file_label, stretch=3)
        file_bar_layout.addWidget(load_button, stretch=1)

        layout.addLayout(file_bar_layout)

        # ======= MASTER I DETAIL =======
        master_detail_layout = QHBoxLayout()

        # --- FILTRY CZASU + MASTER ---
        master_side_layout = QVBoxLayout()

        # Filtry czasu
        filter_layout = QHBoxLayout()

        label_from = QLabel("From")
        filter_layout.addWidget(label_from)

        self.start_filter = QDateTimeEdit()
        self.start_filter.setDisplayFormat("yyyy-MM-dd")
        self.start_filter.setCalendarPopup(True)
        filter_layout.addWidget(self.start_filter)
        filter_layout.addStretch(1)

        label_to = QLabel("To")
        filter_layout.addWidget(label_to)

        self.end_filter = QDateTimeEdit()
        self.end_filter.setDisplayFormat("yyyy-MM-dd")
        self.end_filter.setCalendarPopup(True)
        filter_layout.addWidget(self.end_filter)
        filter_layout.addStretch(1)

        filter_button = QPushButton("Filter")
        filter_button.clicked.connect(self.apply_filter)
        filter_layout.addWidget(filter_button)

        master_side_layout.addLayout(filter_layout)

        # Lista logów (widok master)
        self.log_list = QListWidget()
        self.log_list.setSpacing(2)
        self.log_list.currentRowChanged.connect(self.display_log_details)

        master_side_layout.addWidget(self.log_list)

        master_detail_layout.addLayout(master_side_layout, stretch=15)

        # --- DETAIL ---
        self.detail_labels = {
            key: QLabel("") for key in
            ["Remote host", "Date", "Time", "Timezone", "Status code", "Method", "Resource", "Size"]
        }

        detail_layout = QFormLayout()
        detail_layout.setVerticalSpacing(30)
        detail_layout.setHorizontalSpacing(25)
        detail_layout.setLabelAlignment(Qt.AlignmentFlag.AlignRight)

        for key, label in self.detail_labels.items():
            detail_layout.addRow(QLabel(key), label)

        detail_widget = QWidget()
        detail_widget.setLayout(detail_layout)

        master_detail_layout.addStretch(1)
        master_detail_layout.addWidget(detail_widget, stretch=10, alignment=Qt.AlignmentFlag.AlignVCenter)

        layout.addLayout(master_detail_layout)

        # ======= NAWIGACJA =======
        nav_layout = QHBoxLayout()
        self.prev_button = QPushButton("Previous")
        self.next_button = QPushButton("Next")
        self.prev_button.clicked.connect(self.prev_log)
        self.next_button.clicked.connect(self.next_log)
        nav_layout.addWidget(self.prev_button)
        nav_layout.addWidget(self.next_button)
        layout.addLayout(nav_layout)

        self.setLayout(layout)
        self.update_nav_buttons()

    def load_logs(self):
        path, _ = QFileDialog.getOpenFileName(self, "Open log file", "", "Logs (*.txt *.log)")
        if path:
            self.loaded_file_label.setText(path)
            self.all_entries = parse_log_file(path)
            self.start_filter.setDateTime(QDateTime.currentDateTime().addDays(-7))
            self.end_filter.setDateTime(QDateTime.currentDateTime())
            self.apply_filter()

    def apply_filter(self):
        start = self.start_filter.dateTime().toPython().replace(tzinfo=timezone.utc)
        end = self.end_filter.dateTime().toPython().replace(tzinfo=timezone.utc)
        self.filtered_entries = [entry for entry in self.all_entries if start <= entry.timestamp <= end]

        self.log_list.clear()
        for entry in self.filtered_entries:
            self.log_list.addItem(entry.summary())

        self.current_index = 0 if self.filtered_entries else -1
        self.log_list.setCurrentRow(self.current_index)

        self.update_nav_buttons()

    def display_log_details(self, index):
        if 0 <= index < len(self.filtered_entries):
            entry = self.filtered_entries[index]

            for key, label in self.detail_labels.items():
                label.setText(entry.details()[key])
            self.current_index = index

        else:
            for label in self.detail_labels.values():
                label.setText("")

        self.update_nav_buttons()

    def update_nav_buttons(self):
        self.prev_button.setEnabled(self.current_index > 0)
        self.next_button.setEnabled(self.current_index + 1 < len(self.filtered_entries))

    def prev_log(self):
        if self.current_index > 0:
            self.current_index -= 1
            self.log_list.setCurrentRow(self.current_index)

    def next_log(self):
        if self.current_index + 1 < len(self.filtered_entries):
            self.current_index += 1
            self.log_list.setCurrentRow(self.current_index)
