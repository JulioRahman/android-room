package com.julio.room.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.julio.room.R
import com.julio.room.db.Constant
import com.julio.room.entity.Team
import com.julio.room.ui.AddDetailActivity
import kotlinx.android.synthetic.main.item_main.view.*

class MainAdapter(private val context: Context, private val teamList: List<Team>) :
    RecyclerView.Adapter<MainAdapter.MainHolder>() {

    private lateinit var bundle: Bundle

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_main,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = teamList.size

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.tvItemMain.text = teamList[position].team
        holder.itemView.setOnClickListener {
            bundle = Bundle()
            bundle.putInt(Constant.TAG_ID, teamList[position].id)
            bundle.putString(Constant.TAG_TEAM, teamList[position].team)
            bundle.putString(Constant.TAG_YEAR, teamList[position].year)
            bundle.putString(Constant.TAG_WEBSITE, teamList[position].website)
            bundle.putString(Constant.TAG_GENDER, teamList[position].gender)
            bundle.putString(Constant.TAG_COUNTRY, teamList[position].country)
            context.startActivity(Intent(context, AddDetailActivity::class.java).putExtras(bundle))
        }
    }

    inner class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvItemMain: TextView = itemView.tvItemMain
    }

}