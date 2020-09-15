SELECT count(*)
FROM questions_24 q,
     question_categories_24 qc,
     (SELECT NAME, id FROM categories WHERE type = 11 AND bank_id = 24) as aa
WHERE q.source_id = qc.question_id
  AND q.ques_type IN (110, 101)
  AND qc.category_id = aa.id
order by q.id;


SELECT count(1)
from questions_24 q,
     question_categories_24 qc,
     categories c
where q.source_id = qc.question_id
  and q.ques_type in (110, 101)
  and qc.category_id = c.id
  and c.type = 11
  AND c.bank_id = 24;

select count(1)
from questions_24 q,
     question_categories_24 qc
where q.source_id = qc.question_id
  AND q.ques_type IN (110, 101)
  and qc.category_id = 108070;

select q.question_id, count(1) x
from question_categories_24 q
where q.category_id = 108070
group by q.question_id
having x > 1;

select *
from questions_24
where ques_type in (110.901);


select cc.name, count(*)
from categories cc,
     (select c.*, SUBSTR(paths, 4, 6) book_id
      from categories c,
           (SELECT id, parent_id, levels, paths
            FROM (
                     SELECT id
                          , parent_id
                          , @le := IF(parent_id = 0, 0,
                                      IF(LOCATE(CONCAT('|', parent_id, ':'), @pathlevel) > 0,
                                         SUBSTRING_INDEX(SUBSTRING_INDEX(@pathlevel, CONCAT('|', parent_id, ':'), -1),
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
where cc.id = gg.book_id
group by cc.name;

select SUBSTR(',0,101436,101437', 4, 6);
select count(*)
from categories
where bank_id = 23;


SELECT id
     , parent_id
     , @le := IF(parent_id = 0, 0,
                 IF(LOCATE(CONCAT('|', parent_id, ':'), @pathlevel) > 0,
                    SUBSTRING_INDEX(SUBSTRING_INDEX(@pathlevel, CONCAT('|', parent_id, ':'), -1),
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
limit 30;



select pp.book_id, pp.book_name,pp.cat_id,pp.cat_name, count(1)
from questions_23 q,
     question_categories_23 qc,
     (select cc.id book_id, cc.name book_name, gg.id cat_id,gg.name cat_name
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
group by pp.book_id, pp.book_name,pp.cat_id,pp.cat_name;