using System.ComponentModel.DataAnnotations;

namespace WebStore.Models
{
    public class CartItem
    {
        public Article Article { get; set; }
        public int Quantity { get; set; }
        public decimal Value => Article.Price * Quantity;
    }
}