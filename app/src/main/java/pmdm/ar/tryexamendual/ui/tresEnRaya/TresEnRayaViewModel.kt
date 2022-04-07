package pmdm.ar.tryexamendual.ui.tresEnRaya

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TresEnRayaViewModel : ViewModel() {

    // Definimos 2 jugadores identificados por los caracteres 'X' y 'O', que serán los que se escriban en la celda
    var tablero = MutableLiveData(
                    Array(3) {
                            arrayOfNulls<Celda>(3)
                        }
                    )

    enum class Jugador { X, O }

    class Celda {
        var value: Jugador? =
            null // Cada celda puede estar vacía o con el valor de un jugador ('O' o 'X')
    }


    private val celdas = Array(3) {
        arrayOfNulls<Celda>(
            3
        )
    } // El tablero se compone de 3x3 celdas


    private var ganador: Jugador? = null
    fun getGanador(): Jugador? {
        return ganador
    }

    enum class GameState {
        JUGANDO, TERMINADO
    }

    var estado: GameState? = null
    var lastMove: List<Int>? = null
    lateinit var lastPlayer: Jugador
    var jugadorEnTurno = MutableLiveData<Jugador>()
//    private var jugadorEnTurno: Jugador? = null

    init {
        reiniciar()
    }

    fun reiniciar() {
        clearCells()
        ganador = null
        estado = GameState.JUGANDO
        jugadorEnTurno.value= Jugador.X
        lastMove = null
        lastPlayer = Jugador.O
    }

    /**
     * Método que marca la celda indicada por los párametros con el caracter del jugador en turno.
     *
     * @param row 0..2
     * @param col 0..2
     * @return (devuelve) el jugador en turno o null si no se ha podido marcar (celda inválida o partida acabada)
     */
//    fun marcar(row: Int, col: Int): Jugador? {
//        if (estado == GameState.TERMINADO) return null // No se sigue marcando si el juego ha terminado
//        if (!isValida(row, col)) return null // Celda inválida (la vista ya no debería permitirlo
//        val jugadorQueMovio = jugadorEnTurno
//        celdas[row][col]!!.value = jugadorQueMovio
//        if (isMovimientoGana(jugadorQueMovio, row, col)) {
//            estado = GameState.TERMINADO
//            ganador = jugadorQueMovio
//        } else {
//            cambiarTurno() // Cambia el Jugador en turno
//        }
//        return jugadorQueMovio
//    }
    fun marcar(row: Int, col: Int) {
//        if (!isValida(row, col)) return Unit // Celda inválida (la vista ya no debería permitirlo
//        if (estado != GameState.TERMINADO)// No se sigue marcando si el juego ha terminado
        if (estado != GameState.TERMINADO && isValida(row, col)) {
            lastPlayer = jugadorEnTurno.value!!
            celdas[row][col]!!.value = lastPlayer
            lastMove = listOf<Int>(row,col)
            if (isMovimientoGana(lastPlayer, row, col)) {
                estado = GameState.TERMINADO
                ganador = lastPlayer
            } else {
                cambiarTurno() // Cambia el Jugador en turno
            }
        }
//        return jugadorQueMovio
    }

    private fun clearCells() {
        for (i in 0..2) {
            for (j in 0..2) {
                celdas[i][j] = Celda()
            }
        }
    }

    private fun isValida(row: Int, col: Int): Boolean {
        var valid = true
        if (isOutOfBounds(row) || isOutOfBounds(col) ||
            isCeldaConValor(row, col)
        ) valid = false
        return valid
    }

    private fun isOutOfBounds(idx: Int): Boolean {
        return idx < 0 || idx > 2
    }

    private fun isCeldaConValor(row: Int, col: Int): Boolean {
        return celdas[row][col]!!.value != null
    }


    /**
     * Devuelve true si el movimiento gana
     */
    private fun isMovimientoGana(player: Jugador?, fila: Int, columna: Int): Boolean {
        return (celdas[fila][0]!!.value == player // 3-in-the-row
                && celdas[fila][1]!!.value == player && celdas[fila][2]!!.value == player
                ) || (celdas[0][columna]!!.value == player // 3-in-the-column
                && celdas[1][columna]!!.value == player && celdas[2][columna]!!.value == player
                ) || (fila == columna // 3-in-the-diagonal
                && celdas[0][0]!!.value == player && celdas[1][1]!!.value == player && celdas[2][2]!!.value == player
                ) || (fila + columna == 2 // 3-in-the-opposite-diagonal
                && celdas[0][2]!!.value == player && celdas[1][1]!!.value == player && celdas[2][0]!!.value == player)
    }

    private fun cambiarTurno() {
        jugadorEnTurno.value= if (jugadorEnTurno.value == Jugador.X) Jugador.O else Jugador.X
    }
}