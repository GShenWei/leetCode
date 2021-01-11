CREATE TABLE rollup_t
(
    orderid   int         NOT NULL,
    orderdate date        NOT NULL,
    empid     int         NOT NULL,
    custid    varchar(10) NOT NULL,
    qty       int         NOT NULL,
    PRIMARY KEY (orderid, orderdate)
);
iNSERT INTO rollup_t
SELECT 1, '2010-01-02', 3, 'A', 10;
INSERT INTO rollup_t
SELECT 2, '2010-04-02', 2, 'B', 20;
INSERT INTO rollup_t
SELECT 3, '2010-05-02', 1, 'A', 30;
INSERT INTO rollup_t
SELECT 4, '2010-07-02', 3, 'D', 40;
INSERT INTO rollup_t
SELECT 5, '2011-01-02', 4, 'A', 20;
INSERT INTO rollup_t
SELECT 6, '2011-01-02', 3, 'B', 30;
INSERT INTO rollup_t
SELECT 7, '2011-01-02', 1, 'C', 40;
INSERT INTO rollup_t
SELECT 8, '2009-01-02', 2, 'A', 10;
INSERT INTO rollup_t
SELECT 9, '2009-01-02', 3, 'B', 20;

select * from rollup_t;

SELECT YEAR(orderdate) year,
       SUM(qty) sum
FROM rollup_t
GROUP BY YEAR(orderdate)
WITH ROLLUP;

SELECT empid, custid,
       YEAR(orderdate) year,
       SUM(qty) sum
FROM rollup_t
GROUP BY empid,custid,YEAR(orderdate)
WITH ROLLUP ;








WITH RECURSIVE pp as
                   (
                       select *
                       from categories
                       where bank_id = 23
                         and parent_id = 0
                         and type > 10
                       UNION ALL
                       select t.*
                       from categories t
                                inner join pp tcte on t.parent_id = tcte.id
                   )
SELECT pp.id, pp.name ,count(1)
FROM pp,
     questions_23 q,
     question_categories_23 qc
where is_leaf = 1
  and q.source_id = qc.question_id
  and qc.category_id = pp.id
  AND q.ques_type IN (203, 201)
group by pp.id, pp.name order by pp.id;