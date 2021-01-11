WITH RECURSIVE pp as
                   (
                       select *
                       from categories
                       where bank_id = 23
                         and parent_id = 0
                         and type > 10
                       UNION ALL
                       select t.*
                       from categories t
                                inner join pp tcte on t.parent_id = tcte.id
                   )
SELECT pp.id, pp.name, count(1)
FROM pp,
     questions_23 q,
     question_categories_23 qc
where is_leaf = 1
  and q.source_id = qc.question_id
  and qc.category_id = pp.id
  AND q.ques_type IN (203, 201)
group by pp.id, pp.name
order by pp.id;

select *
from categories
where bank_id = 23
  and parent_id = 0;


select pp.book_id, pp.book_name, pp.cat_id, pp.cat_name, count(1)
from questions_23 q,
     question_categories_23 qc,
     (select cc.id book_id, cc.name book_name, gg.id cat_id, gg.name cat_name
      from categories cc,
           (select c.*, SUBSTR(paths, 4, 6) book_id
            from categories c,
                 (SELECT id, parent_id, levels, paths
                  FROM (
                           SELECT id
                                , parent_id
                                , @le := IF(parent_id = 0, 0,
                                            IF(LOCATE(CONCAT('|', parent_id, ':'), @pathlevel) > 0,
                                               SUBSTRING_INDEX(
                                                       SUBSTRING_INDEX(@pathlevel, CONCAT('|', parent_id, ':'), -1),
                                                       '|', 1) + 1
                                                , @le + 1))                                   levels
                                , @pathlevel := CONCAT(@pathlevel, '|', id, ':', @le, '|')    pathlevel
                                , @pathnodes := IF(parent_id = 0, ',0',
                                                   CONCAT_WS(',',
                                                             IF(LOCATE(CONCAT('|', parent_id, ':'), @pathall) > 0,
                                                                SUBSTRING_INDEX(
                                                                        SUBSTRING_INDEX(@pathall, CONCAT('|', parent_id, ':'), -1),
                                                                        '|', 1)
                                                                 , @pathnodes), parent_id))   paths
                                , @pathall := CONCAT(@pathall, '|', id, ':', @pathnodes, '|') pathall
                           FROM (select * from categories where bank_id = 23) l,
                                (SELECT @le := 0, @pathlevel := '', @pathall := '', @pathnodes := '') vv
                           ORDER BY parent_id, id
                       ) src
                  ORDER BY id) kk
            where c.id = kk.id
              and c.type > 10
              and c.is_leaf = 1) gg
      where cc.id = gg.book_id) pp
where q.source_id = qc.question_id
  and qc.category_id = pp.cat_id
  AND q.ques_type IN (203, 201)
group by pp.book_id, pp.book_name, pp.cat_id, pp.cat_name;


select *
from questions_23;
select difficulty, count(1)
from questions_23
group by difficulty;

CREATE TABLE diff
(
    id   int         NOT NULL auto_increment,
    name varchar(10) NOT NULL,
    PRIMARY KEY (id)
);
insert into diff(name)
values
(1),
(2),
(3),
(4),
(5);

select * from questions_23 q,diff d where q.difficulty = d.id;

select 1 <=> 1;
select 1 <=> null;

select * from (select null a,1 b union all
select null a,1 b union all
select null a,1 b union all
select null a,1 b union all
select 1 a,1 b union all
select 1 a,1 b) s where s.a <=> null;
