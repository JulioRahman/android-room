package com.julio.room.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.julio.room.R
import com.julio.room.db.Constant
import com.julio.room.db.FootballDatabase
import com.julio.room.entity.Team
import kotlinx.android.synthetic.main.activity_add_detail.*

class AddDetailActivity : AppCompatActivity(), View.OnClickListener {

    private var bundle: Bundle? = null

    private lateinit var teams: Team

    private var footballDB: FootballDatabase? = null

    private var alertDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_detail)

        bEdit.setOnClickListener(this)
        bDelete.setOnClickListener(this)
        bDone.setOnClickListener(this)
        bAdd.setOnClickListener(this)

        bundle = intent.extras

        footballDB = FootballDatabase.createDatabase(this)

        if (bundle == null) {
            addActivity()
        } else {
            detailActivity()
        }
    }

    private fun addActivity() {

        title = "Add New Data"

        bDelete.visibility = View.GONE
        bEdit.visibility = View.GONE
        bDone.visibility = View.GONE
        bAdd.visibility = View.VISIBLE
    }

    private fun detailActivity() {

        title = "Detail Data"

        val id = bundle?.getInt(Constant.TAG_ID)
        val sTeam = bundle?.getString(Constant.TAG_TEAM)
        val sYear = bundle?.getString(Constant.TAG_YEAR)
        val sWebsite = bundle?.getString(Constant.TAG_WEBSITE)
        val sGender = bundle?.getString(Constant.TAG_GENDER)
        val sCountry = bundle?.getString(Constant.TAG_COUNTRY)

        bDelete.visibility = View.VISIBLE
        bEdit.visibility = View.VISIBLE
        bDone.visibility = View.GONE
        bAdd.visibility = View.GONE

        tietTeam.isEnabled = false
        tietYear.isEnabled = false
        tietWebsite.isEnabled = false
        tietGender.isEnabled = false
        tietCountry.isEnabled = false

        tietTeam.setText(sTeam)
        tietYear.setText(sYear)
        tietWebsite.setText(sWebsite)
        tietGender.setText(sGender)
        tietCountry.setText(sCountry)

        teams = Team(
            id ?: 0,
            sTeam,
            sYear,
            sWebsite,
            sGender,
            sCountry
        )
    }

    override fun onClick(v: View?) {
        val sTeamX = tietTeam.text.toString()
        val sYearX = tietYear.text.toString()
        val sWebsiteX = tietWebsite.text.toString()
        val sGenderX = tietGender.text.toString()
        val sCountryX = tietCountry.text.toString()

        val empty =
            sTeamX.isEmpty() || sYearX.isEmpty() || sWebsiteX.isEmpty() || sGenderX.isEmpty() || sCountryX.isEmpty()

        when (v?.id) {
            bEdit.id -> {
                title = "Edit"

                tietTeam.isEnabled = true
                tietYear.isEnabled = true
                tietWebsite.isEnabled = true
                tietGender.isEnabled = true
                tietCountry.isEnabled = true

                bEdit.visibility = View.GONE
                bDelete.visibility = View.GONE
                bDone.visibility = View.VISIBLE
                bAdd.visibility = View.GONE
            }
            bDelete?.id -> {
                alertDialog = AlertDialog.Builder(this)
                    .setCancelable(false)
                    .setMessage("Are you sure to delete $sTeamX ?")
                    .setPositiveButton(android.R.string.ok) { _, _ ->
                        Toast.makeText(applicationContext, "done", Toast.LENGTH_SHORT)
                            .show()
                        footballDB?.teamDao()?.delete(teams)
                        finish()
                    }
                    .setNegativeButton(android.R.string.no) { _, _ -> alertDialog!!.cancel() }
                    .show()
            }
            bDone?.id -> {
                if (!empty) {
                    teams = Team(
                        0,
                        sTeamX,
                        sYearX,
                        sWebsiteX,
                        sGenderX,
                        sCountryX
                    )
                    footballDB?.teamDao()?.update(teams)
                    Toast.makeText(this, "done", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "cannot be empty", Toast.LENGTH_SHORT).show()
                }
            }
            bAdd?.id -> {
                if (!empty) {
                    intent.putExtra(Constant.TAG_TEAM, sTeamX)
                    intent.putExtra(Constant.TAG_YEAR, sYearX)
                    intent.putExtra(Constant.TAG_WEBSITE, sWebsiteX)
                    intent.putExtra(Constant.TAG_GENDER, sGenderX)
                    intent.putExtra(Constant.TAG_COUNTRY, sCountryX)

                    Toast.makeText(this, "done", Toast.LENGTH_SHORT).show()
                    setResult(RESULT_OK, intent)
                    finish()
                } else {
                    Toast.makeText(this, "cannot be empty", Toast.LENGTH_SHORT).show()
                    setResult(RESULT_CANCELED, intent)
                }
            }
        }
    }
}
