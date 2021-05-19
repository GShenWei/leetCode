-- 估分sql
select org_id,
       subject,
       subject_id,
       case
           when ss < 50 then 50
           when ss < 100 then 100
           when ss < 120 then 120
           when ss < 150 then 150
           else ceiling(ss / 100) * 100 end gg
from (select org_id, subject, subject_id, max(score) ss
      from t_score
      where exam_id = 2
      group by subject, subject_id, org_id) t;

-- 更新 t_score 的科目
select *
from t_examination_subject;

-- 分值插入sql
insert into t_examination_subject(org_id, exam_id, subject_id,
                                  subject_name, total_score)
select org_id,
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
from (select org_id, exam_id, subject, subject_id, max(score) ss
      from t_score

      group by subject, exam_id, subject_id, org_id) t
on duplicate key update subject_id   = t.subject_id,
                        subject_name = t.subject,
                        total_score  = t.ss;

-- 年级得分率等基本数据插入
select s.subject,
       es.total_score,
       avg(score)                                                 avg_score,
       max(score)                                                 max_score,
       min(score)                                                 min_score,
       sum(if((score / es.total_score) >= 0.85, 1, 0)) / count(1) grate_rate,
       sum(if((score / es.total_score) >= 0.70, 1, 0)) / count(1) good_rate,
       sum(if((score / es.total_score) >= 0.60, 1, 0)) / count(1) passing_rate,
       sum(if((score / es.total_score) < 0.60, 1, 0)) / count(1)  not_passing_rate
from t_score s,
     t_examination_subject es
where s.exam_id = 2
  and s.exam_id = es.exam_id
  and s.subject = es.subject_name
group by s.subject, es.total_score;

-- TODO 年级层级数据插入
select *
from t_score;

-- 班级得分率等基本数据插入
with temp_sbj as (select id,
                         modifier,
                         created_at,
                         updated_at,
                         status,
                         user_id,
                         org_id,
                         exam_id,
                         subject_id,
                         subject_name,
                         total_score
                  from t_examination_subject
                  where exam_id = ?
                  union all
                  select max(id),
                         max(modifier),
                         max(created_at),
                         max(updated_at),
                         max(status),
                         max(user_id),
                         max(org_id),
                         max(exam_id),
                         0,
                         '总分',
                         sum(total_score)
                  from t_examination_subject
                  where exam_id = ?),
     temp_score as (select id,
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
                    where exam_id = ?
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
                    where exam_id = ?
                    group by student_name,
                             student_id,
                             student_no,
                             org_id,
                             exam_id,
                             class_name,
                             class_id)
/*select s.exam_id,
       s.exam_time,
       if(es.subject_id = 0, 1, 0) as                             type,
       max(e.grade) dep_name,
       e.grade_id dep_id,
       max(s.subject) subject,
       es.subject_id,
       es.total_score,
       count(distinct s.student_id)                               students,
       avg(score)                                                 avg_score,
       max(score)                                                 max_score,
       min(score)                                                 min_score,
       sum(if((score / es.total_score) >= 0.85, 1, 0)) / count(1) great_rate,
       sum(if((score / es.total_score) >= 0.70, 1, 0)) / count(1) good_rate,
       sum(if((score / es.total_score) >= 0.60, 1, 0)) / count(1) passing_rate,
       sum(if((score / es.total_score) < 0.60, 1, 0)) / count(1)  not_passing_rate
from temp_score s,
     temp_sbj es,
     t_examination e
where s.exam_id = ?
  and s.exam_id = es.exam_id
  and s.exam_id = e.id
  and s.subject = es.subject_name
group by s.exam_id,
         s.exam_time,
         type,
         dep_id,
         es.subject_id,
         es.total_score*/

