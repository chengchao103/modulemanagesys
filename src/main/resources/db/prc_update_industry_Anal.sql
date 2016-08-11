DELIMITER $$
DROP PROCEDURE IF EXISTS prc_update_industry_Anal $$
CREATE PROCEDURE prc_update_industry_Anal()
Begin
/*
根据行业分析模组数量、客户数量

*/
declare  p_id int ;
declare  p_industry varchar(20) ;
declare  p_module_count int DEFAULT 0;
declare  p_customer_count int DEFAULT 0;
declare  p_percentage FLOAT DEFAULT 0.0;
declare  p_total_module_count int DEFAULT 0;
declare  p_num int;

declare stopFlag int;
DECLARE industry_Anal CURSOR
FOR select distinct industry from tm_main_modules;
DECLARE CONTINUE HANDLER FOR NOT FOUND set stopFlag=1;

select COUNT(*) into p_total_module_count from tm_main_modules;

/*遍历行业，统计每个行业的 module_count customer_count*/
OPEN industry_Anal;
REPEAT
FETCH industry_Anal INTO p_industry;
begin
  select count(*) into p_num from ts_sys_statistics_analysis WHERE industry=p_industry and province=-1;
  if p_num=0 then
  insert into ts_sys_statistics_analysis( industry   ,
     province
)
values( p_industry,
   -1);
end if;

 select id into p_id from ts_sys_statistics_analysis where
 industry=p_industry and province=-1;

  select count(*) INTO p_customer_count from tm_main_customers
  WHERE industry = p_industry;

  select count(*) INTO p_module_count from tm_main_modules
  WHERE industry = p_industry;

  SET p_percentage=p_module_count/p_total_module_count;

  UPDATE ts_sys_statistics_analysis set customer_count=p_customer_count,module_count=p_module_count,percentage=p_percentage where id=p_id;
end;
UNTIL stopFlag = 1
END REPEAT;
CLOSE industry_Anal;

 END$$
DELIMITER ;