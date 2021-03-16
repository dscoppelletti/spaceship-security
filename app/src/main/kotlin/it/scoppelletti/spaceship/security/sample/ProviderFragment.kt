@file:Suppress("JoinDeclarationAndAssignment")

package it.scoppelletti.spaceship.security.sample

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import it.scoppelletti.spaceship.app.hideSoftKeyboard
import it.scoppelletti.spaceship.security.sample.databinding.ProviderFragmentBinding
import it.scoppelletti.spaceship.security.sample.lifecycle.ProviderViewModel

class ProviderFragment : Fragment(R.layout.provider_fragment) {

    private lateinit var viewModel: ProviderViewModel
    private val binding by viewBinding(ProviderFragmentBinding::bind)

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        val activity: FragmentActivity

        super.onViewStateRestored(savedInstanceState)

        activity = requireActivity()

        activity.hideSoftKeyboard()
        viewModel = ViewModelProvider(this).get(ProviderViewModel::class.java)
        viewModel.state.observe(viewLifecycleOwner) { state ->
            if (state != null) {
                binding.txtContent.text = state
            }
        }

        viewModel.load()
    }

    companion object {
        fun newInstance() = ProviderFragment()
    }
}
