package org.billy.resortmanagementsystem;

import org.billy.resortmanagementsystem.domain.*;
import org.billy.resortmanagementsystem.repositories.ProductRepository;
import org.billy.resortmanagementsystem.repositories.ReservationRepository;
import org.billy.resortmanagementsystem.repositories.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class ResortManagementSystemApplication implements CommandLineRunner {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;


    public static void main(String[] args) {
        SpringApplication.run(ResortManagementSystemApplication.class, args);
    }

    public void run(String... args) {
        List<Product> products = Arrays.asList(
                new Product("Room Example 1", "Description of Room 1", "Excerpt of Room 1", ProductType.Room, 100.0f),
                new Product("Villa Example 1", "Description of Villa 1", "Excerpt of Villa 1", ProductType.Villa, 200.0f),
                new Product("Room Example 2", "Description of Room 2", "Excerpt of Room 2", ProductType.Room, 150.0f),
                new Product("Villa Example 2", "Description of Villa 2", "Excerpt of Villa 2", ProductType.Villa, 250.0f)
        );
        productRepository.saveAll(products);

        List<Customer> customers = Arrays.asList(
                new Customer("user1", bCryptPasswordEncoder.encode( "pass1"), true, true, "John", "Doe", "johndoe@example.com"),
                new Customer("user2", bCryptPasswordEncoder.encode("pass2"), true, true, "Jane", "Smith", "janesmith@example.com"),
                new Customer("user3", bCryptPasswordEncoder.encode("pass3"), true, true,"Alice", "Johnson", "alicejohnson@example.com"),
                new Customer("user4", bCryptPasswordEncoder.encode("pass4"), true, true, "Bob", "Brown", "bobbrown@example.com")
        );
        userRepository.saveAll(customers);

        List<Item> items = Arrays.asList(
                new Item(2, LocalDate.now(), LocalDate.now().plusDays(5), products.get(0)),
                new Item(1, LocalDate.now(), LocalDate.now().plusDays(2), products.get(1)),
                new Item(3, LocalDate.parse("2023-10-23"), LocalDate.parse("2023-11-20"), products.get(2)),
                new Item(4, LocalDate.parse("2023-10-25"), LocalDate.parse("2023-11-20"), products.get(3))
        );

        List<Reservation> reservations = new ArrayList<>();

        // Reservation with 3 items
        Reservation reservation1 = new Reservation(customers.get(0), new ArrayList<>());
        reservation1.addItem(items.get(0));
        reservation1.addItem(items.get(1));
        reservation1.addItem(items.get(2));
        reservations.add(reservation1);

        // Other reservations
        Reservation reservation2 = new Reservation(customers.get(1), new ArrayList<>());
        reservations.add(reservation2);

        Reservation reservation3 = new Reservation(customers.get(2), new ArrayList<>());
        reservation3.addItem(items.get(3));
        reservations.add(reservation3);

        Reservation reservation4 = new Reservation(customers.get(3), new ArrayList<>());
        reservations.add(reservation4);

        reservationRepository.saveAll(reservations);
    }

}

