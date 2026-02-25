using System;
using System.Text;

namespace Zad5_DrawCard
{
    internal class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Przykład 1:");
            DrawCard("Ryszard", "Rys", 'X', 2, 20);

            Console.WriteLine("\nPrzykład 2 (domyślne parametry):");
            DrawCard("Anna", "Brzęczyszczykiewicz");

            Console.WriteLine("\nPrzykład 3 (parametry nazwane):");
            DrawCard(firstLine: "Marek", borderChar: '#', minWidth: 30, secondLine: "Bogusz", borderThickness: 1);
        }

        static void DrawCard(
            string firstLine,
            string secondLine = "",
            char borderChar = '*',
            int borderThickness = 1,
            int minWidth = 10)
        {
            if (borderThickness < 1) borderThickness = 1;
            if (minWidth < 1) minWidth = 1;

            int contentMax = Math.Max(firstLine.Length, secondLine.Length);
            int innerWidth = Math.Max(minWidth - 2 * borderThickness, contentMax + 2);
            int totalWidth = innerWidth + 2 * borderThickness;

            // Górna ramka
            for (int i = 0; i < borderThickness; i++)
                Console.WriteLine(new string(borderChar, totalWidth));

            // Treść wycentrowana
            PrintCentered(firstLine, innerWidth, borderChar, borderThickness);
            PrintCentered(secondLine, innerWidth, borderChar, borderThickness);

            // Dolna ramka
            for (int i = 0; i < borderThickness; i++)
                Console.WriteLine(new string(borderChar, totalWidth));
        }

        static void PrintCentered(string text, int innerWidth, char borderChar, int borderThickness)
        {
            int spaces = innerWidth - (text.Length);
            int left = spaces / 2;
            int right = spaces - left;
            Console.WriteLine(
                new string(borderChar, borderThickness)
                + new string(' ', left) + text + new string(' ', right)
                + new string(borderChar, borderThickness)
            );
        }
    }
}
