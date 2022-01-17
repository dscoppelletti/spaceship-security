@file:Suppress("JoinDeclarationAndAssignment")

package it.scoppelletti.spaceship.security.sample

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import it.scoppelletti.spaceship.app.showExceptionDialog
import it.scoppelletti.spaceship.security.sample.databinding.MainActivityBinding
import it.scoppelletti.spaceship.security.sample.lifecycle.MainState
import it.scoppelletti.spaceship.security.sample.lifecycle.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val fragment = supportFragmentManager.findFragmentById(
                R.id.contentFrame)
        if (fragment == null) {
            navigateToFragment(R.id.cmd_key)
        }

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.state.observe(this) { state ->
            if (state != null) {
                stateObserver(state)
            }
        }
    }

    private fun stateObserver(state: MainState) {
        if (state.waiting) {
            binding.progressIndicator.show()
            return
        }

        binding.progressIndicator.hide()

        state.messageId?.poll()?.let { messageId ->
            Snackbar.make(binding.contentFrame, messageId,
                    Snackbar.LENGTH_SHORT).show()
        }

        state.error?.poll()?.let { err ->
            showExceptionDialog(err)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        MenuCompat.setGroupDividerEnabled(menu, true)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (navigateToFragment(item.itemId)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun navigateToFragment(itemId: Int): Boolean {
        val fragment: Fragment

        when (itemId) {
            R.id.cmd_key -> {
                fragment = KeyFragment.newInstance()
            }

            R.id.cmd_cipher -> {
                fragment = CipherFragment.newInstance()
            }

            R.id.cmd_providers -> {
                fragment = ProviderFragment.newInstance()
            }

            else -> {
                return false
            }
        }

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.contentFrame, fragment)
                .commit()

        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val fragment: Fragment?

        fragment = supportFragmentManager.findFragmentById(R.id.contentFrame)
        when (fragment) {
            is KeyFragment -> {
                menu?.findItem(R.id.cmd_key)?.isChecked = true
            }

            is CipherFragment -> {
                menu?.findItem(R.id.cmd_cipher)?.isChecked = true
            }

            is ProviderFragment -> {
                menu?.findItem(R.id.cmd_providers)?.isChecked = true
            }
        }

        return super.onPrepareOptionsMenu(menu)
    }
}
