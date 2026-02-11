import SwiftUI

@main
struct iOSApp: App {
    var body: some Scene {
        WindowGroup {
            NavigationView {
                List {
                    NavigationLink(destination: KtorMonitoringDemoView()) {
                        Text("Ktor & Monitoring demo")
                    }
                }
                .listStyle(.insetGrouped)
            }
        }
    }
}
