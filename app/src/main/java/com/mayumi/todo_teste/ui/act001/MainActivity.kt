package com.mayumi.todo_teste.ui.act001

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.GestureDetector
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.get
import androidx.recyclerview.widget.ItemTouchHelper
import com.mayumi.todo_teste.ui.act002.NotaActivity
import com.mayumi.todo_teste.R
import com.mayumi.todo_teste.banco.Constantes
import com.mayumi.todo_teste.dao.NotaDao
import com.mayumi.todo_teste.lista.HMAuxB
import com.mayumi.todo_teste.lista.MeuAdapter

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.celula.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var context: Context

    private lateinit var notaDao: NotaDao
    private lateinit var meuAdapter: MeuAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            addNota(-1)
        }

        initVar()
        initActions()
    }

    private fun initVar() {
        context = this@MainActivity
        notaDao = NotaDao(context)

        //inicializa o adaptador
        montarLista()
    }

    private fun initActions() {

        lv_lista.setOnItemLongClickListener { parent, view, position, id ->

           lv_lista.getChildAt(position).setBackgroundColor(getColor(R.color.corCelApagar))

            val builder = AlertDialog.Builder(context)

            builder.setMessage("Deletar ToDo?")

            builder.setPositiveButton("SIM") { dialog, which ->
                var item = parent.getItemAtPosition(position) as HMAuxB
                notaDao.apagarNota(item[NotaDao.IDNOTA]!!.toLong())
                montarLista()
            }

            builder.setNegativeButton("NÃO") { dialog, which ->
                Toast.makeText(context, "Nada foi modificado.", Toast.LENGTH_SHORT).show()
                lv_lista.getChildAt(position).setBackgroundColor(getColor(R.color.corCel))
            }

            val dialog: AlertDialog = builder.create()
            dialog.show()

            return@setOnItemLongClickListener true

        }

        lv_lista.setOnItemClickListener { parent, view, position, id ->
            var item = parent.getItemAtPosition(position) as HMAuxB

            addNota(item[NotaDao.IDNOTA]!!.toLong())
        }

    }

    private fun montarLista() {

        meuAdapter = MeuAdapter(
            context,
            R.layout.celula,
            notaDao.obterListaNota()
        )

        lv_lista.adapter = meuAdapter
    }

    private fun addNota(idnota: Long) {
        val mIntent = Intent(context, NotaActivity::class.java)
        mIntent.putExtra(Constantes.PARAMETRO_ID, idnota)

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
       }

       */
}
