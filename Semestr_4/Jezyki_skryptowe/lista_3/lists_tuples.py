import sys
from datetime import datetime, UTC
import ipaddress


def read_log():
    log_entries = []

    for line in sys.stdin:
        line = line.strip()
        if line:
            fields = line.split('\t')

            ts = datetime.fromtimestamp(float(fields[0]), tz=UTC)
            uid = fields[1]
            id_orig_h = fields[2]
            id_orig_p = int(fields[3])
            id_resp_h = fields[4]
            id_resp_p = int(fields[5])
            method = fields[7] if fields[7] != "-" else None
            host = fields[8] if fields[8] != "-" else None
            uri = fields[9] if fields[9] != "-" else None
            status_code = int(fields[14]) if fields[14] != "-" else None

            log_entry = (
                ts,
                uid,
                id_orig_h,
                id_orig_p,
                id_resp_h,
                id_resp_p,
                method,
                host,
                uri,
                status_code
            )

            log_entries.append(log_entry)

    return log_entries


def sort_log(log, index):
    try:
        if len(log) == 0 or index < 0 or index >= len(log[0]):
            raise IndexError("Indeks przekracza rozmiar krotek.")

        return sorted(log, key=lambda entry: entry[index])

    except Exception as e:
        print(f"Wystąpił nieoczekiwany błąd: {e}")
        return []


def get_entries_by_addr(log, addr):
    filtered_entries = []

    try:
        ipaddress.ip_address(addr)

        # return [entry for entry in log if entry[2] == addr]
        for entry in log:
            try:
                if entry[2] == addr:
                    filtered_entries.append(entry)
            except (IndexError, TypeError):
                continue

    except ValueError:
        # return [entry for entry in log if entry[7] == addr]
        # host to strona ODBIERAJĄCA, a nie WYKONUJĄCA żądanie
        return []

    return filtered_entries


def get_entries_by_code(log, status_code):
    if not (status_code == 0 or 100 <= status_code <= 599):
        raise ValueError(f"Kod statusu HTTP {status_code} jest poza dopuszczalnym zakresem 100-599")

    # return [entry for entry in log if entry[9] == status_code]

    filtered_entries = []

    for entry in log:
        try:
            if entry[9] == status_code:
                filtered_entries.append(entry)
        except (IndexError, TypeError):
            continue

    return filtered_entries


def get_failed_reads(log, merge=False):
    # client_errors = [entry for entry in log if 400 <= entry[9] < 500]
    # server_errors = [entry for entry in log if 500 <= entry[9] < 600]

    client_errors = []
    server_errors = []

    for entry in log:
        try:
            if 400 <= entry[9] <= 499:
                client_errors.append(entry)
            elif 500 <= entry[9] <= 599:
                server_errors.append(entry)
        except (IndexError, TypeError):
            continue

    if merge:
        return client_errors + server_errors
    else:
        return client_errors, server_errors


def get_entries_by_extension(log, extension):
    if not isinstance(extension, str) or not extension:
        raise ValueError("Rozszerzenie pliku musi być niepustym ciągiem znaków.")

    extension = extension.lower()

    # return [entry for entry in log if entry[8].lower().endswith(f".{extension}")]

    filtered_entries = []

    for entry in log:
        try:
            uri = entry[8].lower()
            if uri.endswith(f".{extension}"):
                filtered_entries.append(entry)
        except (IndexError, TypeError, AttributeError):
            continue

    return filtered_entries
