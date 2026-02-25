#ifndef ButtonHandler_h
#define ButtonHandler_h

#include <Arduino.h>

class ButtonHandler {
public:
    typedef void (*CallbackFunction)();

    ButtonHandler(uint8_t pin, bool activeLow = true);
    void begin(unsigned long debounceTime = 50, unsigned long longPressTime = 1000);

    void onShortPress(CallbackFunction callback);
    void onLongPress(CallbackFunction callback);

    static void process();

private:
    uint8_t _pin;
    bool _activeLow;
    unsigned long _debounceTime;
    unsigned long _longPressTime;
    
    volatile unsigned long _lastInterruptTime;
    volatile bool _interruptFlag;
    
    bool _lastStableState;
    bool _currentPressed;
    unsigned long _pressStartTime;
    bool _longPressHandled;

    CallbackFunction _shortPressCallback;
    CallbackFunction _longPressCallback;

    void handleButton();
    bool readButtonState();

    static ButtonHandler* instances[10];
    static uint8_t instanceCount;

    friend void PCINT0_vect_func();
    friend void PCINT1_vect_func();
    friend void PCINT2_vect_func();
};

#endif