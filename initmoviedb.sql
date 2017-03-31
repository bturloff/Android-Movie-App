DROP TABLE movie;
-- DROP TABLE student;
-- DROP TABLE course;

CREATE TABLE movie (
  title TEXT PRIMARY KEY,
  yearr TEXT,
  rating TEXT,
  released NUMBER,
  runtime INTEGER ,
  genre TEXT,
  actors TEXT,
  plot TEXT
  );

-- CREATE TABLE course (
--   coursename TEXT,
--   courseid INTEGER PRIMARY KEY);

-- CREATE TABLE studenttakes (
--   studentid INTEGER,
--   courseid INTEGER,
--   FOREIGN KEY(studentid) REFERENCES student(studentid),
--   FOREIGN KEY(courseid) REFERENCES course(courseid));

INSERT INTO movie VALUES
   ('Interstellar','2014','PG-13','27 NOV 2014', '169 min',
   'Adventure', 'Ellen Burstyn, Matthew McConaughey, Mackenzie Foy, John Lithgow',
   'A team of explorers travel through a wormhole in space in an attempt to ensure humanity\'s survival.');
-- INSERT INTO student VALUES
--    ('Harry Holms','Software','harry.holms@asu.edu',102,2);
-- INSERT INTO student VALUES
--    ('Sam Smith','Engineering','sammy@asu.edu',103,3);
-- INSERT INTO course VALUES
--    ('Ser421 Web',1);
-- INSERT INTO course VALUES
--    ('Ser502 Langs',2);
-- INSERT INTO course VALUES
--    ('Cse445 Dist Apps',3);
-- INSERT INTO course VALUES
--    ('Ser423 Mobile',4);
-- INSERT INTO studenttakes VALUES
--    (1,1);
-- INSERT INTO studenttakes VALUES
--    (1,2);
-- INSERT INTO studenttakes VALUES
--    (2,3);
-- INSERT INTO studenttakes VALUES
--    (2,1);
-- INSERT INTO studenttakes VALUES
--    (3,4);
-- INSERT INTO studenttakes VALUES
--    (3,1);
