package domain.screening

import domain.movie.Title
import domain.reservation.TicketBucket
import java.time.LocalDate

data class ScreeningSchedule(
    val screenings: List<Screening>,
) {
    init {
        require(screenings.isNotEmpty()) { "상영 일정이 없습니다. 영화관 문 닫았습니다.. 망했음.." }
        val screeningsByMovie = screenings.groupBy { it.movie }

        screeningsByMovie.values.forEach { movieScreenings ->
            require(movieScreenings.size == movieScreenings.distinctBy { it.startTime }.size) {
                "동일한 영화의 상영 시간은 중복될 수 없습니다."
            }
        }
    }

    fun getMovieSchedule(
        title: Title,
        date: LocalDate,
    ): ScreeningSchedule = ScreeningSchedule(screenings.filter { it.movie.title == title && it.startTime.toLocalDate() == date })

    fun reserve(bucket: TicketBucket): ScreeningSchedule {
        val updatedScreenings =
            bucket.tickets.fold(screenings) { acc, ticket ->
                require(acc.any { it.id == ticket.screening.id }) { "존재하지 않는 상영 입니다." }
                acc.map { screening ->
                    if (screening.id == ticket.screening.id) screening.reserve(ticket.seatPositions) else screening
                }
            }
        return copy(screenings = updatedScreenings)
    }
}
