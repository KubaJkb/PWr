from lists_tuples import *
from dictionaries import *

def main():
    with open("http_first_100k.log", "r") as f:
        sys.stdin = f

        log_data = read_log()

        # log_data = sort_log(log_data, 2)
        # print_log_entries(log_data)

        # print_log_entries(get_entries_by_addr(log_data, "192.168.202.107"))
        # print_log_entries(get_entries_by_code(log_data, 505))
        # print_log_entries(get_failed_reads(log_data, True))
        # print_log_entries(get_entries_by_extension(log_data, "htr"))

        print_dict_entry_dates(log_to_dict(log_data))


def print_log_entries(log_entries):
    print("{:<25} {:<20} {:<15} {:<6} {:<15} {:<6} {:<8} {:<20} {:<30} {:<5}".format(
        "Timestamp", "UID", "Src IP", "S.Port", "Dst IP", "D.Port", "Method", "Host", "URI", "Status"
    ))
    print("-" * 140)

    for entry in log_entries:
        ts, uid, id_orig_h, id_orig_p, id_resp_h, id_resp_p, method, host, uri, status_code = entry
        print("{:<25} {:<20} {:<15} {:<6} {:<15} {:<6} {:<8} {:<20} {:<30} {:<5}".format(
            ts.strftime('%Y-%m-%d %H:%M:%S %Z'), uid, id_orig_h, id_orig_p, id_resp_h, id_resp_p,
            method if method else "-", host if host else "-", uri if uri else "-", status_code if status_code else "-"
        ))


if __name__ == "__main__":
    main()
