@file:Suppress("JoinDeclarationAndAssignment")

package it.scoppelletti.spaceship.security.sample

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import it.scoppelletti.spaceship.app.appComponent
import it.scoppelletti.spaceship.app.hideSoftKeyboard
import it.scoppelletti.spaceship.lifecycle.ViewModelProviderEx
import it.scoppelletti.spaceship.security.sample.databinding.KeyFragmentBinding
import it.scoppelletti.spaceship.security.sample.lifecycle.KeyForm
import it.scoppelletti.spaceship.security.sample.lifecycle.KeyViewModel
import it.scoppelletti.spaceship.security.sample.lifecycle.MainViewModel

class KeyFragment : Fragment(R.layout.key_fragment) {

    private lateinit var mainModel: MainViewModel
    private lateinit var keyModel: KeyViewModel
    private val binding by viewBinding(KeyFragmentBinding::bind)

    init {
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtAlias.setOnEditorActionListener { _, _, _ ->
            onGenerate(keyModel.form)
            true
        }

        binding.txtExpire.setOnEditorActionListener { _, _, _ ->
            onGenerate(keyModel.form)
            true
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        val activity: FragmentActivity
        val viewModelProvider: ViewModelProviderEx

        super.onActivityCreated(savedInstanceState)

        activity = requireActivity()
        viewModelProvider = activity.appComponent().viewModelProvider()
        mainModel = ViewModelProvider(activity).get(MainViewModel::class.java)
        keyModel = viewModelProvider.get(this, KeyViewModel::class.java)
        binding.model = keyModel.form

        keyModel.state.observe(viewLifecycleOwner) { state ->
            if (state != null) {
                mainModel.setState(state)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.key, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.cmd_ok -> {
               onGenerate(keyModel.form)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun onGenerate(form: KeyForm) {
        try {
            requireActivity().hideSoftKeyboard()
            if (!form.validate()) {
                return
            }

            keyModel.createSecretKey(form.alias, form.expire)
        } catch (ex: RuntimeException) {
            keyModel.setError(ex)
        }
    }

    companion object {
        fun newInstance() = KeyFragment()
    }
}
