using System;

namespace Zad2_ClassVar
{
    internal class Program
    {
        static void Main(string[] args)
        {
            // 'class' jest słowem kluczowym dlatego trzeba użyć @
            var @class = "NET – Grupa 1";

            Console.WriteLine("Zmienna o nazwie 'class' (użycie @):");
            Console.WriteLine(@class);
        }
    }
}
