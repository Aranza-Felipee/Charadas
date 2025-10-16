package com.example.charadas.data

object WordProvider {

    private val wordsByCategory = mapOf(
        "Animales" to listOf(
            "Perro", "Gato", "Elefante", "León", "Tigre", "Jirafa", "Mono", "Cebra", "Oso", "Caballo",
            "Vaca", "Pato", "Gallina", "Cerdo", "Oveja"
        ),
        "Películas" to listOf(
            "Titanic", "Avatar", "Star Wars", "Jurassic Park", "El Padrino", "Forrest Gump", "Pulp Fiction",
            "El Señor de los Anillos", "Harry Potter", "La La Land", "Matrix", "El Rey León", "Coco",
            "Toy Story", "Buscando a Nemo"
        ),
        "Profesiones" to listOf(
            "Médico", "Ingeniero", "Abogado", "Profesor", "Científico", "Artista", "Bombero", "Policía",
            "Chef", "Periodista", "Arquitecto", "Enfermero", "Músico", "Actor", "Deportista"
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

    fun getCategories(): List<String> {
        return wordsByCategory.keys.toList()
    }
    
    fun getWordCountForCategory(category: String): Int {
        return wordsByCategory[category]?.size ?: 0
    }

    fun resetUsedWords(category: String) {
        usedWords[category]?.clear()
    }

    fun resetAllUsedWords() {
        usedWords.clear()
    }
}