select s.exam_id,
       s.exam_time,
       if(es.subject_id = 0, 1, 0) as                             type,
       max(s.class_name)                                          dep_name,
       s.class_id                                                 dep_id,
       max(s.subject)                                             subject,
       es.subject_id,
       es.total_score,
       count(distinct s.student_id)                               students,
       avg(score)                                                 avg_score,
       max(score)                                                 max_score,
       min(score)                                                 min_score,
       sum(if((score / es.total_score) >= 0.85, 1, 0)) / count(1) great_rate,
       sum(if((score / es.total_score) >= 0.70, 1, 0)) / count(1) good_rate,
       sum(if((score / es.total_score) >= 0.60, 1, 0)) / count(1) passing_rate,
       sum(if((score / es.total_score) < 0.60, 1, 0)) / count(1)  not_passing_rate
from temp_score s,
     temp_sbj es,
     t_examination e
where s.exam_id = ?
  and s.exam_id = es.exam_id
  and s.exam_id = e.id
  and s.subject = es.subject_name
group by s.exam_id,
         s.exam_time,
         type,
         dep_id,
         es.subject_id,
         es.total_score;

-- TODO 班级层级数据插入
##  fdfd

select class_id, subject, student_name, score, rank() over (partition by class_id, subject order by score) rk
from t_score s
where exam_id = 2
order by class_id, subject, rk;

update t_examination_segment
set segment_seq = 1
where segment_name = 'A';
update t_examination_segment
set segment_seq = 2
where segment_name = 'B';
update t_examination_segment
set segment_seq = 3
where segment_name = 'C';
update t_examination_segment
set segment_seq = 4
where segment_name = 'D';

select *
from t_examination_segment;
select *
from t_score
where exam_id = 2;
insert into t_examination_segment(org_id, exam_id, segment_set,
                                  count_type, segment_name, segment_ratio, segment_seq)
SELECT 889051, 2, 1, 1, 'A', 0.25, 1
union all
SELECT 889051, 2, 1, 1, 'B', 0.3, 2
union all
SELECT 889051, 2, 1, 1, 'C', 0.2, 3
union all
SELECT 889051, 2, 1, 1, 'D', 0.25, 4;


with temp_seg as (select exam_id,
                         segment_seq,
                         max - segment_ratio min,
                         max
                  from (select exam_id,
                               segment_name,
                               segment_seq,
                               segment_ratio,
                               IF(@ex != e.exam_id, (@ra := 0) + segment_ratio, @ra + segment_ratio) max,
                               @ex := e.exam_id,
                               @ra := @ra + segment_ratio
                        from t_examination_segment e,
                             (select @ra := 0, @ex := 0) t
                        order by e.exam_id, e.segment_seq) gg
),
     temp_stus as (select exam_id, subject, count(1) stu_count from t_score group by exam_id, exam_id, subject)
select exam_id,
       subject_id,
       subject_name                             subject,
       sum(IF(segment_seq = 1, level_rate, 0))  level_rate1,
       sum(IF(segment_seq = 2, level_rate, 0))  level_rate2,
       sum(IF(segment_seq = 3, level_rate, 0))  level_rate3,
       sum(IF(segment_seq = 4, level_rate, 0))  level_rate4,
       sum(IF(segment_seq = 5, level_rate, 0))  level_rate5,
       sum(IF(segment_seq = 6, level_rate, 0))  level_rate6,
       sum(IF(segment_seq = 7, level_rate, 0))  level_rate7,
       sum(IF(segment_seq = 8, level_rate, 0))  level_rate8,
       sum(IF(segment_seq = 9, level_rate, 0))  level_rate9,
       sum(IF(segment_seq = 10, level_rate, 0)) level_rate10
from (select s.exam_id, subject_name, s.subject_id, segment_seq, count(1) / stus.stu_count level_rate
      from t_score s,
           temp_seg se,
           t_examination_subject su,
           temp_stus stus
      where s.subject = su.subject_name
        and s.exam_id = su.exam_id
        and s.exam_id = se.exam_id
        and s.exam_id = stus.exam_id
        and stus.subject = su.subject_name
        and s.score / su.total_score >= min
        and s.score / su.total_score < max
        and s.exam_id = 2
      group by s.exam_id, s.subject_id, subject_name, s.subject_id, segment_seq
     ) g
