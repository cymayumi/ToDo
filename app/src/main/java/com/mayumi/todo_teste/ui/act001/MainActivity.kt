package com.mayumi.todo_teste.ui.act001

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.mayumi.todo_teste.ui.act002.NotaActivity
import com.mayumi.todo_teste.R
import com.mayumi.todo_teste.banco.Constantes
import com.mayumi.todo_teste.banco.HMAux
import com.mayumi.todo_teste.dao.NotaDao

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var context: Context
    private lateinit var notaDao: NotaDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            addNota()
        }

        initVar()
        initActions()
    }

    private fun initVar() {
        context = this@MainActivity
        notaDao = NotaDao(context)

        montarLista()
    }

    private fun initActions() {

        lv_lista.setOnItemClickListener { parent, view, position, id ->

            val builder = AlertDialog.Builder(context)

            builder.setMessage("Deletar ToDo?")

            builder.setPositiveButton("SIM") { dialog, which ->

                var item = parent.getItemAtPosition(position) as HMAux

                notaDao.apagarNota(item[NotaDao.IDNOTA]!!.toLong())
                montarLista()

            }

            builder.setNegativeButton("NÃO") { dialog, which ->
                Toast.makeText(context, "Nada foi modificado.", Toast.LENGTH_SHORT).show()
            }

            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }

    private fun montarLista() {
        val De = arrayOf(NotaDao.NOTA)
        val Para = intArrayOf(android.R.id.text1)

        lv_lista.adapter = SimpleAdapter(
            context,
            notaDao.obterListaNota(),
            android.R.layout.simple_list_item_1,
            De,
            Para
        )
    }

    private fun addNota() {
        val mIntent = Intent(context, NotaActivity::class.java)

        startActivity(mIntent)

        finish()
    }

 /*   override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }*/
}
