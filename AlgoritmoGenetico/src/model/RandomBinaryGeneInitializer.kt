package model

open class RandomBinaryGeneInitializer(private val wordCount: Int,
                                       private val wordSize: Int) {

    // Gera uma lista de 'wordCount' strings de 'wordSize' de tamanho contento 0 e 1 aleatoriamente
    fun initialize(): List<String> =
       List(wordCount) {
            List(wordSize) {
                if (Math.random() > 0.5) '1' else '0'
            }.joinToString("")
        }
}