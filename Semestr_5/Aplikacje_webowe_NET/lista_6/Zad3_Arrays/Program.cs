using System;

namespace Zad3_Arrays
{
    internal class Program
    {
        static void Main(string[] args)
        {
            int[] arr = { 5, 2, 9, 1, 5, 6 };
            Console.WriteLine("Początkowa tablica: " + string.Join(", ", arr));

            // 1) Sortowanie
            Array.Sort(arr);
            Console.WriteLine("Array.Sort = " + string.Join(", ", arr));

            // 2) Odwracanie kolejności
            Array.Reverse(arr);
            Console.WriteLine("Array.Reverse = " + string.Join(", ", arr));

            // 3) Szukanie indeksu
            int idx = Array.IndexOf(arr, 5);
            Console.WriteLine($"Array.IndexOf(array, 5) = {idx}");

            // 4) Czyszczenie fragmentu
            Array.Clear(arr, 1, 2);
            Console.WriteLine("Array.Clear(array,1,2) = " + string.Join(", ", arr));

            // 5) Zmiana rozmiaru
            Array.Resize(ref arr, 8);
            Console.WriteLine("Array.Resize(ref array, 8) = " + string.Join(", ", arr));

            // 6) Kopiowanie na dany indeks
            int[] src = { 1, 2, 3 };
            int[] dst = new int[5];
            Array.Copy(src, 0, dst, 1, src.Length);
            Console.WriteLine("Array.Copy(src->dst) = " + string.Join(", ", dst));
        }
    }
}
