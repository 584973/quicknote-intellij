package com.github.hawk.quicknote.actions

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import java.io.File

class QuickNoteExport : AnAction("Export quicknote") {
    override fun actionPerformed(p0: AnActionEvent) {
        val project = p0.project ?: return
        val file = File(project.basePath ?: return, ".idea/quicknote.md")

        if (!file.exists()) {
            notifyFileNotFound()
            return
        }

        val homeDir = System.getProperty("user.home")
        val exportFile = File(homeDir, "${project.name}_quicknote.md")

        try {
            exportFile.writeText(file.readText())
            notifyFileExported(exportFile)
        } catch (e: Exception) {
            notifyError("Failed to export note: ${e.message}")
        }
    }

    private fun notifyFileNotFound() {
        val notification = Notification(
            "QuickNote",
            "QuickNote Export",
            "QuickNote file not found",
            NotificationType.WARNING
        )
        Notifications.Bus.notify(notification)
        return
    }

    private fun notifyFileExported(file: File) {
        Notifications.Bus.notify(
            Notification(
                "QuickNote",
                "Export Complete",
                "Note exported to: ${file.absolutePath}",
                NotificationType.INFORMATION
            )
        )
    }

    private fun notifyError(msg: String) {
        Notifications.Bus.notify(
            Notification("QuickNote", "Error", msg, NotificationType.ERROR)
        )
    }
}
