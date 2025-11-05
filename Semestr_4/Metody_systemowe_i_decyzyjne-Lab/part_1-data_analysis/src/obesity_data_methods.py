import pandas as pd
import numpy as np
import json
from pathlib import Path


def load_obesity_data(file_path="../data/ObesityDataSet.csv"):
    """
    Wczytuje zbiór danych dotyczący otyłości z pliku CSV.

    Parametry:
    ----------
    file_path : str
        Ścieżka do pliku CSV z danymi

    Zwraca:
    -------
    pd.DataFrame
        DataFrame zawierający wczytane dane

    Wyjątki:
    --------
    FileNotFoundError
        Gdy plik nie istnieje
    pd.errors.EmptyDataError
        Gdy plik jest pusty
    """
    try:
        if not Path(file_path).is_file():
            raise FileNotFoundError(f"Plik {file_path} nie istnieje")

        data = pd.read_csv(file_path)

        if data.empty:
            raise pd.errors.EmptyDataError("Plik jest pusty")

        return data

    except Exception as e:
        print(f"Błąd podczas wczytywania danych: {str(e)}")
        raise


def calculate_and_save_statistics(data, output_prefix="data/data_statistics.csv"):
    """
    Oblicza i zapisuje statystyki opisowe dla cech numerycznych i kategorialnych.

    Parametry:
    ----------
    data : pd.DataFrame
        DataFrame zawierający dane do analizy
    output_file : str, optional
        Nazwa pliku wyjściowego (domyślnie "data_statistics.csv")
    """
    numeric_stats = []
    categorical_stats = []

    for column in data.columns:
        col_data = data[column]

        # cechy numeryczne
        if pd.api.types.is_numeric_dtype(col_data):
            stats = {
                'feature': column,
                'type': 'numeric',
                'mean': col_data.mean(),
                'median': col_data.median(),
                'min': col_data.min(),
                'max': col_data.max(),
                'std': col_data.std(),
                'percentile_5': np.percentile(col_data.dropna(), 5),
                'percentile_95': np.percentile(col_data.dropna(), 95),
                'missing_values': col_data.isna().sum()
            }
            numeric_stats.append(stats)

        # cechy kategorialne
        else:
            value_counts = col_data.value_counts(normalize=True)
            stats = {
                'feature': column,
                'type': 'categorical',
                'unique_classes': col_data.nunique(),
                'missing_values': col_data.isna().sum(),
                'most_frequent_class': value_counts.idxmax(),
                'most_frequent_proportion': value_counts.max(),
                'classes_proportions': value_counts.to_dict()
            }
            categorical_stats.append(stats)

    numeric_df = pd.DataFrame(numeric_stats)
    categorical_df = pd.DataFrame(categorical_stats)

    all_stats = pd.concat([numeric_df, categorical_df], ignore_index=True)

    out_file = f"data/{output_prefix}.csv"
    all_stats.to_csv(out_file, index=False)
    print(f"Statystyki zostały zapisane do pliku: {out_file}")

    return all_stats


def calculate_and_save_statistics_separate(data, output_prefix="data_statistics"):
    """
    Oblicza i zapisuje statystyki opisowe dla cech numerycznych i kategorialnych
    w osobnych plikach CSV.

    Parametry:
    ----------
    data : pd.DataFrame
        DataFrame zawierający dane do analizy
    output_prefix : str, optional
        Prefix nazwy plików wyjściowych (domyślnie "data_statistics")
    """
    numeric_stats = []
    categorical_stats = []

    for column in data.columns:
        col_data = data[column]

        # Dla cech numerycznych
        if pd.api.types.is_numeric_dtype(col_data):
            stats = {
                'feature': column,
                'mean': col_data.mean(),
                'median': col_data.median(),
                'min': col_data.min(),
                'max': col_data.max(),
                'std': col_data.std(),
                'percentile_5': np.percentile(col_data.dropna(), 5),
                'percentile_95': np.percentile(col_data.dropna(), 95),
                'missing_values': col_data.isna().sum()
            }
            numeric_stats.append(stats)

        # Dla cech kategorialnych
        else:
            value_counts = col_data.value_counts(normalize=True)
            stats = {
                'feature': column,
                'unique_classes': col_data.nunique(),
                'missing_values': col_data.isna().sum(),
                'most_frequent_class': value_counts.idxmax(),
                'most_frequent_proportion': value_counts.max(),
                'classes_proportions': value_counts.to_dict()
            }

            categorical_stats.append(stats)

    numeric_df = pd.DataFrame(numeric_stats)
    categorical_df = pd.DataFrame(categorical_stats)

    numeric_file = f"data/{output_prefix}_numeric.csv"
    categorical_file = f"data/{output_prefix}_categorical.csv"

    numeric_df.to_csv(numeric_file, index=False)
    categorical_df.to_csv(categorical_file, index=False)

    print(f"Statystyki numeryczne zapisane do: {numeric_file}")
    print(f"Statystyki kategorialne zapisane do: {categorical_file}")

    return numeric_df, categorical_df
