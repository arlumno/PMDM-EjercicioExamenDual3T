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
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import pmdm.ar.tryexamendual.R
import pmdm.ar.tryexamendual.databinding.FragmentTresEnRayaBinding
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
//            button.text = it.toString()
//            viewModel.ganador?.let { // Comprobamos si el movimiento ha generado un ganador
//                findNavController().navigate(JuegoFragmentDirections.actionNavDest1ToNavDest2(it.toString()))
//                binding.winnerPlayerLabel.text = it.toString()
//                binding.winnerPlayerViewGroup.visibility = View.VISIBLE
//            }
//        }
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