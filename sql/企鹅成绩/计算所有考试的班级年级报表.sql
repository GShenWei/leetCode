delete from t_compositive_class_stat;
delete from t_compositive_grade_stat;
delete from t_score_class_stat;
delete from t_score_grade_stat;

-- 年级基本成绩
insert into t_score_grade_stat(exam_id, exam_time, type, dep_name, dep_id, subject, subject_id,
                               total_score, students, avg_score, max_score, min_score, great_rate, good_rate,
                               passing_rate, not_passing_rate)
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
                         exam_id,
                         0,
                         '总分',
                         sum(total_score)
                  from t_examination_subject
                  where 1 = 1
                  group by exam_id),
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
       if(es.subject_id = 0, 1, 0) as                                        type,
       max(e.grade)                                                          dep_name,
       e.grade_id                                                            dep_id,
       max(s.subject)                                                        subject,
       es.subject_id,
       es.total_score,
       count(distinct s.student_id)                                          students,
       ROUND(avg(score), 2)                                                 avg_score,
       ROUND(max(score), 2)                                                 max_score,
       ROUND(min(score), 2)                                                 min_score,
       ROUND(sum(if((score / es.total_score) >= 0.85, 1, 0)) / count(1), 4) great_rate,
       ROUND(sum(if((score / es.total_score) >= 0.70, 1, 0)) / count(1), 4) good_rate,
       ROUND(sum(if((score / es.total_score) >= 0.60, 1, 0)) / count(1), 4) passing_rate,
       ROUND(sum(if((score / es.total_score) < 0.60, 1, 0)) / count(1), 4)  not_passing_rate
from temp_score s,
     temp_sbj es,
     t_examination e
where 1 = 1
  and s.exam_id = es.exam_id
  and s.exam_id = e.id
  and s.subject_id = es.subject_id
group by s.exam_id,
         s.exam_time,
         type,
         dep_id,
         es.subject_id,
         es.total_score
on duplicate key update students         = values(students),
                        total_score      = values(total_score),
                        avg_score        = values(avg_score),
                        max_score        = values(max_score),
                        min_score        = values(min_score),
                        great_rate       = values(great_rate),
                        good_rate        = values(good_rate),
                        passing_rate     = values(passing_rate),
                        not_passing_rate = values(not_passing_rate);


-- 年级 根据得分率的等级
insert into t_score_grade_stat(exam_id, subject, subject_id, dep_id, exam_time,
                               level_rate1, level_rate2, level_rate3, level_rate4,
                               level_rate5, level_rate6, level_rate7, level_rate8, level_rate9, level_rate10)
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
                         exam_id,
                         0,
                         '总分',
                         sum(total_score)
                  from t_examination_subject
                  where 1 = 1
                  group by exam_id),
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
        ,
     temp_seg as (select exam_id,
                         segment_seq,
                         ROUND(min, 2)                 min,
                         ROUND(min + segment_ratio, 2) max
                  from (select exam_id,
                               segment_name,
                               segment_seq,
                               segment_ratio,
                               IF(@ex != e.exam_id, (@ra := 1) - segment_ratio, @ra - segment_ratio) min,
                               @ex := e.exam_id,
                               @ra := @ra - segment_ratio
                        from t_examination_segment e,
                             (select @ra := 1, @ex := 0) t
                        where 1 = 1
                          and e.count_type = 2
                        order by e.exam_id, e.segment_seq) gg
     ),
     temp_stus as (select exam_id, subject_id, count(1) stu_count
                   from temp_score
                   where 1 = 1
                   group by exam_id, subject_id)
select exam_id,
       subject_name                             subject,
       subject_id,
       grade_id,
       '2000-06-10'                             exam_time,
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
from (select s.exam_id,
             e.grade_id,
             max(subject_name)         subject_name,
             s.subject_id,
             segment_seq,
             count(1) / stus.stu_count level_rate
      from temp_score s,
           temp_seg se,
           temp_sbj su,
           temp_stus stus,
           t_examination e
      where s.subject_id = su.subject_id
        and s.exam_id = su.exam_id
        and s.exam_id = se.exam_id
        and s.exam_id = stus.exam_id
        and stus.subject_id = su.subject_id
        and s.score / su.total_score >= min
        and s.score / su.total_score < if(max >= 1, 1.01, max)
        and e.id = s.exam_id
      group by s.exam_id, s.subject_id, s.subject_id, segment_seq
     ) g
