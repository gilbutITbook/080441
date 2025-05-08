import SwiftUI
import GoogleGenerativeAI  // 패키지 추가

struct ContentView: View {
    var body: some View {
        VStack {
            Image(systemName: "globe")
                .imageScale(.large)
                .foregroundStyle(.tint)
            Text("Hello, world!")
        }
        .padding()
        .onAppear() {
            Task {
                await runGemini()
            }
        }
    }
    
    func runGemini() async {
        // 모델 준비
        let model = GenerativeModel(
            name: "models/gemini-1.5-flash",
            apiKey: APIKey.default
        )
        
        // 이미지 준비
        let image = UIImage(named: "image")!
        
        // 추론 실행
        do {
            let response = try await model.generateContent(
                image,
                "이것은 무슨 이미지 입니까?"
            )
            if let text = response.text {
                print(text)
            }
        } catch {
            print("Error: \(error)")
        }
    }
}

#Preview {
    ContentView()
}
