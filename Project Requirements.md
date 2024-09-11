### Final Project: Library Management System (LMS)

---

### 1. **Story/Background:**

The **Central City Library** is implementing a digital **Library Management System (LMS)** to manage its collection of books and patrons efficiently. The system will enable patrons to register, borrow, and return books, while also allowing library staff to manage the book inventory and track borrowings. The system must enforce borrowing limits based on membership type, handle overdue fines for late returns, and maintain borrowing history for patrons.

The project will involve building a RESTful API that will implement these features with proper validations and transactional integrity across all operations.

---

### 2. **What Needs to Be Developed:**

The development team will focus on the following key functionalities:

- **Patron Management:**
  - Register new patrons with unique emails and membership types (regular or premium).
  - Allow updating of patron details.
  - View borrowing history for each patron, showing books borrowed, due dates, and overdue fines.

- **Book Management:**
  - Add new books to the system with title, author, ISBN, and quantity.
  - Update book information, including available quantity.
  - Display all available books and search for books by title or author.

- **Borrowing and Returning Books:**
  - Borrow books based on availability and membership-specific borrowing limits.
  - Return books and calculate overdue fines for late returns.

- **Overdue Fine Calculation:**
  - Automatically calculate overdue fines based on the number of days past the due date.

---

### 3. **ERD (Entity Relationship Diagram) Database Design:**

The system consists of three main entities: **Patrons**, **Books**, and **Transactions**. Below is the explanation and responsibilities of each entity, followed by a relational overview of the database.

#### **Entity Responsibilities:**

##### 1. **Patrons:**
- **Responsibility:** Stores information about library patrons, including their name, email, and membership type (regular or premium). Each patron is allowed to borrow a certain number of books based on their membership type.
  
  **Attributes:**
  - `id`: Primary key, unique identifier for each patron.
  - `name`: Name of the patron.
  - `email`: Unique email address for each patron.
  - `membership_type`: Type of membership, which determines borrowing limits.
  - `created_at`: Timestamp for when the patron was registered.
  - `updated_at`: Timestamp for when the patron's information was last updated.

##### 2. **Books:**
- **Responsibility:** Manages the collection of books in the library. Stores book information such as title, author, ISBN, and availability status.
  
  **Attributes:**
  - `id`: Primary key, unique identifier for each book.
  - `title`: Title of the book.
  - `author`: Author of the book.
  - `isbn`: Unique ISBN for each book.
  - `quantity`: Total number of copies of the book.
  - `available_copies`: Number of copies currently available for borrowing.
  - `created_at`: Timestamp for when the book was added to the system.
  - `updated_at`: Timestamp for when the book's information was last updated.

##### 3. **Transactions:**
- **Responsibility:** Tracks the borrowing and returning of books by patrons. It also calculates overdue fines for late returns.
  
  **Attributes:**
  - `id`: Primary key, unique identifier for each transaction.
  - `patron_id`: Foreign key to the `Patrons` table.
  - `book_id`: Foreign key to the `Books` table.
  - `borrow_date`: Date when the book was borrowed.
  - `due_date`: Date when the book is due for return.
  - `return_date`: Date when the book was returned (if applicable).
  - `fine`: The fine amount calculated if the book is returned late.


---

### **API Design with Requirements**

Here is the detailed API design for the Library Management System, including the necessary validation, expected responses, and business logic for each endpoint.

---

#### **1. Register a Patron**

- **Endpoint:** `POST /api/patrons`
- **Description:** Register a new patron with name, email, and membership type.
- **Request:**
  ```json
  {
    "name": "John Doe",
    "email": "john.doe@example.com",
    "membership_type": "regular" // or "premium"
  }
  ```
- **Validations:**
  - Ensure `name` is not empty.
  - Validate that `email` is in a correct format and is unique.
  - Ensure `membership_type` is either `"regular"` or `"premium"`.
  
- **Business Logic:**
  - Store the new patron in the database with the provided details.
  
- **Response (Success):**
  - **Status Code:** 201 Created
  - **Response:**
    ```json
    {
      "id": 1,
      "name": "John Doe",
      "membership_type": "regular"
    }
    ```

- **Response (Validation Error):**
  - **Status Code:** 400 Bad Request
  - **Response:**
    ```json
    {
      "error": "Invalid email or missing required fields."
    }
    ```

---

#### **2. Update Patron Details**

- **Endpoint:** `PUT /api/patrons/{patron_id}`
- **Description:** Update the patron’s name, email, or membership type.
- **Request:**
  ```json
  {
    "name": "John Smith",
    "email": "john.smith@example.com",
    "membership_type": "premium"
  }
  ```
