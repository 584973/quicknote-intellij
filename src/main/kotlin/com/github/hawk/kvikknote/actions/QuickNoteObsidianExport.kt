package com.github.hawk.kvikknote.actions

import com.github.hawk.kvikknote.settings.KvikknoteSettings
import com.github.hawk.kvikknote.utils.NotificationUtil
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileEditor.FileDocumentManager
import java.io.File

class QuickNoteObsidianExport : AnAction("Export quicknote to obsidian") {
    private val notificationUtil = NotificationUtil()
    override fun actionPerformed(p0: AnActionEvent) {
        val project = p0.project ?: return
        val noteFile = File(project.basePath ?: return, ".idea/quicknote.md")

        if (!noteFile.exists()) {
            notificationUtil.notify("No quicknote.md found in this project.", NotificationType.WARNING)
            return
        }

        FileDocumentManager.getInstance().saveAllDocuments()

        val settings = KvikknoteSettings.getInstance()
        val vaultPath = settings.state.vaultPath

        if (vaultPath.isBlank()) {
            notificationUtil.notify("Vault path is not set. Go to Settings → Kvikknote to configure it.", NotificationType.WARNING)
            return
        }

        val exportFile = File(vaultPath, "${project.name}_quicknote.md")
        try {
            exportFile.writeText(noteFile.readText())
            notificationUtil.notify("Note exported to Obsidian vault: ${exportFile.absolutePath}", NotificationType.INFORMATION)
        } catch (e: Exception) {
            notificationUtil.notify("Export failed: ${e.message}", NotificationType.ERROR)
        }
    }

}
