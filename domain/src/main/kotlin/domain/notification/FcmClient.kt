package domain.notification

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import com.google.gson.JsonParser
import com.google.gson.stream.JsonReader
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import java.io.InputStreamReader

@Configuration
class FcmClient(
    private val firebaseConfigPath: String = "firebase/fcm.json"
) {
    init {
        val resource = ClassPathResource(firebaseConfigPath)
        val inputStream = resource.inputStream
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        val jsonElement = JsonParser.parseString(jsonString)
        val credentials = GoogleCredentials.fromStream(jsonElement.toString().byteInputStream())

        val options = FirebaseOptions.Builder()
            .setCredentials(credentials)
            .build()

        FirebaseApp.initializeApp(options)
    }

    fun send(token: String, title: String, body: String, link: String) {
        val notification = Notification.builder().setTitle(title).setBody(body).build()
        val message = Message.builder()
            .setToken(token)
            .setNotification(notification)
            .putData("icon", "icon")
            .putData("link", link)
            .build()

        FirebaseMessaging.getInstance().send(message)
    }
}
