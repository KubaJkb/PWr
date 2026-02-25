import Foundation

struct Meal: Identifiable, Hashable {
    let id: UUID
    var name: String
    var description: String
    var ingredients: [String]
    var preparation: String
    var calories: Int

    init(id: UUID = UUID(), name: String, description: String, ingredients: [String], preparation: String = "", calories: Int = 0) {
        self.id = id
        self.name = name
        self.description = description
        self.ingredients = ingredients
        self.preparation = preparation
        self.calories = calories
    }
}
