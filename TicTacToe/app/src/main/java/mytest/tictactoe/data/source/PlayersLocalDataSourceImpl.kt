package mytest.tictactoe.data.source

import mytest.tictactoe.result.Result
import mytest.tictactoe.data.source.db.PlayersDao
import mytest.tictactoe.data.source.mapper.PlayerMapper
import mytest.tictactoe.domain.model.Player
import javax.inject.Inject

class PlayersLocalDataSourceImpl @Inject constructor(
    private val playerDao: PlayersDao,
    private val playerMapper: PlayerMapper
): PlayersLocalDataSource{

    override suspend fun getPlayers(): Result<List<Player>> {
        return try{
            Result.Success(
                playerMapper.mapFromEntityList(playerDao.getAll())
            )
        }catch(e: Exception){
            Result.Error(e)
        }
    }

    override suspend fun getPlayerByName(name: String): Result<Player> {
        return try{
            Result.Success(playerMapper.mapFromEntity(playerDao.findPlayerByName(name)))
        }catch(e: Exception){
            Result.Error(e)
        }
    }

    override suspend fun getPlayersByNames(vararg playersName: String): Result<List<Player>> {
        return try{
            val players = ArrayList<Player>()
            playersName.forEach { name ->
                players.add(playerMapper.mapFromEntity(playerDao.findPlayerByName(name)))
            }
            Result.Success( players )
        }catch(e: Exception){
            Result.Error(e)
        }
    }

    override suspend fun insertPlayers(vararg players: Player): Result<Boolean> {
        return try{
            playerDao.insertAll(*playerMapper.mapToEntityList(players.toList()).toTypedArray())
            Result.Success(true)
        }catch(e: Exception){
            Result.Error(e)
        }
    }

}