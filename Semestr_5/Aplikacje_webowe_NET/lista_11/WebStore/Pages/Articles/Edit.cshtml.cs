using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using WebStore.Data;
using WebStore.Models;

namespace WebStore.Pages.Articles
{
    public class EditModel : PageModel
    {
        private readonly StoreDbContext _context;
        private readonly IWebHostEnvironment _hostEnvironment;

        public EditModel(StoreDbContext context, IWebHostEnvironment hostEnvironment)
        {
            _context = context;
            _hostEnvironment = hostEnvironment;
        }

        [BindProperty]
        public Article Article { get; set; } = default!;

        [BindProperty]
        public IFormFile? UploadedFile { get; set; }

        public async Task<IActionResult> OnGetAsync(int? id)
        {
            if (id == null) return NotFound();

            var article = await _context.Articles.FindAsync(id);
            if (article == null) return NotFound();

            Article = article;
            ViewData["CategoryId"] = new SelectList(_context.Categories, "Id", "Name", Article.CategoryId);
            return Page();
        }

        public async Task<IActionResult> OnPostAsync()
        {
            if (!ModelState.IsValid)
            {
                ViewData["CategoryId"] = new SelectList(_context.Categories, "Id", "Name", Article.CategoryId);
                return Page();
            }

            if (UploadedFile != null)
            {
                string uploadsFolder = Path.Combine(_hostEnvironment.WebRootPath, "images");
                if (!Directory.Exists(uploadsFolder)) Directory.CreateDirectory(uploadsFolder);

                string safeFileName = Path.GetFileName(UploadedFile.FileName).Replace("+", "_").Replace(" ", "_");
                string uniqueFileName = Guid.NewGuid().ToString() + "_" + safeFileName;
                string filePath = Path.Combine(uploadsFolder, uniqueFileName);

                using (var fileStream = new FileStream(filePath, FileMode.Create))
                {
                    await UploadedFile.CopyToAsync(fileStream);
                }
                Article.ImageName = uniqueFileName;
            }
            else
            {
                var oldArticle = await _context.Articles.AsNoTracking().FirstOrDefaultAsync(a => a.Id == Article.Id);
                if (oldArticle != null) Article.ImageName = oldArticle.ImageName;
            }

            _context.Attach(Article).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!_context.Articles.Any(e => e.Id == Article.Id)) return NotFound();
                else throw;
            }

            return RedirectToPage("./Index");
        }
    }
}