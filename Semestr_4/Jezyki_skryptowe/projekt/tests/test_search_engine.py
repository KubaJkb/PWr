import os
import pytest
from unittest.mock import patch
from src.search_engine import search_in_documents, normalize_text, similar
from src.file_loader import load_document

TEST_DOCX = "test_data/sample.docx"
TEST_PDF = "test_data/sample.pdf"

# ========== Helper Fixtures ==========

@pytest.fixture
def docx_content():
    return load_document(TEST_DOCX)

@pytest.fixture
def pdf_content():
    return load_document(TEST_PDF)

# ========== Unit Tests: normalize_text & similar ==========

def test_normalize_text_removes_diacritics():
    assert normalize_text("Zażółć gęślą jaźń") == "zazolcgeslajazn"

def test_normalize_text_removes_spaces():
    assert normalize_text("  P y  t   h o n ") == "python"

def test_similar_ratio_identical():
    assert similar("Python", "Python") == 1.0

def test_similar_ratio_partial():
    assert similar("Python", "Pithon") > 0.8

# ========== Unit Tests: Exact Match ==========

def test_exact_match_docx():
    results = search_in_documents([TEST_DOCX], "Python", exact_match=True)
    assert len(results) == 1
    assert "Python" in results[0]["snippet"]
    assert results[0]["file"].endswith(".docx")

def test_exact_match_pdf():
    results = search_in_documents([TEST_PDF], "Python", exact_match=True)
    assert len(results) == 1
    assert "Python" in results[0]["snippet"]
    assert results[0]["file"].endswith(".pdf")

def test_exact_match_not_found():
    results = search_in_documents([TEST_PDF], "Nieistniejący", exact_match=True)
    assert results == []

# ========== Unit Tests: Approximate Match ==========

def test_approximate_match_docx():
    results = search_in_documents([TEST_DOCX], "pyton", exact_match=False, similarity=80)
    assert len(results) == 1
    assert "Python" in results[0]["snippet"]

def test_approximate_match_pdf():
    results = search_in_documents([TEST_PDF], "pythom", exact_match=False, similarity=80)
    assert len(results) == 1
    assert "Python" in results[0]["snippet"]

def test_approximate_match_low_similarity_no_match():
    results = search_in_documents([TEST_PDF], "pythom", exact_match=False, similarity=99)
    assert results == []

# ========== Unit Tests: File Handling ==========

def test_unsupported_file_type(tmp_path):
    dummy_file = tmp_path / "unsupported.txt"
    dummy_file.write_text("To nie jest PDF ani DOCX")
    results = search_in_documents([str(dummy_file)], "test", exact_match=True)
    assert results == []

def test_pdf_load_error(monkeypatch):
    with monkeypatch.context() as m:
        m.setattr("src.file_loader.extract_text_from_pdf", lambda x: (_ for _ in ()).throw(Exception("PDF ERROR")))
        assert load_document("test_data/sample.pdf") == ""

def test_docx_load_error(monkeypatch):
    with monkeypatch.context() as m:
        m.setattr("src.file_loader.extract_text_from_docx", lambda x: (_ for _ in ()).throw(Exception("DOCX ERROR")))
        assert load_document("test_data/sample.docx") == ""

# ========== Multiple File Search ==========

def test_search_multiple_files():
    results = search_in_documents([TEST_PDF, TEST_DOCX], "Python", exact_match=True)
    assert len(results) == 2
    paths = {res["file_path"] for res in results}
    assert TEST_PDF in paths and TEST_DOCX in paths
