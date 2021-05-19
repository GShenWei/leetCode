with g_temp_moral as (select exam_id,
                             class_id,
                             class_name,
                             evaluation_type,
                             row_number() over (partition by exam_id,class_id order by rate) rk,
                             rate
                      from (select exam_id,
                                   class_id,
                                   max(class_name)                                      class_name,
                                   moral_edu                                            evaluation_type,
                                   count(1) / sum(count(1)) over (partition by exam_id) rate
                            from t_score_other
                            where exam_id = ?
                            group by exam_id, class_id, moral_edu) t),
     g_temp_sport as (select exam_id,
                             class_id,
                             class_name,
                             evaluation_type,
                             row_number() over (partition by exam_id,class_id order by rate) rk,
                             rate
                      from (select exam_id,
                                   class_id,
                                   max(class_name)                                      class_name,
                                   sport_edu                                            evaluation_type,
                                   count(1) / sum(count(1)) over (partition by exam_id) rate
                            from t_score_other
                            where exam_id = ?
                            group by exam_id, class_id, sport_edu) t),
     g_temp_aesthetic as (select exam_id,
                                 class_id,
                                 class_name,
                                 evaluation_type,
                                 row_number() over (partition by exam_id,class_id order by rate) rk,
                                 rate
                          from (select exam_id,
                                       class_id,
                                       max(class_name)                                      class_name,
                                       aesthetic_edu                                        evaluation_type,
                                       count(1) / sum(count(1)) over (partition by exam_id) rate
                                from t_score_other
                                where exam_id = ?
                                group by exam_id, class_id, aesthetic_edu) t),
     g_temp_labor as (select exam_id,
                             class_id,
                             class_name,
                             evaluation_type,
                             row_number() over (partition by exam_id,class_id order by rate) rk,
                             rate
                      from (select exam_id,
                                   class_id,
                                   max(class_name)                                      class_name,
                                   labor_edu                                            evaluation_type,
                                   count(1) / sum(count(1)) over (partition by exam_id) rate
                            from t_score_other
                            where exam_id = ?
                            group by exam_id, class_id, labor_edu) t),
     g_temp_intellectual as (select exam_id,
                                    class_id,
                                    class_name,
                                    evaluation_type,
                                    row_number() over (partition by exam_id,class_id order by rate) rk,
                                    rate
                             from (select exam_id,
                                          class_id,
                                          max(class_name)                                      class_name,
                                          intellectual_edu                                     evaluation_type,
                                          count(1) / sum(count(1)) over (partition by exam_id) rate
                                   from t_score_other
                                   where exam_id = ?
                                   group by exam_id, class_id, intellectual_edu) t)
select m.exam_id,
       ex.org_id,
       m.class_name dep_name,
       m.class_id   dep_id,
       moral_edu,
       moral_rate_a,
       sport_edu,
       sport_rate_a,
       aesthetic_edu,
       aesthetic_rate_a,
       labor_edu,
       labor_rate_a,
       intellectual_edu,
       intellectual_rate_a
