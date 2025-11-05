#include "Point.h"

using namespace GroupingChallenge;

void Point::addCoordinate(double coordinate) {
	coordinates.push_back(coordinate);
}

double Point::calculateDistance(const Point& other) const {
	if (coordinates.empty() || coordinates.size() != other.coordinates.size()) {
		return WRONG_DISTANCE_VALUE;
	}

	double squared_distance = 0;

	for (size_t i = 0; i < coordinates.size(); i++) {
		squared_distance += (coordinates[i] - other.coordinates[i]) * (coordinates[i] - other.coordinates[i]);
	}

	return sqrt(squared_distance);
}