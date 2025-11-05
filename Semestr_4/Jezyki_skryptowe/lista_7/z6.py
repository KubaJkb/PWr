import logging
import functools
import time
import inspect

logging.basicConfig(
    level=logging.DEBUG,
    format='[%(levelname)s] %(asctime)s - %(message)s',
    datefmt='%H:%M:%S'
)

def log(level=logging.INFO):
    def decorator(obj):
        # dekoracja klasy
        if inspect.isclass(obj):
            original_init = obj.__init__

            @functools.wraps(original_init)
            def new_init(self, *args, **kwargs):
                logging.log(
                    level,
                    f"Instantiated class: {obj.__name__} | "
                    f"Args: {args}, Kwargs: {kwargs}"
                )
                original_init(self, *args, **kwargs)

            obj.__init__ = new_init
            return obj

        # dekoracja funkcji
        elif callable(obj):
            @functools.wraps(obj)
            def wrapper(*args, **kwargs):
                start_time = time.time()
                result = obj(*args, **kwargs)
                end_time = time.time()
                duration = end_time - start_time

                logging.log(
                    level,
                    f"Called function: {obj.__name__} | "
                    f"Args: {args}, Kwargs: {kwargs} | "
                    f"Returned: {result} | Duration: {duration:.6f}s"
                )
                return result

            return wrapper

        else:
            raise TypeError("Unsupported type for logging")
    return decorator


def main():
    # Person = log()(Person) - zwraca decorator(Person)
    @log(logging.INFO)
    class Person:
        def __init__(self, name, age):
            self.name = name
            self.age = age

    p = Person("Ala", 30)

    # add = log()(add) - zwraca decorator(add)
    @log(logging.DEBUG)
    def add(x, y):
        return x + y
    result = add(10, 5)

if __name__ == '__main__':
    main()