using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace WebStore.Models
{
    public class Article
    {
        public int Id { get; set; }

        [Display(Name = "Nazwa towaru")]
        [Required(ErrorMessage = "Proszę podać nazwę towaru.")]
        [StringLength(100, MinimumLength = 3, ErrorMessage = "Nazwa musi mieć od 3 do 100 znaków.")]
        public string Name { get; set; }

        [Display(Name = "Cena")]
        [Required(ErrorMessage = "Proszę podać cenę.")]
        [Range(0.01, 100000.00, ErrorMessage = "Cena musi być większa od 0.")]
        [DataType(DataType.Currency)]
        [Column(TypeName = "decimal(18, 2)")]
        public decimal Price { get; set; }

        [Display(Name = "Data ważności")]
        [DataType(DataType.Date)]
        public DateTime ExpirationDate { get; set; }

        [Display(Name = "Zdjęcie")]
        public string? ImageName { get; set; }

        [Display(Name = "Kategoria")]
        [Required(ErrorMessage = "Proszę wybrać kategorię.")]
        public int CategoryId { get; set; }

        [ForeignKey("CategoryId")]
        [Display(Name = "Kategoria")]
        public Category? Category { get; set; }
    }
}