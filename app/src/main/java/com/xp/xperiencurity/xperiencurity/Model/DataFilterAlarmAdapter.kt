package com.xp.xperiencurity.xperiencurity.Model

class DataFilterAlarmAdapter {
    private var Name: String? = null
    private var Version: Double? = null
    private var Data_logging: String? = null

    constructor() : this("", 0.0, "")


    constructor(Name: String?, Version: Double?, Data_logging: String?) {
        this.Name = Name
        this.Version = Version
        this.Data_logging = Data_logging
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

    var datalog: String?
        get() = Data_logging
        set(value) {
            Data_logging = value
        }

}