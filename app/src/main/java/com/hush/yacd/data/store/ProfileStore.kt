package com.hush.yacd.data.store

import com.dylanc.mmkv.MMKVOwner
import com.dylanc.mmkv.mmkvBool
import com.dylanc.mmkv.mmkvString
import com.hush.yacd.data.bean.ConnProfile
import com.hush.yacd.util.GSON
import com.hush.yacd.util.fromJson
import com.hush.yacd.util.toast

object ProfileStore : MMKVOwner {


    private var initialized by mmkvBool(false)

    private var profiles by mmkvString()

     var selectedProfile by mmkvString()

    init {
        if (!initialized) {
            initialized = true
            val prof = ConnProfile(
                hostName = "192.168.50.1",
                port = "9990",
                secret = "clash",
                name = "Merlin Clash"
            )
            addProfile(prof)
            selectedProfile = prof.uuid
        }
    }

    @Throws
    private fun parseProfiles(): List<ConnProfile> = GSON.fromJson(profiles)

    fun saveProfiles(profs: List<ConnProfile>) {
        profiles = GSON.toJson(profs)
    }

    fun getProfiles() = runCatching { parseProfiles() }.getOrDefault(listOf())

    fun addProfile(profile: ConnProfile) {
        val old = getProfiles()
        if (old.any { it.uuid == profile.uuid }) {
            "Profile already exists".toast()
        } else {
            saveProfiles(old + profile)
        }
    }

    fun removeProfile(uuid: String) {
        val old = getProfiles()
        if (old.size <= 1) {
            "At least 1 profile needed".toast()
            return
        }
        val new = old.filter { it.uuid != uuid }
        saveProfiles(new)
        if (uuid == selectedProfile) {
            selectedProfile = new.firstOrNull()?.uuid
        }
    }

    fun selectProfile(uuid: String) {
        selectedProfile = uuid
    }

    fun selectedProfile():ConnProfile = getProfiles().firstOrNull { it.uuid == selectedProfile } ?: getProfiles().first()

}