using System.ComponentModel.DataAnnotations;

namespace WebStore.Models
{
    public class OrderViewModel
    {
        public List<CartItem> CartItems { get; set; } = new List<CartItem>();
        public decimal Total => CartItems.Sum(i => i.Value);

        [Required(ErrorMessage = "Imię i nazwisko jest wymagane")]
        [Display(Name = "Imię i Nazwisko")]
        public string FullName { get; set; }

        [Required(ErrorMessage = "Adres jest wymagany")]
        [Display(Name = "Adres dostawy")]
        public string Address { get; set; }

        [Required(ErrorMessage = "Wybierz metodę płatności")]
        [Display(Name = "Metoda płatności")]
        public string PaymentMethod { get; set; }
    }
}