package com.pusher.chatbotapi

import khttp.responses.Response
import org.json.JSONObject
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

data class WebhookResponse(val fulfillmentText: String)

@RestController
@RequestMapping("/webhook")
class WebhookController {

    @PostMapping
    fun postMessage(@RequestBody json: String) : WebhookResponse {
        val jsonObj = JSONObject(json)

        System.out.println(jsonObj)

        val num = jsonObj.getJSONObject("queryResult").getJSONObject("parameters").getInt("number")

        val response: Response = khttp.get("http://numbersapi.com/$num?json")
        val responseObj: JSONObject = response.jsonObject

        return WebhookResponse(responseObj["text"] as String)
    }
}