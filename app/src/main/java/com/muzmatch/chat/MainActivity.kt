package com.muzmatch.chat

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.Window
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

    activity_main.viewTreeObserver.addOnGlobalLayoutListener {
      val heightDiff = activity_main.rootView.height - activity_main.height
      val contentViewTop = window.findViewById<View>(Window.ID_ANDROID_CONTENT).top

      if (heightDiff > contentViewTop)
      {
        recyclerView.scrollToPosition(recyclerView.adapter.itemCount - 1)
      }
    }
  }

  override fun onClick(v: View?)
  {
    if (v == send)
    {
      if (input.text.isNotEmpty())
      {

//        val newMessage = createNewMessage(Message(input.text.toString(), System.currentTimeMillis(), true, true))


        //        val (anim1X, anim1Y) = SpringAnimation(newMessage, DynamicAnimation.TRANSLATION_X, 500f) to
        //            SpringAnimation(newMessage, DynamicAnimation.TRANSLATION_Y, recyclerView.bottom.toFloat())
        //
        //
        //        anim1Y.addEndListener { animation, canceled, value, velocity ->
        //          activity_main.removeView(newMessage)
        //          (recyclerView.adapter as ChatAdapter).addItem(Message(input.text.toString(), System.currentTimeMillis(), true, true))
        //          recyclerView.adapter.notifyDataSetChanged()
        //          recyclerView.scrollToPosition(recyclerView.adapter.itemCount - 1)
        //          input.text.clear()
        //        }
        //        anim1X.animateToFinalPosition()
        //        ObjectAnimator.ofFloat(newMessage, "translationY", recyclerView.bottom.toFloat()).apply {
        //        duration = 2000
        //
        //        start()
        //      }
        (recyclerView.adapter as ChatAdapter).addItem(Message(input.text.toString(), System.currentTimeMillis(), true))
        recyclerView.adapter.notifyDataSetChanged()
        recyclerView.scrollToPosition(recyclerView.adapter.itemCount - 1)
        input.text.clear()
      }

    }
  }

  private fun initList()
  {
    val timestamp = System.currentTimeMillis() - 60 * 60 * 1000

    val messages = arrayListOf(
        Message("Hey !", timestamp, false),
        Message("Hello !", timestamp, true),
        Message("how are you ?", timestamp, false),
        Message("fine, what about you?", timestamp, true))
    recyclerView.layoutManager = LinearLayoutManager(this)
    recyclerView.adapter = ChatAdapter(messages)
  }

  private fun createNewMessage(message: Message): TextView
  {

    val textView = TextView(this@MainActivity).apply {
      setBackgroundResource(R.drawable.bg_message_me_tail)
      text = message.payload
      setTextColor(ContextCompat.getColor(this@MainActivity, android.R.color.white))

    }
    activity_main.addView(textView)
    textView.top = input.top

    return textView
  }
}