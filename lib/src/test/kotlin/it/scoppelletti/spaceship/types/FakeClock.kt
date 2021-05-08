package it.scoppelletti.spaceship.types

import java.time.Clock
import java.time.Instant
import java.time.ZoneId

class FakeClock(var impl: Clock) : Clock() {

    override fun instant(): Instant = impl.instant()

    override fun withZone(zone: ZoneId?): Clock = impl.withZone(zone)

    override fun getZone(): ZoneId = impl.zone
}
