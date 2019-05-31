package com.xp.xperiencurity.xperiencurity.Model

class DataFilterAlarmAdapter {
    private var Name: String? = null
    private var Version: Double? = null
    private var Data_log: Boolean? = null

    constructor():this("", 0.0, false)


    constructor(Name: String?, Version: Double?, Data_log: Boolean?) {
        this.Name = Name
        this.Version = Version
    }

    var name: String?
        get() = Name
        set(value) {
            Name = value
        }

    var version: Double?
        get() = Version
        set(value) {
            Version = value
        }

    var data_log: Boolean?
        get() = Data_log
        set(value) {
            data_log = value
        }
}