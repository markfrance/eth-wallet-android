package com.myetherwallet.mewconnect.feature.main.fragment

import android.os.Bundle
import android.os.Handler
import android.view.View
import com.myetherwallet.mewconnect.R
import com.myetherwallet.mewconnect.core.di.ApplicationComponent
import com.myetherwallet.mewconnect.core.persist.prefenreces.PreferencesManager
import com.myetherwallet.mewconnect.core.ui.fragment.BaseDiFragment
import kotlinx.android.synthetic.main.fragment_transaction_confirmed.*

import javax.inject.Inject


class ConfirmTransactionFragment : BaseDiFragment() {

    companion object {
        fun newInstance(url: String): ConfirmTransactionFragment {
            val fragment = ConfirmTransactionFragment()
            val arguments = Bundle()

            arguments.putString("URL", url);
            fragment.arguments = arguments
            return fragment
        }
    }

    @Inject
    lateinit var preferences: PreferencesManager
    private val handler = Handler()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        transaction_url.text = arguments?.getString("URL")

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

    override fun layoutId() = R.layout.fragment_transaction_confirmed
}
