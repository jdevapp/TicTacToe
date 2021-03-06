package mytest.tictactoe.domain.repository

import mytest.tictactoe.domain.model.Player
import mytest.tictactoe.result.Result

/**
 *
 * Interface to the data layer
 */
interface PlayersRepository {
    /**
     *
     * Return a list of [Player]s.
     */
    suspend fun getPlayers(): Result<List<Player>>
    /**
     *
     * Insert new [Player]s.
     */
    suspend fun insertPlayers(playerX: String, playerO: String): Result<List<Player>>
}