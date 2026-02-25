using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Http;
using System;

namespace lista_8.Controllers
{
    public class GameController : Controller
    {
        private int _min
        {
            get => HttpContext.Session.GetInt32(nameof(_min)) ?? 0;
            set => HttpContext.Session.SetInt32(nameof(_min), value);
        }

        private int _max
        {
            get => HttpContext.Session.GetInt32(nameof(_max)) ?? 100;
            set => HttpContext.Session.SetInt32(nameof(_max), value);
        }

        private int _randValue
        {
            get => HttpContext.Session.GetInt32(nameof(_randValue)) ?? 0;
            set => HttpContext.Session.SetInt32(nameof(_randValue), value);
        }

        private int _counter
        {
            get => HttpContext.Session.GetInt32(nameof(_counter)) ?? 0;
            set => HttpContext.Session.SetInt32(nameof(_counter), value);
        }

        private static Random _random = new Random();

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
            ViewBag.SessionId = HttpContext.Session.Id;

            return View("Index");
        }

        [Route("Draw")]
        public IActionResult Draw()
        {
            _randValue = _random.Next(_min, _max + 1);
            _counter = 0;

            ViewBag.Message = "Wylosowano nową liczbę!";
            ViewBag.SubMessage = $"Zakres: {_min} - {_max}. Licznik prób wyzerowany. Zacznij zgadywać używając /Guess,X";
            ViewBag.CssClass = "game-draw";
            ViewBag.SessionId = HttpContext.Session.Id;

            return View("Index");
        }

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
            ViewBag.SessionId = HttpContext.Session.Id;

            return View("Index");
        }
    }
}