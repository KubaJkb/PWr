using System;
using System.Collections.Generic;
using ExamplesLinq; // używamy klas z ExamplesLinq
using lista_7.Models;
using lista_7.Services;

namespace lista_7
{
    class Program
    {
        static void Main(string[] args)
        {
            // dane wejściowe — generowane przez ExamplesLinq.Generator
            var students = Generator.GenerateStudentsWithTopicsEasy();

            // 1) Grupowanie posortowanych studentów po n elementów
            Console.WriteLine("1) Grupowanie posortowanych studentów po n elementów (n=3):");
            var groups = LinqSolutions.GroupSortedStudentsIntoBatches(students, 3);
            int gi = 1;
            foreach (var g in groups)
            {
                Console.WriteLine($"Grupa {gi++}:");
                foreach (var s in g)
                    Console.WriteLine($"  {s.Name} (index={s.Index})");
            }
            Console.WriteLine();

            // 2a) Tematy posortowane wg częstości występowania
            Console.WriteLine("2a) Tematy posortowane wg częstości występowania:");
            var freq = LinqSolutions.TopicsByFrequency(students);
            foreach (var t in freq)
                Console.WriteLine($"  {t.topic} - {t.count}");
            Console.WriteLine();

            // 2b) Tematy wg płci i częstości
            Console.WriteLine("2b) Tematy posortowane wg częstości występowania, rozdzielone wg płci:");
            var byGender = LinqSolutions.TopicsByFrequencyByGender(students);
            foreach (var kv in byGender)
            {
                Console.WriteLine($"Gender: {kv.Key}");
                foreach (var (topic, count) in kv.Value)
                    Console.WriteLine($"  {topic} - {count}");
            }
            Console.WriteLine();

            // 3) Transformacja StudentWithTopics -> Student (tematy jako id) oraz lista Topic
            Console.WriteLine("3) Transformacja StudentWithTopics -> Student (topic ids) oraz lista Topic:");
            var (topics, mappedStudents) = LinqSolutions.ConvertStudentsAndGenerateTopics(students);
            Console.WriteLine("Topics:");
            topics.ForEach(t => Console.WriteLine($"  {t.Id} => {t.Name}"));
            Console.WriteLine("Mapped Students (Name => topicIds):");
            mappedStudents.ForEach(s => Console.WriteLine($"  {s.Name} => [{string.Join(", ", s.TopicIds)}]"));
            Console.WriteLine();

            // 3c) Wersja n:n (StudentToTopic)
            Console.WriteLine("3c) Reprezentacja n:n (StudentToTopic pairs):");
            var (studentsOnly, pairs) = LinqSolutions.ConvertToManyToMany(students);
            Console.WriteLine("Pary (StudentId - TopicId):");
            pairs.ForEach(p => Console.WriteLine($"  {p.StudentId} - {p.TopicId}"));
            Console.WriteLine();

            // 4) Reflection: tworzenie obiektu z nazwy i wywołanie metody z parametrami
            Console.WriteLine("4) Tworzenie obiektu Reflection.Person i wywołanie metody Show:");
            XYZReflectionDemo.Run();
            Console.WriteLine();

        }
    }
}