from (select t1.exam_id, t1.evaluation_type moral_edu, ifnull(t2.rate, 0) moral_rate_a, t1.class_id, class_name
      from (select exam_id, evaluation_type, rate, class_id, class_name
            from g_temp_moral
            where rk = 1) t1
               left join (select exam_id, evaluation_type, rate, class_id
                          from g_temp_moral
                          where evaluation_type = 'A') t2 on t1.exam_id = t2.exam_id and t1.class_id = t2.class_id) m
         left join (select t1.exam_id,
                           t1.evaluation_type sport_edu,
                           ifnull(t2.rate, 0) sport_rate_a,
                           t1.class_id,
                           class_name
                    from (select exam_id, evaluation_type, rate, class_id, class_name
                          from g_temp_sport
                          where rk = 1) t1
                             left join (select exam_id, evaluation_type, rate, class_id
                                        from g_temp_sport
                                        where evaluation_type = 'A') t2
                                       on t1.exam_id = t2.exam_id and t1.class_id = t2.class_id) s
                   on m.exam_id = s.exam_id and m.class_id = s.class_id
         left join (select t1.exam_id,
                           t1.evaluation_type aesthetic_edu,
                           ifnull(t2.rate, 0) aesthetic_rate_a,
                           t1.class_id,
                           class_name
                    from (select exam_id, evaluation_type, rate, class_id, class_name
                          from g_temp_aesthetic
                          where rk = 1) t1
                             left join (select exam_id, evaluation_type, rate, class_id
                                        from g_temp_aesthetic
                                        where evaluation_type = 'A') t2
                                       on t1.exam_id = t2.exam_id and t1.class_id = t2.class_id) a
                   on m.exam_id = a.exam_id and m.class_id = a.class_id
         left join (select t1.exam_id,
                           t1.evaluation_type labor_edu,
                           ifnull(t2.rate, 0) labor_rate_a,
                           t1.class_id,
                           class_name
                    from (select exam_id, evaluation_type, rate, class_id, class_name
                          from g_temp_labor
                          where rk = 1) t1
                             left join (select exam_id, evaluation_type, rate, class_id
                                        from g_temp_labor
                                        where evaluation_type = 'A') t2
                                       on t1.exam_id = t2.exam_id and t1.class_id = t2.class_id) l
                   on m.exam_id = l.exam_id and m.class_id = l.class_id
         left join (select t1.exam_id,
                           t1.evaluation_type intellectual_edu,
                           ifnull(t2.rate, 0) intellectual_rate_a,
                           t1.class_id,
                           class_name
                    from (select exam_id, evaluation_type, rate, class_id, class_name
                          from g_temp_intellectual
                          where rk = 1) t1
                             left join (select exam_id, evaluation_type, rate, class_id
                                        from g_temp_intellectual
                                        where evaluation_type = 'A') t2
                                       on t1.exam_id = t2.exam_id and t1.class_id = t2.class_id) i
                   on m.exam_id = i.exam_id and m.class_id = i.class_id
         left join t_examination ex on m.exam_id = ex.id;



select exam_id, evaluation_type, row_number() over (partition by exam_id order by rate) rk, rate
from (select exam_id,
             moral_edu                                            evaluation_type,
             count(1) / sum(count(1)) over (partition by exam_id) rate
      from t_score_other
      where exam_id = ?
      group by exam_id, moral_edu) t;


select exam_id,
       class_id,
       class_name,
       evaluation_type,
       row_number() over (partition by exam_id,class_id order by rate) rk,
       rate
from (select exam_id,
             class_id,
             max(class_name)                                      class_name,
             moral_edu                                            evaluation_type,
             count(1) / sum(count(1)) over (partition by exam_id) rate
      from t_score_other
      where exam_id = ?
      group by exam_id, class_id, moral_edu) t;


delete
from t_score
where id in (select ddd
             from (select max(id) ddd, class_id, exam_id, subject_id, student_id, count(id) x
                   from t_score
                   group by class_id, exam_id, subject_id, student_id
                   having x > 1) t);

ALTER TABLE `examination`.`t_score`
    ADD UNIQUE INDEX `index_stu_exam_sub` (`student_id`, `exam_id`, `subject_id`) USING BTREE;

explain
select *
from t_score,
     (select @id := 2) t
where exam_id = @id;
select *
from t_score
where exam_id = 2;

select *
from t_score_grade_stat;
select *
from t_score_class_stat;



select t.*, rank() over (partition by exam_id,subject_id order by score desc) rk
from (select id,
             modifier,
             created_at,
             updated_at,
             status,
             user_id,
             student_name,
             student_id,
             student_no,
             org_id,
             exam_id,
             class_name,
             class_id,
             subject,
             score,
             subject_id,
             score_level,
             exam_time
      from t_score

      union all
      select max(id),
             max(modifier),
             max(created_at),
             max(updated_at),
             max(status),
             max(user_id),
             student_name,
             student_id,
             student_no,
             org_id,
             exam_id,
             class_name,
             class_id,
             '总分',
             sum(score),
             0,
             max(score_level),
             max(exam_time)
      from t_score

      group by student_name,
               student_id,
               student_no,
               org_id,
               exam_id,
               class_name,
               class_id) t;


select id,
       modifier,
       created_at,
       updated_at,
       status,
       user_id,
       student_name,
       student_id,
       student_no,
       org_id,
       exam_id,
       class_name,
       class_id,
       subject,
       score,
       subject_id,
       score_level,
       exam_time,
       rank() over (partition by exam_id,subject_id order by score desc) rk
