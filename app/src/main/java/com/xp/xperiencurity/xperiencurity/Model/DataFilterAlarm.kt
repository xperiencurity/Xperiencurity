package com.xp.xperiencurity.xperiencurity.Model

class DataFilterAlarmAdapter {
    private var Name: String? = null
    private var Version: String? = null


    constructor():this("","")


    constructor(Name: String?, Version: String?) {
        this.Name = Name
        this.Version = Version
    }

    var name: String?
        get() = Name
        set(value) {
            Name = value
        }

    var version: String?
        get() = Version
        set(value) {
            Version = value
        }
}