using System;

namespace Zad6_CountMyTypes
{
    internal class Program
    {
        static void Main(string[] args)
        {
            var result = CountMyTypes(1, 2, 3.5, -4.2, "short", "longerString", true, 4, 6.0, "abcd", '*', "abcde");

            Console.WriteLine($"Parzyste int: {result.evenInts}");
            Console.WriteLine($"Dodatnie double: {result.positiveDoubles}");
            Console.WriteLine($"Napisy >=5 znaków: {result.longStrings}");
            Console.WriteLine($"Inne typy: {result.others}");
        }

        static (int evenInts, int positiveDoubles, int longStrings, int others) CountMyTypes(params object[] items)
        {
            int evens = 0, posDoubles = 0, longStr = 0, others = 0;

            foreach (var item in items)
            {
                switch (item)
                {
                    case int i when i % 2 == 0:
                        evens++;
                        break;
                    case int:
                        break;
                    case double d when d > 0:
                        posDoubles++;
                        break;
                    case double:
                        break;
                    case string s when s.Length >= 5:
                        longStr++;
                        break;
                    case string:
                        break;
                    default:
                        others++;
                        break;
                }
            }

            return (evens, posDoubles, longStr, others);
        }
    }
}
