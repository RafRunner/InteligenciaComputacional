package util

fun <E> List<E>.updated(index: Int, elem: E): List<E> {
    return this.mapIndexed { i, existingElem -> if (i == index) elem else existingElem }
}
