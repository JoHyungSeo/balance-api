package jooyung.com.balance_api.exception

class ApiException : RuntimeException {

    val resultCode: ResultCode

    constructor() : super(ResultCode.ERR_SYSTEM.message) {
        this.resultCode = ResultCode.ERR_SYSTEM
    }

    constructor(resultCode: ResultCode) : super(resultCode.message) {
        this.resultCode = resultCode
    }

    constructor(resultCode: ResultCode, message: String?) : super(message ?: resultCode.message) {
        this.resultCode = resultCode
    }
}