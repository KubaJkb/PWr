def a_acronym(words):
    return ''.join(map(lambda w: w[0], words))

def b_median(numbers):
    sorted_numbers = sorted(numbers)
    n = len(sorted_numbers)
    return (
        sorted_numbers[n // 2]
        if n % 2 == 1
        else (sorted_numbers[n // 2 - 1] + sorted_numbers[n // 2]) / 2
    )

def c_square_root(x, epsilon=0.0001):
    def iterate(y):
        return (y + x / y) / 2

    def within_epsilon(y):
        return abs(y * y - x) < epsilon

    def approx(y):
        return y if within_epsilon(y) else approx(iterate(y))

    return approx(x / 2) if x >= 0 else None

def d_make_alpha_dict(text):
    words = text.split()
    chars = set(filter(lambda ch: ch.isalpha(), text.lower()))

    return dict(
        map(
            lambda ch: (ch, list(filter(lambda w: ch in w.lower(), words))),
            chars
        )
    )

def e_flatten(lst):
    return list(
        sum(
            map(
                lambda x: e_flatten(x) if isinstance(x, (list, tuple)) else [x],
                lst
            ),
            []
        )
    )



def main():
    print("\n******************** A ************************")
    print(a_acronym(["Zakład", "Ubezpieczeń", "Społecznych"]))

    print("\n******************** B ************************")
    print(b_median([1,1,19,2,3,4,4,5,1]))

    print("\n******************** C ************************")
    print(c_square_root(-3, epsilon = 0.1))

    print("\n******************** D ************************")
    print(d_make_alpha_dict("on i ona"))

    print("\n******************** E ************************")
    print(e_flatten([1, [2, 3], [[4, 5], 6]]))

if __name__ == '__main__':
    main()