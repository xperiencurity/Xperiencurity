package com.xp.xperiencurity.xperiencurity

class RemoveDeviceModel {
    private var Name: String? = null
    private var Version: Double? = null
    private var Type: String? = null

    constructor() : this("", 0.0, "")


    constructor(Name: String?, Version: Double?, Type: String?) {
        this.Name = Name
        this.Version = Version
        this.Type = Type
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

    var type: String?
        get() = Type
        set(value) {
            Type = value
        }
}