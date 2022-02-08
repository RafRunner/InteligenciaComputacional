package model

open class RandomBinaryGeneInitializer(private val wordCount: Int,
                                       private val wordSize: Int) {

    fun initialize(): List<String> =
       List(wordCount) {
            List(wordSize) {
                if (Math.random() > 0.5) '1' else '0'
            }.joinToString("")
        }
}