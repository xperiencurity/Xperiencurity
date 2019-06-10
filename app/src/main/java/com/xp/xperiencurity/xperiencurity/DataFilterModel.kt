package com.xp.xperiencurity.xperiencurity

class DataFilterModel {
    private var Name: String? = null
    private var Version: Double? = null
    private var Data_logging: Boolean? = null

    constructor() : this("", 0.0, false)


    constructor(Name: String?, Version: Double?, Data_logging: Boolean?) {
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

    var datalog: Boolean?
        get() = Data_logging
        set(value) {
            Data_logging = value
        }

}