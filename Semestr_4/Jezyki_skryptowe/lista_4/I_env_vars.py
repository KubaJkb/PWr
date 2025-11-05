import os
import sys


def display_environment_variables():
    env_vars = sorted(os.environ.items())

    if len(sys.argv) > 1:
        keywords = sys.argv[1:]
        env_vars = [var for var in env_vars if any(kw in var[0] for kw in keywords)]

    for name, value in env_vars:
        print(f"{name} = {value}")


if __name__ == "__main__":
    display_environment_variables()
