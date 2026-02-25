import Foundation
import Combine

class MealsViewModel: ObservableObject {
    @Published var meals: [Meal] = [
        Meal(
            name: "Owsianka z owocami",
            description: "Gotowana owsianka z owocami sezonowymi.",
            ingredients: ["Płatki owsiane – 80 g", "Mleko – 200 ml", "Banany – 1 szt.", "Jagody – 50 g", "Miód – 1 łyżka"],
            preparation: "Ugotuj płatki owsiane na mleku, dodaj pokrojone owoce, wymieszaj i polej miodem.",
            calories: 350
        ),
        Meal(
            name: "Kurczak z ryżem",
            description: "Smażony kurczak z przyprawami podawany z ryżem.",
            ingredients: ["Pierś z kurczaka – 150 g", "Ryż biały – 100 g", "Oliwa – 1 łyżka", "Papryka słodka – 1 łyżeczka", "Sól – 1 szczypta"],
            preparation: "Pokrój kurczaka, dopraw i podsmaż. Ugotuj ryż i podawaj razem.",
            calories: 600
        ),
        Meal(
            name: "Sałatka warzywna",
            description: "Lekka sałatka z sosem jogurtowym.",
            ingredients: ["Sałata – 1 garść", "Pomidor – 1 szt.", "Ogórek – 1 szt.", "Jogurt naturalny – 2 łyżki", "Pieprz – 1 szczypta"],
            preparation: "Pokrój warzywa, wymieszaj je z jogurtem i przyprawami.",
            calories: 220
        ),
        Meal(
            name: "Omlet z warzywami",
            description: "Puszysty omlet z papryką i szpinakiem.",
            ingredients: ["Jajka – 2 szt.", "Mleko – 30 ml", "Papryka – 50 g", "Szpinak świeży – 30 g", "Masło – 1 łyżeczka"],
            preparation: "Wymieszaj jajka z mlekiem, dodaj pokrojone warzywa i usmaż na maśle.",
            calories: 310
        ),
        Meal(
            name: "Spaghetti Bolognese",
            description: "Klasyczne spaghetti z sosem pomidorowym i mięsem.",
            ingredients: ["Makaron spaghetti – 120 g", "Mięso mielone – 150 g", "Pomidory krojone – 200 g", "Czosnek – 1 ząbek", "Ser parmezan – 20 g"],
            preparation: "Podsmaż mięso z czosnkiem, dodaj pomidory i gotuj. Podawaj z makaronem i startym serem.",
            calories: 680
        ),
        Meal(
            name: "Tosty z awokado",
            description: "Chrupiące tosty z pastą z awokado.",
            ingredients: ["Chleb tostowy – 2 kromki", "Awokado – 1/2 szt.", "Cytryna – 1 łyżeczka soku", "Sól – szczypta", "Pieprz – szczypta"],
            preparation: "Rozgnieć awokado z sokiem z cytryny i przyprawami, rozsmaruj na podpieczonym chlebie.",
            calories: 290
        ),
        Meal(
            name: "Zupa pomidorowa",
            description: "Delikatna, kremowa zupa pomidorowa.",
            ingredients: ["Bulion warzywny – 400 ml", "Pomidory – 200 g", "Śmietana 18% – 1 łyżka", "Makaron – 40 g", "Sól i pieprz – do smaku"],
            preparation: "Gotuj pomidory z bulionem, zmiksuj, dodaj śmietanę i makaron.",
            calories: 240
        ),
        Meal(
            name: "Burger wołowy",
            description: "Klasyczny burger z wołowiną i serem.",
            ingredients: ["Bułka burgerowa – 1 szt.", "Mięso wołowe – 150 g", "Ser cheddar – 1 plaster", "Sałata – 1 liść", "Ketchup – 1 łyżka"],
            preparation: "Uformuj kotlet, usmaż, przełóż do bułki z dodatkami.",
            calories: 720
        ),
        Meal(
            name: "Naleśniki z twarogiem",
            description: "Słodkie naleśniki z nadzieniem twarogowym.",
            ingredients: ["Mąka – 120 g", "Mleko – 200 ml", "Jajko – 1 szt.", "Twaróg – 100 g", "Cukier – 1 łyżka"],
            preparation: "Usmaż naleśniki, twaróg wymieszaj z cukrem, nałóż do środka i złóż.",
            calories: 450
        ),
        Meal(
            name: "Ryba z warzywami",
            description: "Pieczona ryba z mieszanką warzyw.",
            ingredients: ["Filet z dorsza – 150 g", "Brokuł – 100 g", "Marchew – 1 szt.", "Oliwa – 1 łyżka", "Sól – szczypta"],
            preparation: "Pokrój warzywa, skrop oliwą, piecz razem z rybą w piekarniku.",
            calories: 380
        )
    ]

    func removeMeal(_ meal: Meal) {
        meals.removeAll { $0.id == meal.id }
    }

    func addMeal(_ meal: Meal) {
        meals.append(meal)
    }
}
