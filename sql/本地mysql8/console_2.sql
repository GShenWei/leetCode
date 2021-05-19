drop procedure if exists create_test_data;
create procedure create_test_data(in data_lines int)
begin
    declare i int default 0;
    while i < data_lines
        do
            set
                @name = CONCAT('学生', i);
            set
                @code = CONCAT('code', i);
            set
                @age = i;
            INSERT INTO student(name,
                                code,
                                age)
            VALUES (@name,
                    @code,
                    @age);
            set
                i = i + 1;
        end while;
end;
call create_test_data(100);


insert into student(name,
                    code,
                    age)
select name,
       code,
       age
from (
         select concat('学生', @a := @a + 1) name, concat('code', @a) code, @a age
         from (select 1 from mysql.help_relation limit 100) t1,
              (select @a := 900) t2
     ) tt;

insert into student(code, name, age, tag)
VALUES ('haha1', '小王1', 18, '1,2,3');

insert into tag(id, name)
VALUES (1, '唱歌'),
       (2, '跳舞'),
       (3, '打篮球');

select *
from (SELECT s.id,
             s.name,
             substring_index(
                     substring_index(
                             s.tag,
                             ',',
                             b.help_topic_id + 1
                         ),
                     ',', - 1
                 ) AS tag
      FROM student s
               inner join mysql.help_topic b ON b.help_topic_id < (
                  length(s.tag) - length(
                      replace(s.tag, ',', '')
                  ) + 1
          )) t,
     tag
where t.tag = tag.id;