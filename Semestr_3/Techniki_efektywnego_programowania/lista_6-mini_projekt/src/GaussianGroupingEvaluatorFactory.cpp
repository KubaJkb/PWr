#include "GaussianGroupingEvaluatorFactory.h"

using namespace GroupingChallenge;

Dimension::Dimension(double meanMin, double meanMax, double standardDeviationMin, double standardDeviationMax, mt19937& randomEngine)
	: mean_uniform_distribution(min(meanMin, meanMax), max(meanMin, meanMax)),
	standard_deviation_uniform_distribution(min(standardDeviationMin, standardDeviationMax), max(standardDeviationMin, standardDeviationMax)),
	random_engine(randomEngine)
{}

double Dimension::generateRandomMean() {
	return mean_uniform_distribution(random_engine);
}

double Dimension::generateRandomStandardDeviation() {
	double standard_deviation = standard_deviation_uniform_distribution(random_engine);

	if (standard_deviation <= 0) {
		standard_deviation = DEFAULT_STANDARD_DEVIATION_VALUE;
	}

	return standard_deviation;
}

UnivariateGaussianDistribution::UnivariateGaussianDistribution(double mean, double standardDeviation, mt19937& randomEngine)
	: normal_distribution(mean, standardDeviation), random_engine(randomEngine)
{}

double UnivariateGaussianDistribution::generateRandomNumber() {
	return normal_distribution(random_engine);
}

MultivariateGaussianDistribution::MultivariateGaussianDistribution(vector<Dimension>& dimensions, mt19937& randomEngine) {
	univariate_gaussian_distributions.reserve(dimensions.size());

	for (size_t i = 0; i < dimensions.size(); i++) {
		univariate_gaussian_distributions.push_back(UnivariateGaussianDistribution(dimensions[i].generateRandomMean(), dimensions[i].generateRandomStandardDeviation(), randomEngine));
	}
}

Point MultivariateGaussianDistribution::generateRandomPoint() {
	Point point;

	for (size_t i = 0; i < univariate_gaussian_distributions.size(); i++) {
		point.addCoordinate(univariate_gaussian_distributions[i].generateRandomNumber());
	}

	return point;
}

GaussianGroupingEvaluatorFactory::GaussianGroupingEvaluatorFactory(int numberOfGroups, int numberOfPoints, int numberOfMultivariateGaussianDistributions)
	: number_of_groups(max(numberOfGroups, NUMBER_OF_GROUPS_MIN_VALUE)),
	number_of_points(max(numberOfPoints, NUMBER_OF_GROUPS_MIN_VALUE)),
	number_of_multivariate_gaussian_distributions(max(numberOfMultivariateGaussianDistributions, NUMBER_OF_MULTIVARIATE_GAUSSIAN_DISTRIBUTIONS_MIN_VALUE))
{}

GaussianGroupingEvaluatorFactory& GaussianGroupingEvaluatorFactory::addDimension(double meanMin, double meanMax, double standardDeviationMin, double standardDeviationMax) {
	dimensions.push_back(Dimension(meanMin, meanMax, standardDeviationMin, standardDeviationMax, random_engine));

	return *this;
}

GroupingEvaluator* GaussianGroupingEvaluatorFactory::createEvaluator() {
	random_device seed_generator;

	return createEvaluator(seed_generator());
}

GroupingEvaluator* GaussianGroupingEvaluatorFactory::createEvaluator(unsigned int seed) {
	random_engine.seed(seed);

	vector<MultivariateGaussianDistribution>* multivariate_gaussian_distributions = create_multivariate_gaussian_distributions();
	vector<Point>* points = create_points(*multivariate_gaussian_distributions);

	GroupingEvaluator* evaluator = new GroupingEvaluator(number_of_groups, *points);

	delete multivariate_gaussian_distributions;
	delete points;

	return evaluator;
}

vector<MultivariateGaussianDistribution>* GaussianGroupingEvaluatorFactory::create_multivariate_gaussian_distributions() {
	vector<MultivariateGaussianDistribution>* multivariate_gaussian_distributions = new vector<MultivariateGaussianDistribution>();

	multivariate_gaussian_distributions->reserve(number_of_multivariate_gaussian_distributions);

	for (int i = 0; i < number_of_multivariate_gaussian_distributions; i++) {
		multivariate_gaussian_distributions->push_back(MultivariateGaussianDistribution(dimensions, random_engine));
	}

	return multivariate_gaussian_distributions;
}

vector<Point>* GaussianGroupingEvaluatorFactory::create_points(vector<MultivariateGaussianDistribution>& multivariateGaussianDistributions) {
	vector<Point>* points = new vector<Point>();

	points->reserve(number_of_points);

	uniform_int_distribution<size_t> multivariate_gaussian_selection_uniform_distribution(0, multivariateGaussianDistributions.size() - 1);

	for (int i = 0; i < number_of_points; i++) {
		size_t multivariate_gaussian_distribution_index = multivariate_gaussian_selection_uniform_distribution(random_engine);

		points->push_back(multivariateGaussianDistributions[multivariate_gaussian_distribution_index].generateRandomPoint());
	}

	return points;
}