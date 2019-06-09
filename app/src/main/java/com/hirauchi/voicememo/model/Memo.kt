package com.hirauchi.voicememo.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

open class Memo: RealmObject() {
    @PrimaryKey
    var id: Long = 0
    @Required
    var content: String = ""
    var dateTime: Long = 0
}