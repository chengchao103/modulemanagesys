  DELIMITER $$
DROP PROCEDURE IF EXISTS prc_update_sepcardmodules_stat $$
CREATE PROCEDURE prc_update_sepcardmodules_stat()
Begin
/*
根据模组信息表更新统计分析表
1.根据模组信息表更新统计分析表的机卡分离的 module_count module_add_count(全局模组,当日新增模组数)
2.遍历客户信息表，统计每个客户的机卡分离的 module_count module_add_count

*/
 declare  p_id        int ;
 declare  p_module_count       int ;
 declare  p_module_add_count        int ;
 declare  p_num        int ;

  declare  p_customer_name        varchar(30) ;

 declare stopFlag int;
DECLARE cursor_customer_sepcardmodules CURSOR
FOR SELECT customer_name FROM tm_main_modules;
DECLARE CONTINUE HANDLER FOR NOT FOUND set stopFlag=1;

/*根据模组信息表更新统计分析表的机卡分离的 module_count module_add_count(全局模组,当日新增模组数)*/
  select count(*) into p_num from ts_sys_statistics_analysis
  WHERE industry=-1 and module_type=-1 and carrier=-1 and comm_sys=-1
        and separate=1 and customer_value=-1 and customer_count=-1
        and customer_add_count=-1 and province=-1 and percentage=-1
        and customer_name=-1;

  if p_num=0 then
  insert into ts_sys_statistics_analysis( industry   ,
   module_type ,
   carrier ,
   comm_sys    ,
   separate       ,
   customer_value,
   customer_count,
   customer_add_count,
   province,
   percentage,
   customer_name
)
values( -1,
   -1,
   -1,
   -1,
   1,

   -1,
   -1,
   -1,
   -1,
   -1,
   -1);
end if;
  select id into p_id from ts_sys_statistics_analysis
  WHERE industry=-1 and module_type=-1 and carrier=-1 and comm_sys=-1
        and separate=1 and customer_value=-1 and customer_count=-1
        and customer_add_count=-1 and province=-1 and percentage=-1
        and customer_name=-1;

select count(*) into p_module_count from tm_main_modules where separate=1;
select count(*) into p_module_add_count from tm_main_modules WHERE separate=1 and
    create_time >date_sub(now(),interval 1 day);
update ts_sys_statistics_analysis set module_count=p_module_count,module_add_count=p_module_add_count where id=p_id;


/*遍历客户信息表，统计每个客户的机卡分离的 module_count module_add_count*/
OPEN cursor_customer_sepcardmodules;
REPEAT
FETCH cursor_customer_sepcardmodules INTO p_customer_name;
begin
  select count(*) into p_num from ts_sys_statistics_analysis
  WHERE industry=-1 and module_type=-1 and carrier=-1 and comm_sys=-1
        and separate=1 and customer_value=-1 and customer_count=-1
        and customer_add_count=-1 and province=-1 and percentage=-1
        and customer_name=p_customer_name;

  if p_num=0 then
   insert into ts_sys_statistics_analysis( industry   ,
   module_type ,
   carrier ,
   comm_sys    ,
   separate       ,
   customer_value,
   customer_count,
   customer_add_count,
   province,
   percentage,
   customer_name
)
values( -1,
   -1,
   -1,
   -1,
   1,
   -1,
   -1,
   -1,
   -1,
   -1,
   p_customer_name);
end if;
 select id into p_id from ts_sys_statistics_analysis
   WHERE industry=-1 and module_type=-1 and carrier=-1 and comm_sys=-1
        and separate=1 and customer_value=-1 and customer_count=-1
        and customer_add_count=-1 and province=-1 and percentage=-1
        and customer_name=p_customer_name;

select count(*) into p_module_count from tm_main_modules where separate=1 and customer_name=p_customer_name;
select count(*) into p_module_add_count from tm_main_modules WHERE separate=1 and
    create_time >date_sub(now(),interval 1 day) and customer_name=p_customer_name;


update ts_sys_statistics_analysis set module_count=p_module_count,module_add_count=p_module_add_count where id=p_id;


end;
UNTIL stopFlag = 1
END REPEAT;
CLOSE cursor_customer_sepcardmodules;

 END$$
DELIMITER ;