from t_score
where exam_id = ?
union all
select t.*, rank() over (partition by exam_id order by score desc) rk
from (select max(id),
             max(modifier),
             max(created_at),
             max(updated_at),
             max(status),
             max(user_id),
             student_name,
             student_id,
             student_no,
             org_id,
             exam_id,
             class_name,
             class_id,
             '总分',
             sum(score) score,
             0,
             max(score_level),
             max(exam_time)
      from t_score
      where exam_id = ?
      group by student_name,
               student_id,
               student_no,
               org_id,
               exam_id,
               class_name,
               class_id) t;


select *
from t_score_other
where exam_id = 154;
select *
from t_examination
where id = 154;
select *
from t_examination
where id = 2;

SELECT t_score_grade_stat.*
FROM `t_score_grade_stat`
         join t_examination e on t_score_grade_stat.exam_id = e.id
WHERE (dep_id = 10706 and subject_id = 1 and e.exam_time >= '2020-08-31 00:00:00' and
       e.exam_time < '2021-09-01 00:00:00');



delete s
from t_score s,
     (select max(id) mdd, student_id, exam_id, subject, count(1) xx
      from t_score
      group by student_id, exam_id, subject
      having xx > 1) t
where s.id != mdd
  and s.student_id = t.student_id
  and s.exam_id = t.exam_id
  and s.subject = t.subject;

-- 实在找不到的科目，随便找个科目id塞进去
update t_score s,(select subject as subject_name, ifnull(subject_id, @dd := @dd + 1) as subject_id
                  from (select distinct subject
                        from t_score) t
                           left join (select distinct subject_name, subject_id from t_examination_subject) b
                                     on subject = subject_name
                           inner join (select @dd := 1000) tttt
                  order by subject_id) e
set s.subject_id = e.subject_id
where s.subject = e.subject_name;

ALTER TABLE `examination`.`t_score`
    ADD UNIQUE INDEX `index_stu_exam_sub` (`student_id`, `exam_id`, `subject_id`) USING BTREE;

delete s
from t_examination_subject s,
     (select max(id) mdd, exam_id, subject_id, count(1) xx
      from t_examination_subject
      group by exam_id, subject_id
      having xx > 1) t
where s.id != mdd
  and s.exam_id = t.exam_id
  and s.subject_id = t.subject_id;
ALTER TABLE `examination`.`t_examination_subject`
    ADD UNIQUE INDEX `index_exam_sub` (`exam_id`, `subject_id`) USING BTREE;
delete s
from t_examination_class s,
     (select max(id) mdd, exam_id, class_id, count(1) xx
      from t_examination_class
      group by exam_id, class_id
      having xx > 1) t
where s.id != mdd
  and s.exam_id = t.exam_id
  and s.class_id = t.class_id;
ALTER TABLE `examination`.`t_examination_class`
    ADD UNIQUE INDEX `index_exam_class` (`exam_id`, `class_id`) USING BTREE;

insert into t_examination_subject(org_id, modifier, user_id, exam_id, subject_id,
                                  subject_name, total_score)
select org_id,
       modifier,
       user_id,
       exam_id,
       subject_id,
       subject,
       case
           when ss < 0 then 100
           when ss < 50 then 50
           when ss < 100 then 100
           when ss < 120 then 120
           when ss < 150 then 150
           else ceiling(ss / 100) * 100 end gg
from (select org_id, exam_id, subject, subject_id, max(modifier) modifier, max(user_id) user_id, max(score) ss
      from t_score
      group by subject, exam_id, subject_id, org_id) t
on duplicate key
    update subject_id   = t.subject_id,
           subject_name = t.subject,
           total_score  = t.ss,
           modifier     = t.modifier,
           user_id      = t.user_id,
           total_score  = gg;

insert into test_1(name, score, d_score)
VALUES ('a', 9.9999999999, 9.9999999999);
insert into test_1(name, score, d_score)
VALUES ('a', 1001.12, 1001.12);
insert into test_1(name, score, d_score)
VALUES ('a', (34.6 - 34.0) * 100000000, (34.6 - 34.0) * 100000000);
select *
from test_1;
