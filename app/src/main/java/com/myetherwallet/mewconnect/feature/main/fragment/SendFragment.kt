package com.myetherwallet.mewconnect.feature.main.fragment

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.util.Log
import com.myetherwallet.mewconnect.R
import com.myetherwallet.mewconnect.content.data.Network
import com.myetherwallet.mewconnect.core.di.ApplicationComponent
import com.myetherwallet.mewconnect.core.persist.prefenreces.KeyStore
import com.myetherwallet.mewconnect.core.persist.prefenreces.WalletPreferences
import com.myetherwallet.mewconnect.core.persist.prefenreces.PreferencesManager
import com.myetherwallet.mewconnect.core.ui.fragment.BaseDiFragment
import com.myetherwallet.mewconnect.feature.main.data.WalletBalance
import com.myetherwallet.mewconnect.feature.main.data.WalletData
import com.myetherwallet.mewconnect.feature.auth.fragment.AuthFragment
import kotlinx.android.synthetic.main.fragment_send.*
import kotlinx.android.synthetic.main.fragment_transaction_confirmed.*
import kotlinx.android.synthetic.main.view_wallet_card.view.*
import org.web3j.crypto.*
import org.web3j.utils.Numeric
import org.web3j.utils.Convert
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameter
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.http.HttpService
import org.web3j.protocol.core.methods.request.Transaction
import org.web3j.protocol.core.methods.response.EthSendTransaction
import java.math.BigInteger

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
    private lateinit var web3: Web3j
    private lateinit var txUrl: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        send_wallet_toolbar.setNavigationIcon(R.drawable.ic_action_back)
        send_wallet_toolbar.setNavigationOnClickListener(View.OnClickListener { close() })

        if(preferences.applicationPreferences.getCurrentNetwork() == Network.ROPSTEN){
            web3 = Web3j.build(HttpService("https://ropsten.infura.io/v3/06610b6665ff46bcb85e19c2355a7769"))
        } else {
            web3 = Web3j.build(HttpService("https://mainnet.infura.io/v3/06610b6665ff46bcb85e19c2355a7769"))
        }

        val web3ClientVersion = web3.web3ClientVersion().sendAsync().get().toString()
        Log.d("WEB 3 CLIENT", web3ClientVersion)

        address = preferences.getCurrentWalletPreferences().getWalletAddress()
        my_wallet.text = address

        confirm_transaction.setOnClickListener {
            txUrl = "https://ropsten.etherscan.io/tx/"
            val amountWei = Convert.toWei(enter_amount_text.text.toString(), Convert.Unit.ETHER).toBigInteger()

            Log.d("AMOUNT_WEI", amountWei.toString())
              val credentials = Credentials.create(ECKeyPair.create(preferences.getCurrentWalletPreferences().getWalletPrivateKey(AuthFragment.ks).toByteArray()))

           Log.d("CREDS", credentials.toString())

            val txCount = web3.ethGetTransactionCount(address, DefaultBlockParameterName.LATEST).send()



            Log.d("TX COUNT", txCount.transactionCount.toString())


           //
                    val price = web3.ethGasPrice().sendAsync().get().gasPrice;

            Log.d("PRICE", price.toString())
               val tx = RawTransaction.createEtherTransaction(txCount.transactionCount,
                         price,
                         BigInteger("200000"),enter_wallet_text.text.toString(),
                         amountWei)
                 Log.d("tx", tx.toString());

                 val txSignedBytes = TransactionEncoder.signMessage(tx, credentials)
                 val txSigned = Numeric.toHexString(txSignedBytes)


                 Log.d("Creds", credentials.toString())

                 val sendTx = web3.ethSendRawTransaction(txSigned).sendAsync().get()

                 val error = sendTx.error
                 val txHash = sendTx.transactionHash

                 Log.d("TX HASH", txHash)

                 assert(error == null)
                 assert(txHash.isNotEmpty())

                 if(preferences.applicationPreferences.getCurrentNetwork() == Network.ROPSTEN){
                     txUrl = "https://ropsten.etherscan.io/tx/{txHash}";
                 } else {
                     txUrl = "https://etherscan.io/tx/{txHash}";
                 }

            

            addFragment(ConfirmTransactionFragment.newInstance(txUrl))

        }
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