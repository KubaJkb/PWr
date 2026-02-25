import Foundation

struct MealPlan {
    var mealsByDate: [Date: [Meal]] = [:]

    mutating func addMeal(_ meal: Meal, for date: Date) {
        let day = Calendar.current.startOfDay(for: date)
        mealsByDate[day, default: []].append(meal)
    }

    func meals(for date: Date) -> [Meal] {
        let day = Calendar.current.startOfDay(for: date)
        return mealsByDate[day] ?? []
    }
}
