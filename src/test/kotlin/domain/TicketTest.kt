package domain

import domain.fixture.createScreening
import domain.fixture.createSeatPositions
import domain.reservation.Ticket
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class TicketTest {
    @Test
    fun `상영 정보와 예매 좌석을 가진다`() {
        Ticket(
            screening = createScreening(),
            seatPositions = createSeatPositions("A" to 1),
        )
    }

    @Test
    fun `예매 좌석에 중복이 있을 경우 예외를 던진다`() {
        assertThrows(IllegalArgumentException::class.java) {
            Ticket(
                screening = createScreening(),
                seatPositions = createSeatPositions("A" to 1, "A" to 1),
            )
        }
    }
}
