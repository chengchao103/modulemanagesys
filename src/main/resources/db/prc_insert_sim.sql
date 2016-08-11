 DELIMITER $$
DROP PROCEDURE IF EXISTS prc_insert_sim $$
CREATE PROCEDURE prc_insert_sim()
Begin
/*
生成SIM卡信息表
1.生成SIM临时表
2.生成客户信息表
3.生成SIM表数据
4.修改模组信息表客户ID
5.修改模组信息位置信息
6.更新SIM表数据处理时间
7.删除临时表
*/
drop TABLE  if EXISTS ti_base_api_tmp;
CREATE TABLE ti_base_api_tmp SELECT * FROM
    ti_base_api
WHERE
    import_time >date_sub(now(),interval 1 day);
create index ti_base_api_tmp_idx on ti_base_api_tmp(iccid);
create index ti_base_api_tmp_idx2 on ti_base_api_tmp(customer_name);

insert into tm_main_customers( customer_name  ,
   industry   ,
   province,
   create_time)
select distinct customer_name,industry,province,now() from ti_base_api_tmp p
where not exists(select 1 from tm_main_customers s where p.customer_name=s.customer_name ) ;

update tm_main_sims s join ti_base_api_tmp p  on s.iccid=p.iccid and  s.combo_sum is null
set  s.combo_sum =p.combo_sum ,
  s.combo_useage= p. combo_useage,
  s. online_time=p. online_time,
  s. combo_remain=p. combo_remain ,
  s. status=p.status ,
  s.combo_name=p.combo_name;

insert into tm_main_sims(
 iccid        ,
   combo_name ,
   combo_sum  ,
   combo_useage,
   online_time,
   combo_remain ,
   status  ,
   customer_name,
   create_time
)
 select
  p.iccid              ,
  p. combo_name ,
  p.combo_sum  ,
  p. combo_useage,
  p. online_time,
  p. combo_remain ,
  p.status ,
  p.customer_name,
  now()
 from ti_base_api_tmp p
 where not exists(select 1 from tm_main_sims s where s.iccid=p.iccid);


drop table ti_base_api_tmp;
 END$$
DELIMITER ;