import Foundation
import Combine

class CalendarViewModel: ObservableObject {
    @Published var mealPlan = MealPlan()
    @Published var selectedDate: Date = Date()

    func meals(for date: Date) -> [Meal] {
        mealPlan.meals(for: date)
    }

    func addMeal(_ meal: Meal, to date: Date) {
        mealPlan.addMeal(meal, for: date)
    }
    
    var dailyCalories: Int {
        meals(for: selectedDate).reduce(0) { $0 + $1.calories }
    }
    
    var shoppingListIngredients: [String: Int] {
        let calendar = Calendar.current
        let today = calendar.startOfDay(for: Date())
        var ingredientsCount: [String: Int] = [:]

        for (date, meals) in mealPlan.mealsByDate {
            let mealDate = calendar.startOfDay(for: date)
            
            if mealDate >= today {
                for meal in meals {
                    for ingredient in meal.ingredients {
                        ingredientsCount[ingredient, default: 0] += 1
                    }
                }
            }
        }
        return ingredientsCount
    }
}
