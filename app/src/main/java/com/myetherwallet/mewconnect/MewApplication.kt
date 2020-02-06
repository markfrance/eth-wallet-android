package com.myetherwallet.mewconnect

import android.app.Application
import com.myetherwallet.mewconnect.core.di.ApplicationComponent
import com.myetherwallet.mewconnect.core.di.ApplicationModule
import com.myetherwallet.mewconnect.core.di.DaggerApplicationComponent
import com.myetherwallet.mewconnect.core.persist.prefenreces.PreferencesManager
import com.myetherwallet.mewconnect.core.persist.prefenreces.PreferencesModule
import com.myetherwallet.mewconnect.core.utils.DisplaySizeHelper
import javax.inject.Inject


class MewApplication : Application() {

    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .preferencesModule(PreferencesModule(this))
                .build()
    }

    @Inject
    lateinit var preferences: PreferencesManager

    override fun onCreate() {
        super.onCreate()
        injectMembers()

        DisplaySizeHelper.init()
        preferences.applicationPreferences.setInstallTime()
    }

    private fun injectMembers() = appComponent.inject(this)
}