import SwiftUI

struct AddMealSheet: View {
    let onSave: (Meal) -> Void
    @Environment(\.dismiss) private var dismiss

    @State private var name: String = ""
    @State private var description: String = ""
    @State private var ingredientsText: String = ""
    @State private var preparation: String = ""
    @State private var caloriesText: String = ""

    var body: some View {
        NavigationStack {
            Form {
                Section("Informacje") {
                    TextField("Nazwa posiłku", text: $name)
                    TextField("Krótki opis", text: $description)
                    TextField("Kalorie", text: $caloriesText)
                        .keyboardType(.numberPad)
                }
                
                Section("Składniki") {
                    ZStack(alignment: .topLeading) {
                        if ingredientsText.isEmpty {
                            Text("Wpisz składniki, każdy w nowej linii...")
                                .foregroundStyle(.secondary)
                                .padding(.top, 8)
                                .padding(.leading, 4)
                        }
                        TextEditor(text: $ingredientsText)
                            .frame(minHeight: 100)
                    }
                }
                
                Section("Sposób przygotowania") {
                    TextEditor(text: $preparation)
                        .frame(minHeight: 100)
                }
            }
            .navigationTitle("Nowy Posiłek")
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .cancellationAction) {
                    Button("Anuluj") { dismiss() }
                }
                ToolbarItem(placement: .confirmationAction) {
                    Button("Zapisz") {
                        saveMeal()
                    }
                    .disabled(name.isEmpty || caloriesText.isEmpty)
                }
            }
        }
    }
    
    private func saveMeal() {
        let ingredients = ingredientsText
            .components(separatedBy: .newlines)
            .map { $0.trimmingCharacters(in: .whitespaces) }
            .filter { !$0.isEmpty }

        let meal = Meal(
            name: name,
            description: description,
            ingredients: ingredients,
            preparation: preparation,
            calories: Int(caloriesText) ?? 0
        )
        onSave(meal)
        dismiss()
    }
}
