using System;
using System.Collections.Generic;
using System.Linq;
using ExamplesLinq;
using lista_7.Models;

namespace lista_7.Services
{
    public static class LinqSolutions
    {
        // 1) Grupowanie posortowanych studentów (Name, then Index) w grupy po n elementów
        public static List<List<StudentWithTopics>> GroupSortedStudentsIntoBatches(List<StudentWithTopics> students, int n)
        {
            if (n <= 0) throw new ArgumentException("n must be > 0", nameof(n));
            var sorted = students.OrderBy(s => s.Name).ThenBy(s => s.Index).ToList();
            var grouped = sorted
                .Select((s, idx) => new { s, idx })
                .GroupBy(x => x.idx / n)
                .Select(g => g.Select(x => x.s).ToList())
                .ToList();
            return grouped;
        }

        // 2a) Posortować tematy wg częstości występowania (globalnie)
        public static List<(string topic, int count)> TopicsByFrequency(List<StudentWithTopics> students)
        {
            var freq = students
                .SelectMany(s => s.Topics)
                .GroupBy(t => t)
                .Select(g => (topic: g.Key, count: g.Count()))
                .OrderByDescending(tc => tc.count)
                .ThenBy(tc => tc.topic)
                .ToList();
            return freq;
        }

        // 2b) Podział względem płci, potem sortowanie tematów wg częstości w ramach każdej płci
        public static Dictionary<Gender, List<(string topic, int count)>> TopicsByFrequencyByGender(List<StudentWithTopics> students)
        {
            var dict = students
                .GroupBy(s => s.Gender)
                .ToDictionary(
                    g => g.Key,
                    g => g.SelectMany(s => s.Topics)
                          .GroupBy(t => t)
                          .Select(tg => (topic: tg.Key, count: tg.Count()))
                          .OrderByDescending(x => x.count)
                          .ThenBy(x => x.topic)
                          .ToList()
                );
            return dict;
        }

        // 3a/3b) Stworzyć Topic (id,name) i Student (tematy jako id).
        public static (List<Topic> topics, List<Student> students) ConvertStudentsAndGenerateTopics(List<StudentWithTopics> studentsWithTopics)
        {
            // unikalne nazwy tematów
            var uniqueNames = studentsWithTopics
                .SelectMany(s => s.Topics)
                .Distinct()
                .OrderBy(n => n)
                .ToList();

            var topics = uniqueNames.Select((name, idx) => new Topic(idx + 1, name)).ToList();
            var nameToId = topics.ToDictionary(t => t.Name, t => t.Id);

            var students = studentsWithTopics.Select(s => new Student
            {
                Id = s.Id,
                Index = s.Index,
                Name = s.Name,
                Gender = s.Gender,
                TopicIds = s.Topics.Select(t => nameToId[t]).ToList()
            }).ToList();

            return (topics, students);
        }

        // 3c) Wersja n:n: lista StudentToTopic (StudentId, TopicId)
        public static (List<Student> students, List<StudentToTopic> pairs) ConvertToManyToMany(List<StudentWithTopics> studentsWithTopics)
        {
            var (topics, studentsMapped) = ConvertStudentsAndGenerateTopics(studentsWithTopics);
            var pairs = new List<StudentToTopic>();
            foreach (var s in studentsMapped)
            {
                foreach (var tid in s.TopicIds.Distinct())
                    pairs.Add(new StudentToTopic(s.Id, tid));
            }

            // studentsOnly bez wypisanych TopicIds (zgodnie z notacją n:n)
            var studentsOnly = studentsMapped
                .Select(s => new Student { Id = s.Id, Index = s.Index, Name = s.Name, Gender = s.Gender })
                .ToList();

            return (studentsOnly, pairs);
        }
    }
}
