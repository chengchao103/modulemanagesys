  DELIMITER $$
DROP PROCEDURE IF EXISTS prc_update_commsys_Anal $$
CREATE PROCEDURE prc_update_commsys_Anal()
Begin
/*
根据通讯制式分析模组数量
1.分析各种通讯制式全局数量及占比
2.分析每个客户的各种通讯制式数量及占比
*/
declare  p_id int ;
declare  p_comm_sys varchar(10) ;
declare  p_module_count int DEFAULT 0;
declare  p_module_add_count int DEFAULT 0;
declare  p_percentage FLOAT DEFAULT 0.0;
declare  p_total_module_count int DEFAULT 0;
declare  p_customer_name varchar(30) ;
declare  p_num int ;


declare stopFlag int;
DECLARE commsys_Anal CURSOR
FOR select distinct comm_sys from tm_main_modules;

DECLARE customer_commsys_Anal CURSOR
FOR SELECT customer_name,comm_sys,count(sn) as module_count,count(create_time > date_sub(now(), INTERVAL 1 DAY )) as module_add_count FROM tm_main_modules group by customer_name,comm_sys;
DECLARE CONTINUE HANDLER FOR NOT FOUND set stopFlag=1;

select COUNT(*) into p_total_module_count from tm_main_modules;

/*遍历通讯制式，分析全局各种通讯制式的 module_count percentage*/
OPEN commsys_Anal;
REPEAT
FETCH commsys_Anal INTO p_comm_sys;
begin
  select count(*) into p_num from ts_sys_statistics_analysis where
 comm_sys=p_comm_sys and province=-1 and customer_name=-1;
  if p_num=0 then
  insert into ts_sys_statistics_analysis( comm_sys   ,
     province,
     customer_name
)
values( p_comm_sys,
   -1,
   -1);
end if;
 select id into p_id from ts_sys_statistics_analysis where
 comm_sys=p_comm_sys and province=-1 and customer_name=-1;

  select count(*) INTO p_module_count from tm_main_modules
  WHERE comm_sys = p_comm_sys;

  select count(*) INTO p_module_add_count from tm_main_modules
  WHERE comm_sys = p_comm_sys and create_time >date_sub(now(),interval 1 day);

 set p_percentage = p_module_count/p_total_module_count;

  UPDATE ts_sys_statistics_analysis set module_add_count=p_module_add_count,module_count=p_module_count,percentage=p_percentage where id=p_id;
end;
UNTIL stopFlag = 1
END REPEAT;
CLOSE commsys_Anal;



set stopFlag = 0;
/*分析每个客户各种通讯制式的 module_count percentage*/
OPEN customer_commsys_Anal;
REPEAT
FETCH customer_commsys_Anal INTO p_customer_name,p_comm_sys,p_module_count,p_module_add_count;
begin

  select count(*) into p_num from ts_sys_statistics_analysis where comm_sys=p_comm_sys and province=-1 and customer_name=p_customer_name;
  if p_num=0 then insert into ts_sys_statistics_analysis( comm_sys   ,
     province,
     customer_name
)
values( p_comm_sys,
   -1,
   p_customer_name);
end if;
 select id into p_id from ts_sys_statistics_analysis where comm_sys=p_comm_sys and province=-1 and customer_name=p_customer_name;

  set p_percentage = p_module_count/p_total_module_count;

  UPDATE ts_sys_statistics_analysis set module_add_count=p_module_add_count,module_count=p_module_count,percentage=p_percentage where id=p_id;

end;

UNTIL stopFlag = 1
END REPEAT;
CLOSE customer_commsys_Anal;



 END$$
DELIMITER ;

