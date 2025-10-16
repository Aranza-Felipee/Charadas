package com.example.charadas.data

object WordProvider {

    private val wordsByCategory = mapOf(
        "Animales" to listOf(
            "Perro", "Gato", "Elefante", "León", "Tigre", "Jirafa", "Mono", "Cebra", "Oso", "Caballo",
            "Vaca", "Pato", "Gallina", "Cerdo", "Oveja", "Rinoceronte", "Hipopótamo", "Cocodrilo",
            "Serpiente", "Loro", "Águila", "Delfín", "Ballena", "Canguro", "Koala"
        ),
        "Películas" to listOf(
            "Titanic", "Avatar", "Star Wars", "Jurassic Park", "El Padrino", "Forrest Gump", "Pulp Fiction",
            "El Señor de los Anillos", "Harry Potter", "La La Land", "Matrix", "El Rey León", "Coco",
            "Toy Story", "Buscando a Nemo", "Gladiador", "Interestelar", "El Origen", "Rocky",
            "Volver al Futuro", "E.T.", "Spider-Man", "Shrek", "Los Vengadores", "Mi Villano Favorito"
        ),
        "Profesiones" to listOf(
            "Médico", "Ingeniero", "Abogado", "Profesor", "Científico", "Artista", "Bombero", "Policía",
            "Chef", "Periodista", "Arquitecto", "Enfermero", "Músico", "Actor", "Deportista",
            "Psicólogo", "Veterinario", "Contador", "Diseñador Gráfico", "Traductor", "Electricista",
            "Mecánico", "Piloto", "Escritor", "Fotógrafo"
        )
    )

     private val usedWords = mutableMapOf<String, MutableSet<String>>()

    fun getRandomWord(category: String): String? {
        val words = wordsByCategory[category] ?: return null
        val availableWords = words.filterNot { it in (usedWords[category] ?: emptySet()) }

        if (availableWords.isEmpty()) {
            return null // No more words available in this category
        }

        val word = availableWords.random()
        usedWords.getOrPut(category) { mutableSetOf() }.add(word)
        return word
    }

    fun getShuffledWordsForCategory(category: String): List<String> {
        return wordsByCategory[category]?.shuffled() ?: emptyList()
    }

    fun getCategories(): List<String> {
        return wordsByCategory.keys.toList()
    }

    fun getWordCountForCategory(category: String): Int {
        return wordsByCategory[category]?.size ?: 0
    }

    // This is kept for now but the main logic will handle resets.
    fun resetUsedWords(category: String) {
        usedWords[category]?.clear()
    }

    fun resetAllUsedWords() {
        usedWords.clear()
    }
}
