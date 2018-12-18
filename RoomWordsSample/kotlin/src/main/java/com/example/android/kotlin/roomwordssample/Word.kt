package com.example.android.kotlin.roomwordssample

import androidx.room.ColumnInfo
import androidx.room.Entity

/**
 * A basic class representing an entity that is a row in a one-column database table.
 *
 * @ Entity - You must annotate the class as an entity and supply a table name if not class name.
 * @ PrimaryKey - You must identify the primary key.
 * @ ColumnInfo - You must supply the column name if it is different from the variable name.
 *
 * See the documentation for the full rich set of annotations.
 * https://developer.android.com/topic/libraries/architecture/room.html
 */

@OpenForTesting
@Entity(tableName = "word_table", primaryKeys = ["word"])
data class Word(
        @field:ColumnInfo(name = "word") val word: String
) : Comparable<Word> {
    override fun compareTo(other: Word) = word.compareTo(other.word)
}

