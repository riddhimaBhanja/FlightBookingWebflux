package com.flightapp.service.impl;

import com.flightapp.dto.*;
import com.flightapp.entity.Booking;
import com.flightapp.entity.FlightInventory;
import com.flightapp.exception.FlightNotFoundException;
import com.flightapp.exception.PNRNotFoundException;
import com.flightapp.repository.BookingRepository;
import com.flightapp.repository.FlightInventoryRepository;
import constants.BookingStatus;
import constants.FlightStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class FlightServiceImplTest {

    @Mock
    private FlightInventoryRepository flightInventoryRepository;

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private FlightServiceImpl flightService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // ------------------------------------------------------------
    // Test addInventory()
    // ------------------------------------------------------------
    @Test
    void testAddInventorySuccess() {
        InventoryRequest request = new InventoryRequest();
        request.setFlightNumber("AI101");
        request.setAirline("AirIndia");
        request.setFromPlace("DEL");
        request.setToPlace("MUM");
        request.setDepartureDateTime(LocalDateTime.now());
        request.setTotalSeats(120);
        request.setTicketPrice(4500.0);

        FlightInventory saved = FlightInventory.builder()
                .id(UUID.randomUUID().toString())
                .flightNumber("AI101")
                .airline("AirIndia")
                .fromPlace("DEL")
                .toPlace("MUM")
                .departureDateTime(request.getDepartureDateTime())
                .totalSeats(120)
                .ticketPrice(4500.0)
                .flightStatus(FlightStatus.ACTIVE)
                .build();

        when(flightInventoryRepository.save(any())).thenReturn(Mono.just(saved));

        StepVerifier.create(flightService.addInventory(request))
                .expectNextMatches(f -> f.getFlightNumber().equals("AI101"))
                .verifyComplete();
    }

    // ------------------------------------------------------------
    // Test searchFlights()
    // ------------------------------------------------------------
    @Test
    void testSearchFlightsSuccess() {
        FlightSearchRequest request = new FlightSearchRequest();
        request.setFromPlace("DEL");
        request.setToPlace("MUM");
        request.setJourneyDate(LocalDate.now());

        FlightInventory f1 = FlightInventory.builder()
                .id("1")
                .fromPlace("DEL")
                .toPlace("MUM")
                .departureDateTime(LocalDateTime.now().plusHours(2))
                .build();

        when(flightInventoryRepository.findFlightsForDay(
                eq("DEL"), eq("MUM"),
                any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Flux.just(f1));

        StepVerifier.create(flightService.searchFlights(request))
                .expectNextCount(1)
                .verifyComplete();
    }

    // ------------------------------------------------------------
    // Test bookTicket()
    // ------------------------------------------------------------
    @Test
    void testBookTicketSuccess() {
        String flightId = "F123";
        BookRequest request = new BookRequest();
        request.setPassengerName("John");
        request.setUserEmail("john@example.com");
        request.setJourneyDate(LocalDate.now());
        request.setNoOfSeats(2);

        FlightInventory flight = new FlightInventory();
        flight.setId(flightId);

        Booking booking = Booking.builder()
                .pnr("PNR123")
                .flightId(flightId)
                .userName("John")
                .userEmail("john@example.com")
                .bookingStatus(BookingStatus.CONFIRMED)
                .bookingDateTime(LocalDateTime.now())
                .build();

        when(flightInventoryRepository.findById(flightId)).thenReturn(Mono.just(flight));
        when(bookingRepository.save(any())).thenReturn(Mono.just(booking));

        StepVerifier.create(flightService.bookTicket(flightId, request))
                .expectNextMatches(b -> b.getFlightId().equals(flightId)
                        && b.getBookingStatus().equals(BookingStatus.CONFIRMED))
                .verifyComplete();
    }

    @Test
    void testBookTicket_FlightNotFound() {
        when(flightInventoryRepository.findById("X")).thenReturn(Mono.empty());

        StepVerifier.create(flightService.bookTicket("X", new BookRequest()))
                .expectError(FlightNotFoundException.class)
                .verify();
    }

    // ------------------------------------------------------------
    // Test getTicketByPnr()
    // ------------------------------------------------------------
    @Test
    void testGetTicketByPnrSuccess() {
        Booking booking = Booking.builder()
                .pnr("PNR1")
                .userEmail("user@mail.com")
                .build();

        when(bookingRepository.findByPnr("PNR1")).thenReturn(Mono.just(booking));

        StepVerifier.create(flightService.getTicketByPnr("PNR1"))
                .expectNextMatches(b -> b.getPnr().equals("PNR1"))
                .verifyComplete();
    }

    @Test
    void testGetTicketByPnr_NotFound() {
        when(bookingRepository.findByPnr("PNR404")).thenReturn(Mono.empty());

        StepVerifier.create(flightService.getTicketByPnr("PNR404"))
                .expectError(PNRNotFoundException.class)
                .verify();
    }

    // ------------------------------------------------------------
    // Test getBookingHistory()
    // ------------------------------------------------------------
    @Test
    void testGetBookingHistorySuccess() {
        Booking b1 = Booking.builder().pnr("P1").userEmail("x@mail.com").build();
        Booking b2 = Booking.builder().pnr("P2").userEmail("x@mail.com").build();

        when(bookingRepository.findByUserEmail("x@mail.com"))
                .thenReturn(Flux.just(b1, b2));

        StepVerifier.create(flightService.getBookingHistory("x@mail.com"))
                .expectNextCount(2)
                .verifyComplete();
    }

    // ------------------------------------------------------------
    // Test cancelTicket()
    // ------------------------------------------------------------
    @Test
    void testCancelTicketSuccess() {
        Booking booking = Booking.builder()
                .pnr("PNR55")
                .bookingStatus(BookingStatus.CONFIRMED)
                .build();

        Booking cancelled = Booking.builder()
                .pnr("PNR55")
                .bookingStatus(BookingStatus.CANCELLED)
                .build();

        when(bookingRepository.findByPnr("PNR55")).thenReturn(Mono.just(booking));
        when(bookingRepository.save(any())).thenReturn(Mono.just(cancelled));

        StepVerifier.create(flightService.cancelTicket("PNR55"))
                .expectNextMatches(b -> b.getBookingStatus().equals(BookingStatus.CANCELLED))
                .verifyComplete();
    }

    @Test
    void testCancelTicket_NotFound() {
        when(bookingRepository.findByPnr("PNR99")).thenReturn(Mono.empty());

        StepVerifier.create(flightService.cancelTicket("PNR99"))
                .expectError(PNRNotFoundException.class)
                .verify();
    }
}
