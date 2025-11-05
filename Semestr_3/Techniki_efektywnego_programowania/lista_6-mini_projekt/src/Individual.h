#ifndef INDIVIDUAL_H
#define INDIVIDUAL_H

#include "GroupingEvaluator.h"

#include <vector>
#include <random>
#include <algorithm>
#include <numeric>

using namespace std;
using namespace GroupingChallenge;

class Individual {
public:
    Individual(int size, mt19937& rng, int lowerBound, int upperBound);

    double calculateFitness(GroupingEvaluator& evaluator);
    static pair<Individual, Individual> crossover(double crossoverProbability, const Individual& parent1, const Individual& parent2, mt19937& rng);
    static vector<Individual> modCrossover(double crossoverProbability, const Individual& parent1, const Individual& parent2, mt19937& rng);
    void mutate(double mutationProbability, mt19937& rng, int lowerBound, int upperBound);

    const vector<int>& getGenotype() const;
    double getFitness() const;

private:
    vector<int> genotype;
    double fitness;
};

#endif