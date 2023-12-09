package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.view.MotionEvent
import android.widget.TextView
import androidx.core.content.ContextCompat
import org.mariuszgromada.math.mxparser.Expression
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    lateinit var text_view:TextView
    private lateinit var gestureDetector: GestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        text_view = findViewById<TextView>(R.id.text_view)
        gestureDetector = GestureDetector(this, GestureListener())

        text_view.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
            true
        }

        findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.button_clear)
            .setOnClickListener{
            text_view.setTextColor(ContextCompat.getColor(this,R.color.white))
            text_view.text = "0"
        }

        findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.button_bracket_open)
            .setOnClickListener {
            text_view.text = addToInputText("(")
        }

        findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.button_bracket_close)
            .setOnClickListener {
            text_view.text = addToInputText(")")
        }

        findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.button_0)
            .setOnClickListener {
            text_view.text = addToInputText("0")
        }

        findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.button_1)
            .setOnClickListener {
            text_view.text = addToInputText("1")
        }

        findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.button_2)
            .setOnClickListener {
            text_view.text = addToInputText("2")
        }

        findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.button_3)
            .setOnClickListener {
            text_view.text = addToInputText("3")
        }

        findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.button_4)
            .setOnClickListener {
            text_view.text = addToInputText("4")
        }

        findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.button_5)
            .setOnClickListener {
            text_view.text = addToInputText("5")
        }

        findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.button_6)
            .setOnClickListener {
            text_view.text = addToInputText("6")
        }

        findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.button_7)
            .setOnClickListener {
            text_view.text = addToInputText("7")
        }

        findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.button_8)
            .setOnClickListener {
            text_view.text = addToInputText("8")
        }

        findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.button_9)
            .setOnClickListener {
            text_view.text = addToInputText("9")
        }

        findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.button_point)
            .setOnClickListener {
            text_view.text = addToInputText(".")
        }

        findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.button_division)
            .setOnClickListener {
            text_view.text = addToInputText("/")
        }

        findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.button_multiplication)
            .setOnClickListener {
            text_view.text = addToInputText("*")
        }

        findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.button_subtraction)
            .setOnClickListener {
            text_view.text = addToInputText("-")
        }

        findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.button_addition)
            .setOnClickListener {
            text_view.text = addToInputText("+")
        }

        findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.button_equals)
            .setOnClickListener {
            showResult()
        }
    }

    private fun addToInputText(value:String):String{
        return if(text_view.text == "0") "$value"
        else "${text_view.text}$value"
    }


    private fun showResult(){
        try{
            val ex = text_view.text.toString()
            val result = Expression(ex).calculate()
            if(result.isNaN()){
                text_view.text = "NaN"
                text_view.setTextColor(ContextCompat.getColor(this,R.color.red))
            }else{
                text_view.setTextColor(ContextCompat.getColor(this,R.color.white))
                text_view.text = DecimalFormat("0.#####").format(result).toString()
            }
        }catch (e:Exception){
            text_view.text = "Error"
            text_view.setTextColor(ContextCompat.getColor(this,R.color.red))
            println(e.message)
        }
    }

    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
        private val SWIPE_THRESHOLD = 100
        private val SWIPE_VELOCITY_THRESHOLD = 100

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            val diffX = e2.x - e1!!.x
            val diffY = e2.y - e1!!.y

            if (Math.abs(diffX) > Math.abs(diffY)
                && Math.abs(diffX) > SWIPE_THRESHOLD
                && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD
            ) {
                // Скользящее движение справа налево - удаление последнего символа
                removeLastCharacter()
                return true
            }

            return super.onFling(e1, e2, velocityX, velocityY)
        }
    }

    private fun removeLastCharacter() {
        val currentText = text_view.text.toString()
        if (currentText.isNotEmpty()) {
            if(currentText == "NaN" || currentText == "Error"){
                text_view.setTextColor(ContextCompat.getColor(this,R.color.white))
                text_view.text = "0"
            }
            else {
                val newText = currentText.substring(0, currentText.length - 1)
                if (newText == "") text_view.text = "0"
                else text_view.text = newText
            }
        }
    }
}