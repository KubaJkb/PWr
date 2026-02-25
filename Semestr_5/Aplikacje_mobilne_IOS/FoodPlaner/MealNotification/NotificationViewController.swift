// MealNotification/NotificationViewController.swift
import UIKit
import UserNotifications
import UserNotificationsUI
import SwiftUI

class NotificationViewController: UIViewController, UNNotificationContentExtension {

    func didReceive(_ notification: UNNotification) {
        // 1. Pobieramy dane z userInfo (te, które wysłaliśmy z głównej aplikacji)
        let userInfo = notification.request.content.userInfo
        
        let name = userInfo["mealName"] as? String ?? "Posiłek"
        let calories = userInfo["mealCalories"] as? Int ?? 0
        let ingredients = userInfo["mealIngredients"] as? [String] ?? []
        
        // 2. Tworzymy widok SwiftUI z tymi danymi
        let swiftUIView = NotificationView(name: name, calories: calories, ingredients: ingredients)
        
        // 3. Ładujemy go za pomocą UIHostingController
        let hostingController = UIHostingController(rootView: swiftUIView)
        
        // 4. Dodajemy HostingController do obecnego ViewControllera
        addChild(hostingController)
        view.addSubview(hostingController.view)
        
        // 5. Ustawiamy layout (autolayout constraints)
        hostingController.view.translatesAutoresizingMaskIntoConstraints = false
        NSLayoutConstraint.activate([
            hostingController.view.topAnchor.constraint(equalTo: view.topAnchor),
            hostingController.view.bottomAnchor.constraint(equalTo: view.bottomAnchor),
            hostingController.view.leadingAnchor.constraint(equalTo: view.leadingAnchor),
            hostingController.view.trailingAnchor.constraint(equalTo: view.trailingAnchor)
        ])
        
        hostingController.didMove(toParent: self)
    }
    
    // Opcjonalnie: Ukrywamy domyślny tytuł powiadomienia, bo mamy własny w SwiftUI
    var mediaPlayPauseButtonType: UNNotificationContentExtensionMediaPlayPauseButtonType {
        return .none
    }
    
    var mediaPlayPauseButtonFrame: CGRect {
        return .zero
    }
}
