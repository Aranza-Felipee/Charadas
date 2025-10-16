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
        _gameState.update {
            it.copy(
                selectedCategory = category,
                isGameOver = false,
                currentRound = 1,
                currentTeamIndex = 0,
                roundWords = WordProvider.getShuffledWordsForCategory(category),
                teams = it.teams.map { team -> team.copy(totalScore = 0, roundsWon = 0) } // Reset stats
            )
        }
        startTurn()
    }

    private fun startTurn() {
        val state = _gameState.value

        // Reset turn-specific state
        _gameState.update { it.copy(isTurnOver = false) }

        val currentTeamIndex = state.currentTeamIndex
        val updatedTeams = state.teams.toMutableList()
        updatedTeams[currentTeamIndex] = updatedTeams[currentTeamIndex].copy(
            guessedWordsInTurn = emptyList(),
            skippedWordsInTurn = emptyList(),
            currentRoundScore = 0
        )
        _gameState.update { it.copy(teams = updatedTeams) }

        // Get first word and start timer
        advanceToNextWord(true)
        startTimer()
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (_gameState.value.timeLeft > 0) {
                delay(1000)
                _gameState.update { it.copy(timeLeft = it.timeLeft - 1) }
            }
            endTurn()
        }
    }

    private fun advanceToNextWord(isInitialWord: Boolean = false) {
        val state = _gameState.value
        val currentTeam = state.teams[state.currentTeamIndex]

        val seenWords = currentTeam.guessedWordsInTurn + currentTeam.skippedWordsInTurn
        val nextWord = state.roundWords.find { it !in seenWords }

        if (nextWord == null) {
            // All words for the round have been seen (guessed or skipped)
            endTurn()
        } else {
             if (isInitialWord) {
                _gameState.update { it.copy(currentWord = nextWord, timeLeft = 60) }
            } else {
                _gameState.update { it.copy(currentWord = nextWord) }
            }
        }
    }

    fun onCorrect() {
        val state = _gameState.value
        if (state.currentWord == "---") return

        val currentTeamIndex = state.currentTeamIndex
        val teams = state.teams.toMutableList()
        val team = teams[currentTeamIndex]

        teams[currentTeamIndex] = team.copy(
            currentRoundScore = team.currentRoundScore + 1,
            guessedWordsInTurn = team.guessedWordsInTurn + state.currentWord
        )
        _gameState.update { it.copy(teams = teams) }

        advanceToNextWord()
    }

    fun onSkip() {
        val state = _gameState.value
        if (state.currentWord == "---") return

        val currentTeamIndex = state.currentTeamIndex
        val teams = state.teams.toMutableList()
        val team = teams[currentTeamIndex]

        teams[currentTeamIndex] = team.copy(
            skippedWordsInTurn = team.skippedWordsInTurn + state.currentWord
        )
         _gameState.update { it.copy(teams = teams) }

        advanceToNextWord()
    }

    private fun endTurn() {
        timerJob?.cancel()
        _gameState.update { it.copy(isTurnOver = true) }
    }

    fun onNextTurn() {
        val state = _gameState.value
        val nextTeamIndex = (state.currentTeamIndex + 1) % state.teams.size

        if (nextTeamIndex == 0) {
            finishRound()
        } else {
            _gameState.update { it.copy(currentTeamIndex = nextTeamIndex) }
            startTurn()
        }
    }

    private fun finishRound() {
        val state = _gameState.value
        val roundWinner = state.teams.maxByOrNull { it.currentRoundScore }

        val updatedTeams = state.teams.map {
            it.copy(
                totalScore = it.totalScore + it.currentRoundScore,
                roundsWon = if (it.id == roundWinner?.id) it.roundsWon + 1 else it.roundsWon
            )
        }

        if (state.currentRound >= state.totalRounds) {
            _gameState.update { it.copy(teams = updatedTeams, isGameOver = true) }
            timerJob?.cancel()
        } else {
            _gameState.update { 
                it.copy(
                    teams = updatedTeams, 
                    currentRound = it.currentRound + 1, 
                    currentTeamIndex = 0,
                    roundWords = WordProvider.getShuffledWordsForCategory(it.selectedCategory)
                )
            }
            startTurn()
        }
    }

    fun pauseGame() {
        timerJob?.cancel()
        _gameState.update { it.copy(isPaused = true) }
    }

    fun resumeGame() {
        _gameState.update { it.copy(isPaused = false) }
        startTimer()
    }

    fun endGame() {
        timerJob?.cancel()
        _gameState.update { it.copy(isGameOver = true) }
    }

    fun resetGame() {
        _gameState.value = GameState()
    }
}

data class GameState(
    val teams: List<Team> = emptyList(),
    val totalRounds: Int = 3,
    val currentRound: Int = 0,
    val selectedCategory: String = "",
    val roundWords: List<String> = emptyList(),
    val currentWord: String = "",
    val timeLeft: Int = 60,
    val isGameOver: Boolean = false,
    val isTurnOver: Boolean = false,
    val isPaused: Boolean = false,
    val currentTeamIndex: Int = 0
)

data class Team(
    val id: Int,
    val totalScore: Int = 0,
    val currentRoundScore: Int = 0,
    val roundsWon: Int = 0,
    val guessedWordsInTurn: List<String> = emptyList(),
    val skippedWordsInTurn: List<String> = emptyList()
)
