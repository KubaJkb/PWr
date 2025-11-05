import random
import string

class PasswordGenerator:
    def __init__(self, length, charset=None, count=1):
        self.length = length
        self.charset = charset or (string.ascii_letters + string.digits)
        self.count = count
        self.generated = 0

    def __iter__(self):
        return self

    def __next__(self):
        if self.generated >= self.count:
            raise StopIteration
        password = ''.join(random.choices(self.charset, k=self.length))
        self.generated += 1
        return password


def main():
    pg = PasswordGenerator(8, count=3)

    for p in pg:
        print(p)

    # print(next(pg))
    # print(next(pg))
    # print(next(pg))
    # print(next(pg))  # StopIteration


if __name__ == '__main__':
    main()