package com.muzmatch.chat

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.muzmatch.chat.bo.Message
import kotlinx.android.synthetic.main.message_item.view.*
import org.joda.time.DateTime


/**
 * Created by Romain on 28/10/2018.
 */
class ChatAdapter(private val messages: ArrayList<Message>) :
    RecyclerView.Adapter<ChatAdapter.MessageViewHolder>()
{

  class MessageViewHolder(val view: View) : RecyclerView.ViewHolder(view)
  {

    fun bind(message: Message, hasSection: Boolean, hasTail: Boolean)
    {
      val tail = if (hasTail) "tail" else ""
      view.message.text = "${message.payload}$tail"
      val lp: FrameLayout.LayoutParams = view.message.layoutParams as FrameLayout.LayoutParams

      if (message.isMe)
      {
        view.message.setTextColor(ContextCompat.getColor(view.context, android.R.color.white))
        view.message.setBackgroundResource(R.drawable.bg_message_me)
        lp.gravity = Gravity.END
      }
      else
      {
        view.message.setTextColor(ContextCompat.getColor(view.context, android.R.color.black))
        view.message.setBackgroundResource(R.drawable.bg_message_not_me)
        lp.gravity = Gravity.START
      }
      view.message.layoutParams = lp

      if (hasSection)
      {
        view.date.visibility = View.VISIBLE
        val dateTime = DateTime(message.timestamp)
        view.date.text = "${dateTime.dayOfWeek().asText} ${dateTime.hourOfDay().asText}:${dateTime.minuteOfHour().asText}"
      }
      else
      {
        view.date.visibility = View.GONE
      }
    }
  }

  override fun onBindViewHolder(holder: MessageViewHolder, position: Int)
  {
    val hasSection = position == 0 || messages[position].hasSection(messages[position - 1])
    val hasTail = position == itemCount - 1 || messages[position].hasTail(messages[position + 1])
    holder.bind(messages[position], hasSection, hasTail)
  }

  override fun onCreateViewHolder(parent: ViewGroup,
                                  viewType: Int): ChatAdapter.MessageViewHolder
  {
    val textView = LayoutInflater.from(parent.context)
        .inflate(R.layout.message_item, parent, false)
    return MessageViewHolder(textView)
  }


  override fun getItemCount() = messages.size

  fun addItem(message: Message)
  {
    messages.add(message)
  }
}