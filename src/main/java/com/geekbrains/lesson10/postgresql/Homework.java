package com.geekbrains.lesson10.postgresql;

public class Homework {
/**
 * sudo apt install postgresql postgresql-contrib
 * sudo systemctl status postgresql
 * TODO: sudo -i -u postgres
 * TODO: psql
 * * * ...
 * \q
 * exit
 * sudo pg_isready
 *
 Создайте таблицу студенты (students): id, имя, серия паспорта, номер  паспорта.
 Пара серия-номер паспорта должны быть уникальными в таблицу студенты.
 FIXME: CREATE TABLE students
        (id SERIAL PRIMARY KEY NOT NULL, students_name TEXT NOT NULL,
         passport_series int NOT NULL, passport_id int NOT NULL,
        CONSTRAINT unique_passport UNIQUE (passport_series, passport_id));

 *
 Создайте таблицу предметы (subjects): id, название предмета;
 FIXME: CREATE TABLE subjects (id SERIAL PRIMARY KEY, subject TEXT NOT NULL UNIQUE);
 *
 Создайте таблицу успеваемость (progress): id, студент, предмет, оценка;
 Оценка может находиться в пределах от 2 до 5;
 При удалении студента из таблицы, вся его успеваемость тоже должна удалиться;
 FIXME:
     CREATE TABLE progress (
     id SERIAL PRIMARY KEY,
     student INTEGER,
     subject INTEGER,
     FOREIGN KEY (student) REFERENCES students (id) ON DELETE CASCADE,
     FOREIGN KEY (subject) REFERENCES subjects (id) ON DELETE CASCADE,
     score INTEGER CHECK(score <= 5 AND score >= 2));
 *
 Вывести список студентов, сдавших определенный предмет, на оценку выше 3;
 FIXME:  SELECT students.name, subjects.subject, progress.score
        FROM students
        JOIN progress ON progress.student = student.id
        JOIN subjects ON subjects.id = progress.subject
        WHERE progress.score >= 3
        AND subjects.subject = 'test2';
 *
 Посчитайте средний бал по определенному предмету;
 FIXME: SELECT AVG(score) FROM progress
        JOIN subjects ON  subjects.id = progress.subject
        AND subjects.subject = 'test2';
 *
 Посчитайте средний бал по определенному студенту;
 FIXME: SELECT AVG(score) FROM progress
        JOIN subjects ON subjects.id = progress.subject
        JOIN students ON students.id = progress.students
        AND students.name = 'Juli';
 *
 Найти три предмета, которые сдали наибольшие количество студентов;
 FIXME ?: SELECT subjects.subject FROM subjects
        JOIN progress ON progress.subject = subjects.id
        GROUP BY subjects.subject
        ORDER BY score DESC
        LIMIT 3;
 *
 */
}
