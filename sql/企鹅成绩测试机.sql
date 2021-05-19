select *
from t_examination
order by id desc;

select *
from t_score
where exam_id = 277;
select *
from t_score_other
where exam_id = 277;
select *
from t_examination_subject
where exam_id = 277;
select *
from t_examination_class
where exam_id = 277;

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
where exam_id = 277
group by exam_id;

delete
from t_score
where exam_id = 277
  and class_id = '';
delete
from t_score_other
where exam_id = 277
  and class_id = '';

select *
from t_score_student_stat
where exam_id = 277;
select *
from t_score_class_stat
where exam_id = 277;
select *
from t_score_grade_stat
where exam_id = 277;
select *
from t_compositive_class_stat
where exam_id = 277;
select *
from t_compositive_grade_stat
where exam_id = 277;

select *
from t_examination
where name = 'qita';


insert into t_score_grade_stat(exam_id, subject, subject_id, dep_id, exam_time,
                               level_rate1, level_rate2, level_rate3, level_rate4,
                               level_rate5, level_rate6, level_rate7, level_rate8, level_rate9, level_rate10)
select e.id         as                                          exam_id,
       subject_name                                             subject,
       subject_id,
       e.grade_id,
       '2000-06-10' as                                          exam_time,
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
      where 1 = 1
      group by exam_id) sb
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

select * from t_score_student_stat where exam_id = 282;
select * from t_score_class_stat where exam_id = 282;
select * from t_score where exam_id = 282;


