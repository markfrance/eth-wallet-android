package com.myetherwallet.mewconnect.content.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.web3j.crypto.RawTransaction
import java.math.BigInteger


@Parcelize
data class Transaction(
        val nonce: BigInteger,
        val gasPrice: BigInteger,
        val gas: BigInteger,
        val to: String?,
        val value: BigInteger,
        val data: String,
        val chainId: Long,
        val currency: TransactionCurrency?
) : BaseMessage(), Parcelable {

    fun toRawTransaction(): RawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gas, to, value, data)
}