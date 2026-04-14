package domain.seat

import domain.payment.Money

data class SeatPosition(
    val row: Row,
    val column: Column,
) {
    val grade: SeatGrade get() = SeatGrade.of(this)
    val price: Money get() = grade.price

    override fun toString(): String = "$row$column"
}
