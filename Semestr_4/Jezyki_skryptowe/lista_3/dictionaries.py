from datetime import datetime, UTC
from collections import defaultdict, Counter


def entry_to_dict(entry):
    keys = [
        "ts",
        "uid",
        "id.orig_h",
        "id.orig_p",
        "id.resp_h",
        "id.resp_p",
        "method",
        "host",
        "uri",
        "status_code"
    ]

    if len(entry) != len(keys):
        raise ValueError(f"Nieprawidłowa liczba pól we wpisie logu: {len(entry)} zamiast {len(keys)}")

    entry_dict = {key: value for key, value in zip(keys, entry)}

    return entry_dict


def log_to_dict(log):
    log_dict = {}

    for entry in log:
        entry_dict = entry_to_dict(entry)
        uid = entry_dict["uid"]

        if uid not in log_dict:
            log_dict[uid] = []

        log_dict[uid].append(entry_dict)

    return log_dict


def print_dict_entry_dates(log_dict):
    host_data_dict = defaultdict(list)
    method_counter_dict = defaultdict(int)
    status_code_2xx_counter = 0
    total_entries_counter = 0

    for uid in log_dict:
        for entry_dict in log_dict[uid]:
            host = entry_dict["host"]
            id_orig_h = entry_dict["id.orig_h"]
            timestamp = entry_dict["ts"]
            method = entry_dict["method"]
            status = entry_dict["status_code"]

            # if host:
            #     host_data_dict[host].append(timestamp)

            host_data_dict[id_orig_h].append(timestamp)

            if host and host not in host_data_dict:
                host_data_dict[host] = []

            if method:
                method_counter_dict[method] += 1

            if status and 200 <= status < 300:
                status_code_2xx_counter += 1

            total_entries_counter += 1

    print("\n1. adresy ip/nazwy domenowe hostów:")
    for host in host_data_dict:
        print(f"  - {host}")

    print("\n2. liczba żądań wykonanych przez danego hosta:")
    for host, timestamps in host_data_dict.items():
        print(f"  - {host}: {len(timestamps)} entries")

    print("\n3. pierwsze i ostatnie żądanie wykonane przez hosta:")
    for host, timestamps in host_data_dict.items():
        if timestamps:
            first = min(timestamps)
            last = max(timestamps)
            print(f"  - {host}: pierwsze - {first}, ostatnie - {last}")

    print("\n4. procentowy udział żądań metod:")
    for method, count in method_counter_dict.items():
        percent = (count / total_entries_counter) * 100
        if percent >= 0.01:
            print(f" {method} - {percent:.2f}%")

    print(f"5. stosunek liczby żądań z kodem 2xx do liczby wszystkich żądań: "
          f"{status_code_2xx_counter} / {total_entries_counter} = {status_code_2xx_counter / total_entries_counter * 100:.2f}%")

    print("\n")
