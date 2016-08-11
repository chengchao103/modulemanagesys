 DELIMITER $$
DROP PROCEDURE IF EXISTS prc_update_customers_stat $$
CREATE PROCEDURE prc_update_customers_stat()
Begin
/*
根据客户信息表更新统计分析表
1.根据客户信息表更新统计分析表的 customer_count 、customer_add_count(客户数,当日新增客户数)


*/
 declare  p_id        int ;
 declare  p_customer_count        int ;
 declare  p_customer_add_count        int ;
 declare  p_num       int ;

  select COUNT(*) into p_num from ts_sys_statistics_analysis
  WHERE industry=-1 and module_type=-1 and carrier=-1 and comm_sys=-1
  and separate=-1 and module_count=-1 and module_add_count=-1 and customer_name =-1
  and customer_value=-1 and province=-1 and  percentage=-1;

  if p_num=0 then
  insert into ts_sys_statistics_analysis( industry   ,
   module_type ,
   carrier ,
   comm_sys    ,
   separate       ,
   module_count,
   module_add_count,
   customer_name,
   customer_value,
   province,
   percentage
)
values( -1,
   -1,
   -1,
   -1,
   -1,
   -1,
   -1,
   -1,
   -1,
   -1,
   -1);

end if;
select id into p_id from ts_sys_statistics_analysis
  WHERE industry=-1 and module_type=-1 and carrier=-1 and comm_sys=-1
  and separate=-1 and module_count=-1 and module_add_count=-1 and customer_name =-1
  and customer_value=-1 and province=-1 and  percentage=-1;

select count(*) into p_customer_count from tm_main_customers;
select count(*) into p_customer_add_count from tm_main_customers WHERE
    create_time >date_sub(now(),interval 1 day);
update ts_sys_statistics_analysis set customer_count=p_customer_count,customer_add_count=p_customer_add_count where id=p_id;

 END$$
DELIMITER ;