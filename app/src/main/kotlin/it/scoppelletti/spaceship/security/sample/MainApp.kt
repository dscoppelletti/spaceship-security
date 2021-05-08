package it.scoppelletti.spaceship.security.sample

import android.app.Application
import it.scoppelletti.spaceship.html.inject.HtmlComponent
import it.scoppelletti.spaceship.html.inject.HtmlComponentProvider
import it.scoppelletti.spaceship.inject.AppComponent
import it.scoppelletti.spaceship.inject.StdlibComponent
import it.scoppelletti.spaceship.security.sample.inject.DaggerSampleComponent
import it.scoppelletti.spaceship.security.sample.inject.SampleComponent

@Suppress("unused")
class MainApp : Application(), HtmlComponentProvider {

    private lateinit var _sampleComponent: SampleComponent

    override fun onCreate() {
        super.onCreate()

        _sampleComponent = DaggerSampleComponent.factory()
                .create(this)
    }

    override fun appComponent(): AppComponent = _sampleComponent

    override fun htmlComponent(): HtmlComponent = _sampleComponent

    override fun stdlibComponent(): StdlibComponent = _sampleComponent
}
