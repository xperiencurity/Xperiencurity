package com.xp.xperiencurity.xperiencurity

class LogViewModel {
    private var LogName: String? = null
    private var Date: String? = null


    constructor():this("","")


    constructor(LogName: String?, Date: String?) {
        this.LogName = LogName
        this.Date = Date
    }

    var logname: String?
        get() = LogName
        set(value) {
            LogName = value
        }

    var date: String?
        get() = Date
        set(value) {
            Date = value
        }
}