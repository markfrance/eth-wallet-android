package com.myetherwallet.mewconnect.feature.auth.fragment

import android.os.Bundle
import android.view.View
import com.myetherwallet.mewconnect.R
import com.myetherwallet.mewconnect.core.di.ApplicationComponent
import com.myetherwallet.mewconnect.core.persist.prefenreces.PreferencesManager
import com.myetherwallet.mewconnect.core.ui.fragment.BaseDiFragment
import kotlinx.android.synthetic.main.fragment_forgot_password.*
import javax.inject.Inject

/**
 * Created by BArtWell on 13.08.2018.
 */

class ForgotPasswordFragment : BaseDiFragment() {

    companion object {

        fun newInstance() = ForgotPasswordFragment()
    }

    @Inject
    lateinit var preferences: PreferencesManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        forgot_password_restore_wallet.setOnClickListener { addFragment(EnterRecoveryPhraseFragment.newInstance()) }
    }

    override fun inject(appComponent: ApplicationComponent) {
        appComponent.inject(this)
    }

    override fun layoutId() = R.layout.fragment_forgot_password
}