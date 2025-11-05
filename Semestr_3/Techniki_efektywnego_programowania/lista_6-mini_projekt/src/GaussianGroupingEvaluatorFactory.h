#ifndef GAUSSIAN_GROUPING_EVALUATOR_FACTORY_H
#define GAUSSIAN_GROUPING_EVALUATOR_FACTORY_H

#include "GroupingEvaluator.h"
#include "Point.h"

#include <random>
#include <vector>

using namespace std;

namespace GroupingChallenge {

	class Dimension {
	public:
		Dimension(double meanMin, double meanMax, double standardDeviationMin, double standardDeviationMax, mt19937& randomEngine);

		double generateRandomMean();
		double generateRandomStandardDeviation();

	private:
		const double DEFAULT_STANDARD_DEVIATION_VALUE = 1.0;

		uniform_real_distribution<double> mean_uniform_distribution;
		uniform_real_distribution<double> standard_deviation_uniform_distribution;
		mt19937& random_engine;
	};

	class UnivariateGaussianDistribution {
	public:
		UnivariateGaussianDistribution(double mean, double standardDeviation, mt19937& randomEngine);

		double generateRandomNumber();

	private:
		normal_distribution<double> normal_distribution;
		mt19937& random_engine;
	};

	class MultivariateGaussianDistribution {
	public:
		MultivariateGaussianDistribution(vector<Dimension>& dimensions, mt19937& randomEngine);

		Point generateRandomPoint();

	private:
		vector<UnivariateGaussianDistribution> univariate_gaussian_distributions;
	};

	class GaussianGroupingEvaluatorFactory {
	public:
		GaussianGroupingEvaluatorFactory(int numberOfGroups, int numberOfPoints, int numberOfMultivariateGaussianDistributions);

		GaussianGroupingEvaluatorFactory& addDimension(double meanMin, double meanMax, double standardDeviationMin, double standardDeviationMax);

		GroupingEvaluator* createEvaluator();
		GroupingEvaluator* createEvaluator(unsigned int seed);

	private:
		const int NUMBER_OF_GROUPS_MIN_VALUE = 1;
		const int NUMBER_OF_POINTS_MIN_VALUE = 1;
		const int NUMBER_OF_MULTIVARIATE_GAUSSIAN_DISTRIBUTIONS_MIN_VALUE = 1;

		vector<MultivariateGaussianDistribution>* create_multivariate_gaussian_distributions();
		vector<Point>* create_points(vector<MultivariateGaussianDistribution>& multivariateGaussianDistributions);

		int number_of_groups;
		int number_of_points;
		int number_of_multivariate_gaussian_distributions;

		vector<Dimension> dimensions;
		mt19937 random_engine;
	};
}

#endif