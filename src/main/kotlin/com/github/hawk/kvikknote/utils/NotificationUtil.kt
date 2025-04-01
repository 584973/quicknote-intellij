package com.github.hawk.kvikknote.utils

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications

class NotificationUtil {
    fun notify(message: String, type: NotificationType) {
        Notifications.Bus.notify(
            Notification("Kvikknote", "Kvikknote Export", message, type)
        )
    }
}