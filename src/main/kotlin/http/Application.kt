package http

import database.DatabaseConfig
import domain.reservation.TicketBucket
import domain.screening.Screening
import domain.seat.Column
import domain.seat.Row
import domain.seat.SeatPosition
import domain.seat.SeatPositions
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import repository.ReservationRepository
import repository.ScreeningRepository

@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}


@RestController
class Controller {

    @GetMapping("/movies")
    fun getMovies(): ResponseEntity<MoviesDto> {
        val repo = ScreeningRepository(DatabaseConfig.getConnection())
        val screenings = repo.getSchedule().screenings

        val movieDtos =
            screenings.groupBy { it.movie }.entries.mapIndexed { index, (movie, screenings) ->
                MovieDto(
                    id = (index + 1).toLong(),
                    title = movie.title.title,
                    runningTimeMinutes = movie.runningTime.duration,
                    screenings = screenings.map { screening ->
                        ScreeningDto(
                            id = screening.id.toLong(),
                            startTime = screening.startTime,
                            endAt = screening.startTime.plusMinutes(movie.runningTime.duration.toLong())
                        )
                    }
                )
            }
        return ResponseEntity.ok(MoviesDto(movieDtos))
    }

    @PostMapping("/reservations")
    fun reserve(reservations: ReservationsDto): ResponseEntity<ReservationsDto> {
        val reservationRepo = ReservationRepository(DatabaseConfig.getConnection())
        val screeningRepo = ScreeningRepository(DatabaseConfig.getConnection())

        val screenings = screeningRepo.getSchedule().screenings
        val ticketBucket = reservations.reservations.fold(TicketBucket()) { bucket, reservation ->
            val screening = screenings.first { it.id == reservation.screeningId.toString() }

            val positions = SeatPositions(
                reservation.seats.map { seat ->
                    SeatPosition(
                        row = Row.valueOf(seat.take(1)),
                        column = Column(seat.drop(1).toInt())
                    )
                }
            )
            bucket.addTicket(screening, positions)
        }
        reservationRepo.save(ticketBucket)
        return ResponseEntity.ok(reservations)
    }
}
