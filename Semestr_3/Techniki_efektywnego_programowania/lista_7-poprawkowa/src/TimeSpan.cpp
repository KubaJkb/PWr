#include "TimeSpan.h"

TimeSpan::TimeSpan(int d, int h, int m) : 
    days(d), 
    hours(h), 
    minutes(m) {
    normalize();
}

void TimeSpan::normalize() {    
    if (minutes < 0) {
        hours -= 1;
        minutes += MINUTES_IN_HOUR;
    }
    else {
        hours += minutes / MINUTES_IN_HOUR;
        minutes %= MINUTES_IN_HOUR;
    }
    
    if (hours < 0) {
        days -= 1;
        hours += HOURS_IN_DAY;
    }
    else {
        days += hours / HOURS_IN_DAY;
        hours %= HOURS_IN_DAY;
    }

    if (days < 0) {
        days = 0;
        hours = 0; 
        minutes = 0;
    }

}

TimeSpan TimeSpan::operator+(const TimeSpan& other) const {
    return TimeSpan(days + other.days, hours + other.hours, minutes + other.minutes);
}

TimeSpan TimeSpan::operator-(const TimeSpan& other) const {
    return TimeSpan(days - other.days, hours - other.hours, minutes - other.minutes);
}


TimeSpan operator""_d(unsigned long long days) {
    return TimeSpan(days, 0, 0);
}

TimeSpan operator""_h(unsigned long long hours) {
    return TimeSpan(0, hours, 0);
}

TimeSpan operator""_m(unsigned long long minutes) {
    return TimeSpan(0, 0, minutes);
}
