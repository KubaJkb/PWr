using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.EntityFrameworkCore;
using WebStore.Data;
using WebStore.Models;

namespace WebStore.Pages.Shop
{
    public class IndexModel : PageModel
    {
        private readonly StoreDbContext _context;

        public IndexModel(StoreDbContext context)
        {
            _context = context;
        }

        public IList<Article> Articles { get; set; } = default!;
        public IList<Category> Categories { get; set; } = default!;
        public int? CurrentCategoryId { get; set; }

        public async Task OnGetAsync(int? categoryId)
        {
            Categories = await _context.Categories.ToListAsync();
            CurrentCategoryId = categoryId;

            var query = _context.Articles.Include(a => a.Category).AsQueryable();

            if (categoryId.HasValue)
            {
                query = query.Where(a => a.CategoryId == categoryId);
            }

            Articles = await query.ToListAsync();
        }
    }
}