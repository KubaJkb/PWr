#ifndef POINT_H
#define POINT_H

#include <cmath>
#include <vector>

using namespace std;

namespace GroupingChallenge {

	class Point {
	public:
		void addCoordinate(double coordinate);
		double calculateDistance(const Point& other) const;

	private:
		const double WRONG_DISTANCE_VALUE = -1;

		vector<double> coordinates;
	};
}

#endif