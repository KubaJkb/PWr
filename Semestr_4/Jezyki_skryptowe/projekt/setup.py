from setuptools import setup, find_packages

setup(
    name="pdf_docx_searcher",
    version="0.1.0",
    description="GUI do wyszukiwania tekstu w plikach PDF i DOCX",
    author="Jakub_Kacprzyk",
    python_requires=">=3.7",
    install_requires=[
        "tkinterdnd2",
        "PyPDF2",
        "python-docx",
    ],
    package_dir={"": "src"},
    packages=find_packages(where="src"),
    entry_points={
        "console_scripts": [
            "search-app = main:run",
        ],
    },
)
