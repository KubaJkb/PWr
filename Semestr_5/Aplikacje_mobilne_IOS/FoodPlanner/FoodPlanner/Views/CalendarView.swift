import SwiftUI

struct CalendarView: View {
    @EnvironmentObject var calendarVM: CalendarViewModel
    @EnvironmentObject var mealsVM: MealsViewModel
    
    @State private var showSelectMealSheet: Bool = false

    var body: some View {
        NavigationStack {
            VStack(spacing: 0) {
                DatePicker(
                    "Wybierz datę",
                    selection: $calendarVM.selectedDate,
                    displayedComponents: .date
                )
                .datePickerStyle(.graphical)
                .padding()
                .background(Color(uiColor: .secondarySystemBackground))
                
                List {
                    Section {
                        let meals = calendarVM.meals(for: calendarVM.selectedDate)
                        
                        if meals.isEmpty {
                            ContentUnavailableView(
                                "Brak posiłków",
                                systemImage: "fork.knife.circle",
                                description: Text("Nie zaplanowano jeszcze nic na ten dzień.")
                            )
                        } else {
                            ForEach(meals) { meal in
                                NavigationLink(value: meal) {
                                    SimpleMealRow(meal: meal)
                                }
                            }
                        }
                    } header: {
                        HStack {
                            Text("Posiłki")
                            Spacer()
                            Text("\(calendarVM.dailyCalories) kcal")
                                .foregroundStyle(.blue)
                        }
                    }

                    Section {
                        Button {
                            showSelectMealSheet = true
                        } label: {
                            HStack {
                                Image(systemName: "plus.circle.fill")
                                    .foregroundStyle(.blue)
                                Text("Dodaj posiłek do tego dnia")
                                    .foregroundStyle(.primary)
                            }
                        }
                    }
                }
                .listStyle(.insetGrouped)
            }
            .navigationTitle("Planer")
            .toolbar {
                ToolbarItem(placement: .primaryAction) {
                    Button {
                        showSelectMealSheet = true
                    } label: {
                        Image(systemName: "plus")
                    }
                }
            }
            .navigationDestination(for: Meal.self) { meal in
                MealDetailView(meal: meal)
            }
            .sheet(isPresented: $showSelectMealSheet) {
                MealSelectionSheet()
            }
        }
    }
}
