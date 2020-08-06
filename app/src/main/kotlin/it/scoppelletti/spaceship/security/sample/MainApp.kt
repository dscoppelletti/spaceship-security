package it.scoppelletti.spaceship.security.sample

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import it.scoppelletti.spaceship.inject.AppComponent
import it.scoppelletti.spaceship.inject.AppComponentProvider
import it.scoppelletti.spaceship.inject.StdlibComponent
import it.scoppelletti.spaceship.security.sample.inject.DaggerSampleComponent
import it.scoppelletti.spaceship.security.sample.inject.SampleComponent

@Suppress("unused")
class MainApp : Application(), AppComponentProvider {

    private lateinit var _sampleComponent: SampleComponent

    override fun onCreate() {
        super.onCreate()

        AndroidThreeTen.init(this)
        _sampleComponent = DaggerSampleComponent.factory()
                .create(this)
    }

    override fun appComponent(): AppComponent = _sampleComponent

    override fun stdlibComponent(): StdlibComponent = _sampleComponent
}
