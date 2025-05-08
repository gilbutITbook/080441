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
        let config = GenerationConfig(
            temperature: 1,
            topP: 1.0,
            topK: 500,
            candidateCount: 1,
            stopSequences: ["\n\n"]
        )
        let model = GenerativeModel(
            name: "models/gemini-1.5-flash",
            apiKey: APIKey.default,
            generationConfig: config
        )
        
        // 추론 실행
        do {
            let response = try await model.generateContent(
                "사이버 펑크 스타일의 빨간 모자 이야기를 써주세요."
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
