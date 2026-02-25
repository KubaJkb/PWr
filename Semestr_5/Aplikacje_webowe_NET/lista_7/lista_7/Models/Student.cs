using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using System.Collections.Generic;

using ExamplesLinq;

namespace lista_7.Models
{
    public class Student
    {
        public int Id { get; set; }
        public int Index { get; set; }
        public string Name { get; set; }
        public Gender Gender { get; set; }
        public System.Collections.Generic.List<int> TopicIds { get; set; } = new System.Collections.Generic.List<int>();
    }
}

