package pmdm.ar.tryexamendual.ui.tresEnRaya

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels

import pmdm.ar.tryexamendual.databinding.ScoreFragmentBinding
import pmdm.ar.tryexamendual.R
import pmdm.ar.tryexamendual.databinding.FragmentTresEnRayaBinding
import pmdm.ar.tryexamendual.databinding.ScoreTresEnRayaFragmentBinding


class ScoreTresEnRayaFragment : Fragment() {
    private var _binding: ScoreTresEnRayaFragmentBinding? = null
    private val binding get() = _binding!!

    //private val viewModel by activityViewModels<ViewModel>()
    private val viewModel: TresEnRayaViewModel
            by navGraphViewModels(R.id.navigation_adivina) {
                defaultViewModelProviderFactory
            }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        //_binding = ScoreTresEnRayaFragmentBinding().inflate(inflater, container, false)
        _binding = ScoreTresEnRayaFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.scoreText.text = viewModel.getGanador().toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
