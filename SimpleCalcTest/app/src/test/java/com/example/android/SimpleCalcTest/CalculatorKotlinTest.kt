package com.example.android.SimpleCalcTest

import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test

/**
 * JUnit4 unit tests for the calculator logic.
 * These are local unit tests; no device needed.
 */
class CalculatorKotlinTest {

    private lateinit var mCalculator : Calculator
    @Before
    fun setUp() {
        mCalculator = Calculator()
    }

    /**
     * Tests for simple addition.
     */
    @Test
    fun addTwoNumber() {
        var resultAdd = mCalculator.add(1.0, 1.0)
        Truth.assertThat(resultAdd).isEqualTo(2.0)
    }

    /**
     * Tests for addition with a negative operand.
     */
    @Test
    fun addTwoNumbersNegative() {
        var resultAdd = mCalculator.add(-1.0, 2.0)
        Truth.assertThat(resultAdd).isEqualTo(1.0)
    }

    /**
     * Tests for addition where the result is negative.
     */
    @Test
    fun addTwoNumbersWorksWithNegativeResult() {
        val resultAdd = mCalculator.add(-1.0, -17.0)
        Truth.assertThat(resultAdd).isEqualTo(-18.0)
    }

    /**
     * Tests for simple subtraction.
     */
    @Test
    fun subTwoNumbers() {
        val resultSub = mCalculator.sub(1.0, 1.0)
        Truth.assertThat(resultSub).isEqualTo(0.0)
    }

    /**
     * Tests for simple subtraction with a negative result.
     */
    @Test
    fun subWorksWithNegativeResult() {
        val resultSub = mCalculator.sub(1.0, 17.0)
        Truth.assertThat(resultSub).isEqualTo(-16.0)
    }

    /**
     * Tests for simple multiplication.
     */
    @Test
    fun mulTwoNumbers() {
        val resultMul = mCalculator.mul(32.0, 2.0)
        Truth.assertThat(resultMul).isEqualTo(64.0)
    }

    /**
     * Tests for simple division.
     */
    @Test
    fun divTwoNumbers() {
        val resultDiv = mCalculator.div(32.0, 2.0)
        Truth.assertThat(resultDiv).isEqualTo(16.0)
    }

    /**
     * Tests for divide by zero; must throw IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException::class)
    fun divByZeroThrows() {
        mCalculator.div(32.0, 0.0)
    }
}