group by exam_id, subject_id, subject;


select *
from t_examination_segment
where exam_id = 2;
select exam_id,
       segment_seq,
       format(max - segment_ratio, 2) min,
       format(max, 2)                 max
from (select exam_id,
             segment_name,
             segment_seq,
             segment_ratio,
             IF(@ex != e.exam_id, (@ra := 0) + segment_ratio, @ra + segment_ratio) max,
             @ex := e.exam_id,
             @ra := @ra + segment_ratio
      from t_examination_segment e,
           (select @ra := 0, @ex := 0) t
      where exam_id = 2
      order by e.exam_id, e.segment_seq) gg;

select *
from t_examination_segment
where exam_id = 2;

select exam_id,
       segment_seq,
       format(min, 2)                 min,
       format(min + segment_ratio, 2) max
from (select exam_id,
             segment_name,
             segment_seq,
             segment_ratio,
             IF(@ex != e.exam_id, (@ra := 1) - segment_ratio, @ra - segment_ratio) min,
             @ex := e.exam_id,
             @ra := @ra - segment_ratio
      from t_examination_segment e,
           (select @ra := 1, @ex := 0) t
           -- where exam_id = 2
      order by e.exam_id, e.segment_seq) gg;


select e.id as                                                   exam_id,
       grade_id                                                  dep_id,
       subject_id,
       subject_name                                              subject,
       format(sum(IF(segment_seq = 1, es.segment_ratio, 0)), 2)  level_rate1,
       format(sum(IF(segment_seq = 2, es.segment_ratio, 0)), 2)  level_rate2,
       format(sum(IF(segment_seq = 3, es.segment_ratio, 0)), 2)  level_rate3,
       format(sum(IF(segment_seq = 4, es.segment_ratio, 0)), 2)  level_rate4,
       format(sum(IF(segment_seq = 5, es.segment_ratio, 0)), 2)  level_rate5,
       format(sum(IF(segment_seq = 6, es.segment_ratio, 0)), 2)  level_rate6,
       format(sum(IF(segment_seq = 7, es.segment_ratio, 0)), 2)  level_rate7,
       format(sum(IF(segment_seq = 8, es.segment_ratio, 0)), 2)  level_rate8,
       format(sum(IF(segment_seq = 9, es.segment_ratio, 0)), 2)  level_rate9,
       format(sum(IF(segment_seq = 10, es.segment_ratio, 0)), 2) level_rate10
from t_examination_segment es,
     t_examination e,
     (select id,
             modifier,
             created_at,
             updated_at,
             status,
             user_id,
             org_id,
             exam_id,
             subject_id,
             subject_name,
             total_score
      from t_examination_subject
      where exam_id = ?
      union all
      select max(id),
             max(modifier),
             max(created_at),
             max(updated_at),
             max(status),
             max(user_id),
             max(org_id),
             max(exam_id),
             0,
             '总分',
             sum(total_score)
      from t_examination_subject
      where exam_id = ?) sb
where e.id = es.exam_id
  and e.id = sb.exam_id
  and e.id = ?
group by exam_id, dep_id, subject_id, subject;

