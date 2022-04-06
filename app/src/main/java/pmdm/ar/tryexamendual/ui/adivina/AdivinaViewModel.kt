package pmdm.ar.tryexamendual.ui.adivina

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pmdm.ar.tryexamendual.R

class AdivinaViewModel(application: Application) : AndroidViewModel(application) {

    private val resources = application.resources

    var word = MutableLiveData("")
    var score = MutableLiveData(0)
    var gameOver = MutableLiveData(false)
    private lateinit var wordList: MutableList<String>


    init {
        Log.i("GameViewModel", "GameViewModel created!")
        resetGame()
    }

    fun resetGame() {
        word.value = ""
        score.value = 0
        gameOver = MutableLiveData(false);
        resetList()
        nextWord()
    }
    private fun resetList() {
        wordList = resources.getStringArray(R.array.palabras).toMutableList().apply { shuffle() }
        // TODO: Igual tiene sentido cargarla primero y solo barajar en cada reset?
    }
    fun finishGame(){
        gameOver = MutableLiveData(true);
    }

    /**
     * Callback called when the ViewModel is destroyed
     */
    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel destroyed!")
    }

    /** Methods for updating the UI **/
    fun onSkip() {
        // https://stackoverflow.com/questions/57219811/is-it-possible-to-increment-mutablelivedata-without-additional-variable
        if(gameOver.value == false){
            score.value?.let { score.value = it-1 }
            nextWord()
        }
    }

    fun onCorrect() {
        if(gameOver.value == false){
            score.value?.let { score.value = it+1 }
            nextWord()
        }
    }

    /**
     * Moves to the next word in the list.
     */
    private fun nextWord() {
        if (wordList.isNotEmpty()) {
            word.postValue(wordList.removeAt(0))
        }else{
            gameOver.value = true;
        }
    }
}