import SwiftUI

struct ShoppingListView: View {
    @EnvironmentObject var calendarVM: CalendarViewModel
    
    // Zbiór odhaczonych składników (przechowywany lokalnie w widoku)
    @State private var checkedItems: Set<String> = []

    var body: some View {
        NavigationStack {
            List {
                // Sekcja: Do kupienia
                Section(header: Text("Do kupienia")) {
                    if itemsToBuy.isEmpty {
                        Text("Wszystko kupione lub brak planów!")
                            .foregroundStyle(.secondary)
                            .italic()
                    } else {
                        ForEach(itemsToBuy, id: \.name) { item in
                            Button {
                                toggleItem(item.name)
                            } label: {
                                HStack {
                                    Image(systemName: "square")
                                        .foregroundStyle(.red)
                                    Text(displayString(for: item))
                                        .foregroundStyle(.primary)
                                }
                            }
                        }
                    }
                }

                // Sekcja: Kupione (Odhaczone)
                if !boughtItems.isEmpty {
                    Section(header: Text("Kupione")) {
                        ForEach(boughtItems, id: \.name) { item in
                            Button {
                                toggleItem(item.name)
                            } label: {
                                HStack {
                                    Image(systemName: "checkmark.square.fill")
                                        .foregroundStyle(.green)
                                    Text(displayString(for: item))
                                        .strikethrough()
                                        .foregroundStyle(.secondary)
                                }
                            }
                        }
                    }
                }
            }
            .listStyle(.insetGrouped)
            .navigationTitle("Lista Zakupów")
            .toolbar {
                // Opcjonalnie: Przycisk czyszczenia odhaczonych
                if !checkedItems.isEmpty {
                    ToolbarItem(placement: .topBarTrailing) {
                        Button("Wyczyść") {
                            checkedItems.removeAll()
                        }
                    }
                }
            }
        }
    }

    // MARK: - Logika pomocnicza
    
    // Struktura pomocnicza do wyświetlania
    struct ShoppingItem {
        let name: String
        let count: Int
    }

    // Pobieramy dane z ViewModelu i dzielimy na dwie listy
    private var allIngredients: [ShoppingItem] {
        calendarVM.shoppingListIngredients.map { name, count in
            ShoppingItem(name: name, count: count)
        }.sorted { $0.name < $1.name }
    }

    private var itemsToBuy: [ShoppingItem] {
        allIngredients.filter { !checkedItems.contains($0.name) }
    }

    private var boughtItems: [ShoppingItem] {
        allIngredients.filter { checkedItems.contains($0.name) }
    }

    private func toggleItem(_ name: String) {
        withAnimation {
            if checkedItems.contains(name) {
                checkedItems.remove(name)
            } else {
                checkedItems.insert(name)
            }
        }
    }
    
    private func displayString(for item: ShoppingItem) -> String {
        if item.count > 1 {
            return "\(item.count)x \(item.name)"
        } else {
            return item.name
        }
    }
}

#Preview {
    ShoppingListView()
        .environmentObject(CalendarViewModel())
}