-- 计算班级在年级中的排名占比
with temp_seg as (select exam_id,
                         segment_seq,
                         format(max - segment_ratio, 2) min,
                         format(max, 2)                 max
                  from (select exam_id,
                               segment_name,
                               segment_seq,
                               segment_ratio,
                               IF(@ex != e.exam_id, (@ra := 0) + segment_ratio, @ra + segment_ratio) max,
                               @ex := e.exam_id,
                               @ra := @ra + segment_ratio
                        from t_examination_segment e,
                             (select @ra := 0, @ex := 0) t
                        where exam_id = ?
                        order by e.exam_id, e.segment_seq) gg
),
     temp_sbj as (select id,
                         modifier,
                         created_at,
                         updated_at,
                         status,
                         user_id,
                         org_id,
                         exam_id,
                         subject_id,
                         subject_name,
                         total_score
                  from t_examination_subject
                  where exam_id = ?
                  union all
                  select max(id),
                         max(modifier),
                         max(created_at),
                         max(updated_at),
                         max(status),
                         max(user_id),
                         max(org_id),
                         max(exam_id),
                         0,
                         '总分',
                         sum(total_score)
                  from t_examination_subject
                  where exam_id = ?),
     temp_score as (select id,
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
                                   class_id) t),
     temp_stus as (select exam_id, subject, count(1) stu_count
                   from temp_score
                   where exam_id = ?
                   group by exam_id, subject)
select exam_id,
       class_id                                 dep_id,
       subject_id,
       subject_name                             subject,
       sum(IF(segment_seq = 1, level_rate, 0))  level_rate1,
       sum(IF(segment_seq = 2, level_rate, 0))  level_rate2,
       sum(IF(segment_seq = 3, level_rate, 0))  level_rate3,
       sum(IF(segment_seq = 4, level_rate, 0))  level_rate4,
       sum(IF(segment_seq = 5, level_rate, 0))  level_rate5,
       sum(IF(segment_seq = 6, level_rate, 0))  level_rate6,
       sum(IF(segment_seq = 7, level_rate, 0))  level_rate7,
       sum(IF(segment_seq = 8, level_rate, 0))  level_rate8,
       sum(IF(segment_seq = 9, level_rate, 0))  level_rate9,
       sum(IF(segment_seq = 10, level_rate, 0)) level_rate10
from (select s.exam_id, class_id, subject_name, s.subject_id, segment_seq, count(1) / stus.stu_count level_rate
      from temp_score s,
           temp_seg se,
           temp_sbj su,
           temp_stus stus
      where s.subject = su.subject_name
        and s.exam_id = su.exam_id
        and s.exam_id = se.exam_id
        and s.exam_id = stus.exam_id
        and stus.subject = su.subject_name
        and s.rk / stus.stu_count >= min
        and s.rk / stus.stu_count < max
        and s.exam_id = ?
      group by s.exam_id, s.class_id, s.subject_id, subject_name, s.subject_id, segment_seq
     ) g
group by exam_id, dep_id, subject_id, subject;

select exam_id,
       segment_seq,
       format(min, 2)                 min,
       format(min + segment_ratio, 2) max
from (select exam_id,
             segment_name,
             segment_seq,
             segment_ratio,
             IF(@ex != e.exam_id, (@ra := 1) - segment_ratio, @ra - segment_ratio) min,
             @ex := e.exam_id,
             @ra := @ra - segment_ratio
      from t_examination_segment e,
           (select @ra := 1, @ex := 0) t
           -- where exam_id = 2
      order by e.exam_id, e.segment_seq) gg;

with temp_seg as (select exam_id,
                         segment_seq,
                         format(min, 2)                 min,
                         format(min + segment_ratio, 2) max
                  from (select exam_id,
                               segment_name,
                               segment_seq,
                               segment_ratio,
                               IF(@ex != e.exam_id, (@ra := 1) - segment_ratio, @ra - segment_ratio) min,
                               @ex := e.exam_id,
                               @ra := @ra - segment_ratio
                        from t_examination_segment e,
                             (select @ra := 1, @ex := 0) t
                        where exam_id = ?
                        order by e.exam_id, e.segment_seq) gg
),
     temp_sbj as (select id,
                         modifier,
                         created_at,
                         updated_at,
                         status,
                         user_id,
                         org_id,
                         exam_id,
                         subject_id,
                         subject_name,
                         total_score
                  from t_examination_subject
                  where exam_id = ?
                  union all
                  select max(id),
                         max(modifier),
                         max(created_at),
                         max(updated_at),
                         max(status),
                         max(user_id),
                         max(org_id),
                         max(exam_id),
                         0,
                         '总分',
                         sum(total_score)
                  from t_examination_subject
                  where exam_id = ?),
     temp_score as (select id,
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
                    where exam_id = ?
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
                    where exam_id = ?
                    group by student_name,
                             student_id,
                             student_no,
                             org_id,
                             exam_id,
                             class_name,
                             class_id),
     temp_stus as (select exam_id, subject, count(1) stu_count
                   from temp_score
                   where exam_id = ?
                   group by exam_id, subject)
