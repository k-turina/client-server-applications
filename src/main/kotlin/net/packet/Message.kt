package net.packet

import java.nio.ByteBuffer
import java.nio.charset.Charset

class Message(var cType: Int, var userID: Int, var msg: String) {

    enum class CommandTypes {
        GET_PRODUCT_COUNT, ADD_GROUP, ADD_PRODUCT_TO_GROUP, INCREASE_PRODUCT_COUNT, DECREASE_PRODUCT_COUNT, SET_PRODUCT_PRICE, SERVER_RESPONSE
    }

    constructor(data: ByteArray) : this(
            cType = ByteBuffer.wrap(data, 0, 4).int,
            userID = ByteBuffer.wrap(data, 4, 4).int,
            msg = data.copyOfRange(8, data.size).toString(Charset.defaultCharset())
    )


    fun toBytes(): ByteArray =
            ByteBuffer.allocate(4 + 4 + msg.length)
                    .putInt(cType)
                    .putInt(userID)
                    .put(msg.toByteArray())
                    .array()

    override fun toString(): String {
        return "Message(cType=$cType, userID=$userID, msg=${msg})"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Message

        if (cType != other.cType) return false
        if (userID != other.userID) return false
        if (!msg.contentEquals(other.msg)) return false

        return true
    }

}