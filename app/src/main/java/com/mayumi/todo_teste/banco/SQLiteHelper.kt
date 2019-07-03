package com.mayumi.todo_teste.banco

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.lang.StringBuilder

class SQLiteHelper (
    context: Context,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(db: SQLiteDatabase?) {
        try {
            val sb = StringBuilder()

            sb.append(
                "CREATE TABLE IF NOT EXISTS [notas] (\n" +
                        "  [idnota] INT NOT NULL, \n" +
                        "  [titulo] INT NOT NULL, \n" +
                        "  [nota] TEXT NOT NULL, \n" +
                        "  CONSTRAINT [] PRIMARY KEY ([idnota]));"
            )

            val comandos = sb.toString().split(";")

            for (i in 0 until comandos.size) {
                db?.execSQL(comandos[i].toLowerCase())
            }

        } catch (ex: Exception) {

        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        try {
            val sb = StringBuilder()

            // Script da CONTATO tabela
            sb.append("DROP TABLE IF EXISTS [notas];")

            val comandos = sb.toString().split(";")

            for (i in 0 until comandos.size) {
                db?.execSQL(comandos[i].toLowerCase())
            }

        } catch (ex: Exception) {

        }

        onCreate(db)
    }
}