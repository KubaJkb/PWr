using Microsoft.AspNetCore.Mvc;
using System;

namespace lista_8.Controllers
{
    public class GameController : Controller
    {
        private static int _min = 0;           
        private static int _max = 100;        
        private static int _randValue = 0;
        private static int _counter = 0;
        private static Random _random = new Random();

        // 1. Zmiana zakresu
        [Route("Set,{min},{max}")]
        public IActionResult Set(int min, int max)
        {
            if (min > max)
            {
                _min = max;
                _max = min;
            }
            else
            {
                _min = min;
                _max = max;
            }

            _counter = 0;

            ViewBag.Message = $"Ustawiono nowy zakres losowania: {_min} - {_max}.";
            ViewBag.SubMessage = "Użyj /Draw, aby wylosować nową liczbę w tym zakresie.";
            ViewBag.CssClass = "game-info";

            return View("Index");
        }

        // 2. Losowanie
        [Route("Draw")]
        public IActionResult Draw()
        {
            _randValue = _random.Next(_min, _max + 1);
            _counter = 0;

            ViewBag.Message = "Wylosowano nową liczbę!";
            ViewBag.SubMessage = $"Zakres: {_min} - {_max}. Licznik prób wyzerowany. Zacznij zgadywać używając /Guess,X";
            ViewBag.CssClass = "game-draw";

            return View("Index");
        }

        // 3. Zgadywanie
        [Route("Guess,{guess}")]
        public IActionResult Guess(int guess)
        {
            _counter++;
            string message = "";
            string cssClass = "";

            if (guess < _randValue)
            {
                message = "Za mało!";
                cssClass = "game-low";
            }
            else if (guess > _randValue)
            {
                message = "Za dużo!";
                cssClass = "game-high";
            }
            else
            {
                message = "Gratulacje! Zgadłeś!";
                cssClass = "game-correct";
            }

            ViewBag.Message = message;
            ViewBag.SubMessage = $"To jest twoja próba numer: {_counter}. (Aktualny zakres: {_min}-{_max})";
            ViewBag.CssClass = cssClass;
            ViewBag.UserGuess = guess;

            return View("Index");
        }
    }
}