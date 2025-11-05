import os
import re
from typing import List, Dict, Any
from src.file_loader import load_document
from difflib import SequenceMatcher


def normalize_text(text: str) -> str:
    substitutions = {
        'ą': 'a', 'ć': 'c', 'ę': 'e', 'ł': 'l', 'ń': 'n', 'ó': 'o', 'ś': 's', 'ź': 'z', 'ż': 'z',
    }
    text = ''.join(substitutions.get(c, c) for c in text.lower())
    return re.sub(r'\s+', '', text)

def similar(a: str, b: str) -> float:
    return SequenceMatcher(None, a, b).ratio()

def search_in_documents(files: List[str], phrase: str, exact_match: bool = True, similarity: int = 100) -> List[Dict[str, Any]]:
    results = []
    min_sim = similarity / 100.0

    for file in files:
        full_text = load_document(file)

        if exact_match:
            text_to_search = full_text
            search_text = phrase
        else:
            text_to_search = normalize_text(full_text)
            search_text = normalize_text(phrase)

            pos_map = []
            for i, c in enumerate(full_text):
                if not c.isspace():
                    pos_map.append(i)

        if exact_match:
            pattern = re.escape(phrase).replace(r'\ ', r'\s+')
            for match in re.finditer(pattern, full_text, re.IGNORECASE):
                idx = match.start()
                snippet = full_text[max(0, idx - 40):idx + len(match.group()) + 40].replace('\n', ' ')
                results.append({
                    "file_path": file,
                    "file": os.path.basename(file),
                    "snippet": snippet.strip(),
                    "match_index": idx
                })
        else:
            length = len(search_text)
            i = 0
            while i <= len(text_to_search) - length:
                window = text_to_search[i:i + length]
                sim = similar(search_text, window)
                if sim >= min_sim:
                    real_start = pos_map[i]
                    real_end = pos_map[i + length - 1] + 1
                    snippet = full_text[max(0, real_start - 40):min(len(full_text), real_end + 40)].replace('\n', ' ')
                    match_length = real_end - real_start
                    results.append({
                        "file_path": file,
                        "file": os.path.basename(file),
                        "snippet": snippet.strip(),
                        "match_index": real_start,
                        "match_length": match_length
                    })

                    i += length
                else:
                    i += 1

    return results
