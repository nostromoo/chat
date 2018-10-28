package com.muzmatch.chat

import android.os.Bundle
import android.support.animation.DynamicAnimation
import android.support.animation.SpringAnimation
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.TextView
import com.muzmatch.chat.bo.Message
import kotlinx.android.synthetic.main.activity_main.*


/**
 * Created by Romain on 28/10/2018.
 */
class MainActivity
  : AppCompatActivity(),
    View.OnClickListener
{

  override fun onCreate(savedInstanceState: Bundle?)
  {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    initList()

    send.setOnClickListener(this)
  }

  override fun onClick(v: View?)
  {
    if (v == send)
    {
      if (input.text.isNotEmpty())
      {

        val newMessage = createNewMessage()
        activity_main.addView(newMessage)
        newMessage.top = input.top

        val springAnimation = SpringAnimation(newMessage, DynamicAnimation.TRANSLATION_Y)

        springAnimation.addEndListener { animation, canceled, value, velocity ->
          activity_main.removeView(newMessage)
          (recyclerView.adapter as ChatAdapter).addItem(Message(input.text.toString(), System.currentTimeMillis(), true, true))
          recyclerView.adapter.notifyDataSetChanged()
        }
        springAnimation.animateToFinalPosition(recyclerView.bottom.toFloat())
      }
    }
  }

  private fun initList()
  {
    val timestamp = System.currentTimeMillis() - 60 * 60 * 1000

    val messages = arrayListOf(
        Message("Hey !", timestamp, false, false),
        Message("Hello !", timestamp, true, false),
        Message("how are you ?", timestamp, false, false),
        Message("fine, what about you?", timestamp, true, false))
    recyclerView.layoutManager = LinearLayoutManager(this)
    recyclerView.adapter = ChatAdapter(messages)
  }

  private fun createNewMessage(): TextView =
      TextView(this@MainActivity).apply {
        setBackgroundResource(R.drawable.bg_message_not_me)
        text = input.text
        setTextColor(ContextCompat.getColor(this@MainActivity, android.R.color.white))
      }
}