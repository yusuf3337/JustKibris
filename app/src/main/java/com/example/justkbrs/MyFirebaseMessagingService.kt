package com.example.justkbrs

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        // Burada bildirim mesajlarınızı işleyebilirsiniz
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // FCM token güncellemelerini burada işleyin
    }
}
