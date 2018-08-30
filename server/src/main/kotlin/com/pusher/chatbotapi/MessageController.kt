package com.pusher.chatbotapi

import com.google.cloud.dialogflow.v2.*
import com.pusher.rest.Pusher
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

data class Message(var user:String,
                   var message:String,
                   var time:Long)

@RestController
@RequestMapping("/message")
class MessageController {
    private val pusher = Pusher("PUSHER_APP_ID", "PUSHER_APP_KEY", "PUSHER_APP_SECRET")
    private val botUser = "bot"
    private val dialogFlowProjectId = "DIALOG_FLOW_PROJECT_ID"
    private val pusherChatName = "chat"
    private val pusherEventName = "new_message"

    init {
        pusher.setCluster("PUSHER_APP_CLUSTER")
    }

    @PostMapping
    fun postMessage(@RequestBody message: Message) : ResponseEntity<Unit> {
        pusher.trigger(pusherChatName, pusherEventName, message)

        if (message.message.startsWith("@$botUser", true)) {
            val messageToBot = message.message.replace("@bot", "", true)

            val response = callDialogFlow(dialogFlowProjectId, message.user, messageToBot)

            val botMessage = Message(botUser, response, Calendar.getInstance().timeInMillis)
            pusher.trigger(pusherChatName, pusherEventName, botMessage)
        }

        return ResponseEntity.ok().build()
    }

    @Throws(Exception::class)
    fun callDialogFlow(projectId: String, sessionId: String,
                       message: String): String {
        // Instantiates a client
        SessionsClient.create().use { sessionsClient ->
            // Set the session name using the sessionId and projectID
            val session = SessionName.of(projectId, sessionId)

            // Set the text and language code (en-US) for the query
            val textInput = TextInput.newBuilder().setText(message).setLanguageCode("en")

            // Build the query with the TextInput
            val queryInput = QueryInput.newBuilder().setText(textInput).build()

            // Performs the detect intent request
            val response = sessionsClient.detectIntent(session, queryInput)

            // Display the query result
            val queryResult = response.queryResult

            println("====================")
            System.out.format("Query Text: '%s'\n", queryResult.queryText)
            System.out.format("Detected Intent: %s (confidence: %f)\n",
                    queryResult.intent.displayName, queryResult.intentDetectionConfidence)
            System.out.format("Fulfillment Text: '%s'\n", queryResult.fulfillmentText)

            return queryResult.fulfillmentText
        }
    }
}