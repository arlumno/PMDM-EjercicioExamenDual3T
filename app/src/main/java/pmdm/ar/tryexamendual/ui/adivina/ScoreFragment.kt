package pmdm.ar.tryexamendual.ui.adivina

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels

import pmdm.ar.tryexamendual.databinding.ScoreFragmentBinding
import pmdm.ar.tryexamendual.R


class ScoreFragment : Fragment() {
    private var _binding: ScoreFragmentBinding? = null
    private val binding get() = _binding!!

    //private val viewModel by activityViewModels<ViewModel>()
    private val viewModel: AdivinaViewModel
            by navGraphViewModels(R.id.navigation_adivina) {
                defaultViewModelProviderFactory
            }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = ScoreFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.score.observe(viewLifecycleOwner) {
            binding.scoreText.text = it.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