group by exam_id, grade_id, subject_id, subject
on duplicate key update level_rate1  = values(level_rate1),
                        level_rate2  = values(level_rate2),
                        level_rate3  = values(level_rate3),
                        level_rate4  = values(level_rate4),
                        level_rate5  = values(level_rate5),
                        level_rate6  = values(level_rate6),
                        level_rate7  = values(level_rate7),
                        level_rate8  = values(level_rate8),
                        level_rate9  = values(level_rate9),
                        level_rate10 = values(level_rate10);

-- 年级 根据排名的等级
insert into t_score_grade_stat(exam_id, subject, subject_id, dep_id, exam_time,
                               level_rate1, level_rate2, level_rate3, level_rate4,
                               level_rate5, level_rate6, level_rate7, level_rate8, level_rate9, level_rate10)
select e.id         as                                           exam_id,
       subject_name                                              subject,
       subject_id,
       e.grade_id,
       '2000-06-10' as                                           exam_time,
       ROUND(sum(IF(segment_seq = 1, es.segment_ratio, 0)), 2)  level_rate1,
       ROUND(sum(IF(segment_seq = 2, es.segment_ratio, 0)), 2)  level_rate2,
       ROUND(sum(IF(segment_seq = 3, es.segment_ratio, 0)), 2)  level_rate3,
       ROUND(sum(IF(segment_seq = 4, es.segment_ratio, 0)), 2)  level_rate4,
       ROUND(sum(IF(segment_seq = 5, es.segment_ratio, 0)), 2)  level_rate5,
       ROUND(sum(IF(segment_seq = 6, es.segment_ratio, 0)), 2)  level_rate6,
       ROUND(sum(IF(segment_seq = 7, es.segment_ratio, 0)), 2)  level_rate7,
       ROUND(sum(IF(segment_seq = 8, es.segment_ratio, 0)), 2)  level_rate8,
       ROUND(sum(IF(segment_seq = 9, es.segment_ratio, 0)), 2)  level_rate9,
       ROUND(sum(IF(segment_seq = 10, es.segment_ratio, 0)), 2) level_rate10
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
      where 1 = 1
      union all
      select max(id),
             max(modifier),
             max(created_at),
             max(updated_at),
             max(status),
             max(user_id),
             max(org_id),
             exam_id,
             0,
             '总分',
             sum(total_score)
      from t_examination_subject
      where 1 = 1 group by exam_id) sb
where e.id = es.exam_id
  and e.id = sb.exam_id
  and 1 = 1
  and es.count_type = 1
group by exam_id, grade_id, subject_id, subject
on duplicate key update level_rate1  = values(level_rate1),
                        level_rate2  = values(level_rate2),
                        level_rate3  = values(level_rate3),
                        level_rate4  = values(level_rate4),
                        level_rate5  = values(level_rate5),
                        level_rate6  = values(level_rate6),
                        level_rate7  = values(level_rate7),
                        level_rate8  = values(level_rate8),
                        level_rate9  = values(level_rate9),
                        level_rate10 = values(level_rate10);


-- 班级基本成绩
insert into t_score_class_stat(exam_id, exam_time, type, dep_name, dep_id, subject, subject_id,
                               total_score, students, avg_score, max_score, min_score, great_rate, good_rate,
                               passing_rate, not_passing_rate)
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
                         exam_id,
                         0,
                         '总分',
                         sum(total_score)
                  from t_examination_subject
                  where 1 = 1
                  group by exam_id),
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
       if(es.subject_id = 0, 1, 0) as                                        type,
       max(s.class_name)                                                     dep_name,
       s.class_id                                                            dep_id,
       max(s.subject)                                                        subject,
       es.subject_id,
       es.total_score,
       count(distinct s.student_id)                                          students,
       ROUND(avg(score), 2)                                                 avg_score,
       ROUND(max(score), 2)                                                 max_score,
       ROUND(min(score), 2)                                                 min_score,
       ROUND(sum(if((score / es.total_score) >= 0.85, 1, 0)) / count(1), 4) great_rate,
       ROUND(sum(if((score / es.total_score) >= 0.70, 1, 0)) / count(1), 4) good_rate,
       ROUND(sum(if((score / es.total_score) >= 0.60, 1, 0)) / count(1), 4) passing_rate,
       ROUND(sum(if((score / es.total_score) < 0.60, 1, 0)) / count(1), 4)  not_passing_rate
