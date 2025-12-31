import Shared
import SwiftUI

struct DetailPage: View {
    
    var response: String
    
    var body: some View {
        
        VStack(alignment: .leading, spacing: 4) {
            ForEach(response.components(separatedBy: "\n"), id: \.self) { line in
                let attributed = try? AttributedString(markdown: line)
                Text(attributed ?? AttributedString(""))
                    .font(.title3)
                    .frame(maxWidth: .infinity, alignment: .leading)
            }
        }
        .padding()
     
        Spacer()
    }
}

struct DetailPage_Previews: PreviewProvider {
    static var previews: some View {
        let response = "**Dry Skin:**  \n- *Cleanser:* Hydrating cream cleanser.  \n- *Moisturizer:* Rich cream with ceramides/hyaluronic acid."
        DetailPage(response: response)
    }
}

