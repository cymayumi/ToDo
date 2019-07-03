package com.mayumi.todo_teste.ui.act002

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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

        montarForm()
        recuperarParametros()
    }

    private fun montarForm() {
        if (idAtual != -1L) {
            //nota antiga será exibida
            var cAux = notaDao.obteNotaByID(idAtual)

            et_titulo_nota.setText(cAux?.titulo)
            et_nota.setText(cAux?.nota)
        } else {
            //nova nota com campos vazios
            et_titulo_nota.requestFocus()
        }
    }

    private fun recuperarParametros() {
        idAtual = intent.getLongExtra(Constantes.PARAMETRO_ID, 0)
    }

    private fun initActions() {
        if(idAtual!= -1L){
            form_btn_salvar.setText("Alterar ToDo")
        }

        form_btn_salvar.setOnClickListener {

            var texto = et_nota.text.toString()

            if (texto.trim().isEmpty()) {
                Toast.makeText(context, "Digite sua nota", Toast.LENGTH_SHORT).show()
            } else {
                salvar()
                chamarLista()
            }
        }
    }

    private fun salvar() {
        var cAux = Nota()
        cAux.titulo = et_titulo_nota.text.toString()
        cAux.nota = et_nota.text.toString()

        if (idAtual != -1L) {
            cAux.idnota = idAtual

            notaDao.alterarNota(cAux)
        } else {
            idAtual = notaDao.proximoID()
            cAux.idnota = idAtual

            notaDao.inserirNota(cAux)
        }
    }

    private fun chamarLista() {
        val mIntent = Intent(context, MainActivity::class.java)
        startActivity(mIntent)

        finish()
    }

    override fun onBackPressed() {
        chamarLista()
    }
}
