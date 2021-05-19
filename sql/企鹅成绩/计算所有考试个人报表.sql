insert into t_score_student_stat (org_id, exam_id, student_name, student_id, subject, subject_id, class_name, class_id,
                                  score, type, total_score, class_rank, grade_rank, class_rank_rate, exam_time,
                                  score_level)
with temp_sbj as (select exam_id,
                         subject_id,
                         subject_name,
                         total_score
                  from t_examination_subject
                  union all
                  select exam_id,
                         0,
                         '总分',
                         sum(total_score)
                  from t_examination_subject
                  GROUP BY exam_id),
     temp_score as (select student_name,
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
                           0 as type
                    from t_score
                    union all
                    select student_name,
                           student_id,
                           student_no,
                           org_id,
                           exam_id,
                           class_name,
                           class_id,
                           '总分'             as subject,
                           sum(score)       as score,
                           0                as subject_id,
                           max(score_level) as score_level,
                           max(exam_time)   as exam_time,
                           1                as type
                    from t_score
                    group by org_id,
                             exam_id,
                             class_id,
                             class_name,
                             student_id,
                             student_no,
                             student_name
     )
        ,
     temp_class as (SELECT exam_id,
                           class_id,
                           subject,
                           count(distinct student_id) as student_num
                    from t_score
                    GROUP BY exam_id, class_id, subject
                    union all
                    SELECT exam_id,
                           class_id,
                           '总分'                       as subject,
                           count(distinct student_id) as student_num
                    from t_score
                    GROUP BY exam_id, class_id
     ),
     temp_grade as (SELECT subject,
                           exam_id,
                           count(distinct student_id) as student_num
                    from t_score
                    GROUP BY exam_id, subject
                    union all
                    SELECT '总分'                       as subject,
                           exam_id,
                           count(distinct student_id) as student_num
                    from t_score
                    GROUP BY exam_id
     ),
     temp_seg_type as (SELECT exam_id,
                              max(count_type) as count_type
                       from t_examination_segment
                       GROUP BY exam_id),
     temp_seg as (select exam_id,
                         segment_seq,
                         segment_name,
                         round(min, 2)                 min,
                         round(min + segment_ratio, 2) max
                  from (select exam_id,
                               segment_name,
                               segment_seq,
                               segment_ratio,
                               IF(@ex != e.exam_id, (@ra := 1) - segment_ratio, @ra - segment_ratio) min,
                               @ex := e.exam_id,
                               @ra := @ra - segment_ratio
                        from t_examination_segment e,
                             (select @ra := 1, @ex := 0) t
                        order by e.exam_id, e.segment_seq) gg)
SELECT aa.org_id,
       aa.exam_id,
       aa.student_name,
       aa.student_id,
       aa.subject,
       aa.subject_id,
       aa.class_name,
       aa.class_id,
       aa.score,
       aa.type,
       aa.total_score,
       aa.class_rank,
       aa.grade_rank,
       aa.class_rank_rate,
       aa.exam_time,
       bb.segment_name as score_level
from (SELECT s.org_id,
             s.exam_id,
             s.student_name,
             s.student_id,
             s.subject,
             s.subject_id,
             s.class_name,
             s.class_id,
             s.score,
             s.type,
             es.total_score,
             g.student_num,
             s.exam_time,
             RANK() over (partition by s.exam_id,s.class_id,s.subject order by s.score desc )                   as class_rank,
             RANK() over (partition by s.exam_id,s.subject order by s.score desc )                              as grade_rank,
             (RANK() over (partition by s.exam_id,s.class_id,s.subject order by s.score desc )) /
             e.student_num                                                                                      as class_rank_rate,
             if(seg.count_type = 1,
                (g.student_num - (RANK() over (partition by s.exam_id,s.subject order by s.score desc)) + 1) /
                g.student_num,
                s.score / es.total_score)                                                                       as stu_rate
      from temp_score s,
           temp_sbj es,
           temp_class e,
           temp_grade g,
           temp_seg_type seg
      where s.exam_id = es.exam_id
        and s.subject = es.subject_name
        and s.exam_id = e.exam_id
        and s.class_id = e.class_id
        and s.subject = e.subject
        and s.exam_id = g.exam_id
        and s.subject = g.subject
        and s.exam_id = seg.exam_id) as aa,
     temp_seg bb
WHERE aa.exam_id = bb.exam_id
  and aa.stu_rate >= bb.min
  and if(aa.stu_rate = 1, 0.99, aa.stu_rate) < bb.max;