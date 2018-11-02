package com.muzmatch.chat

import android.animation.Animator
import android.animation.ObjectAnimator
import android.graphics.Point
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.DisplayMetrics
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
        val message = Message(input.text.toString(), System.currentTimeMillis(), true)
        input.text.clear()
        val newMessage = createNewMessage(message)
        createAnimations(newMessage, message)
      }
    }
  }

  private fun initList()
  {
    val timestamp = System.currentTimeMillis() - 60 * 60 * 1000

    val messages = arrayListOf(
        Message("Hey !", timestamp, false),
        Message("how are you ?", timestamp, false),
        Message("Hello !", timestamp, true),
        Message("fine, what about you?", timestamp, true))
    recyclerView.layoutManager = LinearLayoutManager(this)
    recyclerView.adapter = ChatAdapter(messages)
  }

  private fun updateList(message: Message)
  {
    (recyclerView.adapter as ChatAdapter).addItem(message)
    recyclerView.adapter.notifyDataSetChanged()
    recyclerView.scrollToPosition(recyclerView.adapter.itemCount - 1)
    input.text.clear()
  }

  private fun createNewMessage(message: Message): View
  {
    val messageView = TextView(this).apply {
      setBackgroundResource(message.getBackground(true))
      val (start, end) = message.getPadding(true)
      setPadding(resources.getDimensionPixelSize(start),
          resources.getDimensionPixelSize(R.dimen.dimen5dip),
          resources.getDimensionPixelSize(end),
          resources.getDimensionPixelSize(R.dimen.dimen5dip))
      text = message.payload
      setTextColor(ContextCompat.getColor(this@MainActivity, android.R.color.white))
    }

    activity_main.addView(messageView)
    messageView.y = inputContainer.y
    messageView.measure(0, 0)

    return messageView
  }

  private fun createAnimations(newMessage: View, message: Message)
  {
    val hasSection = recyclerView.adapter.itemCount == 0 || message.hasSection((recyclerView.adapter as ChatAdapter).messages[recyclerView.adapter.itemCount - 1])
    val display = windowManager.defaultDisplay
    val size = Point()
    display.getSize(size)
    val listBottom =  recyclerView.bottom
    val finalYPosition = listBottom - resources.getDimensionPixelSize(R.dimen.dimen10dip) - (if (hasSection) resources.getDimensionPixelSize(R.dimen.dimen30dip) else 0) - resources.getDimensionPixelSize(R.dimen.dimen50dip)
    ObjectAnimator.ofFloat(newMessage, "translationY", finalYPosition.toFloat()).apply {
      duration = 1000
      start()
      addListener(object : Animator.AnimatorListener
      {
        override fun onAnimationEnd(a: Animator)
        {
          activity_main.removeView(newMessage)
          updateList(message)
        }

        override fun onAnimationStart(a: Animator)
        {

        }

        override fun onAnimationCancel(a: Animator)
        {

        }

        override fun onAnimationRepeat(a: Animator)
        {

        }

      })
    }
    ObjectAnimator.ofFloat(newMessage, "translationX", (size.x - newMessage.measuredWidth).toFloat()).apply {
      duration = 1000
      start()
    }
  }
}