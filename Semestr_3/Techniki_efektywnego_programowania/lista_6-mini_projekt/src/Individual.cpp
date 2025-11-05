#include "Individual.h"

Individual::Individual(int size, mt19937& rng, int lowerBound, int upperBound)
: fitness(numeric_limits<double>::max()) {
    
    uniform_int_distribution<int> distribution(lowerBound, upperBound);
    
    genotype.resize(size);
    
    for (int& gene : genotype) {
        gene = distribution(rng);
    }
}

double Individual::calculateFitness(GroupingEvaluator& evaluator) {
    fitness = evaluator.evaluate(genotype);
    return fitness;
}

pair<Individual, Individual> Individual::crossover(double crossoverProbability, const Individual& parent1, const Individual& parent2, mt19937& rng) {
    size_t genotypeSize = parent1.genotype.size();
    uniform_int_distribution<size_t> pointDist(1, genotypeSize - 1);
    size_t crossoverPoint = pointDist(rng);

    uniform_real_distribution<double> probDist(0.0, 1.0);
    double randomValue = probDist(rng);

    Individual child1 = parent1;
    Individual child2 = parent2;

    if (randomValue < crossoverProbability) {
        for (size_t i = crossoverPoint; i < genotypeSize; ++i) {
            swap(child1.genotype[i], child2.genotype[i]);
        }
    }

    return { child1, child2 };
}

vector<Individual> Individual::modCrossover(double crossoverProbability, const Individual& parent1, const Individual& parent2, mt19937& rng) {
    uniform_real_distribution<double> probDist(0.0, 1.0);
    double randomValue = probDist(rng);

    if (randomValue >= crossoverProbability) {
        return { parent1, parent2 };
    }

    size_t genotypeSize = parent1.genotype.size();
    vector<Individual> children(5, parent1);

    uniform_int_distribution<int> parentChooser(0, 1);

    for (Individual& child : children) {
        for (size_t i = 0; i < genotypeSize; ++i) {
            if (parentChooser(rng) == 0) {
                child.genotype[i] = parent1.genotype[i];
            }
            else {
                child.genotype[i] = parent2.genotype[i];
            }
        }
    }

    return children;
}


void Individual::mutate(double mutationProbability, mt19937& rng, int lowerBound, int upperBound) {
    uniform_real_distribution<double> probabilityDist(0.0, 1.0);
    uniform_int_distribution<int> geneDist(lowerBound, upperBound);

    //for (size_t i = 0; i < genotype.size(); i++) { // Iteracja po indeksach
    //    if (probabilityDist(rng) < mutationProbability) {
    //        genotype[i] = geneDist(rng); // Modyfikacja genu przez indeks
    //    }
    //}

    for (int& gene : genotype) {
       if (probabilityDist(rng) < mutationProbability) {
           gene = geneDist(rng);
       }
    }
}

const vector<int>& Individual::getGenotype() const {
    return genotype;
}

double Individual::getFitness() const {
    return fitness;
}
