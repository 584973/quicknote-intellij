package com.github.hawk.kvikknote.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.OpenFileDescriptor
import com.intellij.openapi.vfs.LocalFileSystem
import java.io.File

class QuickNoteOpen : AnAction("Open quicknote") {
    override fun actionPerformed(p0: AnActionEvent) {
        val project = p0.project ?: return
        val noteFile = File(project.basePath ?: return, ".idea/quicknote.md")

        if (!noteFile.exists()) {
            noteFile.createNewFile()
        }

        val virtualFile = LocalFileSystem.getInstance()
            .refreshAndFindFileByIoFile(noteFile)

        virtualFile?.let {
            FileEditorManager.getInstance(project)
                .openTextEditor(OpenFileDescriptor(project, it), true)
        }
    }
}