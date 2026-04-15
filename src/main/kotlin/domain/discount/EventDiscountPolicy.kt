package domain.discount

import domain.payment.Money
import java.time.LocalDateTime
import java.time.LocalTime

interface EventDiscountPolicy {
    fun discount(
        money: Money,
        dateTime: LocalDateTime,
    ): Money
}

class TheaterEventDiscount : EventDiscountPolicy {
    private val policies = listOf(MovieDayEvent(), TimeEvent())

    override fun discount(
        money: Money,
        dateTime: LocalDateTime,
    ): Money = policies.fold(money) { acc, policy -> policy.discount(acc, dateTime) }
}

class MovieDayEvent : EventDiscountPolicy {
    override fun discount(
        money: Money,
        dateTime: LocalDateTime,
    ): Money {
        if (dateTime.toLocalDate().dayOfMonth % MOVIE_DAY_INTERVAL == 0) return money * (1 - DISCOUNT_RATE)

        return money
    }

    companion object {
        private const val MOVIE_DAY_INTERVAL = 10
        private const val DISCOUNT_RATE = 0.1
    }
}

class TimeEvent : EventDiscountPolicy {
    override fun discount(
        money: Money,
        dateTime: LocalDateTime,
    ): Money {
        if (dateTime.toLocalTime() !in PEEK_START_TIME..<PEEK_END_TIME) return money - DISCOUNT_AMOUNT

        return money
    }

    companion object {
        private val PEEK_START_TIME = LocalTime.of(11, 0)
        private val PEEK_END_TIME = LocalTime.of(20, 0)

        private val DISCOUNT_AMOUNT = Money(2000)
    }
}
