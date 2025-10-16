package com.example.taller_1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.charadas.data.WordProvider
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {

    private val _gameState = MutableStateFlow(GameState())
    val gameState: StateFlow<GameState> = _gameState

    private var timerJob: Job? = null

    fun setGameData(playerCount: Int, roundCount: Int) {
        val teams = (1..playerCount).map { Team(id = it) }
        _gameState.update { it.copy(teams = teams, totalRounds = roundCount) }
    }

    fun selectCategory(category: String) {
        WordProvider.resetAllUsedWords()
        _gameState.update {
            it.copy(
                selectedCategory = category,
                isGameOver = false,
                currentRound = 0,
                teams = it.teams.map { team -> team.copy(score = 0, guessedWords = emptyList(), hasFinished = false) } // Reset teams
            )
        }
        startTurn()
    }

    private fun startTurn() {
        // Check for game over condition before starting a new turn
        if (_gameState.value.currentRound >= _gameState.value.totalRounds || _gameState.value.teams.all { it.hasFinished }) {
            finishGame()
            return
        }
        
        val newWord = WordProvider.getRandomWord(_gameState.value.selectedCategory)
        
        _gameState.update {
            it.copy(
                currentWord = newWord ?: "---", // Show placeholder if no words left
                timeLeft = 60,
                 currentRound = it.currentRound + 1
            )
        }
        startTimer()
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (_gameState.value.timeLeft > 0) {
                delay(1000)
                _gameState.update { it.copy(timeLeft = it.timeLeft - 1) }
            }
            endTurnAndFindNextPlayer()
        }
    }

    fun onCorrect() {
        val state = _gameState.value
        val currentTeamIndex = state.currentTeamIndex
        val currentWord = state.currentWord

        val updatedTeams = state.teams.toMutableList()
        val currentTeam = updatedTeams[currentTeamIndex]

        val newGuessedWords = currentTeam.guessedWords + currentWord
        updatedTeams[currentTeamIndex] = currentTeam.copy(
            score = currentTeam.score + 1,
            guessedWords = newGuessedWords
        )

        _gameState.update { it.copy(teams = updatedTeams) }

        val newWord = WordProvider.getRandomWord(state.selectedCategory)
        if (newWord == null) {
            // Team has guessed all the words
            val finishedTeamIndex = state.currentTeamIndex
            val teams = _gameState.value.teams.toMutableList()
            teams[finishedTeamIndex] = teams[finishedTeamIndex].copy(hasFinished = true)
            _gameState.update { it.copy(teams = teams) }
            
            endTurnAndFindNextPlayer()
        } else {
            _gameState.update { it.copy(currentWord = newWord) }
        }
    }

    fun onSkip() {
        val newWord = WordProvider.getRandomWord(_gameState.value.selectedCategory)
        _gameState.update { it.copy(currentWord = newWord ?: "---") }
    }

    private fun endTurnAndFindNextPlayer() {
        timerJob?.cancel()

        val nextTeamIndex = findNextActiveTeamIndex()
        if (nextTeamIndex == -1) {
            finishGame()
        } else {
            _gameState.update { it.copy(currentTeamIndex = nextTeamIndex) }
            startTurn()
        }
    }

    private fun findNextActiveTeamIndex(): Int {
        val teams = _gameState.value.teams
        var nextIndex = (_gameState.value.currentTeamIndex + 1) % teams.size
        repeat(teams.size) {
            if (!teams[nextIndex].hasFinished) {
                return nextIndex
            }
            nextIndex = (nextIndex + 1) % teams.size
        }
        return -1 // All teams have finished
    }

    private fun finishGame() {
        timerJob?.cancel()
        _gameState.update { it.copy(isGameOver = true) }
    }

    fun resetGame() {
        _gameState.value = GameState()
    }
}

data class GameState(
    val teams: List<Team> = emptyList(),
    val totalRounds: Int = 0,
    val currentRound: Int = 0,
    val selectedCategory: String = "",
    val currentWord: String = "",
    val timeLeft: Int = 60,
    val isGameOver: Boolean = false,
    val currentTeamIndex: Int = 0
)

data class Team(
    val id: Int,
    val score: Int = 0,
    val guessedWords: List<String> = emptyList(),
    val hasFinished: Boolean = false
)
