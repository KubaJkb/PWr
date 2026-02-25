using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace lista_7.Models
{
    // para StudentId - TopicId (reprezentacja n:n)
    public class StudentToTopic
    {
        public int StudentId { get; set; }
        public int TopicId { get; set; }
        public StudentToTopic(int studentId, int topicId)
        {
            StudentId = studentId;
            TopicId = topicId;
        }
        public override string ToString() => $"{StudentId} - {TopicId}";
    }
}
