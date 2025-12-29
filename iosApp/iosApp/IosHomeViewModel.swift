import Shared
import SwiftUI
import Combine
import KMPNativeCoroutinesAsync

@MainActor
final class IosHomeViewModel: ObservableObject {
    
    @Published var state: HomeState =  HomeState(title: "", refinements: [], loading: false)
    private let delegate: HomeViewModel
    private var task: Task<Void, Never>?
    
    init(viewModel: HomeViewModel) {
        self.delegate = viewModel
        self.state = delegate.state
        
        task = Task { [weak self] in
            guard let self else { return }
            do {
                let sequence = asyncSequence(for: delegate.stateFlow)
                for try await state in sequence {
                    self.state = state
                }
            } catch is CancellationError {
                // Expected during deinit / view disappearance
            } catch {
                print("StateFlow failed:", error)
            }
            
        }
    }
    
    func handleTraitClick(trait: String, refinement: String) {
        delegate.handleTraitClick(trait: trait, refinement: refinement)
    }
    
    func effect() -> NativeFlowAsyncSequence<HomeEffect, any Error, KotlinUnit> {
        asyncSequence(for: delegate.effect)
    }
    
    func handleSubmitClick() {
        delegate.handleSubmit()
    }
    
    deinit {
        task?.cancel()
    }
}