from temp_score s,
     temp_sbj es,
     t_examination e
where 1 = 1
  and s.exam_id = es.exam_id
  and s.exam_id = e.id
  and s.subject_id = es.subject_id
group by s.exam_id,
         s.exam_time,
         type,
         dep_id,
         es.subject_id,
         es.total_score
on duplicate key update students         = values(students),
                        total_score      = values(total_score),
                        avg_score        = values(avg_score),
                        max_score        = values(max_score),
                        min_score        = values(min_score),
                        great_rate       = values(great_rate),
                        good_rate        = values(good_rate),
                        passing_rate     = values(passing_rate),
                        not_passing_rate = values(not_passing_rate);

-- 班级 根据得分率 等级
insert into t_score_class_stat(exam_id, dep_id, subject, subject_id, exam_time,
                               level_rate1, level_rate2, level_rate3, level_rate4,
                               level_rate5, level_rate6, level_rate7, level_rate8, level_rate9, level_rate10)
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
                         exam_id,
                         0,
                         '总分',
                         sum(total_score)
                  from t_examination_subject
                  where 1 = 1
                  group by exam_id),
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
        ,
     temp_seg as (select exam_id,
                         segment_seq,
                         ROUND(min, 2)                 min,
                         ROUND(min + segment_ratio, 2) max
                  from (select exam_id,
                               segment_name,
                               segment_seq,
                               segment_ratio,
                               IF(@ex != e.exam_id, (@ra := 1) - segment_ratio, @ra - segment_ratio) min,
                               @ex := e.exam_id,
                               @ra := @ra - segment_ratio
                        from t_examination_segment e,
                             (select @ra := 1, @ex := 0) t
                        where 1 = 1
                        order by e.exam_id, e.segment_seq) gg
     ),
     temp_stus as (select exam_id, subject_id, count(1) stu_count
                   from temp_score
                   where 1 = 1
                   group by exam_id, subject_id)
select exam_id,
       class_id                                 dep_id,
       subject_name                             subject,
       subject_id,
       '2000-06-10'                             exam_time,
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
from (select s.exam_id,
             s.class_id,
             max(subject_name)         subject_name,
             s.subject_id,
             segment_seq,
             count(1) / stus.stu_count level_rate
      from temp_score s,
           temp_seg se,
           temp_sbj su,
           temp_stus stus
      where s.subject_id = su.subject_id
        and s.exam_id = su.exam_id
        and s.exam_id = se.exam_id
        and s.exam_id = stus.exam_id
        and stus.subject_id = su.subject_id
        and s.score / su.total_score >= min
        and s.score / su.total_score < if(max >= 1, 1.01, max)
        and 1 = 1
      group by s.exam_id, s.class_id, s.subject_id, s.subject_id, segment_seq
     ) g
group by exam_id, dep_id, subject_id, subject
on duplicate key update level_rate1  = values(level_rate1),
                        level_rate2  = values(level_rate2),
                        level_rate3  = values(level_rate3),
                        level_rate4  = values(level_rate4),
                        level_rate5  = values(level_rate5),
                        level_rate6  = values(level_rate6),
                        level_rate7  = values(level_rate7),
                        level_rate8  = values(level_rate8),
                        level_rate9  = values(level_rate9),
                        level_rate10 = values(level_rate10);

-- 班级根据排名 等级
insert into t_score_class_stat(exam_id, dep_id, subject, subject_id, exam_time,
                               level_rate1, level_rate2, level_rate3, level_rate4,
                               level_rate5, level_rate6, level_rate7, level_rate8, level_rate9, level_rate10)
