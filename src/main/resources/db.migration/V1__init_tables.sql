-- db/migration/V1__init_tables.sql
CREATE TABLE "user" (
    uuid UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    role VARCHAR(15) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'Active'
);

INSERT INTO "user" (role, email, password_hash, status)
VALUES (
   'Administrator',
   'admin@example.com',
   '$2a$10$jb8Q8yQuqifhQipSafaEgul8zXaqY9ZR36XcZo9GFDMFhoGwbE2qG', -- password: admin123
   'Active'
);

INSERT INTO "user" (role, email, password_hash, status)
VALUES (
   'Moderator',
   'moderator@example.com',
   '$2a$10$0K9rLohyHZTe6.yL1nTwTer6m6qed20j1KM.jUMgnEs7C0/6LnGcS', -- password: moder123
   'Active'
);