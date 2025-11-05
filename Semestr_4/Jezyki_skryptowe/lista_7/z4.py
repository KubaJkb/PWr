def make_generator(f):
    def generator():
        n = 1
        while True:
            yield f(n)
            n += 1
    return generator()


def main():
    print("\n******************** A ************************")
    def fib(n):
        if n <= 2:
            return 1
        return fib(n - 1) + fib(n - 2)

    gen_fib = make_generator(fib)
    print([next(gen_fib) for _ in range(10)])  # 1 1 2 3 5 8 13 21 34 55

    print("\n******************** B ************************")
    gen_ar = make_generator(lambda n: 3 * n + 1)
    print([next(gen_ar) for _ in range(5)])  # [4, 7, 10, 13, 16]
    gen_geo = make_generator(lambda n: 2 ** n)
    print([next(gen_geo) for _ in range(5)])  # [2, 4, 8, 16, 32]
    gen_square = make_generator(lambda n: n ** 2)
    print([next(gen_square) for _ in range(5)])  # [1, 4, 9, 16, 25]

if __name__ == '__main__':
    main()