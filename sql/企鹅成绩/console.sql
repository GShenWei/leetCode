select *
from t_examination
where user_id = 30048063;
update t_examination
set opt_status = 4
where user_id = 30048063;

select *
from t_examination
where user_id = 30048063;

select format(rand() * 100, 0);

update t_score
set score = format(rand() * 100, 0)
where user_id = 30048063;

update t_score_other o,(select exam_id, student_id, sum(score) ss
                        from t_score s
                        where user_id = 30048063
                        group by exam_id, student_id) t
set o.total_score = t.ss
where o.exam_id = t.exam_id
  and o.student_id = t.student_id;

select *
from t_score_other
where user_id = 30048063;


select *
from t_score_class_stat;
select *
from t_score_grade_stat;
select *
from t_compositive_class_stat;
select *
from t_compositive_grade_stat;

update t_score t,(select 1 id, 2 sub union all select 2, 3) g
set t.subject_id = g.sub
where t.id = g.id;


update t_score_other
set intellectual_edu='A'
WHERE exam_id = 2
  and class_id = '5044189'
  and student_id = '3723812765';
update t_score_other
set intellectual_edu='A'
WHERE exam_id = 2
  and class_id = '5044189'
  and student_id = '3723812764';
update t_score_other
set intellectual_edu='A'
WHERE exam_id = 2
  and class_id = '5044189'
  and student_id = '3723812763';

update t_score_other t,(
    select 2 exam_id, '5044189' class_id, '3723812765' student_id, 'A' dt
    union all
    select 2, '5044189', '3723812764', 'A'
    union all
    select 2, '5044189', '3723812763', 'A'
) g
set t.intellectual_edu = g.dt
where t.exam_id = g.exam_id
  and t.class_id = g.class_id
  and t.student_id = g.student_id;

select *
from t_examination_segment
where exam_id in (124, 125, 126, 127);

select *
from t_score_class_stat
where exam_id = 124;

DELETE
from t_score_student_stat
WHERE exam_id = 2
  and student_id in (
    SELECT DISTINCT student_id
    from t_score_student_stat
    WHERE exam_id = 2
      and subject_id != 0
      and id not in (
        SELECT st.id
        from t_score_student_stat st
                 inner join t_score e on st.exam_id = e.exam_id
        WHERE e.exam_id = 2
          and st.exam_id = 2
          and st.student_id = e.student_id
          and st.subject_id = e.subject_id));


delete ss
from t_score_student_stat ss
         left join t_score t on ss.exam_id = 2
    and ss.subject_id != 0
    and ss.exam_id = t.exam_id
    and ss.subject_id = t.subject_id
    and ss.student_id = t.student_id
where t.id is null;

delete ss
from t_score_student_stat ss
where ss.subject_id = 0
  and ss.exam_id = 2
  and not exists(select 1 from t_score t where ss.exam_id = t.exam_id and ss.student_id = t.student_id);

select *
from t_examination
where id = 127;
select *
from t_score_class_stat
where exam_id = 127;


ALTER TABLE `examination`.`t_examination_class`
    MODIFY COLUMN `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT FIRST;
ALTER TABLE `examination`.`t_examination_subject`
    MODIFY COLUMN `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT FIRST;



SELECT count(*)
FROM `t_score_student_stat`
WHERE (exam_id = 127 and subject_id = 0)
  AND (class_id = 24);

select *
from t_score_student_stat
where exam_id = 127;


SELECT a.*
FROM t_examination a
         left join t_examination_input_user b on a.id = b.exam_id
WHERE (
        (a.user_id = '30048057' and a.version = 1) or (b.user_id = '30048057' and a.version = 2)
    )
  and a.org_id = 100010
  and a.status = 0
ORDER BY a.id desc
LIMIT 10 OFFSET 0;


select *
from t_examination a
where a.user_id = '30048057'
  and a.org_id = 100010
  and a.status = 0;

select *
from t_examination_input_user a
where a.user_id = '30048057'
  and a.org_id = 100010
  and a.status = 0;

select *
from t_score_student_stat
where exam_id = 127;

select count(*) from t_score_student_stat;

select * from t_score_student_stat where exam_id = 127;

insert into t_score_student_stat(created_at, org_id, exam_id, type, student_name, student_id, subject, subject_id,
                                 class_name, class_id, total_score, score, score_level, class_rank, grade_rank,
                                 exam_time, class_rank_rate)

select created_at, org_id, exam_id, type, m, 90009113+d, subject, subject_id,
       class_name, class_id, total_score, score, score_level, class_rank, grade_rank,
       exam_time, class_rank_rate
from t_score_student_stat tt1,
     (select concat('假张', @a := @a + 1) m, @a d
      from (select @a := 0) t,
           (select 1 from t_examination limit 100) t2) tt2
where tt1.exam_id = 127 and tt1.student_id = 30009113;

select * from t_score where exam_id = 127;
select * from t_examination ;
select * from t_examination where id = 124;