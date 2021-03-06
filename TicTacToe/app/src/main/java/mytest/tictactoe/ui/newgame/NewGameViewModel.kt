package mytest.tictactoe.ui.newgame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mytest.tictactoe.domain.model.Player
import mytest.tictactoe.domain.repository.GamesRepository
import mytest.tictactoe.domain.repository.PlayersRepository
import mytest.tictactoe.result.ErrorType
import mytest.tictactoe.result.Result
import mytest.tictactoe.result.data
import javax.inject.Inject

@HiltViewModel
class NewGameViewModel @Inject constructor(
    private val playersRepository: PlayersRepository,
    private val gamesRepository: GamesRepository
) : ViewModel(){

    private val _players = MutableStateFlow<List<Player>>(emptyList())
    val players : StateFlow<List<Player>> = _players

    private val _error = MutableStateFlow<ErrorType?>(null)
    val error : StateFlow<ErrorType?> = _error

    private val _startTheGame = MutableStateFlow<Long?>(null)
    val startTheGame : StateFlow<Long?> = _startTheGame

    var playerX =""
    var playerO =""

    init {
        fetchPlayers()
    }

    private fun fetchPlayers() {
        viewModelScope.launch {
            val result = playersRepository.getPlayers()
            when(result){
                is Result.Success -> {
                    _players.value = result.data
                }
                is Result.Error -> {
                    _error.value = result.errorType
                }
            }
        }
    }

    private fun startNewGame(playerX: String, playerO: String) {
        viewModelScope.launch {
            val result =  playersRepository.insertPlayers(playerX, playerO)
            when(result){
                is Result.Success -> {
                    val players = result.data
                    _startTheGame.value = gamesRepository.startNewGame(players[0], players[1]).data
                }
                is Result.Error -> {
                    _error.value = result.errorType
                }
            }
        }
    }

    fun onStartClicked(){
        startNewGame(playerX, playerO)
    }

    fun onErrorShowed(){
        _error.value = null
    }

}
