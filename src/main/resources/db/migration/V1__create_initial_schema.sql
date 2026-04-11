CREATE TABLE books (
    id SERIAL PRIMARY KEY,
    isbn VARCHAR(13) UNIQUE  NOT NULL,
    title VARCHAR(200) NOT NULL,
    author_first_name VARCHAR(100) NOT NULL,
    author_last_name VARCHAR(100) NOT NULL,
    author_middle_name VARCHAR(100),
    publisher VARCHAR(200) NOT NULL,
    publication_year INTEGER NOT NULL,
    pages INTEGER NOT NULL CHECK (pages > 0),
    genre VARCHAR(100),
    price NUMERIC(10, 2) NOT NULL CHECK ( price >= 0 ),
    stock INTEGER NOT NULL DEFAULT 0 CHECK ( stock >= 0 ),
    description VARCHAR(1000)
);

CREATE TABLE customers (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(12) NOT NULL UNIQUE,
    address VARCHAR(200),
    date_of_birth DATE NOT NULL
);

CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    customer_id INTEGER NOT NULL,
    order_date DATE NOT NULL DEFAULT CURRENT_DATE,
    status VARCHAR(9) NOT NULL CHECK (status IN ('PENDING', 'PAID', 'SHIPPED', 'DELIVERED', 'CANCELLED')),
    total_amount NUMERIC(12,2) NOT NULL DEFAULT 0 CHECK (total_amount >= 0),
    FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE
);

CREATE TABLE order_items (
    id SERIAL PRIMARY KEY,
    order_id INTEGER NOT NULL,
    book_id INTEGER NOT NULL,
    quantity INTEGER NOT NULL CHECK (quantity > 0),
    price_at_order NUMERIC(10,2) NOT NULL CHECK (price_at_order >= 0),
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES books(id)
);