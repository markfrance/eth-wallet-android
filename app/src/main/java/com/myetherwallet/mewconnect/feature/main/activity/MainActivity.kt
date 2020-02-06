package com.myetherwallet.mewconnect.feature.main.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import com.myetherwallet.mewconnect.R
import com.myetherwallet.mewconnect.core.di.ApplicationComponent
import com.myetherwallet.mewconnect.core.persist.prefenreces.PreferencesManager
import com.myetherwallet.mewconnect.core.ui.activity.BaseDiActivity
import com.myetherwallet.mewconnect.core.ui.fragment.BaseFragment
import com.myetherwallet.mewconnect.feature.auth.fragment.AuthFragment
import com.myetherwallet.mewconnect.feature.main.fragment.IntroFragment
import com.myetherwallet.mewconnect.feature.main.utils.FragmentTransactor
import javax.inject.Inject

/**
 * Created by BArtWell on 30.06.2018.
 */

class MainActivity : BaseDiActivity() {

    companion object {
        fun createIntent(context: Context) = Intent(context, MainActivity::class.java)
    }

    @Inject
    lateinit var preferences: PreferencesManager
    private lateinit var fragmentTransactor: FragmentTransactor
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentTransactor = FragmentTransactor()

        if (preferences.getCurrentWalletPreferences().isWalletExists()) {
            replaceFragment(AuthFragment.newInstance())
        } else {
            replaceFragment(IntroFragment.newInstance())
        }

        setStatusBarColor()

    }


    override fun onPostResume() {
        super.onPostResume()
        fragmentTransactor.resume(supportFragmentManager)
    }

    fun addFragment(fragment: Fragment) {
        fragmentTransactor.add(supportFragmentManager, fragment)
    }

    fun replaceFragment(fragment: Fragment) {
        fragmentTransactor.replace(supportFragmentManager, fragment)
    }

    fun addOrReplaceFragment(fragment: Fragment, tag: String) {
        fragmentTransactor.addOrReplace(supportFragmentManager, fragment, tag)
    }

    fun closeFragment() {
        fragmentTransactor.pop(supportFragmentManager)
    }

    fun closeFragmentsToFirst() {
        fragmentTransactor.popToFirst(supportFragmentManager)
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.main_fragment_container)
        if (fragment !is BaseFragment || !fragment.onBackPressed()) {
            super.onBackPressed()
        }
    }

    override fun inject(appComponent: ApplicationComponent) {
        appComponent.inject(this)
    }
}

