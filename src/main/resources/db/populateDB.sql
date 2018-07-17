DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (datetime,description,calories,user_id) VALUES
  ('2018-7-15 08:00:00', 'breakfast', 1000, 100000),
  ('2018-7-15 15:00:00', 'dinner', 800, 100000),
  ('2018-7-16 08:00:00', 'breakfast', 900, 100000),
  ('2018-7-15 08:00:00', 'admin breakfast', 900, 100001),
  ('2018-7-15 15:00:00', 'admin dinner', 1000, 100001);