- **Validations:**
  - Ensure `name` and `email` are valid.
  - Ensure `email` is unique if changed.
  - Ensure `membership_type` is either `"regular"` or `"premium"`.

- **Business Logic:**
  - Retrieve the patron by `patron_id`.
  - Update the patron’s details in the database.

- **Response (Success):**
  - **Status Code:** 200 OK
  - **Response:**
    ```json
    {
      "message": "Patron details updated successfully."
    }
    ```

- **Response (Not Found):**
  - **Status Code:** 404 Not Found
  - **Response:**
    ```json
    {
      "error": "Patron not found."
    }
    ```

---

#### **3. Add a New Book**

- **Endpoint:** `POST /api/books`
- **Description:** Add a new book to the library collection.
- **Request:**
  ```json
  {
    "title": "Effective Java",
    "author": "Joshua Bloch",
    "isbn": "9780134685991",
    "quantity": 5
  }
  ```
- **Validations:**
  - Ensure `title`, `author`, and `isbn` are not empty.
  - Ensure `isbn` is unique.
  - Ensure `quantity` is a positive integer.
  
- **Business Logic:**
  - Store the new book in the database and set `available_copies` equal to `quantity`.

- **Response (Success):**
  - **Status Code:** 201 Created
  - **Response:**
    ```json
    {
      "id": 1,
      "title": "Effective Java",
      "available_copies": 5
    }
    ```

- **Response (Validation Error):**
  - **Status Code:** 400 Bad Request
  - **Response:**
    ```json
    {
      "error": "Invalid input. Ensure all fields are filled and ISBN is unique."
    }
    ```

---

#### **4. Update Book Details**

- **Endpoint:** `PUT /api/books/{book_id}`
- **Description:** Update details of a book such as title, author, or quantity.
- **Request:**
  ```json
  {
    "title": "Clean Code",
    "author": "Robert C. Martin",
    "quantity": 10
  }
  ```
- **Validations:**
  - Ensure the `title` and `author` are not empty.
  - Ensure `quantity` is a positive integer.

- **Business Logic:**
  - Retrieve the book by `book_id`.
  - Update the book details in the database.
  
- **Response (Success):**
  - **Status Code:** 200 OK
  - **Response:**
    ```json
    {
      "message": "Book details updated successfully."
    }
    ```

- **Response (Not Found):**
  - **Status Code:** 404 Not Found
  - **Response:**
    ```json
    {
      "error": "Book not found."
    }
    ```

---

#### **5. List All Available Books**

- **Endpoint:** `GET /api/books`
- **Description:** Retrieve a list of all available books in the library.
- **Response (Success):**
  - **Status Code:** 200 OK
  - **Response:**
    ```json
    [
      {
        "id": 1,
        "title": "Effective Java",
        "author": "Joshua Bloch",
        "available_copies": 5
      },
      {
        "id": 2,
        "title": "Clean Code",
        "author": "Robert C. Martin",
        "available_copies": 3
      }
    ]
    ```

---

#### **6. Search Books by Title or Author**

- **Endpoint:** `GET /api/books/search`
- **Description:** Search for books by title or author.
- **Query Parameters:**
  - `title` (optional)
  - `author` (optional)

- **Validations:**
  - Ensure at least one search parameter is provided (`title` or `author`).
  
- **Business Logic:**
  - Query the database for books matching the search criteria.

- **Response (Success):**
  - **Status Code:** 200 OK
  - **Response:**
    ```json
    [
      {
        "id": 1,
        "title": "Effective Java",
        "author": "Joshua Bloch",
        "available_copies": 5
      }
    ]
    ```

---

#### **7. Borrow a Book**

- **Endpoint:** `POST /api/borrow`
- **Description:** Allow a patron to borrow a book if available.
- **Request:**
  ```json
  {
    "patron_id": 1,
    "book_id": 1
  }
  ```
- **Validations:**
  - Ensure the `patron_id` and `book_id` are valid.
  - Ensure the patron hasn’t reached their borrowing limit.
  - Ensure the book has available copies.
  
- **Business Logic:**
  - Retrieve the patron and book records.
  - Check if the patron can borrow more books based on their membership type.
  - Check if the book has available copies.
  - Update the `available_copies` of the book and create a transaction record.

- **Response (Success):**
  - **Status Code:** 200 OK
  - **Response:**
    ```json
    {
      "message": "Book borrowed successfully",
      "due_date": "2023-09-20"
    }
    ```

