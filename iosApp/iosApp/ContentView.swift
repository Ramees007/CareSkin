import Shared
import SwiftUI

struct ContentView: View {
    @ObservedObject private(set) var viewModel: IosHomeViewModel

    @State private var navPath = NavigationPath()

    var body: some View {
        NavigationStack(path: $navPath) {
            HomePage(viewModel: viewModel)
                .navigationDestination(for: AppRoute.self) { route in
                    switch route {
                    case .home:
                        HomePage(viewModel: viewModel)
                    case .details:
                        DetailPage()
                    }
                }
        }
        .task {

            do {
                for try await event in viewModel.effect() {
                    switch event {
                    case is HomeEffect.NavigateToDetailPage:
                        navPath.append(AppRoute.details)
                    default:
                        break
                    }
                }
            } catch {

            }
        }
    }

    enum AppRoute: Hashable {
        case home
        case details
    }

    struct HomePage: View {

        @ObservedObject private(set) var viewModel: IosHomeViewModel

        var body: some View {
            NavigationView {
                List {
                    ForEach(viewModel.state.refinements, id: \.name) {
                        refinement in
                        Section(header: Text(refinement.name).font(.headline)) {
                            // Each row from [[Trait]]
                            ForEach(0..<refinement.rows.count, id: \.self) {
                                rowIndex in
                                let traits: [Trait] = refinement.rows[rowIndex]
                                TraitsView(traits: traits) { (trait: String) in
                                    viewModel.handleTraitClick(
                                        trait: trait,
                                        refinement: refinement.name
                                    )
                                }

                            }
                        }
                    }

                }
                .navigationTitle("Refinements")
            }.safeAreaInset(edge: .bottom, spacing: 0) {
                if viewModel.state.loading {
                    ProgressView()
                } else {
                    Button("Submit") {
                        viewModel.handleSubmitClick()
                    }
                }
            }
        }
    }

    struct DetailPage: View {
        var body: some View {
            Text("Detail PAGE")
        }
    }

    struct TraitsView: View {

        let traits: [Trait]
        let onTraitTapped: (String) -> Void

        var body: some View {
            LazyVGrid(
                columns: [
                    GridItem(.flexible()),
                    GridItem(.flexible()),
                ],
                spacing: 8
            ) {
                ForEach(traits, id: \.name) { trait in
                    Text(trait.name)
                        .frame(maxWidth: .infinity)
                        .padding()
                        .background(
                            trait.isSelected
                                ? Color.blue.opacity(0.8)
                                : Color.blue.opacity(0.1)
                        )
                        .cornerRadius(8)
                        .onTapGesture {
                            onTraitTapped(trait.name)
                        }
                }
            }
            .padding(.vertical, 4)
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView(viewModel: IosHomeViewModel())
    }
}
