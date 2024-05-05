-- Insertion des données pour les chansons
--INSERT INTO music (name, duration, artist, spotify_url, image_url) VALUES
--('Smoke in the water', 210, 'Deep Purple', 'url_spotify_1', 'url_image_1'),
--('Beat it', 180, 'Michael Jackson', 'url_spotify_2', 'url_image_2'),
--('Un autre monde', 200, 'Telephone', 'url_spotify_3', 'url_image_3'),
--('Still waiting', 230, 'Sum 41', 'url_spotify_4', 'url_image_4');

-- Insertion des données pour les participants
--INSERT INTO session_participant (hour_of_come, hour_of_leave, is_guest, name_guest) VALUES
--('2024-04-06 08:00:00', '2024-04-06 12:00:00', false, 'Participant 1'),
--('2024-04-06 09:00:00', '2024-04-06 11:00:00', true, 'Guest 1'),
--('2024-04-06 10:00:00', '2024-04-06 13:00:00', false, 'Participant 2'),
--('2024-04-06 11:00:00', '2024-04-06 14:00:00', true, 'Guest 2');

-- À ce stade, il faudrait insérer des données pour poll_turn_music et vote,
-- en s'assurant que les clés étrangères correspondent aux ID des musiques et des participants.
-- Par exemple :
--INSERT INTO poll_turn_music (id_music) VALUES
--((SELECT id_music FROM music WHERE name = 'Chanson 1')),
--((SELECT id_music FROM music WHERE name = 'Chanson 2'));

-- Insertion des données pour les votes
-- Assurez-vous que les IDs pour poll_turn_music_id et session_participant_id existent
--INSERT INTO vote (id_poll_turn_music, id_participant) VALUES
--(1, 1),
--(1, 2),
--(2, 1),
--(2, 2);

-- Notez que les IDs sont des exemples, et devraient correspondre aux IDs réels dans votre base de données.


INSERT INTO user (creation_date, id, mail, username) VALUES
('2024-04-22 12:11:15.862515', 1, 'liance.remi@gmail.com', 'remi'),
('2024-04-22 12:11:15.862515', 2, 'lululefoufou@gmail.com', 'mou');

INSERT INTO `qr_code` (`is_global`, `begin_date`, `end_date`, `id_user`, `idqrcode`, `qr_code_info`) VALUES (b'1', '2024-05-03 08:53:33.521084', NULL, 1, 1, 'YsNsjUut6CnQ');
INSERT INTO `qr_code` (`is_global`, `begin_date`, `end_date`, `id_user`, `idqrcode`, `qr_code_info`) VALUES (b'1', '2024-05-03 08:53:33.521084', NULL, 2, 2, 'YsNseSut6CnQ');

INSERT INTO streaming_service (name) VALUES ('Spotify');
INSERT INTO streaming_service (name) VALUES ('Deezer');
