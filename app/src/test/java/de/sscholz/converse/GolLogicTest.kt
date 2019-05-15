package de.sscholz.converse

import org.junit.Test

import org.junit.Assert.*

class GolLogicTest {
    @Test
    fun testNextStatus() {
        val gol = GolLogic()
        assertEquals(gol.isCellAliveNextIteration(true, 2), true)
        assertEquals(gol.isCellAliveNextIteration(true, 3), true)
        assertEquals(gol.isCellAliveNextIteration(true, 1), false)
        assertEquals(gol.isCellAliveNextIteration(true, 4), false)
        assertEquals(gol.isCellAliveNextIteration(false, 2), false)
        assertEquals(gol.isCellAliveNextIteration(false, 4), false)
        assertEquals(gol.isCellAliveNextIteration(false, 3), true)
    }

    @Test
    fun testGetSetAndSize() {
        val gol = GolLogic()
        assertEquals(gol[0,1], false)
        gol[0,1] = true
        assertEquals(gol[0,1], true)
        assertEquals(gol.size, Vector2i(1,1))
        gol[-1,5] = true
        assertEquals(gol.size, Vector2i(2,5))
        assertEquals(gol.min, Vector2i(-1,1))
        assertEquals(gol.max, Vector2i(0,5))
    }

    @Test
    fun testNextStep() {
        val gol = GolLogic()
        gol[0,0] = true
        gol[1,0] = true
        gol[-1,0] = true
        gol.stepForward()
        assertEquals(gol[-1,0], false)
        assertEquals(gol[1,0], false)
        assertEquals(gol[0,1], true)
        assertEquals(gol[0,-1], true)
        gol.stepBack()
        assertEquals(gol[-1,0], true)
        assertEquals(gol[1,0], true)
        assertEquals(gol[0,1], false)
        assertEquals(gol[0,-1], false)
    }
}
