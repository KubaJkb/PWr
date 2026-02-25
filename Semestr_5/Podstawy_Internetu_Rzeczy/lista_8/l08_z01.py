from config import *
import RPi.GPIO as GPIO
import time

current_led = led1
duty_cycle = 50
pwm_object = None
encoder_state = 0

led_map = {
    1: led1,
    2: led2,
    3: led3,
    4: led4
}
current_led_number = 1

def initialize_pwm(led_pin):
    global pwm_object
    
    if pwm_object is not None:
        pwm_object.stop()
    
    pwm_object = GPIO.PWM(led_pin, 50)
    pwm_object.start(duty_cycle)
    print(f"LED {current_led_number} wybrana, jasność: {duty_cycle}%")

def encoder_callback(channel):
    global duty_cycle, encoder_state
    
    left_state = GPIO.input(encoderLeft)
    right_state = GPIO.input(encoderRight)
    
    if channel == encoderLeft:
        if left_state == GPIO.LOW:
            if right_state == GPIO.HIGH:
                duty_cycle = min(100, duty_cycle + 5)
            else:
                duty_cycle = max(0, duty_cycle - 5)
    
    if pwm_object is not None:
        pwm_object.ChangeDutyCycle(duty_cycle)
        print(f"LED {current_led_number} - jasność: {duty_cycle}%")

def red_button_callback(channel):
    global current_led, current_led_number
    
    current_led_number -= 1
    if current_led_number < 1:
        current_led_number = 4
    
    current_led = led_map[current_led_number]
    initialize_pwm(current_led)

def green_button_callback(channel):
    global current_led, current_led_number
    
    current_led_number += 1
    if current_led_number > 4:
        current_led_number = 1
    
    current_led = led_map[current_led_number]
    initialize_pwm(current_led)

def main():
    global pwm_object
    
    initialize_pwm(current_led)
    
    GPIO.add_event_detect(encoderLeft, GPIO.FALLING, 
                         callback=encoder_callback, 
                         bouncetime=10)
    GPIO.add_event_detect(encoderRight, GPIO.FALLING, 
                         callback=encoder_callback, 
                         bouncetime=10)
    
    GPIO.add_event_detect(buttonRed, GPIO.FALLING,
                         callback=red_button_callback,
                         bouncetime=200)
    GPIO.add_event_detect(buttonGreen, GPIO.FALLING,
                         callback=green_button_callback,
                         bouncetime=200)
    
    try:
        while True:
            time.sleep(0.1)
    
    except KeyboardInterrupt:
        print("\n\nZamykanie programu...")
    
    finally:
        if pwm_object is not None:
            pwm_object.stop()
        GPIO.cleanup()
        print("Program zakończony\n")

if __name__ == "__main__":
    main()
