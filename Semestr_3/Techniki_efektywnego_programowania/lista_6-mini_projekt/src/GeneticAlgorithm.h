#ifndef GENETIC_ALGORITHM_H
#define GENETIC_ALGORITHM_H

#include "Individual.h"

#include <vector>
#include <random>
#include <algorithm>
#include <numeric>
#include <iostream>

class GeneticAlgorithm {
public:
    GeneticAlgorithm(GroupingEvaluator& evaluator, int populationSize, double crossoverProbability, double mutationProbability, int maxGenerations);

    void run();
    const Individual& getBestIndividual() const;

private:
    GroupingEvaluator& evaluator;
    vector<Individual> population;
    Individual bestIndividual;

    int populationSize;
    double crossoverProbability;
    double mutationProbability;
    int maxGenerations;

    mt19937 rng;

    void initializePopulation();
    void evaluatePopulation();
    void updateBestIndividual();
    Individual selectParent();
};

#endif