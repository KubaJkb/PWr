using Microsoft.AspNetCore.Mvc;
using System;

namespace lista_8.Controllers
{
    public class ToolController : Controller
    {
        // routing atrybutów
        [Route("Tool/Solve/{a}/{b}/{c}")]
        public IActionResult Solve(double a, double b, double c)
        {
            string message = "";
            string cssClass = "";

            // 1: Równanie tożsamościowe
            if (a == 0 && b == 0 && c == 0)
            {
                message = "Równanie tożsamościowe: nieskończenie wiele rozwiązań.";
                cssClass = "result-identity";
            }
            // 2: Równanie sprzeczne
            else if (a == 0 && b == 0)
            {
                message = "Równanie sprzeczne: brak rozwiązań.";
                cssClass = "result-none";
            }
            // 3: Równanie liniowe
            else if (a == 0)
            {
                double x = -c / b;
                message = $"To jest równanie liniowe. Jedno rozwiązanie: x = {x:F2}";
                cssClass = "result-linear";
            }
            // 4: Równanie kwadratowe
            else
            {
                double delta = (b * b) - (4 * a * c);

                if (delta > 0)
                {
                    double x1 = (-b - Math.Sqrt(delta)) / (2 * a);
                    double x2 = (-b + Math.Sqrt(delta)) / (2 * a);
                    message = $"Dwa rozwiązania rzeczywiste: x1 = {x1:F2}, x2 = {x2:F2}";
                    cssClass = "result-two";
                }
                else if (delta == 0)
                {
                    double x0 = -b / (2 * a);
                    message = $"Jedno rozwiązanie rzeczywiste (podwójne): x0 = {x0:F2}";
                    cssClass = "result-one";
                }
                else
                {
                    message = "Delta ujemna: brak rozwiązań w zbiorze liczb rzeczywistych.";
                    cssClass = "result-none";
                }
            }

            ViewBag.Message = message;
            ViewBag.CssClass = cssClass;

            ViewBag.Equation = $"{a}x² + {b}x + {c} = 0";

            return View();
        }
    }
}