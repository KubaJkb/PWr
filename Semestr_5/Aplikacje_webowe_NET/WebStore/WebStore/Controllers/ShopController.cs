using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using WebStore.Data;
using WebStore.Models;

namespace WebStore.Controllers
{
    public class ShopController : Controller
    {
        private readonly StoreDbContext _context;

        public ShopController(StoreDbContext context)
        {
            _context = context;
        }

        public async Task<IActionResult> Index(int? categoryId)
        {
            ViewBag.Categories = await _context.Categories.ToListAsync();
            ViewBag.CurrentCategoryId = categoryId;

            var articlesQuery = _context.Articles.Include(a => a.Category).AsQueryable();

            if (categoryId.HasValue)
            {
                articlesQuery = articlesQuery.Where(a => a.CategoryId == categoryId);
            }

            // Ładujemy wszystkie do modelu, ale w widoku użyjemy Take(12) 
            // a reszta zostanie obsłużona przez JS i API
            return View(await articlesQuery.ToListAsync());
        }

        public async Task<IActionResult> Details(int? id)
        {
            if (id == null) return NotFound();

            var article = await _context.Articles
                .Include(a => a.Category)
                .FirstOrDefaultAsync(m => m.Id == id);

            if (article == null) return NotFound();

            return View(article);
        }

        [Authorize(Policy = "DenyAdmin")]
        public IActionResult AddToCart(int id)
        {
            string cookieName = "article" + id;
            int quantity = 1;

            if (Request.Cookies.ContainsKey(cookieName))
            {
                int.TryParse(Request.Cookies[cookieName], out quantity);
                quantity++;
            }

            CookieOptions options = new CookieOptions
            {
                Expires = DateTime.Now.AddDays(7)
            };

            Response.Cookies.Append(cookieName, quantity.ToString(), options);

            return RedirectToAction(nameof(Index));
        }

        [Authorize(Policy = "DenyAdmin")]
        public async Task<IActionResult> Cart()
        {
            var cartItems = await GetCartItemsFromCookies();
            return View(cartItems);
        }

        [Authorize(Policy = "DenyAdmin")]
        public IActionResult Increase(int id)
        {
            string cookieName = "article" + id;
            if (Request.Cookies.ContainsKey(cookieName))
            {
                if (int.TryParse(Request.Cookies[cookieName], out int quantity))
                {
                    quantity++;
                    Response.Cookies.Append(cookieName, quantity.ToString(), new CookieOptions { Expires = DateTime.Now.AddDays(7) });
                }
            }
            return RedirectToAction(nameof(Cart));
        }

        [Authorize(Policy = "DenyAdmin")]
        public IActionResult Decrease(int id)
        {
            string cookieName = "article" + id;
            if (Request.Cookies.ContainsKey(cookieName))
            {
                if (int.TryParse(Request.Cookies[cookieName], out int quantity))
                {
                    quantity--;
                    if (quantity > 0)
                    {
                        Response.Cookies.Append(cookieName, quantity.ToString(), new CookieOptions { Expires = DateTime.Now.AddDays(7) });
                    }
                    else
                    {
                        Response.Cookies.Delete(cookieName);
                    }
                }
            }
            return RedirectToAction(nameof(Cart));
        }

        [Authorize(Policy = "DenyAdmin")]
        public IActionResult Remove(int id)
        {
            string cookieName = "article" + id;
            Response.Cookies.Delete(cookieName);
            return RedirectToAction(nameof(Cart));
        }

        [Authorize]
        [Authorize(Policy = "DenyAdmin")]
        public async Task<IActionResult> Order()
        {
            var cartItems = await GetCartItemsFromCookies();
            if (!cartItems.Any())
            {
                return RedirectToAction(nameof(Index));
            }

            var model = new OrderViewModel
            {
                CartItems = cartItems
            };

            return View(model);
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        [Authorize]
        [Authorize(Policy = "DenyAdmin")]
        public async Task<IActionResult> Order(OrderViewModel model)
        {
            model.CartItems = await GetCartItemsFromCookies();

            if (ModelState.IsValid)
            {
                foreach (var item in model.CartItems)
                {
                    Response.Cookies.Delete("article" + item.Article.Id);
                }

                return View("OrderConfirmation", model);
            }

            return View(model);
        }

        private async Task<List<CartItem>> GetCartItemsFromCookies()
        {
            var cartItems = new List<CartItem>();
            var cookieKeys = Request.Cookies.Keys.Where(k => k.StartsWith("article")).ToList();
            var articleIds = new List<int>();

            foreach (var key in cookieKeys)
            {
                if (int.TryParse(key.Replace("article", ""), out int id))
                {
                    articleIds.Add(id);
                }
            }

            var articles = await _context.Articles
                .Include(a => a.Category)
                .Where(a => articleIds.Contains(a.Id))
                .ToListAsync();

            foreach (var article in articles)
            {
                string cookieName = "article" + article.Id;
                if (int.TryParse(Request.Cookies[cookieName], out int quantity))
                {
                    cartItems.Add(new CartItem
                    {
                        Article = article,
                        Quantity = quantity
                    });
                }
            }
            return cartItems;
        }
    }
}