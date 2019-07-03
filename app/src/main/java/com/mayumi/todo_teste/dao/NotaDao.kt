package com.mayumi.todo_teste.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.mayumi.todo_teste.banco.Dao
import com.mayumi.todo_teste.lista.HMAuxB
import com.mayumi.todo_teste.nota.Nota
import java.lang.Exception

class NotaDao (context: Context) : Dao (context) {

    companion object {
        const val TABELA = "notas"

        const val IDNOTA = "idnota"
        const val TITULO = "titulo"
        const val NOTA = "nota"
    }

    fun inserirNota(nota:Nota) {
        abrirBanco()

        val cv = ContentValues()

        cv.put(IDNOTA, nota.idnota)
        cv.put(TITULO,nota.titulo)
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

    fun alterarNota(nota: Nota) {
        abrirBanco()

        val cv = ContentValues()

        val filtro = "$IDNOTA = ? "
        val argumentos = arrayOf(nota.idnota.toString())

        cv.put(TITULO, nota.titulo)
        cv.put(NOTA, nota.nota)

        db?.update(TABELA, cv, filtro, argumentos)

        fecharBanco()
    }


    fun obterListaNota (): ArrayList<HMAuxB> {
        val notas = ArrayList<HMAuxB>()

        abrirBanco()

        val cursor: Cursor?

        try {
            val comando = " select $IDNOTA, $TITULO, $NOTA from $TABELA"

            cursor = db?.rawQuery(comando.toLowerCase(), null)

            while (cursor!!.moveToNext()) {
                val hmAux = HMAuxB()

                hmAux[IDNOTA] = cursor.getString(cursor.getColumnIndex(IDNOTA))
                hmAux[TITULO] = cursor.getString(cursor.getColumnIndex(TITULO))
                hmAux[NOTA] = cursor.getString(cursor.getColumnIndex(NOTA))

                notas.add(hmAux)
            }

            cursor.close()

        } catch (ex: Exception) {
        }

        fecharBanco()

        return notas
    }

    fun obteNotaByID(idnota: Long): Nota? {
        var cAux: Nota? = null

        abrirBanco()

        val cursor: Cursor?

        try {
            val comando = " select * from $TABELA where $IDNOTA = ? "
            val argumentos = arrayOf(idnota.toString())

            cursor = db?.rawQuery(comando.toLowerCase(), argumentos)

            while (cursor!!.moveToNext()) {
                cAux = Nota()
                cAux.idnota = cursor.getLong(cursor.getColumnIndex(IDNOTA))
                cAux.titulo = cursor.getString(cursor.getColumnIndex(TITULO))
                cAux.nota = cursor.getString(cursor.getColumnIndex(NOTA))
            }

            cursor.close()

        } catch (ex: Exception) {
        }

        fecharBanco()

        return cAux
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