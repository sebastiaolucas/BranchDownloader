package br.com.marquesapps.branchdownloader

import android.app.Application
import br.com.marquesapps.branchdownloader.di.repositoryModule
import br.com.marquesapps.branchdownloader.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppApplication)
            modules(listOf(
                repositoryModule,
                viewModelModule
            ))
        }
    }

}