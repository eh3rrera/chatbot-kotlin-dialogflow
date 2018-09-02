# chatbot-kotlin-dialogflow
Chat app for Android made with Kotlin and Pusher that integrates a chatbot build with Dialogflow. Follow the tutorial [here](https://pusher.com/tutorials/chatbot-kotlin-dialogflow).

## Getting Started

1. Clone this repository.
2. Create a [Pusher app](https://dashboard.pusher.com).
3. Enter your Pusher app information in `server/src/main/kotlin/com/pusher/chatbotapi/MessageController.kt` and in `android/app/src/main/java/com/pusher/chatbot/ChatActivity.kt`.
4. Create a [Dialogflow agent](https://console.dialogflow.com/api-client) and configure it either by following the steps of the tutorial or importing the zip file `TriviaBot.zip` in the root of this repo using the option *Export and Import* in the *Settings* page of your agent.
5. In the *General* section of the *Settings* page, copy the *Project ID* and enter it in the class `server/src/main/kotlin/com/pusher/chatbotapi/MessageController.kt`.
6. Go to your [Google Cloud Platform console](https://console.cloud.google.com/home/dashboard), choose the project created for your Dialogflow agent, create a new *Service account key* in the *APIs & Services*>*Credentials* section as a JSON file and download the file to a directory outside this project.
7. Configure the environment variable `GOOGLE_APPLICATION_CREDENTIALS` and set as its value the location of the JSON file that contains the private key you created in the previous step.
8. In a terminal window, go to the `server` directory and execute `gradlew bootRun` to start the server.
9. In a terminal window in that directory and execute `ngrok http localhost:8080` to expose your local server to the world.
10. Copy the HTTPS forwarding URL, something like https://5a4f24b2.ngrok.io.
11. In your Dialogflow console, click on the *Fulfillment* option, enable the *Webhook* option, and add the URL you just copied from ngrok appending the path of the webhook endpoint (`webhook`).
12. Open the app with Android Studio.
13. Build the project and run it on two emulator.
14. Choose an username and start sending messages

### Prerequisites

- [Android Studio 3.1.4](https://developer.android.com/studio/index.html)
  - MinSdkVersion: 15
  - TargetSdkVersion: 27
  - buildToolsVersion: 27.1.1
- [Pusher account](https://pusher.com/signup)
- [Dialogflow (Google) account](https://console.dialogflow.com/api-client/#/login)
- [ngrok](https://ngrok.com/)

## Built With

* [Pusher](https://pusher.com/) - APIs to enable devs building realtime features
* [Dialogflow](https://dialogflow.com/) A Google-owned developer of human–computer interaction technologies based on natural language conversations

## Acknowledgments
* Thanks to [Pusher](https://pusher.com/) for sponsoring this tutorial.

## LICENSE
MIT
