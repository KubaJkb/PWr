import SwiftUI

struct MealDetailView: View {
    let meal: Meal
    
    // Stan do sterowania widocznością arkusza przypisywania
    @State private var showAssignSheet = false

    var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 20) {
                // Nagłówek: Nazwa i Kalorie
                VStack(alignment: .leading, spacing: 8) {
                    Text(meal.name)
                        .font(.largeTitle)
                        .bold()
                    
                    HStack {
                        Image(systemName: "flame.fill")
                            .foregroundStyle(.orange)
                        Text("\(meal.calories) kcal")
                            .font(.headline)
                            .foregroundStyle(.secondary)
                    }
                }
                .padding(.bottom, 10)

                // Sekcja: Opis
                Text("Opis")
                    .font(.title3).bold()
                Text(meal.description)
                    .font(.body)
                
                Divider()

                // Sekcja: Składniki
                Text("Składniki")
                    .font(.title3).bold()
                VStack(alignment: .leading, spacing: 6) {
                    ForEach(meal.ingredients, id: \.self) { ingredient in
                        HStack(alignment: .top) {
                            Image(systemName: "circle.fill")
                                .font(.system(size: 6))
                                .padding(.top, 8)
                            Text(ingredient)
                        }
                        .foregroundStyle(.secondary)
                    }
                }

                Divider()

                // Sekcja: Przygotowanie
                Text("Przygotowanie")
                    .font(.title3).bold()
                Text(meal.preparation)
                    .font(.body)
                    .frame(maxWidth: .infinity, alignment: .leading)
                
                Divider()
                
                // Sekcja: Powiadomienia
                Button {
                    NotificationManager.shared.scheduleNotification(for: meal, at: Date())
                } label: {
                    HStack {
                        Image(systemName: "bell.badge.fill")
                        Text("Przypomnij o posiłku (Test: 5s)")
                    }
                    .font(.headline)
                    .foregroundStyle(.white)
                    .frame(maxWidth: .infinity)
                    .padding()
                    .background(Color.blue)
                    .cornerRadius(12)
                }
                .padding(.top, 10)
            }
            .padding()
        }
        .navigationBarTitleDisplayMode(.inline)
        // Dodajemy przycisk w pasku nawigacji
        .toolbar {
            ToolbarItem(placement: .primaryAction) {
                Button {
                    showAssignSheet = true
                } label: {
                    Image(systemName: "calendar.badge.plus")
                }
            }
        }
        // Otwieramy arkusz przypisywania
        .sheet(isPresented: $showAssignSheet) {
            MealAssignmentSheet(meal: meal)
        }
    }
}
