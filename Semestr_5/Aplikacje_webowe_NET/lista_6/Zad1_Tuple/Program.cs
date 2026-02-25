using System;

namespace Zad1_Tuple
{
    internal class Program
    {
        static void Main(string[] args)
        {
            var person = ("Jan", "Kowalski", 34, 5600.75);
            PrintTuple(person);


            Console.WriteLine("\n--- Różne sposoby użycia krotki ---");

            Console.WriteLine("A) Przez Item1,Item2,...:");
            Console.WriteLine($"Imię: {person.Item1}, Nazwisko: {person.Item2}, Wiek: {person.Item3}, Płaca: {person.Item4}");

            Console.WriteLine("\nB) Krotka nazwana:");
            var personNamed = (First: "Maria", Last: "Nowak", Age: 28, Salary: 4300.0);
            Console.WriteLine($"Imię: {personNamed.First}, Nazwisko: {personNamed.Last}, Wiek: {personNamed.Age}, Płaca: {personNamed.Salary}");

            Console.WriteLine("\nC) Krotka nazwana z deklaracjami pól:");
            (string first, string last, int ageN, double salaryN) personNamed2 = ("Krzysztof", "Kowal", 42, 5600.0);
            Console.WriteLine($"Imię: {personNamed2.first}, Nazwisko: {personNamed2.last}, Wiek: {personNamed2.ageN}, Płaca: {personNamed2.salaryN}");

            Console.WriteLine("\nD) Dekonstrukcja:");
            var (first, last, age, salary) = person;
            Console.WriteLine($"Imię: {first}, Nazwisko: {last}, Wiek: {age}, Płaca: {salary}");

            Console.WriteLine("\nE) Dekonstrukcja z pominięciem pól:");
            var (n, s, _, _) = ("Adam", "Zieliński", 45, 7000.0);
            Console.WriteLine($"Imię: {n}, Nazwisko: {s}");
        }

        static void PrintTuple((string, string, int, double) t)
        {
            Console.WriteLine("Funkcja otrzymała krotkę:");
            Console.WriteLine($"Item1: {t.Item1}, Item2: {t.Item2}, Item3: {t.Item3}, Item4: {t.Item4}");
        }
    }
}
