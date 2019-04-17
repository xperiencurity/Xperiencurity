package com.xp.xperiencurity.xperiencurity.Model

class DevicesToUpdate {
    private var Name: String? = null
    private var Description: String? = null


    constructor():this("","") {

    }


    constructor(Name: String?, Description: String?) {
        this.Name = Name
        this.Description = Description
    }

    var name: String?
        get() = Name
        set(value) {
            Name = value
        }

    var desc: String?
        get() = Description
        set(value) {
            Description = value
        }
}