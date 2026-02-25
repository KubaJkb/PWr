import SwiftUI

@main
struct FoodPlannerApp: App {
    @StateObject private var calendarVM = CalendarViewModel()
    @StateObject private var mealsVM = MealsViewModel()
    
    init() {
        // Inicjalizacja powiadomień
        NotificationManager.shared.requestAuthorization()
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
                .environmentObject(calendarVM)
                .environmentObject(mealsVM)
        }
    }
}
