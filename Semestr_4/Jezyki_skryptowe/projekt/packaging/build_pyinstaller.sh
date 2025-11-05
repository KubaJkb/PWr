#!/usr/bin/env bash
set -e

PROJECT_ROOT="$(cd "$(dirname "$0")/.."; pwd)"
cd "$PROJECT_ROOT"

# python3 -m venv .venv && source .venv/bin/activate

pip install --upgrade pip
pip install -r requirements.txt pyinstaller

pyinstaller \
  --noconfirm \
  --clean \
  packaging/search_app.spec

echo "Gotowe! Twoja aplikacja jest w dist/SearchApp/"
