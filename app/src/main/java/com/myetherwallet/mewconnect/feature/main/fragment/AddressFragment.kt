package com.myetherwallet.mewconnect.feature.main.fragment

import android.app.Activity.RESULT_OK
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat.getSystemService
import androidx.appcompat.widget.Toolbar
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import com.myetherwallet.mewconnect.R
import com.myetherwallet.mewconnect.content.data.Network
import com.myetherwallet.mewconnect.core.di.ApplicationComponent
import com.myetherwallet.mewconnect.core.persist.prefenreces.PreferencesManager
import com.myetherwallet.mewconnect.core.ui.fragment.BaseDiFragment
import com.myetherwallet.mewconnect.core.utils.HexUtils
import kotlinx.android.synthetic.main.fragment_address.*
import net.glxn.qrgen.android.QRCode
import org.web3j.crypto.Keys
import javax.inject.Inject

/**
 * Created by BArtWell on 14.10.2018.
 */
class AddressFragment : BaseDiFragment() {

    companion object {

        const val CHOOSER_REQUEST_CODE = 101

        fun newInstance() = AddressFragment()
    }

    @Inject
    lateinit var preferences: PreferencesManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        address_toolbar.inflateMenu(R.menu.close)
        address_toolbar.setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener {
            close()
            true
        })

        val size = resources.getDimension(R.dimen.address_qr_size).toInt()

        if (preferences.applicationPreferences.getCurrentNetwork() == Network.ROPSTEN) {
            address_title.setText(R.string.address_title_ropsten)
        } else {
            address_title.setText(R.string.address_title_eth)
        }

        val address = HexUtils.withPrefix(Keys.toChecksumAddress(preferences.getCurrentWalletPreferences().getWalletAddress()))
        address_qr.setImageBitmap(QRCode.from(address).withHint(EncodeHintType.MARGIN, 0).withErrorCorrection(ErrorCorrectionLevel.H).withSize(size, size).bitmap())
        address_text.text = address

        address_share.setOnClickListener {
            val intent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, address)
                type = "text/plain"
            }
            startActivityForResult(Intent.createChooser(intent, resources.getText(R.string.address_share_title)), CHOOSER_REQUEST_CODE)
        }

        address_copy.setOnClickListener {
            val clipboard = getSystemService(requireContext(), ClipboardManager::class.java)
            clipboard!!.primaryClip = ClipData.newPlainText("", address)
            Toast.makeText(context, R.string.address_copied, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        setMaxBrightness(true)
    }

    override fun onPause() {
        setMaxBrightness(false)
        super.onPause()
    }

    private fun setMaxBrightness(isEnabled: Boolean) {
        val window = requireActivity().window
        val layout = window.attributes
        layout.screenBrightness = if (isEnabled)
            WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_FULL
        else
            WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE
        window.attributes = layout
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            close()
        }
    }

    override fun inject(appComponent: ApplicationComponent) {
        appComponent.inject(this)
    }

    override fun layoutId() = R.layout.fragment_address
}