package it.scoppelletti.spaceship.io

import java.io.File
import java.nio.file.Files

class FakeIOProvider : IOProvider {
    private val _dataDir: File = Files.createTempDirectory("data").toFile()

    override val noBackupFilesDir: File = _dataDir
}
