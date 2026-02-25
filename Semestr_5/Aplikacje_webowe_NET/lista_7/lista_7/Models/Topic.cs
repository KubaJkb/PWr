using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace lista_7.Models
{
    public class Topic
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public Topic(int id, string name)
        {
            Id = id;
            Name = name;
        }
        public override string ToString() => $"{Id}) {Name}";
    }
}
