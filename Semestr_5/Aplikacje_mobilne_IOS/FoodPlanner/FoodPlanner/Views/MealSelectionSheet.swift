import SwiftUI

struct MealSelectionSheet: View {
    @EnvironmentObject var mealsVM: MealsViewModel
    @EnvironmentObject var calendarVM: CalendarViewModel
    @Environment(\.dismiss) var dismiss

    var body: some View {
        NavigationStack {
            List(mealsVM.meals) { meal in
                Button {
                    calendarVM.addMeal(meal, to: calendarVM.selectedDate)
                    dismiss()
                } label: {
                    HStack {
                        VStack(alignment: .leading) {
                            Text(meal.name).font(.headline)
                            Text("\(meal.calories) kcal").font(.caption).foregroundStyle(.secondary)
                        }
                        Spacer()
                        Image(systemName: "plus.circle.fill")
                            .foregroundStyle(.blue)
                    }
                    .padding(.vertical, 4)
                }
                .foregroundStyle(.primary)
            }
            .navigationTitle("Wybierz posiłek")
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .cancellationAction) {
                    Button("Anuluj") { dismiss() }
                }
            }
        }
    }
}
