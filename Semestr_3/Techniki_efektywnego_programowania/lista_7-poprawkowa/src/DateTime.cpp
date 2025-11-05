#include "DateTime.h"

const double DateTime::MINUTES_IN_HOUR_D = 60.0;
const double DateTime::HOURS_IN_DAY_D = 50.0;
const double DateTime::MINUTES_IN_DAY_D = MINUTES_IN_HOUR_D * HOURS_IN_DAY_D;

DateTime::DateTime(int year, int month, int day, int hour, int minute) {
    timestamp = toTimestamp(year, month, day, hour, minute);
}

DateTime::DateTime(double ts) : timestamp(ts) {}

double DateTime::toTimestamp(int year, int month, int day, int hour, int minute) {
    double totalDays = 0;
    for (int y = 1; y < year; ++y) {
        totalDays += isLeapYear(y) ? DAYS_IN_LEAP_YEAR : DAYS_IN_YEAR;
    }
    for (int m = 1; m < month; ++m) {
        totalDays += daysInMonth(year, m);
    }
    totalDays += day - 1;
    return totalDays + (hour / HOURS_IN_DAY_D) + (minute / MINUTES_IN_HOUR_D / HOURS_IN_DAY_D);
}

bool DateTime::isLeapYear(int year) {
    return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
}

int DateTime::daysInMonth(int year, int month) {
    const int daysPerMonth[] = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
    
    if (month == 2 && isLeapYear(year)) return 29;
    return daysPerMonth[month - 1];
}

string DateTime::printDate() const {
    int year, month, day, hour, minute;
    double ts = timestamp;

    int totalDays = static_cast<int>(ts);
    double fractionalDay = ts - totalDays;

    hour = static_cast<int>(fractionalDay * HOURS_IN_DAY);
    minute = static_cast<int>((fractionalDay * HOURS_IN_DAY - hour) * MINUTES_IN_HOUR);

    year = 1;
    while (true) {
        int daysInYear = isLeapYear(year) ? DAYS_IN_LEAP_YEAR : DAYS_IN_YEAR;
        if (totalDays < daysInYear) break;
        totalDays -= daysInYear;
        year++;
    }

    month = 1;
    while (true) {
        int daysInCurrentMonth = daysInMonth(year, month);
        if (totalDays < daysInCurrentMonth) break;
        totalDays -= daysInCurrentMonth;
        month++;
    }

    day = totalDays + 1;

    ostringstream oss;
    oss << setfill('0') << setw(2) << hour << ":"
        << setfill('0') << setw(2) << minute << " "
        << setfill('0') << setw(2) << day << "."
        << setfill('0') << setw(2) << month << "."
        << year << "r.";

    return oss.str();
}

bool DateTime::operator<(const DateTime& other) const { return timestamp < other.timestamp; }
bool DateTime::operator<=(const DateTime& other) const { return timestamp <= other.timestamp; }
bool DateTime::operator>(const DateTime& other) const { return timestamp > other.timestamp; }
bool DateTime::operator>=(const DateTime& other) const { return timestamp >= other.timestamp; }
bool DateTime::operator==(const DateTime& other) const { return timestamp == other.timestamp; }
bool DateTime::operator!=(const DateTime& other) const { return timestamp != other.timestamp; }

DateTime DateTime::operator+(const TimeSpan& span) const {
    double newTimestamp = timestamp + span.getDays() + span.getHours() / HOURS_IN_DAY_D + span.getMinutes() / MINUTES_IN_DAY + REPAIR_VALUE / MINUTES_IN_DAY;
    
    return DateTime(newTimestamp);
}

DateTime DateTime::operator-(const TimeSpan& span) const {
    double newTimestamp = timestamp - span.getDays() - span.getHours() / HOURS_IN_DAY_D - span.getMinutes() / MINUTES_IN_DAY + REPAIR_VALUE / MINUTES_IN_DAY;
    
    return DateTime(max(0.0, newTimestamp));
}

TimeSpan DateTime::operator-(const DateTime& other) const {
    double diff = abs(timestamp - other.timestamp) + REPAIR_VALUE;
    
    int days = static_cast<int>(diff);
    int minutes = static_cast<int>((diff - days) * MINUTES_IN_DAY);
    return TimeSpan(days, 0, minutes);
}