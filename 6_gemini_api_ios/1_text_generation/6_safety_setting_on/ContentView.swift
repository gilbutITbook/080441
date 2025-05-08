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
            apiKey: APIKey.default,
            safetySettings: [  // 안전 설정
                SafetySetting(harmCategory: .harassment, threshold: .blockNone),
                SafetySetting(harmCategory: .hateSpeech, threshold: .blockNone),
                SafetySetting(harmCategory: .sexuallyExplicit, threshold: .blockNone),
                SafetySetting(harmCategory: .dangerousContent, threshold: .blockNone)
            ]
        )
                
        // 추론 실행
        do {
            let response = try await model.generateContent(
                "너는 너무 어리석어!"
            )
            if let text = response.text {
                print(text)
            }
        } catch {
            // 종료 이유
            print("Error: \(error)")
        }
    }
}

#Preview {
    ContentView()
}
