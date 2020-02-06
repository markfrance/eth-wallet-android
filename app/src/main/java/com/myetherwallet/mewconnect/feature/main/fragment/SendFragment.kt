package com.myetherwallet.mewconnect.feature.main.fragment

import android.os.Bundle
import android.os.Handler
import android.view.View
import com.myetherwallet.mewconnect.R
import com.myetherwallet.mewconnect.core.di.ApplicationComponent

import com.myetherwallet.mewconnect.core.persist.prefenreces.PreferencesManager
import com.myetherwallet.mewconnect.core.ui.fragment.BaseDiFragment
import com.myetherwallet.mewconnect.feature.main.data.WalletBalance
import com.myetherwallet.mewconnect.feature.main.data.WalletData
import kotlinx.android.synthetic.main.fragment_send.*
import kotlinx.android.synthetic.main.fragment_transaction_confirmed.*
import kotlinx.android.synthetic.main.view_wallet_card.view.*
import org.web3j.crypto.Keys
import javax.inject.Inject


class SendFragment : BaseDiFragment() {

    companion object {
        fun newInstance(): SendFragment {
            val fragment = SendFragment()
            val arguments = Bundle()

            fragment.arguments = arguments
            return fragment
        }
    }

    @Inject
    lateinit var preferences: PreferencesManager
    private val handler = Handler()
    private lateinit var address: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        send_wallet_toolbar.setNavigationIcon(R.drawable.ic_action_back)
        send_wallet_toolbar.setNavigationOnClickListener(View.OnClickListener { close() })

        address = preferences.getCurrentWalletPreferences().getWalletAddress()
        my_wallet.text = address


    }


    override fun onStart() {
        super.onStart()

    }

    override fun onStop() {

        super.onStop()
    }


    override fun onBackPressed(): Boolean {
        if (targetFragment != null) {
                close()

                return true
            }

        return false
    }

    override fun inject(appComponent: ApplicationComponent) {
        appComponent.inject(this)
    }

    override fun layoutId() = R.layout.fragment_send
}