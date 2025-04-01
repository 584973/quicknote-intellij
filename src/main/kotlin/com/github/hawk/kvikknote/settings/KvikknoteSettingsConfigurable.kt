package com.github.hawk.kvikknote.settings

import com.intellij.openapi.fileChooser.FileChooser
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.options.Configurable
import java.awt.BorderLayout
import java.awt.FlowLayout
import javax.swing.*

class KvikknoteSettingsConfigurable : Configurable {
    private val textField = JTextField()
    private val browseButton = JButton("Browse...")
    private val panel = JPanel(BorderLayout())

    init {
        val inputPanel = JPanel(FlowLayout(FlowLayout.LEFT)).apply {
            preferredSize = java.awt.Dimension(500, 40)
            add(textField)
            add(browseButton)
        }

        panel.add(JLabel("Obsidian Vault Path:"), BorderLayout.NORTH)
        panel.add(inputPanel, BorderLayout.CENTER)

        browseButton.addActionListener {
            val descriptor = FileChooserDescriptorFactory.createSingleFolderDescriptor()
            descriptor.title = "Select Obsidian Vault Directory"
            val project = ProjectManager.getInstance().defaultProject

            FileChooser.chooseFile(descriptor, project, null) { file: VirtualFile? ->
                if (file != null) {
                    val path = VfsUtil.virtualToIoFile(file).absolutePath
                    textField.text = path
                }
            }
        }
    }

    override fun getDisplayName() = "Kvikknote"
    override fun createComponent(): JComponent = panel
    override fun isModified() = textField.text != KvikknoteSettings.getInstance().state.vaultPath
    override fun apply() {
        KvikknoteSettings.getInstance().state.vaultPath = textField.text
    }
    override fun reset() {
        textField.text = KvikknoteSettings.getInstance().state.vaultPath
    }
}
