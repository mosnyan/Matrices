package processor

class ScMulCommand : Command {
    override fun execute() {
        print("Enter size of matrix: ")
        val (rowA, colA) = readln().split(" ").map {it.toInt()}
        println("Enter matrix: ")
        val A = Matrix(MutableList(rowA) { Util.formatRowData(readln()) })
        print("Enter constant: ")
        val k = readln().toDouble()

        println("The result is: ")
        println(A * k)
    }

    override fun name(): String {
        return "Multiply matrix by a constant"
    }
}