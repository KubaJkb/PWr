import os
import sys


def print_path_dirs():
    path_dirs = [d for d in os.getenv("PATH", "").split(os.pathsep) if d.strip()]   # stworzenie listy NIEPUSTYCH katalogów z PATH

    for directory in path_dirs:
        print(directory)

def print_path_dirs_with_executables():
    path_dirs = [d for d in os.getenv("PATH", "").split(os.pathsep) if d.strip()]

    for directory in path_dirs:
        executables = get_executables(directory)

        print(f"{directory}:")
        for exe in executables:
            print(f"  - {exe}")
        print()

def get_executables(directory):
    if not os.path.isdir(directory):    # sprawdzanie czy podane directory jest katalogiem
        return []

    executables = []
    for file in os.listdir(directory):
        filepath = os.path.join(directory, file)
        if os.path.isfile(filepath) and os.access(filepath, os.X_OK):   # sprawdzanie czy jest plikiem i czy jest wykonywalny
            executables.append(file)

    return executables


if __name__ == "__main__":
    if len(sys.argv) == 2:
        option = sys.argv[1]
        if option == "list":
            print_path_dirs()
            sys.exit(1)
        elif option == "executables":
            print_path_dirs_with_executables()
            sys.exit(1)

    print("Użyj parametru 'list' lub 'executables'.")