select exam_id,
       class_id                                 dep_id,
       subject_id,
       subject_name                             subject,
       sum(IF(segment_seq = 1, level_rate, 0))  level_rate1,
       sum(IF(segment_seq = 2, level_rate, 0))  level_rate2,
       sum(IF(segment_seq = 3, level_rate, 0))  level_rate3,
       sum(IF(segment_seq = 4, level_rate, 0))  level_rate4,
       sum(IF(segment_seq = 5, level_rate, 0))  level_rate5,
       sum(IF(segment_seq = 6, level_rate, 0))  level_rate6,
       sum(IF(segment_seq = 7, level_rate, 0))  level_rate7,
       sum(IF(segment_seq = 8, level_rate, 0))  level_rate8,
       sum(IF(segment_seq = 9, level_rate, 0))  level_rate9,
       sum(IF(segment_seq = 10, level_rate, 0)) level_rate10
from (select s.exam_id, class_id, subject_name, s.subject_id, segment_seq, count(1) / stus.stu_count level_rate
      from temp_score s,
           temp_seg se,
           temp_sbj su,
           temp_stus stus
      where s.subject = su.subject_name
        and s.exam_id = su.exam_id
        and s.exam_id = se.exam_id
        and s.exam_id = stus.exam_id
        and stus.subject = su.subject_name
        and s.score / su.total_score >= min
        and s.score / su.total_score < max
        and s.exam_id = ?
      group by s.exam_id, s.class_id, s.subject_id, subject_name, s.subject_id, segment_seq
     ) g
group by exam_id, dep_id, subject_id, subject;



select *
from t_examination_subject
where exam_id = 2;


update t_score t,
        (select 1 id, '2020-10-01' exam_time union all select 2, '2020-10-02') g
set t.exam_time = g.exam_time
where t.id = g.id;

update t_score
set exam_time = '2020-10-01'
where id = 1;
update t_score
set exam_time = '2020-10-02'
where id = 2;

select count(*)
from t_score
where subject_id = 0;

select *
from t_examination_subject
where exam_id = 2;

update t_score s,t_examination_subject e
set s.subject_id = e.subject_id
where s.subject = e.subject_name
  and s.org_id = e.org_id;

update t_examination_subject
set subject_id = id
where subject_id = 0;

delete
from t_examination_subject
where 1 = 1;
select *
from t_examination_subject;

select *
from t_score_student_stat;

select id,
       created_at,
       org_id,
       exam_id,
       type,
       student_name,
       student_id,
       subject,
       subject_id,
       class_name,
       class_id,
       total_score,
       score / total_score score_rate,
       score_level,
       class_rank,
       grade_rank
from t_score_student_stat
group by class_name;

select id,
       created_at,
       org_id,
       exam_id,
       type,
       student_name,
       student_id,
       subject,
       subject_id,
       class_name,
       class_id,
       total_score,
       score / total_score score_rate,
       score_level,
       class_rank,
       grade_rank
from t_score_student_stat
group by class_name;

show variables like '%mode%';



select exam_id, moral_edu, count(1)
from t_score_other
group by exam_id, moral_edu
with rollup;

