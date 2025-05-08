package processor

object Util {
    fun formatRowData(row: String): MutableList<Double> {
        return row.split(" ").map { it.toDouble() }.toMutableList()
    }
}