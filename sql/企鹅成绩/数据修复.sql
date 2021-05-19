-- 状态数据修复开始，新老数据状态映射关系，发布后需要统一数据
-- 老版本 opt_status=0(未发布) --> 映射到opt_status=2(计算成功); 更新sql：
update `examination`.`t_examination`
set opt_status = 2
where version = 1
  and opt_status = 0;
-- 老版本 opt_status=1(已发布) --> 映射到opt_status=4(已发布)；更新sql：
update `examination`.`t_examination`
set opt_status = 4
where version = 1
  and opt_status = 1;
-- 状态数据修复结束

-- 估分sql 开始
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
           when ss <= 100 then 100
           when ss <= 120 then 120
           when ss <= 150 then 150
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
           total_score  = values(total_score);

update t_score s,t_examination e
set s.exam_time = e.exam_time
where s.exam_id = e.id;

-- 估分sql 结束

-- 补充t_score考试时间开始
update t_score s,t_examination e
set s.exam_time = e.exam_time
where s.exam_id = e.id;
-- 补充t_score考试时间结束

-- segment表修复 开始
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
-- segment表修复 结束

--  成绩表有班级信息，但是 t_examination_class 没有班级信息 开始
-- 可能需要先执行   考试成绩没有班级信息的情况

-- 考试成绩没有班级信息的情况 开始
-- 寻找一个正确的班级信息
drop table if exists temp_class;
create table temp_class
select exam_id, class_name, @dd := @dd + 1 class_id
from (select exam_id, class_name
      from t_score
      where class_id = 0
      group by exam_id, class_name) t,
     (select @dd := 900000) tt;

insert into t_examination_class(modifier, created_at, updated_at, status, user_id, org_id, exam_id, class_id,
                                class_name, path_name)
select modifier,
       created_at,
       updated_at,
       status,
       user_id,
       org_id,
       exam_id,
       class_id,
       class_name,
       concat(e.grade, c.class_name)
from temp_class c,
     t_examination e
where e.id = c.exam_id;
update t_score s,temp_class g
set s.class_id = g.class_id
where s.exam_id = g.exam_id
  and s.class_name = g.class_name
  and s.class_id = 0;
drop table if exists temp_class;
-- 考试成绩没有班级信息的情况 结束

insert into t_examination_class(modifier, created_at, updated_at, status, user_id, org_id, exam_id, class_id,
                                class_name, path_name)
select max(s.modifier),
       max(s.created_at),
       max(s.updated_at),
       max(s.status),
       max(s.user_id),
       max(s.org_id),
       s.exam_id,
       s.class_id,
       max(s.class_name),
       concat(max(e.grade), max(s.class_name))
from t_score s
         inner join t_examination e on s.exam_id = e.id
         left join t_examination_class c on s.exam_id = c.exam_id
where c.class_id is null
group by s.exam_id, s.class_id;
--  成绩表有班级信息，但是 t_examination_class 没有班级信息 结束


-- 补充t_examination 中 class 和 subjects信息  开始
update t_examination e,(select e.id, group_concat(c.class_name) nn
                        from t_examination e,
                             t_examination_class c
                        where e.id = c.exam_id
                        group by e.id) t
set e.class =t.nn
where e.id = t.id
  and e.grade_id > 0
  and e.bg_type = 4
  and (e.class is null or e.class = '');

/*update t_examination e,(select e.id,group_concat(c.subject_name) nn
                        from t_examination e,
                             t_examination_subject c
                        where e.id = c.exam_id group by e.id) t
set e.subjects =t.nn where e.id = t.id;*/
-- 补充t_examination 中 class 和 subjects信息  结束

-- 修改综合评价默认值 开始
update t_examination
set quality       = if(exam_type = '期末考试', 1, 0),
    message       = if(exam_type = '期末考试', 1, 0),
    attention_set = if(exam_type = '期末考试', 1, 0)
where version = 1;
-- 修改综合评价默认值 结束
-- -------------------------------------------------------------------------------------------------------------------------

-- 考试成绩没有年级的情况 开始
-- 寻找一个正确的年级信息
select distinct class_id
from t_examination e,
     t_score s
where e.id = s.exam_id
  and e.grade_id = 0;
-- 后续由代码来操作
-- 考试成绩没有年级的情况 结束
