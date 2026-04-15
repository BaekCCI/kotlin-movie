package domain

import domain.fixture.createMovie
import domain.fixture.createScreening
import domain.fixture.createScreeningRoom
import domain.screening.ScreeningSchedule
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class ScreeningScheduleTest {
    @Test
    fun `상영 정보 리스트를 가진다`() {
        ScreeningSchedule(
            screenings = listOf(createScreening()),
        )
    }

    @Test
    fun `한 영화가 동시에 상영될 경우 예외를 던진다`() {
        val movie = createMovie(title = "허닛")
        assertThrows(IllegalArgumentException::class.java) {
            ScreeningSchedule(
                screenings =
                    listOf(
                        createScreening(
                            movie = movie,
                            room = createScreeningRoom(name = "커피"),
                            startTime = LocalDateTime.of(2026, 4, 8, 10, 0),
                        ),
                        createScreening(
                            movie = movie,
                            room = createScreeningRoom(name = "커피샵"),
                            startTime = LocalDateTime.of(2026, 4, 8, 10, 0),
                        ),
                    ),
            )
        }
    }
}
