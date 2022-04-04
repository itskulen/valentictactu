package com.example.valentictactu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.example.valentictactu.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity()
{
    enum class Turn
    {
        NOUGHT,
        CROSS
    }
// Baris 11 membuat kelas enum berupa konstanta yg berisi NOUGHT dan CROSS



    private var firstTurn = Turn.CROSS
    private var currentTurn = Turn.CROSS

// membuat variabel giliran pertama dan giliran yang berlangsung

    private var crossScore = 0
    private var noughtScore = 0
// variable menampilkan skor Cross
// variable menampilkan skor Nought


    private var boardList = mutableListOf<Button>()

// membuat variable boardlist untuk button pada permainan

    private lateinit var binding : ActivityMainBinding

// membuat latenit variable atau non-null type


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBoard()
        val kirim = findViewById<Button>(R.id.btn_play)
        kirim.setOnClickListener{
            send()
        }

    }

// menginisialisasi bindng menjadi ActivityMainBinding
// menginisialisasi fungsi initBoard
// menginisialisasi btn play


    private fun send(){
    val intent = Intent(this,about::class.java)
        startActivity(intent)
    }
// variabel send untuk memindahkan ke app berikutnya
    private fun initBoard()
    {
        boardList.add(binding.a1)
        boardList.add(binding.a2)
        boardList.add(binding.a3)
        boardList.add(binding.b1)
        boardList.add(binding.b2)
        boardList.add(binding.b3)
        boardList.add(binding.c1)
        boardList.add(binding.c2)
        boardList.add(binding.c3)
    }
// menempatkan semua button yang sudah dibuat


    fun boardTapped(view: View)
    {
        if (view !is Button)
            return
        addToBoard(view)

        if(checkForVictory(NOUGHT))
        {
            noughtScore++
            result("NOUGHTS IS THE WINNER")
        }
        if(checkForVictory(CROSS))
        {
            crossScore++
            result("CROSS IS THE WINNER")

        }

        if(fullBoard())
        {
         result("SERI")
        }

    }
// membuat fungsi board tapped, apabila kondisi view bukan button akan return ke fungsi add to board
// pada baris 96-98 apabila board pada permainan penuh akan menampilkan sebuah title seri
// pada baris 84-87 apabila kondisi checkforvitory adalah Nought maka akan menampilkan result title Nought adalah pemenangnya
// pada baris 89-92 apabila kondisi checkforvitory adalah Cross maka akan menampilkan result title Cross adalah pemenangnya

    private fun checkForVictory(s: String): Boolean
    {
        //Horizontal Menang
        if (match(binding.a1,s)&& match(binding.a2,s)&& match(binding.a3,s))
            return true
        if (match(binding.b1,s)&& match(binding.b2,s)&& match(binding.b3,s))
            return true
        if (match(binding.c1,s)&& match(binding.c2,s)&& match(binding.c3,s))
            return true
// pada baris 110 kondisi menang apabila button a1,a2,a3 berada dalam satu row
// pada baris 112 kondisi menang apabila button b1,b2,b3 berada dalam satu row
// pada baris 114 kondisi menang apabila button c1,c2,c3 berada dalam satu row


        //Vertikal Menang
        if (match(binding.a1,s)&& match(binding.b1,s)&& match(binding.c1,s))
            return true
        if (match(binding.a2,s)&& match(binding.b2,s)&& match(binding.c2,s))
            return true
        if (match(binding.a3,s)&& match(binding.b3,s)&& match(binding.c3,s))
            return true
// pada baris 122 kondisi menang apabila button a1,b1,c1 berada dalam satu row
// pada baris 124 kondisi menang apabila button a2,b2,b3 berada dalam satu row
// pada baris 126 kondisi menang apabila button a3,a3,c3 berada dalam satu row

        //Diagonal Menang
        if (match(binding.a1,s)&& match(binding.b2,s)&& match(binding.c3,s))
            return true
        if (match(binding.a3,s)&& match(binding.b2,s)&& match(binding.c1,s))
            return true
// pada baris 133 kondisi menang apabila button a1,b2,c3 menyerong
// pada baris 133 kondisi menang apabila button a3,b2,c1 menyerong


        return false
    }
//

    private fun match(button: Button, symbol : String) = button.text == symbol

    private fun result(title: String)
    {
        val message = "\nnought $noughtScore\n\ncross $crossScore"
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Reset")
            { _,_ ->
                resetBoard()
            }
            .setCancelable(false)
            .show()
    }
// pada baris 153 terdapat function reset board

    private fun resetBoard()
    {
        for (button in boardList)
        {
            button.text = ""
        }

        if(firstTurn == Turn.NOUGHT)
            firstTurn = Turn.CROSS
        else if(firstTurn == Turn.CROSS)
            firstTurn = Turn.NOUGHT

        currentTurn = firstTurn
        setTurnLabel()

    }
// pada baris 169-170 apabila firstTurn giliranya adalah Nought maka selanjutnya akan menjadi Cross
// pada baris 171-172 apabila firstTurn giliranya adalah Nought maka selanjutnya akan menjadi Cross

    private fun fullBoard(): Boolean
    {
        for(button in boardList)
        {
            if (button.text == "")
                return false
        }
        return true
    }

// pada baris 185 apabila button text kosong akan return false
    private fun addToBoard(button: Button)
    {
        if(button.text != "")
            return

        if(currentTurn == Turn.NOUGHT)
        {
            button.text = NOUGHT
            currentTurn = Turn.CROSS
        }
        else if(currentTurn == Turn.CROSS)
        {
            button.text = CROSS
            currentTurn = Turn.NOUGHT
        }
        setTurnLabel()
    }
// apabila pada baris ke 194 nilainya tidak sama dengan apapun akan return
// apabila pada baris ke 197-200 nilainya adalah Nought maka sama dengan giliran Cross
// apabila pada baris ke 202-205 nilainya adalah Cross maka sama dengan giliran Nought
// Baris ke  memangil fungsi setTurnLabel


    private fun setTurnLabel()
    {
        var turnText = ""
        if(currentTurn == Turn.CROSS)
            turnText = "Turn $CROSS"
        else if(currentTurn == Turn.NOUGHT)
            turnText = "Turn $NOUGHT"

        binding.turnTV.text = turnText
    }
// apabila pada baris ke 218-219 currentTurn adalah giliran Cross maka akan menampilkan text Turn Cross
// apabila pada baris ke 220-221 currentTurn adalah giliran Nought maka akan menampilkan text Turn Nought

    companion object
    {
        const val NOUGHT = "O"
        const val  CROSS = "X"
    }

}