#include "Optimizer.h"

using namespace GroupingChallenge;

Optimizer::Optimizer(GroupingEvaluator& evaluator)
	: evaluator(evaluator) {
	random_device seed_generator;
	random_engine.seed(seed_generator());
}

void Optimizer::initialize() {
	numeric_limits<double> double_limits;
	current_best_fitness = double_limits.max();

	current_best.clear();
	current_best.resize(evaluator.getNumberOfPoints());
}

void Optimizer::runIteration() {
	vector<int> candidate(evaluator.getNumberOfPoints());

	uniform_int_distribution<int> candidate_distribution(evaluator.getLowerBound(), evaluator.getUpperBound());

	for (size_t i = 0; i < candidate.size(); i++) {
		candidate[i] = candidate_distribution(random_engine);
	}

	double candidate_fitness = evaluator.evaluate(candidate);

	if (candidate_fitness < current_best_fitness) {
		current_best = candidate;
		current_best_fitness = candidate_fitness;
	}

	cout << current_best_fitness << endl;
}