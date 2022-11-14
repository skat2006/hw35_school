select * from students
WHERE age > 20 AND age < 35;

select name from students;

select * from students
WHERE name like '%y%';

select * from students
WHERE age < students.id;

select * from students
ORDER BY age;