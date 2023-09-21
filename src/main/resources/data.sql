
-- User
INSERT INTO "user" ("fullname", "username", "email", "description","hidden") VALUES
('Erik Eriksson', 'erik1000', 'erik100@hotmail.com', 'I love coding.', 'PRIVATE'),
('Frida Fridasson', 'fridaFun1111', 'frid1a@hotmail.com', 'Design is my passion.','PUBLIC');


-- Project
INSERT INTO project ("title", "description", "gitlink", "category", "status", user_id) VALUES
('Music App', 'A fun music streaming app', 'https://github.com/ErikEriksson/MusicApp', 'MUSIC', 'COMPLETED', 1),
('Game Hub', 'A platform for indie games', 'https://github.com/FridaFridasson/GameHub', 'GAME', 'IN_PROGRESS', 2),
('Foodies', 'A food recipe and review platform', 'https://github.com/ArneArnesson/Foodies', 'WEB', 'NOT_STARTED', 1),
('Cinema Central', 'Watch and review movies', 'https://github.com/BilalSvensson/CinemaCentral', 'FILM', 'COMPLETED', 2);



INSERT INTO comment (text, time, project_id, user_id) VALUES
('Kommentar 1 i projekt 1', NOW(), 1, 1),
('Kommentar 2 i projekt 1', NOW(), 1, 2);

-- Collaborator
INSERT INTO collaborator (status, request_date, approval_date, motivation, user_id, project_id) VALUES
(1, NOW(), NULL, 'Motivation för medverkan i projekt 1', 1, 2),
 (1, NOW(), NOW(), 'Motivation för medverkan i projekt 2', 2, 1);



-- Message
INSERT INTO message (title, text, date, read, sender_id, receiver_id) VALUES
('Meddelande 1', 'Innehåll i meddelande 1', NOW(), 'READ', 1, 2),
('Meddelande 2', 'Innehåll i meddelande 2', NOW(), 'UNREAD', 2, 1);