using System.ComponentModel.DataAnnotations;

namespace WebStore.Models
{
    public class Category
    {
        [Key]
        public int Id { get; set; }

        [Required(ErrorMessage = "Proszę podać nazwę kategorii.")]
        [Display(Name = "Nazwa kategorii")]
        [MaxLength(50)]
        public string Name { get; set; }

        public ICollection<Article>? Articles { get; set; }
    }
}