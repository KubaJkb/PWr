import Foundation
import UserNotifications
import Combine

class NotificationManager: ObservableObject {
    static let shared = NotificationManager()
    
    // Unikalny identyfikator kategorii - musi być taki sam w Extension!
    let categoryIdentifier = "MEAL_REMINDER"
    
    func requestAuthorization() {
        let options: UNAuthorizationOptions = [.alert, .sound, .badge]
        UNUserNotificationCenter.current().requestAuthorization(options: options) { granted, error in
            if let error = error {
                print("Błąd autoryzacji: \(error.localizedDescription)")
            } else {
                print("Zgoda na powiadomienia: \(granted)")
                self.registerCategories()
            }
        }
    }
    
    // Rejestrujemy kategorię i przyciski akcji (np. "Zjadłem")
    private func registerCategories() {
        let eatenAction = UNNotificationAction(
            identifier: "MARK_EATEN",
            title: "Oznacz jako zjedzone",
            options: .foreground // Otworzy aplikację (można zmienić na .destructive lub bez opcji)
        )
        
        let category = UNNotificationCategory(
            identifier: categoryIdentifier,
            actions: [eatenAction],
            intentIdentifiers: [],
            options: .customDismissAction
        )
        
        UNUserNotificationCenter.current().setNotificationCategories([category])
    }
    
    func scheduleNotification(for meal: Meal, at date: Date) {
        let content = UNMutableNotificationContent()
        content.title = "Czas na posiłek!"
        content.body = "Dziś w planach: \(meal.name)"
        content.sound = .default
        content.categoryIdentifier = categoryIdentifier
        
        // PRZEKAZYWANIE DANYCH DO EXTENSION (To jest kluczowe!)
        // Przekazujemy proste typy danych w słowniku userInfo
        content.userInfo = [
            "mealName": meal.name,
            "mealCalories": meal.calories,
            "mealIngredients": meal.ingredients,
            "mealPrep": meal.preparation
        ]
        
        // Ustawiamy wyzwalacz czasowy (dla testów: za 5 sekund)
        // W produkcji użyłbyś CalendarNotificationTrigger z datą
        let trigger = UNTimeIntervalNotificationTrigger(timeInterval: 5, repeats: false)
        
        let request = UNNotificationRequest(
            identifier: UUID().uuidString,
            content: content,
            trigger: trigger
        )
        
        UNUserNotificationCenter.current().add(request)
    }
}
