using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;
using WebStore.Models;

namespace WebStore.Data
{
    public class StoreDbContext : IdentityDbContext
    {
        public StoreDbContext(DbContextOptions<StoreDbContext> options) : base(options)
        {
        }

        public DbSet<Article> Articles { get; set; }
        public DbSet<Category> Categories { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);

            modelBuilder.Entity<Category>().HasData(
                new Category { Id = 1, Name = "Elektronika" },
                new Category { Id = 2, Name = "Spożywcze" },
                new Category { Id = 3, Name = "Odzież" },
                new Category { Id = 4, Name = "Chemia" },
                new Category { Id = 5, Name = "Inne" }
            );

            var articles = new List<Article>();


            articles.Add(new Article { Id = 1, CategoryId = 1, Name = "Laptop Gamingowy", Price = 4500.00M, ExpirationDate = DateTime.Now.AddYears(2), ImageName = "laptop.jpg" });
            articles.Add(new Article { Id = 2, CategoryId = 1, Name = "Mysz Bezprzewodowa", Price = 120.00M, ExpirationDate = DateTime.Now.AddYears(3), ImageName = "mysz.jpg" });
            articles.Add(new Article { Id = 3, CategoryId = 1, Name = "Monitor 24 cale", Price = 650.00M, ExpirationDate = DateTime.Now.AddYears(5), ImageName = "monitor.jpg" });
            articles.Add(new Article { Id = 4, CategoryId = 1, Name = "Kabel HDMI", Price = 25.00M, ExpirationDate = DateTime.Now.AddYears(10), ImageName = "hdmi.jpg" });

            articles.Add(new Article { Id = 5, CategoryId = 2, Name = "Mleko 3.2%", Price = 3.50M, ExpirationDate = DateTime.Now.AddDays(7), ImageName = "mleko.jpg" });
            articles.Add(new Article { Id = 6, CategoryId = 2, Name = "Chleb Pełnoziarnisty", Price = 4.20M, ExpirationDate = DateTime.Now.AddDays(3), ImageName = "chleb.jpg" });
            articles.Add(new Article { Id = 7, CategoryId = 2, Name = "Kawa Ziarnista 1kg", Price = 55.00M, ExpirationDate = DateTime.Now.AddMonths(12), ImageName = "kawa.jpg" });
            articles.Add(new Article { Id = 8, CategoryId = 2, Name = "Czekolada Mleczna", Price = 5.00M, ExpirationDate = DateTime.Now.AddMonths(6), ImageName = "czekolada.jpg" });

            articles.Add(new Article { Id = 9, CategoryId = 3, Name = "Koszulka T-Shirt", Price = 45.00M, ExpirationDate = DateTime.Now.AddYears(1), ImageName = "koszulka.jpg" });
            articles.Add(new Article { Id = 10, CategoryId = 3, Name = "Jeansy Męskie", Price = 150.00M, ExpirationDate = DateTime.Now.AddYears(2), ImageName = "jeansy.jpg" });
            articles.Add(new Article { Id = 11, CategoryId = 3, Name = "Kurtka Zimowa", Price = 300.00M, ExpirationDate = DateTime.Now.AddYears(3), ImageName = "kurtka.jpg" });
            articles.Add(new Article { Id = 12, CategoryId = 3, Name = "Czapka z daszkiem", Price = 30.00M, ExpirationDate = DateTime.Now.AddYears(5), ImageName = "czapka.jpg" });

            articles.Add(new Article { Id = 13, CategoryId = 4, Name = "Płyn do naczyń", Price = 8.50M, ExpirationDate = DateTime.Now.AddYears(2), ImageName = "plyn_naczynia.jpg" });
            articles.Add(new Article { Id = 14, CategoryId = 4, Name = "Proszek do prania", Price = 45.00M, ExpirationDate = DateTime.Now.AddYears(1), ImageName = "proszek_pranie.jpg" });
            articles.Add(new Article { Id = 15, CategoryId = 4, Name = "Mydło w płynie", Price = 6.00M, ExpirationDate = DateTime.Now.AddYears(2), ImageName = "mydlo.jpg" });

            articles.Add(new Article { Id = 16, CategoryId = 5, Name = "Zestaw długopisów", Price = 12.00M, ExpirationDate = DateTime.Now.AddYears(5), ImageName = "dlugopisy.jpg" });
            articles.Add(new Article { Id = 17, CategoryId = 5, Name = "Notatnik A4", Price = 15.00M, ExpirationDate = DateTime.Now.AddYears(10), ImageName = "notatnik.jpg" });
            articles.Add(new Article { Id = 18, CategoryId = 5, Name = "Parasol", Price = 35.00M, ExpirationDate = DateTime.Now.AddYears(4), ImageName = "parasol.jpg" });

            for (int i = 19; i <= 100; i++)
            {
                int catId = (i % 5) + 1;
                articles.Add(new Article
                {
                    Id = i,
                    CategoryId = catId,
                    Name = $"Towar Testowy nr {i}",
                    Price = new decimal(new Random().NextDouble() * 100 + 10),
                    ExpirationDate = DateTime.Now.AddMonths(i),
                    ImageName = null
                });
            }

            modelBuilder.Entity<Article>().HasData(articles);
        }
    }
}