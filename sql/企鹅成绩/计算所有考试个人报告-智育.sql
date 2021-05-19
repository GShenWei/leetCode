UPDATE t_score_other INNER JOIN
(SELECT oth.exam_id,oth.id,
			 CASE
	     WHEN oth.total_score/sub.full_mark >= 0.85 THEN 'A'
		   WHEN oth.total_score/sub.full_mark >= 0.7 THEN 'B'
			 WHEN oth.total_score/sub.full_mark >= 0.6 THEN 'C'
			 ELSE 'D'
			END as intellectual
from t_score_other oth ,
(SELECT exam_id,sum(total_score) as full_mark from t_examination_subject GROUP BY exam_id) sub
WHERE oth.exam_id = sub.exam_id ) as temp
ON t_score_other.id = temp.id
set intellectual_edu = temp.intellectual