with temp_seg as (select exam_id,
                         segment_seq,
                         ROUND(max - segment_ratio, 2) min,
                         ROUND(max, 2)                 max
                  from (select exam_id,
                               segment_name,
                               segment_seq,
                               segment_ratio,
                               IF(@ex != e.exam_id, (@ra := 0) + segment_ratio, @ra + segment_ratio) max,
                               @ex := e.exam_id,
                               @ra := @ra + segment_ratio
                        from t_examination_segment e,
                             (select @ra := 0, @ex := 0) t
                        where 1 = 1
                          and e.count_type = 1
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
                  where 1 = 1
                  union all
                  select max(id),
                         max(modifier),
                         max(created_at),
                         max(updated_at),
                         max(status),
                         max(user_id),
                         max(org_id),
                         exam_id,
                         0,
                         '总分',
                         sum(total_score)
                  from t_examination_subject
                  where 1 = 1
                  group by exam_id),
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
                           percent_rank() over (partition by exam_id,subject_id order by score desc) pct_rank
                    from t_score
                    where 1 = 1
                    union all
                    select t.*, percent_rank() over (partition by exam_id order by score desc) pct_rank
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
                          where 1 = 1
                          group by student_name,
                                   student_id,
                                   student_no,
                                   org_id,
                                   exam_id,
                                   class_name,
                                   class_id) t),
     temp_stus as (select exam_id, subject_id, count(1) stu_count
                   from temp_score
                   where 1 = 1
                   group by exam_id, subject_id)
select exam_id,
       class_id                                 dep_id,
       subject_name                             subject,
       subject_id,
       '2000-06-10'                             exam_time,
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
from (select s.exam_id,
             class_id,
             max(subject_name)         subject_name,
             s.subject_id,
             segment_seq,
             count(1) / stus.stu_count level_rate
      from temp_score s,
           temp_seg se,
           temp_sbj su,
           temp_stus stus
      where s.subject_id = su.subject_id
        and s.exam_id = su.exam_id
        and s.exam_id = se.exam_id
        and s.exam_id = stus.exam_id
        and stus.subject_id = su.subject_id
        and s.pct_rank >= min
        and s.pct_rank < if(max >= 1, 1.01, max)
        and 1 = 1
      group by s.exam_id, s.class_id, s.subject_id, s.subject_id, segment_seq
     ) g
group by exam_id, dep_id, subject_id, subject
on duplicate key update level_rate1  = values(level_rate1),
                        level_rate2  = values(level_rate2),
                        level_rate3  = values(level_rate3),
                        level_rate4  = values(level_rate4),
                        level_rate5  = values(level_rate5),
                        level_rate6  = values(level_rate6),
                        level_rate7  = values(level_rate7),
                        level_rate8  = values(level_rate8),
                        level_rate9  = values(level_rate9),
                        level_rate10 = values(level_rate10);


-- 班级综合
insert into t_compositive_class_stat(exam_id, org_id, dep_name, dep_id, moral_edu, moral_rate_a, sport_edu,
                                     sport_rate_a, aesthetic_edu, aesthetic_rate_a, labor_edu, labor_rate_a,
                                     intellectual_edu, intellectual_rate_a)
with g_temp_moral as (select exam_id,
                             class_id,
                             class_name,
                             evaluation_type,
                             row_number() over (partition by exam_id, class_id order by rate) rk,
                             rate
                      from (select exam_id,
                                   class_id,
                                   max(class_name)                                      class_name,
                                   moral_edu                                            evaluation_type,
                                   count(1) / sum(count(1)) over (partition by exam_id) rate
                            from t_score_other
                            where 1 = 1
                            group by exam_id, class_id, moral_edu) t),
     g_temp_sport as (select exam_id,
                             class_id,
                             class_name,
                             evaluation_type,
                             row_number() over (partition by exam_id, class_id order by rate) rk,
                             rate
                      from (select exam_id,
                                   class_id,
                                   max(class_name)                                      class_name,
                                   sport_edu                                            evaluation_type,
                                   count(1) / sum(count(1)) over (partition by exam_id) rate
                            from t_score_other
                            where 1 = 1
                            group by exam_id, class_id, sport_edu) t),
     g_temp_aesthetic as (select exam_id,
                                 class_id,
                                 class_name,
                                 evaluation_type,
                                 row_number() over (partition by exam_id, class_id order by rate) rk,
                                 rate
                          from (select exam_id,
                                       class_id,
                                       max(class_name)                                      class_name,
                                       aesthetic_edu                                        evaluation_type,
                                       count(1) / sum(count(1)) over (partition by exam_id) rate
                                from t_score_other
                                where 1 = 1
                                group by exam_id, class_id, aesthetic_edu) t),
     g_temp_labor as (select exam_id,
                             class_id,
                             class_name,
                             evaluation_type,
                             row_number() over (partition by exam_id, class_id order by rate) rk,
                             rate
                      from (select exam_id,
                                   class_id,
                                   max(class_name)                                      class_name,
                                   labor_edu                                            evaluation_type,
                                   count(1) / sum(count(1)) over (partition by exam_id) rate
                            from t_score_other
                            where 1 = 1
                            group by exam_id, class_id, labor_edu) t),
     g_temp_intellectual as (select exam_id,
                                    class_id,
                                    class_name,
                                    evaluation_type,
                                    row_number() over (partition by exam_id, class_id order by rate) rk,
                                    rate
                             from (select exam_id,
                                          class_id,
                                          max(class_name)                                      class_name,
                                          intellectual_edu                                     evaluation_type,
                                          count(1) / sum(count(1)) over (partition by exam_id) rate
                                   from t_score_other
                                   where 1 = 1
                                   group by exam_id, class_id, intellectual_edu) t)
