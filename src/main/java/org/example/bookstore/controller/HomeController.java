package org.example.bookstore.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return """
                <html>
                <body>
                <h3>Доступные эндпоинты:</h3>
                <ul>
                <li><a href="/api/books">/api/books</a> — список книг</li>
                <li><a href="/api/customers">/api/customers</a> — список клиентов</li>
                <li><a href="/api/orders">/api/orders</a> — список заказов</li>
                </ul>
                </body>
                </html>
                """;
    }
}