- **Response (Error):**
  - **Status Code:** 400 Bad Request
  - **Response:**
    ```json
    {
      "error": "Cannot borrow. Either the book is unavailable or the patron has reached their limit."
    }
    ```

---

#### **8. Return a Book**

- **Endpoint:** `POST /api/return`
- **Description:** Return a borrowed book and calculate any overdue fines.
- **Request:**
  ```json
  {
    "patron_id": 1,
    "book_id": 1
  }
  ```
- **Validations:**
  - Ensure the `patron_id` and `book_id` are valid.
  - Check if the return is overdue and calculate the fine if applicable.

- **Business Logic:**
  - Retrieve the transaction for the book borrowed by the patron.
  - Check if the return is late and calculate the fine if necessary.
  - Update the book’s `available_copies` and mark the transaction as complete.

- **Response (Success):**
  - **Status Code:** 200 OK
  - **Response:**
    ```json
    {
      "message": "Book returned successfully",
      "fine": 0 // if overdue, the fine will be included
    }
    ```

---

#### **9. View Borrowing History**

- **Endpoint:** `GET /api/patrons/{patron_id}/borrow_history`
- **Description:** Retrieve the borrowing history of a patron.
- **Response (Success):**
  - **Status Code:** 200 OK
  - **Response:**
    ```json
    [
      {
        "book_title": "Effective Java",
        "borrowed_date": "2023-09-01",
        "returned_date": "2023-09-07",
        "fine": 0
      }
    ]
    ```

---

#### **10. View Patron Details**

- **Endpoint:** `GET /api/patrons/{patron_id}`
- **Description:** Retrieve details of a specific patron, including current borrowed books.
- **Response (Success):**
  - **Status Code:** 200 OK
  - Response:

    ```json
    {
      "id": 1,
      "name": "John Doe",
      "email": "john.doe@example.com",
      "membership_type": "regular",
      "borrowed_books": [
        {
          "book_id": 1,
          "book_title": "Effective Java",
          "due_date": "2023-09-20"
        }
      ]
    }
    ```

---

#### **11. Delete a Patron**

- **Endpoint:** `DELETE /api/patrons/{patron_id}`
- **Description:** Deletes a patron from the system.
- **Validations:**
  - Ensure the patron has no active borrowings.
  
- **Business Logic:**
  - Check if the patron has active loans. If not, delete the patron record.

- **Response (Success):**
  - **Status Code:** 200 OK
  - **Response:**
    ```json
    {
      "message": "Patron deleted successfully."
    }
    ```

- **Response (Error):**
  - **Status Code:** 400 Bad Request
  - **Response:**
    ```json
    {
      "error": "Cannot delete patron with active loans."
    }
    ```

---

#### **12. Delete a Book**

- **Endpoint:** `DELETE /api/books/{book_id}`
- **Description:** Deletes a book from the system.
- **Validations:**
  - Ensure no copies of the book are currently borrowed.
  
- **Business Logic:**
  - Check if the book has any active loans. If not, delete the book record.

- **Response (Success):**
  - **Status Code:** 200 OK
  - **Response:**
    ```json
    {
      "message": "Book deleted successfully."
    }
    ```

- **Response (Error):**
  - **Status Code:** 400 Bad Request
  - **Response:**
    ```json
    {
      "error": "Cannot delete book with active loans."
    }
    ```

---

#### **13. Get Overdue Books**

- **Endpoint:** `GET /api/books/overdue`
- **Description:** Retrieve a list of overdue books and their patrons.
- **Response (Success):**
  - **Status Code:** 200 OK
  - **Response:**
    ```json
    [
      {
        "book_title": "Effective Java",
        "patron_name": "John Doe",
        "due_date": "2023-09-10",
        "days_overdue": 5,
        "fine": 50
      }
    ]
    ```

---

#### **14. Check Available Copies of a Book**

- **Endpoint:** `GET /api/books/{book_id}/availability`
- **Description:** Check how many copies of a book are available for borrowing.
- **Response (Success):**
  - **Status Code:** 200 OK
  - **Response:**
    ```json
    {
      "book_title": "Effective Java",
      "available_copies": 2
    }
    ```

---

#### **15. Get Patron’s Current Borrowed Books**

- **Endpoint:** `GET /api/patrons/{patron_id}/current_borrowings`
- **Description:** Retrieve the list of books currently borrowed by a patron.
- **Response (Success):**
  - **Status Code:** 200 OK
  - **Response:**
    ```json
    [
      {
        "book_title": "Effective Java",
        "borrowed_date": "2023-09-01",
        "due_date": "2023-09-15"
      }
    ]
    ```