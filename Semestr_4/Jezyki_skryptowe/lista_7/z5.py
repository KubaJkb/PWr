import time
from functools import cache

from z4 import make_generator

def make_generator_mem(f):
    cached_f = cache(f)
    return make_generator(cached_f)


def main():
    @cache
    def fib(n):
        if n <= 2:
            return 1
        return fib(n - 1) + fib(n - 2)

    gen_fib_mem = make_generator_mem(fib)
    print([next(gen_fib_mem) for _ in range(100)])

if __name__ == '__main__':
    main()