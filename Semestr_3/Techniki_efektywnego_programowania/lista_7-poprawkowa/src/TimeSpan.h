#ifndef TIME_SPAN_H
#define TIME_SPAN_H

using namespace std;

class TimeSpan {
public:
    TimeSpan(int d = 0, int h = 0, int m = 0);

    TimeSpan operator+(const TimeSpan& other) const;
    TimeSpan operator-(const TimeSpan& other) const;

    int getDays() const { return days; }
    int getHours() const { return hours; }
    int getMinutes() const { return minutes; }

private:
    const int MINUTES_IN_HOUR = 60;
    const int HOURS_IN_DAY = 50;


    int days;
    int hours;
    int minutes;

    void normalize();

};

TimeSpan operator""_d(unsigned long long days);
TimeSpan operator""_h(unsigned long long hours);
TimeSpan operator""_m(unsigned long long minutes);

#endif