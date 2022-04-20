package pmdm.ar.tryexamendual.ui.tresEnRaya

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import pmdm.ar.tryexamendual.R
import pmdm.ar.tryexamendual.databinding.FragmentTresEnRayaBinding
import pmdm.ar.tryexamendual.ui.adivina.AdivinaFragmentDirections
import pmdm.ar.tryexamendual.ui.adivina.AdivinaViewModel

class TresEnRayaFragment : Fragment() {

    private var _binding: FragmentTresEnRayaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: TresEnRayaViewModel
            by navGraphViewModels(R.id.navigation_tres) {
                defaultViewModelProviderFactory
            }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val tresEnRayaViewModel =
//            ViewModelProvider(this).get(TresEnRayaViewModel::class.java)

        _binding = FragmentTresEnRayaBinding.inflate(inflater, container, false)

        binding.buttonGrid.setOnClickListener(::onCellClicked) // Asignamos comportamiento a los botones
//        Toast.makeText(activity,"here",Toast.LENGTH_SHORT).show()
        if( viewModel.estado == TresEnRayaViewModel.GameState.TERMINADO){
            reset()
        }
        binding.btReset.setOnClickListener(){
            reset()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /** Método que se lanza cuando se hace click en una celda */
    private fun onCellClicked(button: Button) {
        if(binding.btReset.visibility == View.INVISIBLE){
            binding.btReset.visibility = View.VISIBLE
        }
        val tag = button.tag.toString().toCharArray()
        val row = tag[0].digitToInt()
        val col = tag[1].digitToInt()
        Log.e("[Ar...]",row.toString() + " / " + col.toString())
        viewModel.marcar(row, col)

//        viewModel.marcar(row, col)?.let {  //jugadorQueMovio ->

//            viewModel.ganador?.let { // Comprobamos si el movimiento ha generado un ganador
//                findNavController().navigate(JuegoFragmentDirections.actionNavDest1ToNavDest2(it.toString()))
//                binding.winnerPlayerLabel.text = it.toString()
//                binding.winnerPlayerViewGroup.visibility = View.VISIBLE
//            }
//        }
        viewModel.jugadorEnTurno.observe(viewLifecycleOwner){
            Log.e("[Ar...]",viewModel.estado.toString())
            Log.e("[Ar...]",viewModel.getGanador().toString())
            var auxBut: Button
            var tag: String
            for (i in 0 until binding.buttonGrid.childCount) {
                auxBut = (binding.buttonGrid.getChildAt(i) as Button)
                tag = (viewModel.lastMove?.get(0).toString()) + viewModel.lastMove?.get(1).toString()
                if(auxBut.tag == tag){
                    auxBut.text = viewModel.lastPlayer.toString()
                }
            }
            viewModel.getGanador()?.let { // Comprobamos si el movimiento ha generado un ganador
                NavHostFragment.findNavController(this).navigate(TresEnRayaFragmentDirections.actionNavTresEnRayaToScoreTresEnRayaFragment());
            }
        }
    }

    private fun reset() {
        binding.btReset.visibility = View.INVISIBLE
        viewModel.reiniciar()
        for (i in 0 until binding.buttonGrid.childCount) {
            (binding.buttonGrid.getChildAt(i) as Button).text = null
        }
    }

    private fun GridLayout.setOnClickListener(onClick: (Button) -> Unit) {
        for (i in 0 until childCount) {
            val boton = getChildAt(i) as Button
            boton.setOnClickListener {
                onClick(boton)
            }
        }
    }
}