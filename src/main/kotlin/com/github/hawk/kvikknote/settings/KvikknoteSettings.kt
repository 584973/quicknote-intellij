package com.github.hawk.kvikknote.settings

import com.intellij.openapi.components.*

@Service(Service.Level.PROJECT)
@State(name = "KvikknoteSettings", storages = [Storage("KvikknoteSettings.xml")])
class KvikknoteSettings : PersistentStateComponent<KvikknoteSettings.State> {

    data class State(var vaultPath: String = "")
    private var state = State()

    override fun getState() = state
    override fun loadState(state: State) {
        this.state = state
    }

    companion object {
        fun getInstance(): KvikknoteSettings = service()
    }
}
