package pmdm.ar.tryexamendual.ui.adivina

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navGraphViewModels
import com.google.android.material.snackbar.Snackbar
import pmdm.ar.tryexamendual.R
import pmdm.ar.tryexamendual.databinding.FragmentAdivinaBinding

class AdivinaFragment : Fragment() {

    private var _binding: FragmentAdivinaBinding? = null

    private val binding get() = _binding!!
    private val viewModel: AdivinaViewModel
            by navGraphViewModels(R.id.navigation_adivina) {
                defaultViewModelProviderFactory
            }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val adivinaViewModel =
//            ViewModelProvider(this).get(AdivinaViewModel::class.java)
//
        _binding = FragmentAdivinaBinding.inflate(inflater, container, false)
//        val root: View = binding.root
//
//        val textView: TextView = binding.textAdivina
//        adivinaViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.correctButton.setOnClickListener { viewModel.onCorrect() }
        binding.skipButton.setOnClickListener { viewModel.onSkip() }
        binding.endGameButton.setOnClickListener { onEndGame() }


        viewModel.word.observe(viewLifecycleOwner) {
            binding.wordText.text = it
        }

        viewModel.score.observe(viewLifecycleOwner) {
            binding.scoreText.text = it.toString()
        }

        if(viewModel.gameOver.value == true){
            viewModel.resetGame()
        }

        viewModel.gameOver.observe(viewLifecycleOwner) {
            if(it){
                Snackbar.make(view, "Se acabó el juego.", Snackbar.LENGTH_LONG).setAction("Reiniciar"){
                    viewModel.resetGame()
                }.show()
            }
        }
    }


    private fun onEndGame() {
        Toast.makeText(activity, "Game has just finished", Toast.LENGTH_SHORT).show()
        viewModel.finishGame()
        NavHostFragment.findNavController(this).navigate(AdivinaFragmentDirections.actionNavAdivinaToScoreFragment());
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}