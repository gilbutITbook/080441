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
        
        // 챗 준비
        let chat = model.startChat()

        do {
            // 첫 번째 질문
            let response1 = try await chat.sendMessage("우리집 고양이 이름은 레오입니다.")
            if let text = response1.text {
                print(text)
            }

            // 두 번째 질문
            let response2 = try await chat.sendMessage("우리집 고양이 이름을 불러주세요.")
            if let text = response2.text {
                print(text)
            }
            
            // 대화 이력 표시
            for content in chat.history {
                print(content.role!, ":", content.parts[0].text!)
            }
        } catch {
            print("Error: \(error)")
        }
    }
}

#Preview {
    ContentView()
}
