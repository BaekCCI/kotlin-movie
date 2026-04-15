package domain.seat

import domain.payment.Money

enum class SeatGrade(
    val price: Money,
) {
    S(Money(18000)),
    A(Money(15000)),
    B(Money(12000)),
    ;

    companion object {
        fun of(position: SeatPosition): SeatGrade =
            when (position.row) {
                Row.A, Row.B -> B
                Row.C, Row.D -> S
                Row.E, Row.F -> A
            }
    }
}
