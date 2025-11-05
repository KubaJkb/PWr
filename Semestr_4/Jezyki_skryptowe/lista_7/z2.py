def forall(pred, iterable):
    return all(map(pred, iterable))

def exists(pred, iterable):
    return any(map(pred, iterable))

def atleast(n, pred, iterable):
    return sum(map(pred, iterable)) >= n

def atmost(n, pred, iterable):
    return sum(map(pred, iterable)) <= n


def main():
    is_even = lambda x: x % 2 == 0
    is_positive = lambda x: x > 0
    print("\n******************** A ************************")
    print("forall(is_even, [2, 4, 6, 8]):", forall(is_even, [2, 4, 6, 8]))
    print("forall(is_even, [2, 4, 5, 8]):", forall(is_even, [2, 4, 5, 8]))

    print("\n******************** B ************************")
    print("exists(is_even, [1, 3, 5, 7]):", exists(is_even, [1, 3, 5, 7]))
    print("exists(is_even, [1, 3, 6, 7]):", exists(is_even, [1, 3, 6, 7]))

    print("\n******************** C ************************")
    print("atleast(3, is_even, [2, 4, 5, 8]):", atleast(3, is_even, [2, 4, 5, 8]))
    print("atleast(3, is_even, [2, 4, 5, 7]):", atleast(3, is_even, [2, 4, 5, 7]))

    print("\n******************** D ************************")
    print("atmost(2, is_even, [2, 4, 5, 8]):", atmost(2, is_even, [2, 4, 5, 8]))
    print("atmost(2, is_even, [2, 4, 5, 7]):", atmost(2, is_even, [2, 4, 5, 7]))

if __name__ == '__main__':
    main()