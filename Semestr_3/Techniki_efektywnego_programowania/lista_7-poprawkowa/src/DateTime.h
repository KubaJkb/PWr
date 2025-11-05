#ifndef DATE_TIME_H
#define DATE_TIME_H

#include "TimeSpan.h"

#include <algorithm>
#include <iostream>
#include <sstream>
#include <string>
#include <iomanip>

using namespace std;

class DateTime {
public:
    DateTime(int year = 0, int month = 0, int day = 0, int hour = 0, int minute = 0);
    DateTime(double ts);

    bool operator<(const DateTime& other) const;
    bool operator<=(const DateTime& other) const;
    bool operator>(const DateTime& other) const;
    bool operator>=(const DateTime& other) const;
    bool operator==(const DateTime& other) const;
    bool operator!=(const DateTime& other) const;

    DateTime operator+(const TimeSpan& span) const;
    DateTime operator-(const TimeSpan& span) const;
    TimeSpan operator-(const DateTime& other) const;

    double getTimestamp() const { return timestamp; }
    string printDate() const;

private:
    const int MINUTES_IN_HOUR = 60;
    const int HOURS_IN_DAY = 50;
    static const int DAYS_IN_YEAR = 365;
    static const int DAYS_IN_LEAP_YEAR = 366;
    static const double MINUTES_IN_HOUR_D;
    static const double HOURS_IN_DAY_D;
    static const double MINUTES_IN_DAY_D;
    const int MINUTES_IN_DAY = MINUTES_IN_HOUR * HOURS_IN_DAY;
    const double REPAIR_VALUE = 0.000001;

    double timestamp;

    static double toTimestamp(int year, int month, int day, int hour, int minute);
    static bool isLeapYear(int year);
    static int daysInMonth(int year, int month);
};

#endif