#include "ButtonHandler.h"

ButtonHandler* ButtonHandler::instances[10];
uint8_t ButtonHandler::instanceCount = 0;

void PCINT0_vect_func();
void PCINT1_vect_func();
void PCINT2_vect_func();

ISR(PCINT0_vect) { PCINT0_vect_func(); }
ISR(PCINT1_vect) { PCINT1_vect_func(); }
ISR(PCINT2_vect) { PCINT2_vect_func(); }

static uint8_t lastPortB = 0;
static uint8_t lastPortC = 0;
static uint8_t lastPortD = 0;

ButtonHandler::ButtonHandler(uint8_t pin, bool activeLow)
: _pin(pin), _activeLow(activeLow),
  _debounceTime(50), _longPressTime(1000),
  _lastInterruptTime(0), _interruptFlag(false),
  _lastStableState(false), _currentPressed(false),
  _pressStartTime(0), _longPressHandled(false),
  _shortPressCallback(nullptr), _longPressCallback(nullptr)
{
    if (instanceCount < 10) {
        instances[instanceCount++] = this;
    }
}

void ButtonHandler::begin(unsigned long debounceTime, unsigned long longPressTime) {
    _debounceTime = debounceTime;
    _longPressTime = longPressTime;

    pinMode(_pin, _activeLow ? INPUT_PULLUP : INPUT);

    _lastStableState = readButtonState();
    
    lastPortB = PINB;
    lastPortC = PINC;
    lastPortD = PIND;

    if (_pin <= 7) {
        PCICR |= (1 << PCIE2);
        PCMSK2 |= (1 << _pin);
    } else if (_pin <= 13) {
        PCICR |= (1 << PCIE0);
        PCMSK0 |= (1 << (_pin - 8));
    } else if (_pin >= A0 && _pin <= A5) {
        PCICR |= (1 << PCIE1);
        PCMSK1 |= (1 << (_pin - A0));
    }
}

bool ButtonHandler::readButtonState() {
    bool raw = digitalRead(_pin);
    return _activeLow ? !raw : raw;
}

void PCINT0_vect_func() {   // PORT B
    uint8_t now = PINB;
    uint8_t changed = now ^ lastPortB;
    lastPortB = now;

    unsigned long currentTime = millis();

    for (uint8_t i = 0; i < ButtonHandler::instanceCount; i++) {
        uint8_t pin = ButtonHandler::instances[i]->_pin;
        if (pin >= 8 && pin <= 13) {
            uint8_t bit = (pin - 8);
            if (changed & (1 << bit)) {
                ButtonHandler::instances[i]->_lastInterruptTime = currentTime;
                ButtonHandler::instances[i]->_interruptFlag = true;
            }
        }
    }
}

void PCINT1_vect_func() {
    uint8_t now = PINC;
    uint8_t changed = now ^ lastPortC;
    lastPortC = now;

    unsigned long currentTime = millis();

    for (uint8_t i = 0; i < ButtonHandler::instanceCount; i++) {
        uint8_t pin = ButtonHandler::instances[i]->_pin;
        if (pin >= A0 && pin <= A5) {
            uint8_t bit = (pin - A0);
            if (changed & (1 << bit)) {
                ButtonHandler::instances[i]->_lastInterruptTime = currentTime;
                ButtonHandler::instances[i]->_interruptFlag = true;
            }
        }
    }
}

void PCINT2_vect_func() {
    uint8_t now = PIND;
    uint8_t changed = now ^ lastPortD;
    lastPortD = now;

    unsigned long currentTime = millis();

    for (uint8_t i = 0; i < ButtonHandler::instanceCount; i++) {
        uint8_t pin = ButtonHandler::instances[i]->_pin;
        if (pin <= 7) {
            uint8_t bit = pin;
            if (changed & (1 << bit)) {
                ButtonHandler::instances[i]->_lastInterruptTime = currentTime;
                ButtonHandler::instances[i]->_interruptFlag = true;
            }
        }
    }
}

void ButtonHandler::process() {
    for (uint8_t i = 0; i < instanceCount; i++) {
        instances[i]->handleButton();
    }
}

void ButtonHandler::handleButton() {
    unsigned long currentTime = millis();
    
    if (_interruptFlag) {
        if (currentTime - _lastInterruptTime >= _debounceTime) {
            _interruptFlag = false;
            
            bool currentState = readButtonState();
            
            if (currentState != _lastStableState) {
                _lastStableState = currentState;
                
                if (currentState) {
                    _currentPressed = true;
                    _pressStartTime = currentTime;
                    _longPressHandled = false;
                } else {
                    if (_currentPressed) {
                        _currentPressed = false;
                        
                        if (!_longPressHandled) {
                            if (_shortPressCallback) {
                                _shortPressCallback();
                            }
                        }
                    }
                }
            }
        }
    }
    
    if (_currentPressed && !_longPressHandled) {
        if (currentTime - _pressStartTime >= _longPressTime) {
            _longPressHandled = true;
            
            if (_longPressCallback) {
                _longPressCallback();
            }
        }
    }
}

void ButtonHandler::onShortPress(CallbackFunction cb) { _shortPressCallback = cb; }
void ButtonHandler::onLongPress(CallbackFunction cb)  { _longPressCallback = cb; }
