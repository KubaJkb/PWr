// MealNotification/NotificationView.swift
import SwiftUI

struct NotificationView: View {
    let name: String
    let calories: Int
    let ingredients: [String]
    
    var body: some View {
        VStack(alignment: .leading, spacing: 12) {
            HStack {
                Text(name)
                    .font(.title3)
                    .bold()
                Spacer()
                Text("\(calories) kcal")
                    .font(.caption)
                    .padding(4)
                    .background(Color.orange)
                    .foregroundColor(.white)
                    .cornerRadius(4)
            }
            
            Divider()
            
            Text("Składniki:")
                .font(.headline)
                .font(.caption)
                .foregroundColor(.secondary)
            
            // Wyświetlamy max 5 składników, żeby nie zapchać powiadomienia
            ForEach(ingredients.prefix(5), id: \.self) { item in
                HStack(spacing: 6) {
                    Circle().frame(width: 4, height: 4)
                    Text(item)
                        .font(.body)
                }
            }
            
            if ingredients.count > 5 {
                Text("+ \(ingredients.count - 5) więcej...")
                    .font(.caption)
                    .italic()
                    .foregroundColor(.secondary)
            }
        }
        .padding()
        .background(Color(uiColor: .systemBackground))
    }
}
