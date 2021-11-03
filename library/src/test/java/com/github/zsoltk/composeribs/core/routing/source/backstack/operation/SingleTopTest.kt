package com.github.zsoltk.composeribs.core.routing.source.backstack.operation

import com.github.zsoltk.composeribs.core.routing.source.backstack.BackStack.TransitionState.*
import com.github.zsoltk.composeribs.core.routing.source.backstack.BackStackElement
import com.github.zsoltk.composeribs.core.routing.source.backstack.UuidGenerator
import com.github.zsoltk.composeribs.core.routing.source.backstack.operation.Routing.*
import org.junit.Assert.assertEquals
import org.junit.Test

internal class SingleTopTest {

    @Test
    fun `is always applicable`() {

        val elements = emptyList<BackStackElement<Routing>>()
        val operation = SingleTop<Routing>(element = Routing1)

        val applicable = operation.isApplicable(elements)

        assertEquals(applicable, true)
    }

    @Test
    fun `performs a push when no element of same type`() {

        val elements = listOf<BackStackElement<Routing>>(
            backStackElement(
                element = Routing1,
                uuid = 1,
                fromState = STASHED_IN_BACK_STACK,
                targetState = STASHED_IN_BACK_STACK
            ),
            backStackElement(
                element = Routing2,
                uuid = 2,
                fromState = ON_SCREEN,
                targetState = ON_SCREEN
            )
        )
        val operation = SingleTop<Routing>(element = Routing3)

        val newElements = operation.invoke(elements = elements, uuidGenerator = UuidGenerator(2))

        val expectedElements = listOf<BackStackElement<Routing>>(
            backStackElement(
                element = Routing1,
                uuid = 1,
                fromState = STASHED_IN_BACK_STACK,
                targetState = STASHED_IN_BACK_STACK
            ),
            backStackElement(
                element = Routing2,
                uuid = 2,
                fromState = ON_SCREEN,
                targetState = STASHED_IN_BACK_STACK
            ),
            backStackElement(
                element = Routing3,
                uuid = 3,
                fromState = CREATED,
                targetState = ON_SCREEN
            )
        )
        assertEquals(newElements, expectedElements)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `crashes when one element of same type and same content but no element on screen`() {

        val elements = listOf<BackStackElement<Routing>>(
            backStackElement(
                element = Routing1,
                uuid = 1,
                fromState = STASHED_IN_BACK_STACK,
                targetState = STASHED_IN_BACK_STACK
            ),
            backStackElement(
                element = Routing2,
                uuid = 2,
                fromState = STASHED_IN_BACK_STACK,
                targetState = STASHED_IN_BACK_STACK
            ),
            backStackElement(
                element = Routing3,
                uuid = 3,
                fromState = STASHED_IN_BACK_STACK,
                targetState = STASHED_IN_BACK_STACK
            ),
            backStackElement(
                element = Routing4("Content 1"),
                uuid = 4,
                fromState = STASHED_IN_BACK_STACK,
                targetState = STASHED_IN_BACK_STACK
            )
        )
        val operation = SingleTop<Routing>(element = Routing4("Content 1"))

        operation.invoke(elements = elements, uuidGenerator = UuidGenerator(2))
    }

    @Test
    fun `destroys current element on screen and reactivate chosen one`() {

        val elements = listOf<BackStackElement<Routing>>(
            backStackElement(
                element = Routing1,
                uuid = 1,
                fromState = STASHED_IN_BACK_STACK,
                targetState = STASHED_IN_BACK_STACK
            ),
            backStackElement(
                element = Routing4("Content 1"),
                uuid = 4,
                fromState = STASHED_IN_BACK_STACK,
                targetState = STASHED_IN_BACK_STACK
            ),
            backStackElement(
                element = Routing2,
                uuid = 2,
                fromState = STASHED_IN_BACK_STACK,
                targetState = STASHED_IN_BACK_STACK
            ),
            backStackElement(
                element = Routing3,
                uuid = 3,
                fromState = ON_SCREEN,
                targetState = ON_SCREEN
            )
        )
        val operation = SingleTop<Routing>(element = Routing4("Content 1"))

        val newElements = operation.invoke(elements = elements, uuidGenerator = UuidGenerator(4))

        val expectedElements = listOf<BackStackElement<Routing>>(
            backStackElement(
                element = Routing1,
                uuid = 1,
                fromState = STASHED_IN_BACK_STACK,
                targetState = STASHED_IN_BACK_STACK
            ),
            backStackElement(
                element = Routing4("Content 1"),
                uuid = 4,
                fromState = STASHED_IN_BACK_STACK,
                targetState = ON_SCREEN
            ),
            backStackElement(
                element = Routing3,
                uuid = 3,
                fromState = ON_SCREEN,
                targetState = DESTROYED
            )
        )
        assertEquals(newElements, expectedElements)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `crashes when one element of same type and different content but no element on screen`() {

        val elements = listOf<BackStackElement<Routing>>(
            backStackElement(
                element = Routing1,
                uuid = 1,
                fromState = STASHED_IN_BACK_STACK,
                targetState = STASHED_IN_BACK_STACK
            ),
            backStackElement(
                element = Routing4("Content 1"),
                uuid = 4,
                fromState = STASHED_IN_BACK_STACK,
                targetState = STASHED_IN_BACK_STACK
            ),
            backStackElement(
                element = Routing2,
                uuid = 2,
                fromState = STASHED_IN_BACK_STACK,
                targetState = STASHED_IN_BACK_STACK
            ),
            backStackElement(
                element = Routing3,
                uuid = 3,
                fromState = STASHED_IN_BACK_STACK,
                targetState = STASHED_IN_BACK_STACK
            )
        )
        val operation = SingleTop<Routing>(element = Routing4("Content 2"))

        operation.invoke(elements = elements, uuidGenerator = UuidGenerator(2))
    }

    @Test
    fun `destroys current element on screen and activate new instance of chosen one`() {

        val elements = listOf<BackStackElement<Routing>>(
            backStackElement(
                element = Routing1,
                uuid = 1,
                fromState = STASHED_IN_BACK_STACK,
                targetState = STASHED_IN_BACK_STACK
            ),
            backStackElement(
                element = Routing4("Content 1"),
                uuid = 4,
                fromState = STASHED_IN_BACK_STACK,
                targetState = STASHED_IN_BACK_STACK
            ),
            backStackElement(
                element = Routing2,
                uuid = 2,
                fromState = STASHED_IN_BACK_STACK,
                targetState = STASHED_IN_BACK_STACK
            ),
            backStackElement(
                element = Routing3,
                uuid = 3,
                fromState = ON_SCREEN,
                targetState = ON_SCREEN
            )
        )
        val operation = SingleTop<Routing>(element = Routing4("Content 2"))

        val newElements = operation.invoke(elements = elements, uuidGenerator = UuidGenerator(4))

        val expectedElements = listOf<BackStackElement<Routing>>(
            backStackElement(
                element = Routing1,
                uuid = 1,
                fromState = STASHED_IN_BACK_STACK,
                targetState = STASHED_IN_BACK_STACK
            ),
            backStackElement(
                element = Routing3,
                uuid = 3,
                fromState = ON_SCREEN,
                targetState = DESTROYED
            ),
            backStackElement(
                element = Routing4("Content 2"),
                uuid = 5,
                fromState = CREATED,
                targetState = ON_SCREEN
            )
        )
        assertEquals(newElements, expectedElements)
    }
}
