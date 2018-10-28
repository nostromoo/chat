package com.muzmatch.chat.bo

import java.io.Serializable

data class Message(val payload: String, val timestamp: Long, val isMe: Boolean, val hasTail: Boolean)
  : Serializable
{
  fun hasSection(message: Message): Boolean =
      timestamp - message.timestamp > 60 * 60 * 1000

  fun hasTail(message: Message): Boolean =
      isMe != message.isMe
}
