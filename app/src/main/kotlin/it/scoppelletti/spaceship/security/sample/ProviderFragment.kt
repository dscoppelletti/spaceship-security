@file:Suppress("JoinDeclarationAndAssignment")

package it.scoppelletti.spaceship.security.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import it.scoppelletti.spaceship.app.hideSoftKeyboard
import it.scoppelletti.spaceship.security.sample.databinding.ProviderFragmentBinding
import it.scoppelletti.spaceship.security.sample.lifecycle.ProviderViewModel

class ProviderFragment : Fragment() {

    private lateinit var viewModel: ProviderViewModel
    private var binding: ProviderFragmentBinding? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.provider_fragment,
                container, false)
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        val activity: FragmentActivity

        super.onActivityCreated(savedInstanceState)

        activity = requireActivity()

        activity.hideSoftKeyboard()
        viewModel = ViewModelProvider(this).get(ProviderViewModel::class.java)
        viewModel.state.observe(viewLifecycleOwner) { state ->
            if (state != null) {
                binding?.txtContent?.text = state
            }
        }

        viewModel.load()
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    companion object {
        fun newInstance() = ProviderFragment()
    }
}
