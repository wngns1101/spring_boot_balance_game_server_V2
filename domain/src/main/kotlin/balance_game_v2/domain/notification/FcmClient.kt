package balance_game_v2.domain.notification

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import java.io.IOException

@Configuration
class FcmClient(
    @Value("classpath:firebase/fcm.json")
    private val resource: Resource,
) {
    init {
        try {
            initFirebase()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Throws(IOException::class)
    private fun initFirebase() {
        resource.inputStream.use { serviceAccount ->
            val options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build()
            FirebaseApp.initializeApp(options)
        }
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
