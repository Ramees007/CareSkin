import Shared
import SwiftUI

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
