package iii_conventions

import iv_properties.toMillis

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {

    override operator fun compareTo(that: MyDate): Int {
        return if (this.toMillis() > that.toMillis()) {
            1
        } else if (this.toMillis() == that.toMillis()) {
            0
        } else {
            -1
        }
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

class DateRange(override val start: MyDate, override val endInclusive: MyDate): ClosedRange<MyDate>, Iterable<MyDate> {

    override fun iterator(): Iterator<MyDate> {
        return DateIterator(this)
    }

    override operator fun contains(date: MyDate): Boolean {
        return start <= date && date <= endInclusive
    }
}

class DateIterator(val dateRange: DateRange) : Iterator<MyDate> {

    private var pointer = dateRange.start

    override fun hasNext(): Boolean {
        return pointer <= dateRange.endInclusive
    }

    override fun next(): MyDate {
        val temp = pointer
        pointer = pointer.nextDay()
        return temp
    }

}
