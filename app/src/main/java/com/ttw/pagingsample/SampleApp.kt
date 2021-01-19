package com.ttw.pagingsample

import android.app.Application
import com.ttw.pagingsample.api.MovieApi
import com.ttw.pagingsample.api.NetworkConnectionInterceptor
import com.ttw.pagingsample.database.MovieDatabase
import com.ttw.pagingsample.repository.MoviePagingSource
import com.ttw.pagingsample.repository.MovieRepository
import com.ttw.pagingsample.ui.MovieViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.bindings.Singleton
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import org.kodein.di.newInstance

class SampleApp : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@SampleApp))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { MovieApi(instance()) }
        bind() from singleton { MovieViewModelFactory(instance(),instance()) }
        bind() from singleton { MovieRepository(instance(),instance()) }
        bind() from singleton { MoviePagingSource(instance()) }
        bind() from singleton { MovieDatabase(instance()) }
    }

    }