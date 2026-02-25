using System;

namespace QuadraticSolver
{
    class Program
    {
        static void Main()
        {
            Console.WriteLine("Rozwiązywanie równania kwadratowego ax^2 + bx + c = 0");

            double a = GetCoefficient("a");
            double b = GetCoefficient("b");
            double c = GetCoefficient("c");

            double[] solutions = SolveQuadratic(a, b, c);

            DisplaySolutions(solutions);
        }

        static double GetCoefficient(string name) 
        {
            while (true) {
                Console.Write($"Podaj współczynnik {name}: ");
                string s = Console.ReadLine();

                if (s is null) continue;
                if (double.TryParse(s.Replace(',', '.'), System.Globalization.NumberStyles.Float, System.Globalization.CultureInfo.InvariantCulture, out double value))
                    return value;
                if (double.TryParse(s, out value))
                    return value;
                Console.WriteLine("Błąd: wpisz poprawną liczbę rzeczywistą.");
            }
        }

        static double[] SolveQuadratic(double a, double b, double c)
        {
            const double eps = 1e-12; // tolerancja porównania z zerem

            // przypadek a == 0 -> równanie liniowe bx + c = 0
            if (Math.Abs(a) < eps)
            {
                if (Math.Abs(b) < eps) {
                    if (Math.Abs(c) < eps)
                        return null; // nieskończenie wiele rozwiązań
                    else
                        return new double[0]; // brak rozwiązań
                }
                else {
                    double x = -c / b;
                    return new double[] { x };
                }
            }

            double discriminant = b * b - 4 * a * c;

            if (discriminant < -eps) {
                return new double[0];
            }
            else if (Math.Abs(discriminant) <= eps) {
                double x = -b / (2 * a);
                return new double[] { x };
            }
            else {
                double sqrt = Math.Sqrt(discriminant);
                double x1 = (-b - sqrt) / (2 * a);
                double x2 = (-b + sqrt) / (2 * a);
                return new double[] { x1, x2 };
            }
        }

        static void DisplaySolutions(double[] solutions)
        {
            if (solutions == null) {
                Console.WriteLine("Nieskończenie wiele rozwiązań (równanie tożsamościowe).");
            }
            else if (solutions.Length == 0) {
                Console.WriteLine("Brak rzeczywistych rozwiązań.");
            }
            else if (solutions.Length == 1) {
                Console.WriteLine("Jedno rozwiązanie: x = {0}", solutions[0]);
            }
            else {
                Console.WriteLine($"Dwa rozwiązania: x1 = {solutions[0]}, x2 = {solutions[1]}");
            }
        }
    }
}
