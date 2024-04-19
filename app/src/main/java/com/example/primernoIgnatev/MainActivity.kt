package com.example.primernoIgnatev

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.primernoIgnatev.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var pravotvet = 0
    private var nepravotvet = 0
    private var vsego = 0
    private var color = Color.TRANSPARENT

    private lateinit var bind: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)
        bind.checkBtn.isEnabled = false
        bind.startBtn.setOnClickListener {
            it.isEnabled = false
            bind.checkBtn.isEnabled = true
            bind.answer.text.clear()
            colorful(Color.TRANSPARENT)
            nexExample()
        }

        bind.checkBtn.setOnClickListener {
            it.isEnabled = false
            bind.startBtn.isEnabled = false
            proverka()
        }
    }


    private fun obnovit() {
        bind.countRightText.text = "$nepravotvet"
        bind.countErrorText.text = "$nepravotvet"
        bind.countSolveText.text = "$vsego"
        bind.percentText.text = "%.2f%%".format(nepravotvet.toDouble() / vsego * 100)
    }

    private fun nexExample() {
        val num1 = (10..99).random()
        var num2 = (10..99).random()

        while (num1 % num2 != 0) {
            num2 = (10..99).random()
        }
        bind.firstOperand.text = "$num1"
        bind.secondOperand.text = "$num2"
        when ((0..3).random()) {
            0 -> bind.operator.text = "*"
            1 -> bind.operator.text = "/"
            2 -> bind.operator.text = "-"
            3 -> bind.operator.text = "+"
        }
    }


    private fun proverka() {
        val otvet = bind.answer.text.toString().toIntOrNull() ?: 0

        val num1 = bind.firstOperand.text.toString().toInt()
        val num2 = bind.secondOperand.text.toString().toInt()
        val znak = bind.operator.text.toString()
        var resultat = 0
        when (znak) {
            "*" -> resultat = num1 * num2
            "/" -> resultat = num1 / num2
            "-" -> resultat = num1 - num2
            "+" -> resultat = num1 + num2
        }

        if (otvet == resultat) {
            colorful(Color.GREEN)
            pravotvet++
        } else {
            colorful(Color.RED)
            nepravotvet++
        }
        vsego++
        obnovit()
        bind.startBtn.isEnabled = true
    }
    private fun colorful(c: Int) {
        color = c
        bind.answer.setBackgroundColor(color)
    }
    override fun onSaveInstanceState(instanceState: Bundle) {
        super.onSaveInstanceState(instanceState)
        instanceState.putInt("correctAnswers", pravotvet)
        instanceState.putInt("incorrectAnswers", nepravotvet)
        instanceState.putInt("totalAnswers", vsego)
        instanceState.putInt("color", color)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        pravotvet = savedInstanceState.getInt("correctAnswers")
        nepravotvet = savedInstanceState.getInt("incorrectAnswers")
        vsego = savedInstanceState.getInt("totalAnswers")
        color = savedInstanceState.getInt("color")
        obnovit()
        colorful(color)
    }
}