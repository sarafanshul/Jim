package com.projectdelta.jim.util.networking

data class StateResource(
    val status: Status,
    val message: Message? = null
)


sealed class Status {

    object Loading : Status() {
        override fun toString(): String {
            return "Status.Loading"
        }
    }

    object Success : Status() {
        override fun toString(): String {
            return "Status.Success"
        }
    }

    object Error : Status() {
        override fun toString(): String {
            return "Status.Error"
        }
    }

}

sealed class Message {

    data object NoInternet : Message()

    data object DbIsUpToDate : Message()

    data class ServerError(val statusCode: Int) : Message()

    data object NetworkError : Message()

    data object EmptyResponse : Message()

}
