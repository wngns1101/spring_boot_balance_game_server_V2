package org.example.balance_game_v2.domain.nocification

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import org.springframework.core.io.ClassPathResource
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class FcmAPI(
    private val firebaseConfigPath: String = "/firebase/firebase.json"
) {
    @GetMapping("/api/v1")
    fun fcmTest() {
        val options = FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(ClassPathResource("firebase/firebase.json").inputStream)).build()

        val token = "cb5LpjhvR-iqugOIaA5UD2:APA91bH9d-ZbYLmCLVPDEGOB5zDWtMldrmoq5R85rQXr2kPDQEcehPgyf7TCSx2-6wXenacF3y1kcMdGIrozcu4EI5rFqFpwsq4OtuP0ryPwMHiWS6iNjqlHey1nL86nUfZlLRQHMsM5"
        val notification = Notification.builder().setTitle("test").setBody("test").build()
        val message = Message.builder()
            .setToken(token)
            .setNotification(notification)
            .putData("data_1", "abc")
            .putData("data_2", "def")
            .build()

        val response = FirebaseMessaging.getInstance(FirebaseApp.initializeApp(options)).send(message)
        print(response)
    }
}
