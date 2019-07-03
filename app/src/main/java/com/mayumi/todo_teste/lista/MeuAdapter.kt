package com.mayumi.todo_teste.lista

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.mayumi.todo_teste.R
import com.mayumi.todo_teste.dao.NotaDao
import kotlinx.android.synthetic.main.celula.view.*

class MeuAdapter (
    private val context: Context,
    private var resource: Int,
    private var data: ArrayList<HMAuxB>
) : BaseAdapter() {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var mView = convertView

        if (mView == null) {
            mView = mInflater.inflate(resource, parent, false)
        }

        var ll_titulo = mView?.findViewById<TextView>(R.id.tv_titulo_cel)
        var ll_nota = mView?.findViewById<TextView>(R.id.tv_nota_cel)

        var item = data[position]

        ll_titulo?.setText(item[HMAuxB.TITULO])
        ll_nota?.setText(item[HMAuxB.NOTA])


        return mView!!
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return data[position][HMAuxB.ID]?.toLong() ?: 0L
    }

    override fun getCount(): Int {
        return data.size
    }
}