package de.sscholz.converse

import java.util.*

data class Vector2i(val x: Int = 0, val y: Int = 0) {

    constructor(x: Long, y: Long) : this(x.toInt(), y.toInt())


    val w: Int
        get() = x
    val h: Int
        get() = y

    val width: Int
        get() = x
    val height: Int
        get() = y

    fun rotate90CW() = Vector2i(y, -x)
    fun rotate90CCW() = Vector2i(-y, x)

    fun length(): Double {
        return Math.sqrt(x.toDouble() * x + y * y)
    }

    fun length2(): Int {
        return x * x + y * y
    }

    fun dot(you: Vector2i): Int {
        return x * you.x + y * you.y
    }

    fun distanceTo(you: Vector2i): Double {
        return (this-you).length()
    }

    fun distanceTo2(you: Vector2i): Int {
        return (this-you).length2()
    }

    operator fun unaryMinus(): Vector2i {
        return Vector2i(-x, -y)
    }

    operator fun plus(o: Vector2i): Vector2i {
        return Vector2i(x + o.x, y + o.y)
    }

    operator fun minus(o: Vector2i): Vector2i {
        return Vector2i(x - o.x, y - o.y)
    }

    operator fun times(o: Int): Vector2i {
        return Vector2i(x * o, y * o)
    }

    fun isLeftOf(other: Vector2i) = x * other.y < other.x * y
    fun isRightOf(other: Vector2i) = x * other.y > other.x * y

    override fun toString(): String {
        return "($x, $y)"
    }

    companion object {
        val X = Vector2i(1, 0)
        val Y = Vector2i(0, 1)
        val XY = Vector2i(1, 1)
        val ZERO = Vector2i(0, 0)
        val ONE = Vector2i(1, 1)
        val NESW4 = arrayListOf(Vector2i(0,1), Vector2i(1,0), Vector2i(0,-1), Vector2i(-1,0))
        val NESW8 = arrayListOf(
                Vector2i(0,1), Vector2i(1,1),
                Vector2i(1,0), Vector2i(1,-1),
                Vector2i(0,-1), Vector2i(-1,-1),
                Vector2i(-1,0), Vector2i(-1,1))
    }

    fun toArrayList(): ArrayList<Int> {
        return arrayListOf(x, y)
    }

    fun negateY(): Vector2i {
        return Vector2i(x, -y)
    }

    fun min(other: Vector2i) = Vector2i(Math.min(x,other.x), Math.min(y,other.y))
    fun max(other: Vector2i) = Vector2i(Math.max(x,other.x), Math.max(y,other.y))


}

