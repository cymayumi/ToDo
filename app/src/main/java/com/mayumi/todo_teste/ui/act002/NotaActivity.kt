package com.mayumi.todo_teste.ui.act002

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mayumi.todo_teste.R
import com.mayumi.todo_teste.banco.Constantes
import com.mayumi.todo_teste.dao.NotaDao
import com.mayumi.todo_teste.nota.Nota
import com.mayumi.todo_teste.ui.act001.MainActivity
import kotlinx.android.synthetic.main.add_anotacao.*

class NotaActivity : AppCompatActivity() {

    private lateinit var context: Context
    private lateinit var notaDao: NotaDao

    private var idAtual = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_anotacao)

        initVar()
        initActions()
    }

    private fun initVar() {
        context = this@NotaActivity
        notaDao = NotaDao(context)

    }

    private fun initActions() {
        form_btn_salvar.setOnClickListener {
            salvar()
            chamarLista()
        }
    }

    private fun chamarLista() {
        val mIntent = Intent(context, MainActivity::class.java)
        startActivity(mIntent)

        finish()
    }

    private fun salvar() {
        var cAux = Nota()
        cAux.nota = et_nota.text.toString()

        idAtual = notaDao.proximoID()
        cAux.idnota = idAtual

        notaDao.inserirNota(cAux)

    }

    override fun onBackPressed() {
        chamarLista()
    }
}
