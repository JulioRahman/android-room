package com.julio.room.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.julio.room.R
import com.julio.room.adapter.MainAdapter
import com.julio.room.db.Constant
import com.julio.room.db.FootballDatabase
import com.julio.room.entity.Team
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var footballDatabase: FootballDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fabMain.setOnClickListener {
            startActivityForResult(Intent(this, AddDetailActivity::class.java), 1)
        }

        footballDatabase = FootballDatabase.createDatabase(this)
    }

    override fun onResume() {
        super.onResume()

        rvMain.layoutManager = LinearLayoutManager(this)
        rvMain.adapter = footballDatabase?.teamDao()?.select()?.let { MainAdapter(this, it) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {

            val teamList = arrayListOf<Team>()

            val teams = Team(
                0,
                data.getStringExtra(Constant.TAG_TEAM),
                data.getStringExtra(Constant.TAG_YEAR),
                data.getStringExtra(Constant.TAG_WEBSITE),
                data.getStringExtra(Constant.TAG_GENDER),
                data.getStringExtra(Constant.TAG_COUNTRY)
            )

            teamList.add(teams)

            footballDatabase?.teamDao()?.insert(teamList)
        }
    }
}
