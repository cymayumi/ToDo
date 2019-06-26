package com.mayumi.todo_teste.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.mayumi.todo_teste.banco.Dao
import com.mayumi.todo_teste.banco.HMAux
import com.mayumi.todo_teste.nota.Nota
import java.lang.Exception

class NotaDao (context: Context) : Dao (context) {

    companion object {
        const val TABELA = "notas"

        const val IDNOTA = "idnota"
        const val NOTA = "nota"

    }

    fun inserirNota(nota:Nota) {
        abrirBanco()

        val cv = ContentValues()

        cv.put(IDNOTA, nota.idnota)
        cv.put(NOTA, nota.nota)

        db?.insert(TABELA, null, cv)

        fecharBanco()
    }


    fun apagarNota(idnota: Long) {
        abrirBanco()

        val cv = ContentValues()

        val filtro = "$IDNOTA = ? "
        val argumentos = arrayOf(idnota.toString())

        db?.delete(TABELA, filtro, argumentos)

        fecharBanco()
    }


    fun obterListaNota (): ArrayList<HMAux> {
        val notas = ArrayList<HMAux>()

        abrirBanco()

        val cursor: Cursor?

        try {
            val comando = " select $IDNOTA, $NOTA from $TABELA"

            cursor = db?.rawQuery(comando.toLowerCase(), null)

            while (cursor!!.moveToNext()) {
                val hmAux = HMAux()

                hmAux[IDNOTA] = cursor.getString(cursor.getColumnIndex(IDNOTA))
                hmAux[NOTA] = cursor.getString(cursor.getColumnIndex(NOTA))

                notas.add(hmAux)
            }

            cursor.close()

        } catch (ex: Exception) {
        }

        fecharBanco()

        return notas
    }

    fun proximoID(): Long {
        var prodID = -1L

        abrirBanco()

        val cursor: Cursor?

        try {
            val comando = " select ifnull(max($IDNOTA) + 1,1) as id from $TABELA "

            cursor = db?.rawQuery(comando.toLowerCase(), null)

            while (cursor!!.moveToNext()) {
                prodID = cursor.getLong(cursor.getColumnIndex("id"))
            }

            cursor.close()

        } catch (ex: Exception) {
        }

        fecharBanco()

        return prodID
    }


}