select exam_id, moral_edu evaluation_type, count(1) / sum(count(1)) over (partition by exam_id) rate
from t_score_other
group by exam_id, moral_edu;
select exam_id, sport_edu evaluation_type, count(1) / sum(count(1)) over (partition by exam_id) rate
from t_score_other
group by exam_id, sport_edu;
select exam_id, aesthetic_edu evaluation_type, count(1) / sum(count(1)) over (partition by exam_id) rate
from t_score_other
group by exam_id, aesthetic_edu;
select exam_id, labor_edu evaluation_type, count(1) / sum(count(1)) over (partition by exam_id) rate
from t_score_other
group by exam_id, labor_edu;
select exam_id, intellectual_edu evaluation_type, count(1) / sum(count(1)) over (partition by exam_id) rate
from t_score_other
group by exam_id, intellectual_edu;

with g_temp_moral as (select exam_id, evaluation_type, row_number() over (partition by exam_id order by rate) rk, rate
                      from (select exam_id,
                                   moral_edu                                            evaluation_type,
                                   count(1) / sum(count(1)) over (partition by exam_id) rate
                            from t_score_other
                            group by exam_id, moral_edu) t),
     g_temp_sport as (select exam_id, evaluation_type, row_number() over (partition by exam_id order by rate) rk, rate
                      from (select exam_id,
                                   sport_edu                                            evaluation_type,
                                   count(1) / sum(count(1)) over (partition by exam_id) rate
                            from t_score_other
                            group by exam_id, sport_edu) t),
     g_temp_aesthetic as (select exam_id,
                                 evaluation_type,
                                 row_number() over (partition by exam_id order by rate) rk,
                                 rate
                          from (select exam_id,
                                       aesthetic_edu                                        evaluation_type,
                                       count(1) / sum(count(1)) over (partition by exam_id) rate
                                from t_score_other
                                group by exam_id, aesthetic_edu) t),
     g_temp_labor as (select exam_id, evaluation_type, row_number() over (partition by exam_id order by rate) rk, rate
                      from (select exam_id,
                                   labor_edu                                            evaluation_type,
                                   count(1) / sum(count(1)) over (partition by exam_id) rate
                            from t_score_other
                            group by exam_id, labor_edu) t),
     g_temp_intellectual as (select exam_id,
                                    evaluation_type,
                                    row_number() over (partition by exam_id order by rate) rk,
                                    rate
                             from (select exam_id,
                                          intellectual_edu                                     evaluation_type,
                                          count(1) / sum(count(1)) over (partition by exam_id) rate
                                   from t_score_other
                                   group by exam_id, intellectual_edu) t)
select *
from (select t1.exam_id, t1.evaluation_type moral_max_type, ifnull(t2.rate, 0) moral_a_rate
      from (select exam_id, evaluation_type, rate
            from g_temp_moral
            where rk = 1) t1
               left join (select exam_id, evaluation_type, rate
                          from g_temp_moral
                          where evaluation_type = 'A') t2 on t1.exam_id = t2.exam_id) m
         left join (select t1.exam_id, t1.evaluation_type sport_max_type, ifnull(t2.rate, 0) sport_a_rate
                    from (select exam_id, evaluation_type, rate
                          from g_temp_sport
                          where rk = 1) t1
                             left join (select exam_id, evaluation_type, rate
                                        from g_temp_sport
                                        where evaluation_type = 'A') t2 on t1.exam_id = t2.exam_id) s
                   on m.exam_id = s.exam_id
         left join (select t1.exam_id, t1.evaluation_type aesthetic_max_type, ifnull(t2.rate, 0) aesthetic_a_rate
                    from (select exam_id, evaluation_type, rate
                          from g_temp_aesthetic
                          where rk = 1) t1
                             left join (select exam_id, evaluation_type, rate
                                        from g_temp_aesthetic
                                        where evaluation_type = 'A') t2 on t1.exam_id = t2.exam_id) a
                   on m.exam_id = a.exam_id
         left join (select t1.exam_id, t1.evaluation_type labor_max_type, ifnull(t2.rate, 0) labor_a_rate
                    from (select exam_id, evaluation_type, rate
                          from g_temp_labor
                          where rk = 1) t1
                             left join (select exam_id, evaluation_type, rate
                                        from g_temp_labor
                                        where evaluation_type = 'A') t2 on t1.exam_id = t2.exam_id) l
                   on m.exam_id = l.exam_id
         left join (select t1.exam_id, t1.evaluation_type intellectual_max_type, ifnull(t2.rate, 0) intellectual_a_rate
                    from (select exam_id, evaluation_type, rate
                          from g_temp_intellectual
                          where rk = 1) t1
                             left join (select exam_id, evaluation_type, rate
                                        from g_temp_intellectual
                                        where evaluation_type = 'A') t2 on t1.exam_id = t2.exam_id) i
                   on m.exam_id = i.exam_id;


