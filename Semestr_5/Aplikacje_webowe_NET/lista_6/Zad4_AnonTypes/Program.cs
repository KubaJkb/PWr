using System;

namespace Zad4_AnonTypes
{
    internal class Program
    {
        static void Main(string[] args)
        {
            // Typ anonimowy zamiast krotki
            var person = new { First = "Ewa", Last = "Kowal", Age = 31, Salary = 5100.25 };

            Console.WriteLine("Anonimowy typ:");
            Console.WriteLine($"{person.First} {person.Last}, wiek: {person.Age}, płaca: {person.Salary}");

            // Przekazywanie jako dynamic
            Console.WriteLine("\nPrzekazywanie jako dynamic:");
            PrintDynamic(person);

            // Przekazywanie jako object i odczyt reflection
            Console.WriteLine("\nPrzekazywanie jako object (reflection):");
            PrintByReflection(person);
        }

        static void PrintDynamic(dynamic p)
        {
            Console.WriteLine($"(dynamic) {p.First} {p.Last}, {p.Age} lat, {p.Salary} zł");
        }

        static void PrintByReflection(object obj)
        {
            var t = obj.GetType();
            var first = t.GetProperty("First")?.GetValue(obj);
            var last = t.GetProperty("Last")?.GetValue(obj);
            var age = t.GetProperty("Age")?.GetValue(obj);
            var salary = t.GetProperty("Salary")?.GetValue(obj);
            // "?" zwraca "null" jeżeli Property nie istnieje

            Console.WriteLine($"(reflection) {first} {last}, {age} lat, {salary} zł");
        }
    }
}
