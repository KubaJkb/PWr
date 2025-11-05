#ifndef OPTIMIZER_H
#define OPTIMIZER_H

#include "GroupingEvaluator.h"

#include <iostream>
#include <numeric>
#include <random>
#include <vector>

using namespace std;

namespace GroupingChallenge {

	class Optimizer {
	public:
		Optimizer(GroupingEvaluator& evaluator);

		void initialize();
		void runIteration();

		vector<int>* getCurrentBest() { return &current_best; }

	private:
		GroupingEvaluator& evaluator;

		double current_best_fitness;
		vector<int> current_best;

		mt19937 random_engine;
	};
}

#endif