ALTER TABLE `examination`.`t_compositive_grade_stat`
    ADD UNIQUE INDEX `nk_dep_exam` (`dep_id`, `exam_id`) USING BTREE;
ALTER TABLE `examination`.`t_compositive_class_stat`
    ADD UNIQUE INDEX `nk_dep_exam` (`dep_id`, `exam_id`) USING BTREE;
ALTER TABLE `examination`.`t_score_class_stat`
    ADD UNIQUE INDEX `uk_dep_exam_sub` (`dep_id`, `exam_id`, `subject_id`) USING BTREE;
ALTER TABLE `examination`.`t_score_grade_stat`
    ADD UNIQUE INDEX `uk_dep_exam_sub` (`dep_id`, `exam_id`, `subject_id`) USING BTREE;

-- 计算年级综合素质
select *
from t_compositive_grade_stat
where exam_id = 153;
select *
from t_compositive_class_stat
where exam_id = 153;
select *
from t_score_other
where exam_id = 153;

delete
from t_score_class_stat
where 1 = 1;
delete
from t_score_grade_stat
where 1 = 1;
select *
from t_score_grade_stat;
select *
from t_score_class_stat;


with temp_sbj as (select id,
                         modifier,
                         created_at,
                         updated_at,
                         status,
                         user_id,
                         org_id,
                         exam_id,
                         subject_id,
                         subject_name,
                         total_score
                  from t_examination_subject
                  where 1 = 1
                  union all
                  select max(id),
                         max(modifier),
                         max(created_at),
                         max(updated_at),
                         max(status),
                         max(user_id),
                         max(org_id),
                         max(exam_id),
                         0,
                         '总分',
                         sum(total_score)
                  from t_examination_subject
                  where 1 = 1),
     temp_score as (select id,
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
                    where 1 = 1
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
                    where 1 = 1
                    group by student_name,
                             student_id,
                             student_no,
                             org_id,
                             exam_id,
                             class_name,
                             class_id)
select s.exam_id,
       s.exam_time,
       if(es.subject_id = 0, 1, 0) as                             type,
       max(e.grade)                                               dep_name,
       e.grade_id                                                 dep_id,
       max(s.subject)                                             subject,
       es.subject_id,
       es.total_score,
       count(distinct s.student_id)                               students,
       avg(score)                             avg_score,
       max(score)                              max_score,
       min(score)                             min_score,
       sum(if((score / es.total_score) >= 0.85, 1, 0)) / count(1) great_rate,
       sum(if((score / es.total_score) >= 0.70, 1, 0)) / count(1) good_rate,
       sum(if((score / es.total_score) >= 0.60, 1, 0)) / count(1) passing_rate,
       sum(if((score / es.total_score) < 0.60, 1, 0)) / count(1)  not_passing_rate
from temp_score s,
     temp_sbj es,
     t_examination e
where 1 = 1
  and s.exam_id = es.exam_id
  and s.exam_id = e.id
  and s.subject = es.subject_name
group by s.exam_id,
         s.exam_time,
         type,
         dep_id,
         es.subject_id,
         es.total_score;

select * from t_score where exam_id = 41 and subject_id = 10;

