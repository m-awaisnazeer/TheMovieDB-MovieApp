package com.applications.utils

import junit.framework.TestCase.assertEquals
import org.junit.Test


class JsonParserTest {

    data class TestClass(
        val name: String, val age: Int
    )

    private val testString: String by lazy {
        "{\"name\":\"Awais\",\"age\":26}"
    }

    private val testData by lazy {
        TestClass("Awais", 26)
    }

    @Test
    fun toJson() {
        assertEquals(testString, JsonParser.toJson(testData))
    }

    @Test
    fun parseJson() {
        val mTestData = JsonParser.parseJson<TestClass>(testString)
        assertEquals(testData, mTestData)
    }
}