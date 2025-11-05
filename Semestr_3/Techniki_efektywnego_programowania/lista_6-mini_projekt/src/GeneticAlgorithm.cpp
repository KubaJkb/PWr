#include "GeneticAlgorithm.h"

#include <vector>

GeneticAlgorithm::GeneticAlgorithm(GroupingEvaluator& evaluator, int populationSize, double crossoverProbability, double mutationProbability, int maxGenerations)
    : evaluator(evaluator),
    populationSize(max(0,populationSize)),
    crossoverProbability(crossoverProbability),
    mutationProbability(mutationProbability),
    maxGenerations(max(0,maxGenerations)),
    bestIndividual(0, rng, 0, 0) {
    random_device rd;
    rng.seed(rd());
}

void GeneticAlgorithm::run() {
    initializePopulation();
    evaluatePopulation();
    updateBestIndividual();

    for (int generation = 0; generation < maxGenerations; ++generation) {
        vector<Individual> newPopulation;

        while (newPopulation.size() < populationSize) {
            Individual parent1 = selectParent();
            Individual parent2 = selectParent();

            /*pair<Individual, Individual> children = Individual::crossover(crossoverProbability, parent1, parent2, rng);

            children.first.mutate(mutationProbability, rng, evaluator.getLowerBound(), evaluator.getUpperBound());
            children.second.mutate(mutationProbability, rng, evaluator.getLowerBound(), evaluator.getUpperBound());

            newPopulation.push_back(children.first);
            if (newPopulation.size() < populationSize) {
                newPopulation.push_back(children.second);
            }*/

            vector<Individual> children = Individual::modCrossover(crossoverProbability, parent1, parent2, rng);

            for (Individual& child : children) {
                child.mutate(mutationProbability, rng, evaluator.getLowerBound(), evaluator.getUpperBound());
            }

            for (Individual& child : children) {
                if (newPopulation.size() < populationSize) {
                    newPopulation.push_back(child);
                }
            }
        }

        population = move(newPopulation);
        evaluatePopulation();
        updateBestIndividual();

        // ********************* TEST ***********************
        cout << "Generation " << generation + 1 << ": Best fitness = " << bestIndividual.getFitness() << endl;
    }
}

const Individual& GeneticAlgorithm::getBestIndividual() const {
    return bestIndividual;
}

void GeneticAlgorithm::initializePopulation() {
    int genotypeSize = evaluator.getNumberOfPoints();
    int lowerBound = evaluator.getLowerBound();
    int upperBound = evaluator.getUpperBound();

    population.clear();
    for (int i = 0; i < populationSize; ++i) {
        population.emplace_back(genotypeSize, rng, lowerBound, upperBound);
    }
}

void GeneticAlgorithm::evaluatePopulation() {
    for (Individual& individual : population) {
        individual.calculateFitness(evaluator);
    }
}

void GeneticAlgorithm::updateBestIndividual() {
    for (const Individual& individual : population) {
        if (individual.getFitness() < bestIndividual.getFitness()) {
            bestIndividual = individual;
        }
    }
}

Individual GeneticAlgorithm::selectParent() {
    uniform_int_distribution<int> dist(0, populationSize - 1);

    int index1 = dist(rng);
    int index2 = dist(rng);

    Individual& candidate1 = population[index1];
    Individual& candidate2 = population[index2];

    if (candidate1.getFitness() <= candidate2.getFitness()) {
        return candidate1;
    }
    else {
        return candidate2;
    }
}