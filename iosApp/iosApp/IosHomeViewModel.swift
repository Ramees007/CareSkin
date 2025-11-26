//
//  IosHomeViewModel.swift
//  iosApp
//
//  Created by Ramees Thattarath on 25/09/25.
//

import Shared
import SwiftUI
import Combine
import KMPNativeCoroutinesAsync

@MainActor
class IosHomeViewModel: ObservableObject {
    
    private let delegate: HomeViewModel
    
    @Published var state: HomeState = HomeState(title: "", refinements: [], loading: false)
    
    
    init() {
        self.delegate = HomeViewModel()
        
        Task {
            do {
                let sequence = asyncSequence(for: delegate.stateFlow)
                for try await state in sequence {
                    self.state = state
                }
            } catch {
                print("Failed with error: \(error)")
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
}

