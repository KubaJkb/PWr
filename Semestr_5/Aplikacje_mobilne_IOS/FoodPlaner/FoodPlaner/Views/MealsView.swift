import SwiftUI

struct MealsView: View {
    @EnvironmentObject var viewModel: MealsViewModel
    @State private var showAddSheet: Bool = false
    @State private var mealToAssign: Meal? = nil

    var body: some View {
        NavigationStack {
            List {
                ForEach(viewModel.meals) { meal in
                    NavigationLink(value: meal) {
                        SimpleMealRow(meal: meal)
                            // SWIPE W PRAWO (Leading) -> Przypisywanie
                            .swipeActions(edge: .leading, allowsFullSwipe: true) {
                                Button {
                                    mealToAssign = meal
                                } label: {
                                    Label("Przypisz", systemImage: "calendar.badge.plus")
                                }
                                .tint(.green)
                            }
                            // SWIPE W LEWO (Trailing) -> Usuwanie
                            .swipeActions(edge: .trailing, allowsFullSwipe: true) {
                                Button(role: .destructive) {
                                    viewModel.removeMeal(meal)
                                } label: {
                                    Label("Usuń", systemImage: "trash")
                                }
                            }
                            // Menu kontekstowe (przytrzymanie palca)
                            .contextMenu {
                                Button {
                                    mealToAssign = meal
                                } label: {
                                    Label("Przypisz do dnia...", systemImage: "calendar.badge.plus")
                                }
                                
                                Button(role: .destructive) {
                                    viewModel.removeMeal(meal)
                                } label: {
                                    Label("Usuń", systemImage: "trash")
                                }
                            }
                    }
                }
            }
            .listStyle(.plain)
            .navigationTitle("Baza Posiłków")
            .toolbar {
                ToolbarItem(placement: .primaryAction) {
                    Button {
                        showAddSheet = true
                    } label: {
                        Image(systemName: "plus")
                    }
                }
            }
            .navigationDestination(for: Meal.self) { meal in
                MealDetailView(meal: meal)
            }
            .sheet(isPresented: $showAddSheet) {
                AddMealSheet { newMeal in
                    viewModel.addMeal(newMeal)
                }
            }
            .sheet(item: $mealToAssign) { meal in
                MealAssignmentSheet(meal: meal)
            }
        }
    }
}
