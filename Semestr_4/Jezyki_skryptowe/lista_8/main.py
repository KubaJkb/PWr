import sys
from PySide6.QtWidgets import QApplication
from gui import LogViewer

if __name__ == "__main__":
    app = QApplication(sys.argv)
    viewer = LogViewer()
    viewer.show()
    sys.exit(app.exec())
