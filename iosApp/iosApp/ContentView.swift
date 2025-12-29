import Shared
import SwiftUI

struct ContentView: View {
    @StateObject private var viewModelStoreOwner: IosViewModelStoreOwner
    @StateObject private var viewModel: IosHomeViewModel
    
    @State private var navPath = NavigationPath()
    
    init() {
        let owner = IosViewModelStoreOwner()
        self._viewModelStoreOwner = StateObject(wrappedValue: owner)
        let vm: HomeViewModel = owner.viewModel(
            factory: MetroHelpersKt.createAppGraph().homeViewModelFactory
        )
        self._viewModel = StateObject(wrappedValue: IosHomeViewModel(viewModel: vm))
    }
    
    var body: some View {
        
        NavigationStack(path: $navPath) {
            HomePage(state: viewModel.state, onTraitTapped: {trait,refinement in
                viewModel.handleTraitClick(trait: trait, refinement: refinement)
            }, onSubmit: {
                viewModel.handleSubmitClick()
            })
                .navigationDestination(for: AppRoute.self) { route in
                    switch route {
                    case .home:
                        HomePage(state: viewModel.state, onTraitTapped: {trait,refinement in
                            viewModel.handleTraitClick(trait: trait, refinement: refinement)
                        }, onSubmit: {
                            viewModel.handleSubmitClick()
                        })
                    case .details(let response):
                        DetailPage(response: response)
                    }
                }
        }
        .task {
            do {
                for try await event in viewModel.effect() {
                    switch event {
                    case is HomeEffect.NavigateToDetailPage:
                        navPath.append(AppRoute.details(data: (event as! HomeEffect.NavigateToDetailPage ).response))
                    default:
                        break
                    }
                }
            } catch {
                
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