select m.exam_id,
       ex.org_id,
       m.class_name dep_name,
       m.class_id   dep_id,
       moral_edu,
       ROUND(moral_rate_a, 4),
       sport_edu,
       ROUND(sport_rate_a, 4),
       aesthetic_edu,
       ROUND(aesthetic_rate_a, 4),
       labor_edu,
       ROUND(labor_rate_a, 4),
       intellectual_edu,
       ROUND(intellectual_rate_a, 4)
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
         left join t_examination ex on m.exam_id = ex.id
on duplicate key update moral_edu           = values(moral_edu),
                        moral_rate_a        = values(moral_rate_a),
                        sport_edu           = values(sport_edu),
                        sport_rate_a        = values(sport_rate_a),
                        aesthetic_edu       = values(aesthetic_edu),
                        aesthetic_rate_a    = values(aesthetic_rate_a),
                        labor_edu           = values(labor_edu),
                        labor_rate_a        = values(labor_rate_a),
                        intellectual_edu    = values(intellectual_edu),
                        intellectual_rate_a = values(intellectual_rate_a);


-- 年级综合
insert into t_compositive_grade_stat(exam_id, org_id, dep_name, dep_id, moral_edu, moral_rate_a, sport_edu,
                                     sport_rate_a, aesthetic_edu, aesthetic_rate_a, labor_edu, labor_rate_a,
                                     intellectual_edu, intellectual_rate_a)
with g_temp_moral as (select exam_id, evaluation_type, row_number() over (partition by exam_id order by rate) rk, rate
                      from (select exam_id,
                                   moral_edu                                            evaluation_type,
                                   count(1) / sum(count(1)) over (partition by exam_id) rate
                            from t_score_other
                            where 1 = 1
                            group by exam_id, moral_edu) t),
     g_temp_sport as (select exam_id, evaluation_type, row_number() over (partition by exam_id order by rate) rk, rate
                      from (select exam_id,
                                   sport_edu                                            evaluation_type,
                                   count(1) / sum(count(1)) over (partition by exam_id) rate
                            from t_score_other
                            where 1 = 1
                            group by exam_id, sport_edu) t),
     g_temp_aesthetic as (select exam_id,
                                 evaluation_type,
                                 row_number() over (partition by exam_id order by rate) rk,
                                 rate
                          from (select exam_id,
                                       aesthetic_edu                                        evaluation_type,
                                       count(1) / sum(count(1)) over (partition by exam_id) rate
                                from t_score_other
                                where 1 = 1
                                group by exam_id, aesthetic_edu) t),
     g_temp_labor as (select exam_id, evaluation_type, row_number() over (partition by exam_id order by rate) rk, rate
                      from (select exam_id,
                                   labor_edu                                            evaluation_type,
                                   count(1) / sum(count(1)) over (partition by exam_id) rate
                            from t_score_other
                            where 1 = 1
                            group by exam_id, labor_edu) t),
     g_temp_intellectual as (select exam_id,
                                    evaluation_type,
                                    row_number() over (partition by exam_id order by rate) rk,
                                    rate
                             from (select exam_id,
                                          intellectual_edu                                     evaluation_type,
                                          count(1) / sum(count(1)) over (partition by exam_id) rate
                                   from t_score_other
                                   where 1 = 1
                                   group by exam_id, intellectual_edu) t)
