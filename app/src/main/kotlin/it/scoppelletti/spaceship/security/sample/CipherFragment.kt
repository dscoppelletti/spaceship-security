@file:Suppress("JoinDeclarationAndAssignment")

package it.scoppelletti.spaceship.security.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import it.scoppelletti.spaceship.app.appComponent
import it.scoppelletti.spaceship.app.hideSoftKeyboard
import it.scoppelletti.spaceship.lifecycle.ViewModelProviderEx
import it.scoppelletti.spaceship.security.sample.databinding.CipherFragmentBinding
import it.scoppelletti.spaceship.security.sample.lifecycle.CipherForm
import it.scoppelletti.spaceship.security.sample.lifecycle.CipherViewModel
import it.scoppelletti.spaceship.security.sample.lifecycle.MainViewModel

class CipherFragment : Fragment() {
    private lateinit var mainModel: MainViewModel
    private lateinit var cipherModel: CipherViewModel
    private lateinit var binding: CipherFragmentBinding

    init {
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.cipher_fragment,
                container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        val activity: FragmentActivity
        val viewModelProvider: ViewModelProviderEx

        super.onActivityCreated(savedInstanceState)

        activity = requireActivity()
        viewModelProvider = activity.appComponent().viewModelProvider()
        mainModel = ViewModelProvider(activity).get(MainViewModel::class.java)
        cipherModel = viewModelProvider.get(this, CipherViewModel::class.java)
        binding.model = cipherModel.form

        cipherModel.state.observe(viewLifecycleOwner) { state ->
            if (state != null) {
                mainModel.setState(state)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.cipher, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.cmd_encrypt -> {
                onEncrypt(cipherModel.form)
                return true
            }

            R.id.cmd_decrypt -> {
                onDecrypt(cipherModel.form)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun onEncrypt(form: CipherForm) {
        try {
            requireActivity().hideSoftKeyboard()
            if (!form.validate()) {
                return
            }

            cipherModel.encrypt(form.alias, form.plainText)
        } catch (ex: RuntimeException) {
            cipherModel.setError(ex)
        }
    }

    private fun onDecrypt(form: CipherForm) {
        try {
            requireActivity().hideSoftKeyboard()
            if (!form.validate()) {
                return
            }

            cipherModel.decrypt(form.alias, form.cipherText)
        } catch (ex: RuntimeException) {
            cipherModel.setError(ex)
        }
    }

    companion object {
        fun newInstance() = CipherFragment()
    }
}