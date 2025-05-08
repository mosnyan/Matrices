package processor

class TransposeCommand : Command {

    override fun execute() {
        println("1. Main diagonal")
        println("2. Side diagonal")
        println("3. Vertical line")
        println("4. Horizontal line")
        print("Your choice: ")
        val type = readln().toInt()
        print("Enter size of matrix: ")
        val (rowA, colA) = readln().split(" ").map {it.toInt()}
        println("Enter matrix: ")
        val A = Matrix(MutableList(rowA) { Util.formatRowData(readln()) })
        println("The result is: ")
        println(when (type) {
            1 -> mainDiagonalTranspose(A)
            2 -> sideDiagonalTranspose(A)
            3 -> verticalLineTranspose(A)
            4 -> horizontalLineTranspose(A)
            else -> throw IllegalArgumentException("Bad choice")
        })

    }

    private fun mainDiagonalTranspose(mat: Matrix): Matrix {
        return mat.transpose()
    }

    private fun sideDiagonalTranspose(mat: Matrix): Matrix {
        return Matrix(mat.nRows, mat.nCols) { x, y -> mat[mat.nRows - 1 - x, mat.nCols - 1 - y] }
    }

    private fun verticalLineTranspose(mat: Matrix): Matrix {
        return Matrix(mat.data.map { it.reversed().toMutableList() }.toMutableList())
    }

    private fun horizontalLineTranspose(mat: Matrix): Matrix {
        return Matrix(mat.nRows, mat.nCols) { x, y -> mat[mat.nRows - 1 - x, y] }
    }

    override fun name(): String {
        return "Transpose matrix"
    }

}