select m.exam_id,
       ex.org_id,
       ex.grade    dep_name,
       ex.grade_id dep_id,
       moral_edu,
       ROUND(moral_rate_a, 4),
       sport_edu,
       ROUND(sport_rate_a, 4),
       aesthetic_edu,
       ROUND(aesthetic_rate_a, 4),
       labor_edu,
       ROUND(labor_rate_a, 4),
       intellectual_edu,
       ROUND(intellectual_rate_a, 4)
from (select t1.exam_id, t1.evaluation_type moral_edu, ifnull(t2.rate, 0) moral_rate_a
      from (select exam_id, evaluation_type, rate
            from g_temp_moral
            where rk = 1) t1
               left join (select exam_id, evaluation_type, rate
                          from g_temp_moral
                          where evaluation_type = 'A') t2 on t1.exam_id = t2.exam_id) m
         left join (select t1.exam_id, t1.evaluation_type sport_edu, ifnull(t2.rate, 0) sport_rate_a
                    from (select exam_id, evaluation_type, rate
                          from g_temp_sport
                          where rk = 1) t1
                             left join (select exam_id, evaluation_type, rate
                                        from g_temp_sport
                                        where evaluation_type = 'A') t2 on t1.exam_id = t2.exam_id) s
                   on m.exam_id = s.exam_id
         left join (select t1.exam_id, t1.evaluation_type aesthetic_edu, ifnull(t2.rate, 0) aesthetic_rate_a
                    from (select exam_id, evaluation_type, rate
                          from g_temp_aesthetic
                          where rk = 1) t1
                             left join (select exam_id, evaluation_type, rate
                                        from g_temp_aesthetic
                                        where evaluation_type = 'A') t2 on t1.exam_id = t2.exam_id) a
                   on m.exam_id = a.exam_id
         left join (select t1.exam_id, t1.evaluation_type labor_edu, ifnull(t2.rate, 0) labor_rate_a
                    from (select exam_id, evaluation_type, rate
                          from g_temp_labor
                          where rk = 1) t1
                             left join (select exam_id, evaluation_type, rate
                                        from g_temp_labor
                                        where evaluation_type = 'A') t2 on t1.exam_id = t2.exam_id) l
                   on m.exam_id = l.exam_id
         left join (select t1.exam_id, t1.evaluation_type intellectual_edu, ifnull(t2.rate, 0) intellectual_rate_a
                    from (select exam_id, evaluation_type, rate
                          from g_temp_intellectual
                          where rk = 1) t1
                             left join (select exam_id, evaluation_type, rate
                                        from g_temp_intellectual
                                        where evaluation_type = 'A') t2 on t1.exam_id = t2.exam_id) i
                   on m.exam_id = i.exam_id
         left join t_examination ex on m.exam_id = ex.id
on duplicate key update moral_edu           = values(moral_edu),
                        moral_rate_a        = values(moral_rate_a),
                        sport_edu           = values(sport_edu),
                        sport_rate_a        = values(sport_rate_a),
                        aesthetic_edu       = values(aesthetic_edu),
                        aesthetic_rate_a    = values(aesthetic_rate_a),
                        labor_edu           = values(labor_edu),
                        labor_rate_a        = values(labor_rate_a),
                        intellectual_edu    = values(intellectual_edu),
                        intellectual_rate_a = values(intellectual_rate_a);


-- 检查班级，年级成绩报表是否全部被计算   0 代表全部被计算
select count(*)
from t_score s,
     t_examination e
where s.exam_id = e.id
  and not exists(select 1
                 from t_score_class_stat cs
                 where s.exam_id = cs.exam_id
                   and s.class_id = cs.dep_id
                   and s.subject_id = cs.subject_id);
select count(*)
from t_score_other s,
     t_examination e
where s.exam_id = e.id
  and not exists(select 1
                 from t_score_class_stat cs
                 where s.exam_id = cs.exam_id
                   and s.class_id = cs.dep_id
                   and 0 = cs.subject_id);