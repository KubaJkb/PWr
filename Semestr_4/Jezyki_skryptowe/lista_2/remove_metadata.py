import sys
import io

sys.stdin = io.TextIOWrapper(sys.stdin.buffer, encoding="utf-8")

def remove_metadata():
    line_nr = 0
    previous_line_empty = False
    in_content = False
    first_10_lines_content = ""

    for line in sys.stdin:
        clean_line = line.rstrip()
        if not in_content:
            line_nr += 1
            first_10_lines_content += line
            if line_nr > 10:
                in_content = True
            elif clean_line == "" and previous_line_empty:
                in_content = True
                first_10_lines_content = ""
            previous_line_empty = clean_line == ""
        else:
            if first_10_lines_content != "":
                print(first_10_lines_content.rstrip())
                first_10_lines_content = ""
            if clean_line == "-----":
                break

            print(clean_line)


if __name__ == "__main__":
    remove_metadata()
