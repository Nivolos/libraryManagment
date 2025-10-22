INSERT INTO publication_types (id, name) VALUES
    (1, 'Book'),
    (2, 'Thesis');

INSERT INTO keywords (id, name) VALUES
    (1, 'Software Engineering'),
    (2, 'Databases'),
    (3, 'Agile');

INSERT INTO borrowers (id, matriculation, first_name, last_name, email) VALUES
    (1, 'S12345', 'Ada', 'Lovelace', 'ada.lovelace@example.com'),
    (2, 'S67890', 'Alan', 'Turing', 'alan.turing@example.com');

INSERT INTO publications (id, title, authors, published_at, publisher, isbn, stock, type_id) VALUES
    (1, 'Clean Code', 'Robert C. Martin', '2008-08-01', 'Prentice Hall', '9780132350884', 3, 1),
    (2, 'Continuous Delivery in Practice', 'Andrew Phillips', '2020-03-15', 'TechPress', NULL, 2, 2);

INSERT INTO publication_keywords (publication_id, keyword_id) VALUES
    (1, 1),
    (1, 3),
    (2, 2);

INSERT INTO loans (id, publication_id, borrower_id, issued_at, due_at, returned_at) VALUES
    (1, 1, 2, '2024-04-01', '2024-04-15', NULL);

ALTER TABLE publication_types ALTER COLUMN id RESTART WITH 3;
ALTER TABLE keywords ALTER COLUMN id RESTART WITH 4;
ALTER TABLE borrowers ALTER COLUMN id RESTART WITH 3;
ALTER TABLE publications ALTER COLUMN id RESTART WITH 3;
ALTER TABLE loans ALTER COLUMN id RESTART WITH 2;
