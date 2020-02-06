package com.myetherwallet.mewconnect.content.api.rates

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RatesApiService
@Inject constructor(client: RatesClient) : RatesApi {

    private val ratesApi by lazy { client.retrofit.create(RatesApi::class.java) }

    override fun getTickerData(filter: String) = ratesApi.getTickerData(filter)
}
