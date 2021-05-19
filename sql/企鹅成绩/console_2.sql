select * from t_score where exam_id = 152 and student_id = 30014164;
select * from t_score_other where exam_id = 152 and student_id = 30014164;


delete s
from t_score_other s,
     (select max(id) mdd, student_id, exam_id,  count(1) xx
      from t_score_other
      group by student_id, exam_id
      having xx > 1) t
where s.id != mdd
  and s.student_id = t.student_id
  and s.exam_id = t.exam_id;