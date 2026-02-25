using System;
using System.Linq;
using System.Reflection;

namespace lista_7.Services
{
    public class XYZ
    {
        public string Name { get; set; }
        public decimal Salary { get; set; }

        // konstruktor bez parametrów
        public XYZ()
        {
            Name = "NoName";
            Salary = 1000m;
        }

        // konstruktor z parametrami
        public XYZ(string name, decimal salary)
        {
            Name = name;
            Salary = salary;
        }

        // metoda z parametrami: zwraca string — będziemy ją wywoływać przez reflection
        public string DescribeAndRaise(string prefix, int percentRaise)
        {
            decimal raise = Math.Round(Salary * percentRaise / 100m, 2);
            Salary += raise;
            return $"{prefix}{Name} -> new salary = {Salary} (raised by {raise} : {percentRaise}%)";
        }

        public override string ToString()
        {
            return $"XYZ(Name={Name}, Salary={Salary})";
        }
    }

    // klasa demonstrująca tworzenie obiektów i wywołanie metody przez reflection
    public static class XYZReflectionDemo
    {
        // pomocnik: znajdź typ po nazwie (pełnej lub krótkiej) w załadowanych assembly
        private static Type FindTypeByName(string className)
        {
            var assemblies = AppDomain.CurrentDomain.GetAssemblies();
            foreach (var asm in assemblies)
            {
                Type[] types;
                try { types = asm.GetTypes(); }
                catch { continue; } 
                var t = types.FirstOrDefault(x => x.FullName == className || x.Name == className);
                if (t != null) return t;
            }
            return null;
        }

        public static void Run()
        {
            Console.WriteLine("=== XYZ reflection demo ===");

            // 1) Nazwa klasy jako string (tu podajemy pełną nazwę lub krótką)
            //    (w tym przykładzie klasa jest w namespace 'lista_7.Services')
            string classNameFull = "lista_7.Services.XYZ";
            string classNameShort = "XYZ";

            // znajdź typ
            var type = FindTypeByName(classNameFull) ?? FindTypeByName(classNameShort);
            if (type == null)
            {
                Console.WriteLine($"Nie odnaleziono typu '{classNameFull}' ani '{classNameShort}'.");
                return;
            }

            Console.WriteLine($"Znaleziono typ: {type.FullName}");

            // a) tworzymy 1-2 obiekty przez reflection (nie używamy 'new'), przechowujemy jako object

            // instance 1: konstruktor bez parametrów
            object obj1 = Activator.CreateInstance(type);
            Console.WriteLine($"Obj1 (object): {obj1} ; typ w runtime = {obj1.GetType().FullName}");

            // instance 2: konstruktor z parametrami (name, salary)
            object obj2 = Activator.CreateInstance(type, new object[] { "Alicja", 4200m });
            Console.WriteLine($"Obj2 (object): {obj2} ; typ w runtime = {obj2.GetType().FullName}");

            // b) wywołanie metody instancyjnej z parametrami przez MethodInfo.Invoke
            var method = type.GetMethod("DescribeAndRaise", new Type[] { typeof(string), typeof(int) });
            if (method == null)
            {
                Console.WriteLine("Nie znaleziono metody 'DescribeAndRaise(string,int)'.");
                return;
            }

            Console.WriteLine($"Znaleziono metodę: {method.Name} (declaring type: {method.DeclaringType.FullName})");

            object[] parameters = new object[] { "INFO: ", 10 };

            object resultObj = method.Invoke(obj2, parameters);

            Console.WriteLine($"Wynik method.Invoke(...) (jako object): {resultObj} (Type: {(resultObj?.GetType().FullName ?? "null")})");

            var propSalary = type.GetProperty("Salary");
            var newSalaryValue = propSalary?.GetValue(obj2);
            Console.WriteLine($"Po wywołaniu, stan obj2: {obj2}");
            Console.WriteLine($"Wartość Salary (z reflection): {newSalaryValue}");

            object resultObj2 = method.Invoke(obj1, new object[] { "CALL: ", 5 });
            Console.WriteLine($"Wynik dla obj1: {resultObj2}");
            Console.WriteLine($"Po wywołaniu, stan obj1: {obj1}");

            Console.WriteLine("=== koniec XYZ reflection demo ===");
        }
    }
}
