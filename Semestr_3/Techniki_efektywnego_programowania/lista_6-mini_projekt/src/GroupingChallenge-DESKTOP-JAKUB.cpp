#include "GaussianGroupingEvaluatorFactory.h"
#include "GroupingEvaluator.h"
#include "Optimizer.h"
#include "GeneticAlgorithm.h"


using namespace GroupingChallenge;

//int main() {
//	GaussianGroupingEvaluatorFactory evaluator_factory(5, 100, 5);
//
//	evaluator_factory
//		.addDimension(-100, 100, 1.0, 1.0)
//		.addDimension(-100, 100, 1.0, 1.0)
//		.addDimension(-100, 100, 1.0, 1.0)
//		.addDimension(-100, 100, 1.0, 1.0)
//		.addDimension(-100, 100, 1.0, 1.0)
//		.addDimension(-100, 100, 1.0, 1.0)
//		.addDimension(-100, 100, 1.0, 1.0)
//		.addDimension(-100, 100, 1.0, 1.0)
//		.addDimension(-100, 100, 1.0, 1.0)
//		.addDimension(-100, 100, 1.0, 1.0);
//
//	GroupingEvaluator* evaluator = evaluator_factory.createEvaluator(0);
//
//	Optimizer optimizer(*evaluator);
//
//	optimizer.initialize();
//
//	for (int i = 0; i < 10; i++) {
//		optimizer.runIteration();
//	}
//
//	delete evaluator;
//
//	return 0;
//}

int main() {
    GaussianGroupingEvaluatorFactory evaluatorFactory(3, 50, 3);
    evaluatorFactory.addDimension(-100, 100, 1.0, 1.0);

    GroupingEvaluator* evaluator = evaluatorFactory.createEvaluator(0);

    GeneticAlgorithm geneticAlgorithm(*evaluator, 100, 0.7, 0.1, 200);
    geneticAlgorithm.run();

    const Individual& best = geneticAlgorithm.getBestIndividual();
    cout << "Best solution fitness: " << best.getFitness() << endl;
    for (int i = 0; i < best.getGenotype().size(); ++i) {
        cout << best.getGenotype()[i] << " ";
    }
    cout << endl;


    delete evaluator;
    return 0;
}