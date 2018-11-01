package com.muzmatch.chat.bo

import android.view.Gravity
import com.muzmatch.chat.R
import java.io.Serializable

data class Message(val payload: String, val timestamp: Long, val isMe: Boolean)
  : Serializable
{
  fun hasSection(message: Message): Boolean =
      timestamp - message.timestamp > 60 * 60 * 1000

  fun hasTail(message: Message): Boolean =
      isMe != message.isMe

  fun getBackground(hasTail: Boolean): Int =
      when
      {
        isMe && hasTail   -> R.drawable.bg_message_me_tail
        isMe && !hasTail  -> R.drawable.bg_message_me
        !isMe && hasTail  -> R.drawable.bg_message_not_me_tail
        !isMe && !hasTail -> R.drawable.bg_message_not_me
        else              -> -1
      }

  fun getPadding(hasTail:Boolean): Pair<Int, Int> =
      when
      {
        isMe && hasTail   -> Pair(R.dimen.dimen10dip, R.dimen.dimen20dip)
        isMe && !hasTail  -> Pair(R.dimen.dimen10dip, R.dimen.dimen10dip)
        !isMe && hasTail  -> Pair(R.dimen.dimen20dip, R.dimen.dimen10dip)
        !isMe && !hasTail -> Pair(R.dimen.dimen10dip, R.dimen.dimen10dip)
        else              -> Pair(0, 0)
      }

  fun getGravity(): Int =
      when
      {
        isMe  -> Gravity.END
        !isMe -> Gravity.START
        else  -> -1
      }

  fun getTextColor(): Int =
      when
      {
        isMe  -> android.R.color.white
        !isMe -> android.R.color.black
        else  -> -1
      }

}
