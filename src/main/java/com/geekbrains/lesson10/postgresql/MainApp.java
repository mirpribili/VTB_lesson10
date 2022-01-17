package com.geekbrains.lesson10.postgresql;

public class MainApp {
    /**
     * sudo apt install postgresql postgresql-contrib
     * sudo systemctl status postgresql
     * sudo -i -u postgres
     * psql
     * * * ...
     * \q
     * exit
     * sudo pg_isready
     *
     * -----------------------------------------------------------------------------------------------------------------
     * TODO: \dn - List of schemas                                      (схема - что то вроде пространства имен)
     * * * for windows: \! chcp 1251                                    (add setting-front Lucida Console)
     * TODO: CREATE TABLE students (id serial, name text, score int);
     * # ТИПЫ ДАННЫХ
     * * - id serial = аналог автоинкримента
     * * small int == in2 = 2 байта / int == int4 = 4/ big int == int8 = 8
     * * var char = не дополняет до конца выделенного пространства символами пробела
     * * деньги храним с numeric
     * TODO: \dt - List of relations
     * SET search_path to demo; - set schema
     * SELECT * FROM demo.table1;
     * DROP TABLE students;
     * INSERT INTO students VALUES (1, 'Bob', 100)                      - короче но если порядок столбцов помен-ся то сломается
     * TODO: INSERT INTO students (name, score) values ('Bob', 100);
     * @INSERT 0 1
     * TODO: INSERT INTO students (name, score) values ('Bob2', 100);
     * @INSERT 0 1
     * TODO: INSERT INTO students (name, score) values ('Bob3', 98) RETURNING *;
     * @id | name | score
     * @----+------+-------
     * @3 | Bob3 |    98
     * @(1 row)
     * @INSERT 0 1
     * TODO: SELECT * FROM students;
     * @ id | name | score
     * @----+------+-------
     * @  1 | Bob  |   100
     * @  2 | Bob2 |   100
     * @  3 | Bob3 |    98
     * @(3 rows)
     * TODO: INSERT INTO students (name, score) VALUES ('Bob4', 80), ('Bob5', 60), ('Bob6', 65) RETURNING *;
     * @ id | name | score
     * @----+------+-------
     * @  4 | Bob4 |    80
     * @  5 | Bob5 |    60
     * @  6 | Bob6 |    65
     * @(3 rows)
     *
     * @INSERT 0 3
     * * * экранирование тут через две одинарные кавычки те НЕ "' А ''' ИЛИ $$'
     * TODO: SELECT * FROM students;
     * @ id | name | score
     * @----+------+-------
     * @  1 | Bob  |   100
     * @  2 | Bob2 |   100
     * @  3 | Bob3 |    98
     * @  4 | Bob4 |    80
     * @  5 | Bob5 |    60
     * @  6 | Bob6 |    65
     * @(6 rows)
     * TODO: SELECT * FROM students WHERE id > 3;
     * ...
     * @(3 rows)
     * TODO: SELECT name FROM students WHERE id > 3;
     *@  name
     *@ ------
     *@  Bob4
     *@  Bob5
     *@  Bob6
     *@(3 rows)
     * TODO: SELECT * FROM students WHERE id > 1 ORDER BY score DESC;
     * TODO: UPDATE students SET score = 78 WHERE name = 'Bob4';
     * @UPDATE 1
     * TODO: DELETE FROM students WHERE score = 100 RETURNING *;
     * @ id | name | score
     * @----+------+-------
     * @  1 | Bob  |   100
     * @  2 | Bob2 |   100
     * @(2 rows)
     *
     * @DELETE 2
     * FIXME: TRUNCATE students; == но быстрее чем DELETE FROM students;
     * TODO: INSERT INTO students (name, score) VALUES ('John', 44), ('Weak', 50) RETURNING *;
     * ...
     * @INSERT 0 2
     * TODO: SELECT * FROM students WHERE name LIKE 'Bob%'; регистр важен
     * ...
     * @(4 rows)
     * TODO: INSERT INTO students (name, score) VALUES ('Max', 46), ('George', 51) RETURNING *;
     * TODO: INSERT INTO students (name, score) VALUES ('Jane', 48), ('July', 57);
     * TODO: SELECT * FROM students WHERE name LIKE 'Bob%'; регистр важен
     * TODO: SELECT * FROM students WHERE name LIKE '_o__';
     * @ id | name | score
     * @----+------+-------
     * @  3 | Bob3 |    98
     * @  5 | Bob5 |    60
     * @  6 | Bob6 |    65
     * @  4 | Bob4 |    78
     * @  7 | Jonh |    44
     * @(5 rows)
     * TODO: SELECT * FROM students WHERE score BETWEEN 50 AND 75;
     * TODO: SELECT name, score, score / 5 AS another_score FROM students; == вычисляемое поле
     // хочу увидеть уникальные баллы в таблице
     * TODO: SELECT DISTINCT score FROM students;
     // топ 3 студентов
     * TODO: SELECT * FROM students ORDER BY score DESC LIMIT 3;
     // топ 3 после топ 3
     * TODO: SELECT * FROM students ORDER BY score DESC LIMIT 3 OFFSET 3;
     // как оценки в виде баллов перевести в Буквы?
     * TODO: SELECT name, score, CASE WHEN score = 50 THEN 'A' WHEN score = 4 THEN 'B' ELSE 'C' END AS mark FROM students;
     * @  name  | score | mark
     * @--------+-------+------
     *  Bob3   |    98 | C
     *  Bob5   |    60 | C
     *  Bob6   |    65 | C
     *  Bob4   |    78 | C
     *  Jonh   |    44 | C
     *  Weak   |    50 | A
     *  Max    |    46 | C
     *  George |    51 | C
     *  Jane   |    48 | C
     *  July   |    57 | C
     * @(10 rows)
     // Как по умолчанию значения проставлять?
     * FIXME: CREATE TABLE progress (id serial, subject text, mark int DEFAULT 4)
     // Стобик с проверкой значений перед добавлением:
     * TODO: CREATE TABLE progress (id serial, subject text, mark int CHECK (mark >= 2 AND mark <= 5));
     * @CREATE TABLE
     * TODO: \dt
     * @         List of relations
     * @ Schema |   Name   | Type  |  Owner
     * @--------+----------+-------+----------
     *  public | progress | table | postgres
     *  public | students | table | postgres
     * @(2 rows)
     * TODO: \d progress
     * @                             Table "public.progress"
     * @ Column  |  Type   | Collation | Nullable |               Default
     * @---------+---------+-----------+----------+--------------------------------------
     *  id      | integer |           | not null | nextval('progress_id_seq'::regclass)
     *  subject | text    |           |          |
     *  mark    | integer |           |          |
     * Check constraints: проверка ограничений
     *     "progress_mark_check" CHECK (mark >= 2 AND mark <= 5)
     * TODO: INSERT INTO progress (subject, mark) VALUES ('Chemistry', 6);
     * @ERROR: new row for relation "progress" violates check constraint "progress_mark_check"
     * @DETAIL:  Failing row contains (1, Chemistry, 6).
     * TODO: INSERT INTO progress (subject, mark) VALUES ('Chemistry', 4);
     * @INSERT 0 1
     // Как сделать столбик с уникальными значениями?
     * FIXME: CREATE TABLE demo (subject text UNIQUE
     * TODO: CREATE TABLE demo (id serial, subject text, CONSTRAINT unique_subject UNIQUE (subject));
     * @CREATE TABLE
     * \d demo
     *                              Table "public.demo"
     *  Column  |  Type   | Collation | Nullable |             Default
     * ---------+---------+-----------+----------+----------------------------------
     *  id      | integer |           | not null | nextval('demo_id_seq'::regclass)
     *  subject | text    |           |          |
     * Indexes:
     *     "unique_subject" UNIQUE CONSTRAINT, btree (subject)
     * TODO: DROP TABLE demo;
     * @DROP TABLE
     // поставить ограничения на два столбца
        Задача: хранить серию и номер паспорта, серии номера могут совпадать,
        но НЕМОГУТ совпадать одновременно и серия и номер у двух людей.
     * FIXME: CREATE TABLE demo (id serial, subject text, CONSTRAINT unique_subject UNIQUE (subject));
     не дописали
    // Первичный ключ - PRIMARY KEY
     * TODO: CREATE TABLE demo (id serial PRIMARY KEY, subject text, CONSTRAINT unique_subject UNIQUE (subject));
     * TODO: \d demo
     * @                            Table "public.demo"
     * @ Column  |  Type   | Collation | Nullable |             Default
     * @---------+---------+-----------+----------+----------------------------------
     *  id      | integer |           | not null | nextval('demo_id_seq'::regclass)
     *  subject | text    |           |          |
     * Indexes:
     *     "demo_pkey" PRIMARY KEY, btree (id) --------!!!!!!!!!
     *     "unique_subject" UNIQUE CONSTRAINT, btree (subject)
     * TODO: DROP TABLE demo;
    // аналогично
     * TODO: CREATE TABLE demo (id serial, subject text, CONSTRAINT unique_subject UNIQUE (subject), PRIMARY KEY (id));
     * TODO: \d demo
    // Внешний ключ - пусть есть 2 таблицы студенты и успеваемость, и мы не можем загнать в успев-ть не существующего студента.
        попробуем хранить успеваемость наших студентов по предметам в progress
     модифицируем таблицу
     * TODO: ALTER TABLE progress ADD COLUMN student_id integer;
     * @ALTER TABLE
     * TODO: \d progress
     * @                              Table "public.progress"
     * @   Column   |  Type   | Collation | Nullable |               Default
     * @------------+---------+-----------+----------+--------------------------------------
     *  id         | integer |           | not null | nextval('progress_id_seq'::regclass)
     *  subject    | text    |           |          |
     *  mark       | integer |           |          |
     *  student_id | integer |           |          |
     * Check constraints:
     *     "progress_mark_check" CHECK (mark >= 2 AND mark <= 5)
    // добавим студентам Первичный ключ а в progress Внешний ключ ссылающ. на первичн.
     * TODO: ALTER TABLE students ADD PRIMARY KEY (id);
     * @ALTER TABLE
     * TODO: ALTER TABLE progress ADD FOREIGN KEY (student_id) REFERENCES students (id);
     * @ALTER TABLE
     * TODO: \d progress
     *@                               Table "public.progress"
     *@    Column   |  Type   | Collation | Nullable |               Default
     *@ ------------+---------+-----------+----------+--------------------------------------
     *  id         | integer |           | not null | nextval('progress_id_seq'::regclass)
     *  subject    | text    |           |          |
     *  mark       | integer |           |          |
     *  student_id | integer |           |          |
     * Check constraints:
     *     "progress_mark_check" CHECK (mark >= 2 AND mark <= 5)
     * Foreign-key constraints:
     *     "progress_student_id_fkey" FOREIGN KEY (student_id) REFERENCES students(id)
    // проверим работает ли
     * TODO: SELECT * FROM students;
     * @ id |  name  | score
     * @----+--------+-------
     *   3 | Bob3   |    98
     *   5 | Bob5   |    60
     *   6 | Bob6   |    65
     *   4 | Bob4   |    78
     *   7 | Jonh   |    44
     *   8 | Weak   |    50
     *   9 | Max    |    46
     *  10 | George |    51
     *  11 | Jane   |    48
     *  12 | July   |    57
     * (10 rows)
     // Пусть Макс сдал химию
     * TODO: INSERT INTO progress (subject, mark, student_id) VALUES ('Chemistry', 5, 9);
     * @INSERT 0 1
     * TODO: SELECT * FROM progress;
     * @ id |  subject  | mark | student_id
     * @----+-----------+------+------------
     *   2 | Chemistry |    4 |
     *   3 | Chemistry |    5 |          9
     * (2 rows)
     // попробуем добавить оценку не существующему студенту
     * TODO: INSERT INTO progress (subject, mark, student_id) VALUES ('Chemistry', 5, 99);
     * ERROR:  insert or update on table "progress" violates foreign key constraint "progress_student_id_fkey"
     * DETAIL:  Key (student_id)=(99) is not present in table "students".
     // а если удалить макса?
     * TODO: DELETE FROM students WHERE id = 9;
     * ERROR:  update or delete on table "students" violates foreign key constraint "progress_student_id_fkey" on table "progress"
     * DETAIL:  Key (id)=(9) is still referenced from table "progress".
     // удалим внешний ключ
     * TODO: ALTER TABLE progress DROP CONSTRAINT progress_student_id_fkey; CONSTRAINT = ограничение
     * TODO: \d progress;
     *                               Table "public.progress"
     *    Column   |  Type   | Collation | Nullable |               Default
     * ------------+---------+-----------+----------+--------------------------------------
     *  id         | integer |           | not null | nextval('progress_id_seq'::regclass)
     *  subject    | text    |           |          |
     *  mark       | integer |           |          |
     *  student_id | integer |           |          |
     * Check constraints:
     *     "progress_mark_check" CHECK (mark >= 2 AND mark <= 5)
    // что делать если удалят то на что ссылается внешний ключ?
     * TODO: ALTER TABLE progress ADD FOREIGN KEY (student_id) REFERENCES students (id) ON DELETE CASCADE;
     * - ON DELETE CASCADE     - при удалении все связанные данные удалятся
     * - ON DELETE RESTRICT;   - при попытке удалить будет запрещено и мы ничего не сможем сделать.
     * - ON DELETE NO ACTION;
     * - ON DELETE SET NULL\DEFAULT;    - оставить "зависшего" студента если нас это устроит или дефолт значен проставить
     =================================================================
      Модифицировать таблицу

    добавить столбец
     * FIXME: ALTER TABLE table_name ADD COLUMN column_name NOT NULL CHECK
    добавить проверку
     * FIXME: ALTER TABLE table_name ADD CHECK (score > 100)
    удалить ограничения
     * FIXME: ALTER TABLE table_name ALTER COLUMN mark DROP NOT NULL
    удалить ограничение зная имя ограничения
     * FIXME: ALTER TABLE table_name CONSTRAINT name_constraint
     изменить тип данных столбца
     * FIXME: ALTER TABLE table_name ALTER COLUMN mark SET DATA TYPE integer;
     * -  если значения числовые то может сработать
     * - если строки нужно перегнать в инт
     * * FIXME: ALTER TABLE table_name ALTER COLUMN mark SET DATA TYPE integer USING
        ( CASE WHEN mark = 'A' THEN 5 WHEN mark = 'B' THEN 4 ELSE 0 END );
     * TODO: SELECT * FROM students;
     * TODO: SELECT score, name, id FROM students WHERE score > 45 ORDER BY name ASC;
     запомнить запрос в память
     * FIXME: CREATE VIEW OR REPLACE my_view AS SELECT score, name, id FROM students WHERE score > 45 ORDER BY name ASC;
     * TODO: CREATE VIEW my_view AS SELECT score, name, id FROM students WHERE score > 45 ORDER BY name ASC;
     * CREATE VIEW
     * TODO: SELECT * FROM my_view;
     * postgres=# SELECT * FROM my_view;
     *  score |  name  | id
     * -------+--------+----
     *     98 | Bob3   |  3
     *     78 | Bob4   |  4
     *     60 | Bob5   |  5
     *     65 | Bob6   |  6
     *     51 | George | 10
     *     48 | Jane   | 11
     *     57 | July   | 12
     *     46 | Max    |  9
     *     50 | Weak   |  8
     * (9 rows)
     */

    /**
     * Метод возвращает максимальное значение
     * из трех переданных аргументов
     * @param a - первый параметр
     * @param b - второй параметр
     * @param c - третий параметр
     * @return - максимальный из параметров
     */
}
