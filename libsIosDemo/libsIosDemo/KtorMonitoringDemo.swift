import Foundation
import SwiftUI
import iosDemo

struct KtorMonitoringDemoView: View {
    let demoRemoteDatasource = DemoRemoteDatasource(monitoring: LBMonitoringDemo.shared)
    @State private var dogFact: String? = nil

    var body: some View {
        List {
            Text(dogFact ?? "Click the button to display a new fact!")
            Button("Get dog fact!") {
                Task {
                    dogFact = try await demoRemoteDatasource.getDogFact()
                }
            }
            Button("Get a 404") {
                Task {
                    dogFact = try await demoRemoteDatasource.getDogFact404()
                }
            }
            NavigationLink(destination: LBMonitoringView()) {
                Text("Consult logs (CMP)")
            }
        }
    }
}
