import os
import pytest
from src.file_loader import (
    extract_text_from_pdf,
    extract_text_from_docx,
    load_document
)

TEST_PDF = "test_data/sample.pdf"
TEST_DOCX = "test_data/sample.docx"

# ========== extract_text_from_pdf ==========

def test_extract_text_from_pdf_success():
    text = extract_text_from_pdf(TEST_PDF)
    assert isinstance(text, str)
    assert "Python" in text

def test_extract_text_from_pdf_file_not_found():
    text = extract_text_from_pdf("test_data/nonexistent.pdf")
    assert text == ""

def test_extract_text_from_pdf_corrupted(monkeypatch):
    def mock_pdf_reader(path):
        raise Exception("Corrupted PDF")
    monkeypatch.setattr("src.file_loader.PdfReader", lambda path: (_ for _ in ()).throw(Exception("Boom")))
    text = extract_text_from_pdf(TEST_PDF)
    assert text == ""

# ========== extract_text_from_docx ==========

def test_extract_text_from_docx_success():
    text = extract_text_from_docx(TEST_DOCX)
    assert isinstance(text, str)
    assert "Python" in text

def test_extract_text_from_docx_file_not_found():
    text = extract_text_from_docx("test_data/nonexistent.docx")
    assert text == ""

def test_extract_text_from_docx_corrupted(monkeypatch):
    monkeypatch.setattr("src.file_loader.Document", lambda path: (_ for _ in ()).throw(Exception("Corrupted DOCX")))
    text = extract_text_from_docx(TEST_DOCX)
    assert text == ""

# ========== load_document ==========

def test_load_document_pdf():
    text = load_document(TEST_PDF)
    assert "Python" in text

def test_load_document_docx():
    text = load_document(TEST_DOCX)
    assert "Python" in text

def test_load_document_unsupported_extension(tmp_path):
    dummy = tmp_path / "unsupported.txt"
    dummy.write_text("Nieobsługiwany format")
    text = load_document(str(dummy))
    assert text == ""
