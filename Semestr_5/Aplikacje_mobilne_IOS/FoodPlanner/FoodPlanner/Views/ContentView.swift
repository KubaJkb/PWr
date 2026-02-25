import SwiftUI

struct ContentView: View {
    var body: some View {
        TabView {
            CalendarView()
                .tabItem {
                    Label("Kalendarz", systemImage: "calendar")
                }

            ShoppingListView()
                .tabItem {
                    Label("Zakupy", systemImage: "cart")
                }

            MealsView()
                .tabItem {
                    Label("Posiłki", systemImage: "fork.knife")
                }
        }
    }
}

#Preview {
    ContentView()
        .environmentObject(CalendarViewModel())
        .environmentObject(MealsViewModel())
}
