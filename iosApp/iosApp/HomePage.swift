import Shared
import SwiftUI

struct HomePage: View {
    
    let state: HomeState
    let onTraitTapped: (String, String) -> Void
    let onSubmit: () -> Void
    
    var body: some View {
        
        NavigationView {
            List {
                ForEach(state.refinements, id: \.name) {
                    refinement in
                    Section(header: Text(refinement.name).font(.headline)) {
                        // Each row from [[Trait]]
                        ForEach(0..<refinement.rows.count, id: \.self) {
                            rowIndex in
                            let traits: [Trait] = refinement.rows[rowIndex]
                            TraitsView(traits: traits) { (trait: String) in
                                onTraitTapped(
                                    trait,
                                    refinement.name
                                )
                            }
                            
                        }
                    }
                }
                
            }
            .navigationTitle("Refinements")
        }.safeAreaInset(edge: .bottom, spacing: 0) {
            if state.loading {
                ProgressView()
            } else {
                Button("Submit") {
                    onSubmit()
                }
            }
        }
    }
}
