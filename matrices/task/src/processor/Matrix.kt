package processor

import kotlin.math.absoluteValue
import kotlin.math.pow

/**
 * Generic matrix class
 */
class Matrix (data: MutableList<MutableList<Double>>) {
    val data: MutableList<MutableList<Double>>
    val nRows: Int
    val nCols: Int

    /**
     * Constructs a matrix.
     * @param nRows The number of rows of the matrix to create.
     * @param nCols The number of columns of the matrix to create.
     */
    constructor (nRows: Int, nCols: Int, default: Double = 0.0) :
            this(MutableList<MutableList<Double>>(nRows) { MutableList<Double>(nCols) { default } }) {}

    constructor (nRows: Int, nCols: Int, func: (Int, Int) -> Double) :
            this(MutableList<MutableList<Double>>(nRows) { x -> MutableList<Double>(nCols) { y -> func(x, y) } })

    init {
        if (data.isEmpty()) throw IllegalArgumentException("Matrix has zero rows.")
        if (data[0].isEmpty()) throw IllegalArgumentException("Matrix has zero columns.")
        if (!data.all { it.size == data[0].size }) throw IllegalArgumentException("Error with matrix dimensions.")
        else {
            this.data = data.map {it.toMutableList()}.toMutableList()
            this.nRows = this.data.size
            this.nCols = this.data[0].size
        }
    }

    /**
     * Get an element.
     * @param rowNum The row index of the element to fetch.
     * @param colNum The column index of the element to fetch.
     * @returns The element.
     * @throws IllegalArgumentException if either index is out of bounds.
     */
    operator fun get(rowNum: Int, colNum: Int): Double {
        if (rowNum < nRows && rowNum >= 0 && colNum < nCols && colNum >= 0) return data[rowNum][colNum]
        else throw IllegalArgumentException("Bad row or column.")
    }

    /**
     * Set an element.
     * @param rowNum The row index of the element to set.
     * @param colNum The column index of the element to set.
     * @param elem The element to set.
     * @throws IllegalArgumentException if either index is out of bounds.
     */
    operator fun set(rowNum: Int, colNum: Int, elem: Double) {
        if (rowNum < nRows && rowNum >= 0 && colNum < nCols && colNum >= 0) data[rowNum][colNum] = elem
        else throw IllegalArgumentException("Bad row or column.")
    }

    /**
     * Performs matrix addition.
     * @param other The other matrix.
     * @returns A matrix with its indices as a sum of the indices of A and B.
     * @throws IllegalArgumentException If the matrices aren't of the same type.
     */
    operator fun plus(other: Matrix): Matrix {
        if (nRows == other.nRows && nCols == other.nCols) {
            return Matrix(nRows, nCols) { x, y -> this[x, y] + other[x, y] }
        } else {
            throw IllegalArgumentException("Matrices aren't of the same type - can't add.")
        }
    }

    /**
     * Does scalar multiplication on a matrix.
     * @param k The scalar to multiply by.
     * @returns A new matrix with its elements multiplied by the scalar.
     */
    operator fun times(k: Double): Matrix {
        return Matrix(nRows, nCols) { x, y -> this[x, y] * k }
    }

    /**
     * Does matrix multiplication.
     * @param other The other matrix to multiply with.
     * @returns A new matrix with is the multiplication of both matrices.
     * @throws IllegalArgumentException If the matrix dimensions are incompatible.
     */
    operator fun times(other: Matrix): Matrix {
        if (nCols != other.nRows) throw IllegalArgumentException("Cannot multiply matrices with inconstant dimensions.")
        else {
            val newMatrix = Matrix(nRows, other.nCols)
            for (i in 0 until newMatrix.nRows) {
                for (j in 0 until newMatrix.nCols) {
                    for (k in 0 until nCols) {
                        newMatrix[i, j] = newMatrix[i, j] + this[i, k] * other[k, j]
                    }
                }
            }
            return newMatrix
        }
    }

    /**
     * Find the determinant of a square matrix recursively.
     * @returns The determinant of the matrix.
     * @throws IllegalStateException If the matrix calling this method is not square.
     */
    fun determinant(): Double {
        fun detHelper(mat: Matrix): Double {
            var det = 0.0
            when {
                mat.nRows != mat.nCols -> throw IllegalStateException("Determinants are only defined for square matrices.")
                mat.nRows == 1 -> det = mat[0, 0]
                mat.nRows == 2 -> det = (mat[0, 0] * mat[1, 1]) - (mat[0, 1] * mat[1, 0])
                else -> for (i in 0 until mat.nCols) {
                    det += (-1.0).pow(i + 2) * mat[0, i] * mat.reducedMatrix(0, i).determinant()
                }
            }
            return det
        }
        return detHelper(this)
    }

    /**
     * Returns the minor of the matrix according to the specified parameters.
     * The minor is the matrix amputated of the specified row and column.
     * @param rowNum The row to strip from the matrix.
     * @param colNum The column to strip for the matrix.
     * @returns The minor matrix.
     */
    fun reducedMatrix(rowNum: Int, colNum: Int): Matrix {
        return Matrix(data.filterIndexed { idx, mat -> idx != rowNum }
            .map { it.filterIndexed { idx, col -> idx != colNum }.toMutableList() }
            .toMutableList())
    }

    /**
     * Transposes the matrix across the main diagonal.
     * @returns The transposed matrix.
     */
    fun transpose(): Matrix {
        return Matrix(nCols, nRows) { x, y -> this[y, x] }
    }

    /**
     * Calculates the cofactor of the given element.
     * @param rowNum The row of the element to evaluate.
     * @param colNum The column of the element to evaluate.
     * @returns The cofactor of the element.
     */
    fun cofactor(rowNum: Int, colNum: Int): Double {
        return (-1.0).pow(rowNum + colNum) * reducedMatrix(rowNum, colNum).determinant()
    }

    /**
     * Calculates the cofactor matrix of the matrix.
     * @returns The cofactor matrix.
     */
    fun cofactorMatrix(): Matrix {
        return Matrix(nRows, nCols) { x, y -> cofactor(x, y) }
    }

    /**
     * Produces the adjugate matrix.
     * @returns The adjugate matrix.
     */
    fun adjugateMatrix(): Matrix {
        return cofactorMatrix().transpose()
    }

    /**
     * Inverses the matrix.
     * @returns A new matrix that is the inverse of the given matrix.
     */
    fun inverse() : Matrix {
        val det = determinant()
        if (det == 0.0) throw IllegalStateException("This matrix doesn't have an inverse.")
        val adjugate = adjugateMatrix()
        return Matrix(nRows, nCols) { x, y -> (1.0 / det) * adjugate[x, y] }
    }

    /**
     * Produces a deep copy of the matrix with the same elements.
     * @returns A deep copy of the object.
     */
    fun deepCopy(): Matrix {
        return Matrix(data.map {it.toMutableList()}.toMutableList())
    }

    /**
     * Displays a matrix in intelligible form.
     * @returns A string representing the matrix.
     */
    override fun toString(): String {
        val sb = StringBuilder()
        for (i in 0 until nRows) {
            for (j in 0 until nCols) {
                if (this[i, j] !in -1e-9..1e-9) sb.append(String.format("%.2f", this[i, j]))
                else sb.append(String.format("%.2f", this[i, j].absoluteValue))
                if (j != nCols - 1) sb.append(" ")
            }
            sb.append("\n")
        }
        return sb.toString()
    }
}