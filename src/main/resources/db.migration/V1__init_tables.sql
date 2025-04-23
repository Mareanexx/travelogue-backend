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

CREATE TABLE profile (
     id SERIAL PRIMARY KEY,
     username VARCHAR(255) NOT NULL,
     full_name VARCHAR(255) NOT NULL,
     bio TEXT NOT NULL,
     avatar VARCHAR(255) NULL,
     cover_photo VARCHAR(255) NULL,
     followers_number INT DEFAULT 0 NOT NULL,
     following_number INT DEFAULT 0 NOT NULL,
     trips_number INT DEFAULT 0 NOT NULL,
     user_uuid UUID NOT NULL,
     fcm_token TEXT NULL,
     CONSTRAINT fk_user FOREIGN KEY (user_uuid) REFERENCES "user"(uuid) ON DELETE CASCADE
);

CREATE TABLE follows (
     follower_id INT REFERENCES profile(id) ON DELETE CASCADE,
     following_id INT REFERENCES profile(id) ON DELETE CASCADE,
     followed_at TIMESTAMP,
     PRIMARY KEY (follower_id, following_id)
);

CREATE TABLE trip (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NULL,
    steps_number INTEGER NOT NULL DEFAULT 0,
    days_number INTEGER NOT NULL DEFAULT 0,
    type VARCHAR(10) NOT NULL CHECK (type IN ('Private', 'Public')),
    status VARCHAR(10) NOT NULL CHECK (status IN ('Current', 'Past')),
    cover_photo VARCHAR(255) NOT NULL,
    profile_id INTEGER NOT NULL REFERENCES profile(id) ON DELETE CASCADE
);

CREATE TABLE tags (
    id SERIAL PRIMARY KEY,
    trip_id INTEGER NOT NULL REFERENCES trip(id) ON DELETE CASCADE,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE report (
    id SERIAL PRIMARY KEY,
    send_date TIMESTAMP NOT NULL,
    status VARCHAR(10) NOT NULL CHECK (status IN ('New', 'Resolved')),
    trip_id INTEGER NOT NULL REFERENCES trip(id) ON DELETE CASCADE
);

CREATE TABLE map_point (
    id SERIAL PRIMARY KEY,
    longitude DOUBLE PRECISION NOT NULL,
    latitude DOUBLE PRECISION NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    likes_number INT NOT NULL DEFAULT 0,
    comments_number INT NOT NULL DEFAULT 0,
    photos_number INT NOT NULL DEFAULT 0,
    arrival_date TIMESTAMP NOT NULL,
    trip_id INT NOT NULL,
    CONSTRAINT fk_trip FOREIGN KEY (trip_id) REFERENCES trip(id) ON DELETE CASCADE
);

CREATE TABLE point_photo (
     id SERIAL PRIMARY KEY,
     file_path VARCHAR(255) NOT NULL,
     map_point_id INT NOT NULL,
     CONSTRAINT fk_map_point FOREIGN KEY (map_point_id) REFERENCES map_point(id) ON DELETE CASCADE
);

CREATE TABLE comment (
    id SERIAL PRIMARY KEY,
    text TEXT NOT NULL,
    send_date TIMESTAMP NOT NULL DEFAULT now(),
    sender_profile_id INT NOT NULL,
    map_point_id INT NOT NULL,
    CONSTRAINT fk_profile_id FOREIGN KEY (sender_profile_id) REFERENCES profile(id) ON DELETE CASCADE,
    CONSTRAINT fk_map_point_id FOREIGN KEY (map_point_id) REFERENCES map_point(id) ON DELETE CASCADE
);

CREATE TABLE likes (
    profile_id INT NOT NULL,
    map_point_id INT NOT NULL,
    CONSTRAINT pk_likes PRIMARY KEY (profile_id, map_point_id),
    CONSTRAINT fk_likes_profile FOREIGN KEY (profile_id) REFERENCES profile(id) ON DELETE CASCADE,
    CONSTRAINT fk_likes_map_point FOREIGN KEY (map_point_id) REFERENCES map_point(id) ON DELETE CASCADE
);

CREATE TABLE notifications (
    id SERIAL PRIMARY KEY,
    recipient_id INT NOT NULL,
    sender_id INT NOT NULL,
    type VARCHAR(20) NOT NULL CHECK (type IN ('Like', 'Comment', 'Follow', 'NewTrip', 'NewMapPoint')),
    related_trip_id INT NULL,
    related_point_id INT NULL,
    related_comment_id INT NULL,
    is_read BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    CONSTRAINT fk_notif_recipient_profile FOREIGN KEY (recipient_id) REFERENCES profile(id) ON DELETE CASCADE,
    CONSTRAINT fk_notif_sender_profile FOREIGN KEY (sender_id) REFERENCES profile(id) ON DELETE CASCADE,
    CONSTRAINT fk_notif_trip FOREIGN KEY (related_trip_id) REFERENCES trip(id) ON DELETE CASCADE,
    CONSTRAINT fk_notif_map_point FOREIGN KEY (related_point_id) REFERENCES map_point(id) ON DELETE CASCADE,
    CONSTRAINT fk_notif_comment FOREIGN KEY (related_comment_id) REFERENCES comment(id) ON DELETE CASCADE
);


-- ЗАПРОСЫ ДАЛЬШЕ ХЗ МБ НЕ РАБОТАЮТ
INSERT INTO profile (username, full_name, bio, avatar, cover_photo, followers_number, following_number, trips_number, user_uuid)
VALUES
    ('john_doe', 'John Doe', 'Bio for John Doe', NULL, NULL, 0, 0, 0, 'a933fc75-d696-439a-88c2-bf4f5066e620'),
    ('jane_doe', 'Jane Doe', 'Bio for Jane Doe', NULL, NULL, 0, 0, 0, 'def644c2-f299-4432-88d4-41100e91ccc1'),
    ('alice_smith', 'Alice Smith', 'Bio for Alice Smith', NULL, NULL, 0, 0, 0, 'b9868325-d4b0-4b02-bbe0-8b514c891092');


INSERT INTO follows (follower_id, following_id, followed_at)
VALUES
    (1, 2, NOW()),  -- John Doe подписан на Jane Doe
    (2, 1, NOW()),  -- Jane Doe подписан на John Doe
    (2, 3, NOW()),  -- Jane Doe подписан на Alice Smith
    (3, 2, NOW()),  -- Alice Smith подписан на Jane Doe
    (3, 1, NOW()),  -- Alice Smith подписан на John Doe
    (1, 3, NOW());  -- John Doe подписан на Alice Smith