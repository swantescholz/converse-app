package de.sscholz.converse

import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet

class GolLogic(val padding: Vector2i = Vector2i(2,2)) {
    private val oldStates = Stack<HashSet<Vector2i>>()
    private var aliveCells = HashSet<Vector2i>()

    val size: Vector2i
        get() = max-min + Vector2i.XY
    var min: Vector2i = Vector2i.ZERO
        private set
    var max: Vector2i = Vector2i.ZERO
        private set


    fun recomputeDimensions() {
        if (aliveCells.isEmpty()) {
            min = Vector2i.ZERO - padding
            max = Vector2i.ZERO + padding
        } else {
            min = Vector2i(
                    aliveCells.asSequence().map { it.x }.min()!!,
                    aliveCells.asSequence().map { it.y }.min()!!
            ) - padding
            max = Vector2i(
                    aliveCells.asSequence().map { it.x }.max()!!,
                    aliveCells.asSequence().map { it.y }.max()!!
            ) + padding
        }
    }


    fun isCellAliveNextIteration(isCellAliveNow: Boolean, numberOfAliveNeighbors: Int): Boolean {
        if (isCellAliveNow) {
            return numberOfAliveNeighbors in 2..3
        } else {
            return numberOfAliveNeighbors == 3
        }
    }

    operator fun set(x: Int, y: Int, isAlive: Boolean) {
        if (isAlive) {
            aliveCells.add(Vector2i(x, y))
        } else {
            aliveCells.remove(Vector2i(x, y))
        }
    }

    operator fun get(x: Int, y: Int): Boolean {
        return Vector2i(x, y) in aliveCells
    }

    operator fun contains(pos: Vector2i) = pos in aliveCells

    fun stepForward() {
        oldStates.add(aliveCells)
        val neighborCounts = HashMap<Vector2i, Int>()
        aliveCells.forEach{cell ->
            Vector2i.NESW8.forEach {off->
                val neighborPos = cell + off
                neighborCounts[neighborPos] = 1 + (neighborCounts[neighborPos]?:0)
            }
        }
        val newState = neighborCounts.filter { (key, numberOfNeighbors) -> isCellAliveNextIteration(key in aliveCells,
                numberOfNeighbors) }.map{it.key}.toHashSet()
        aliveCells = newState
    }

    fun stepBack() {
        if (oldStates.empty())
            return
        aliveCells = oldStates.pop()
    }

    operator fun get(pos: Vector2i): Boolean = this[pos.x, pos.y]
    operator fun set(pos: Vector2i, isAlive: Boolean) {
        this[pos.x, pos.y] = isAlive
    }

    fun gridViewPositionToGolCoord(position: Int): Vector2i {
        val x = position % size.w
        val y = size.h - 1 - (position / size.w)
        return min + Vector2i(x,y)
    }

    fun clear() {
        aliveCells.clear()
    }

    // dead chars must not be spaces!
    fun readString(s: String, aliveChar: Char = 'x') {
        clear()
        s.split("\n").map{it.trim()}.filter { !it.isEmpty() }.zip(0..Int.MAX_VALUE).forEach{(line,y)->
            for (x in 0..(line.length-1)) {
                val c = line[x]
                if (c == aliveChar) {
                    aliveCells.add(Vector2i(x,-y))
                }
            }
        }
    }

    fun rotateClockwise() {
        aliveCells = aliveCells.map { it.rotate90CW() }.toHashSet()
    }

    companion object Patterns {
        val cross = """
            .x.
            xxx
            .x.
            """
        val beacon = """
            xx..
            xx..
            ..xx
            ..xx
            """
        val glider = """
            .x.
            ..x
            xxx
            """
        val spaceship = """
            .xxxx
            x...x
            ....x
            x..x
            """
        val turtle = """
            xx......xx
            .xxxxxxxx.
            ..........
            .x......x.
            .x......x.
            ...x..x..
            .xx.xx.xx.
            ..xx..xx..
            x.x....x.x
            xx......xx
            xx.x..x.xx
            ....xx....
            """
        val acorn = """
            .x.....
            ...x...
            xx..xxx
            """
    }
}

