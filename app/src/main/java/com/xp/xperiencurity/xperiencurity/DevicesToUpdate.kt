package com.xp.xperiencurity.xperiencurity

class DevicesToUpdate {
    private var Name: String? = null
    private var Desc: String? = null


    constructor():this("","")


    constructor(Name: String?, Desc: String?) {
        this.Name = Name
        this.Desc = Desc
    }

    var name: String?
        get() = Name
        set(value) {
            Name = value
        }

    var desc: String?
        get() = Desc
        set(value) {
            Desc = value
        }
}