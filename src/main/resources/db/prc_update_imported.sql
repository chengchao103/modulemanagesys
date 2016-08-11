 DELIMITER $$
DROP PROCEDURE IF EXISTS prc_update_imported $$
CREATE PROCEDURE prc_update_imported()
Begin
/*
根据导入表生成模组表和客户表
1.生成临时表上传数据，
2.生成客户表数据
3.根据cursor循环判断是否存在模组表，新增模组信息或者更新iccid信息和客户ID
4.更新上传表数据处理时间
5.删除临时表
*/
 declare  p_customer_name        varchar(30) ;
 declare  p_industry             varchar(20) ;
 declare  p_province             varchar(30) ;
 declare  p_imei                 varchar(40) ;
 declare  p_sn                   varchar(40) ;
 declare  p_comm_sys             varchar(10) ;
 declare  p_app_ver              varchar(40) ;
 declare  p_iccid                varchar(40) ;
 declare  p_combo_name           varchar(30) ;
 declare  p_prom_online_time     varchar(30) ;
 declare  p_module_type          varchar(20) ;

declare p_num int;
declare stopFlag int;
DECLARE cursor_import CURSOR
FOR select customer_name,industry,province,imei,sn,comm_sys,app_ver,iccid,combo_name,prom_online_time,module_type
from ti_base_imported_tmp;
DECLARE CONTINUE HANDLER FOR NOT FOUND set stopFlag=1;

drop TABLE  if EXISTS ti_base_imported_tmp ;
CREATE TABLE ti_base_imported_tmp SELECT * FROM
    ti_base_imported
WHERE
    import_time >date_sub(now(),interval 1 day);
create index ti_base_imported_tmp_idx on ti_base_imported_tmp(sn);
create index ti_base_imported_tmp_idx2 on ti_base_imported_tmp(customer_name);
create index ti_base_imported_tmp_idx3 on ti_base_imported_tmp(iccid);


OPEN cursor_import;
REPEAT
FETCH cursor_import INTO p_customer_name,p_industry,p_province,p_imei,p_sn,p_comm_sys,p_app_ver,p_iccid,p_combo_name,p_prom_online_time,p_module_type;
begin
/*更新tm_main_modules*/
select count(1) into p_num from tm_main_modules where sn=p_sn;
if p_num =0 then
insert into tm_main_modules( sn   ,
   imei ,
  customer_name ,
   iccid    ,
   comm_sys       ,
   app_ver,
   module_type,
   industry,
   create_time
)
values( p_sn  ,
   p_imei   ,
   p_customer_name,
   p_iccid      ,
   p_comm_sys   ,
   p_app_ver,
   p_module_type,
   p_industry,
   now()
   );

   else

 if p_customer_name!='' and p_customer_name is not null  then
    update tm_main_modules   set customer_name=p_customer_name where sn=p_sn  ;
    end if;
 if p_imei!='' and p_imei is not null then
    update tm_main_modules   set imei=p_imei where sn=p_sn  ;
     end if;
 if p_module_type!='' and p_module_type is not null  then
    update tm_main_modules   set module_type=p_module_type where sn=p_sn  ;
     end if;
 if p_comm_sys!='' and p_comm_sys is not null  then
    update tm_main_modules   set comm_sys=p_comm_sys where sn=p_sn  ;
     end if;
 if p_app_ver!='' and p_app_ver is not null  then
    update tm_main_modules   set app_ver=p_app_ver where sn=p_sn  ;
     end if;
 if p_industry!='' and p_industry is not null  then
    update tm_main_modules   set industry=p_industry where sn=p_sn  ;
     end if;
 if p_iccid!='' and p_iccid is not null  then
    update tm_main_modules   set iccid=p_iccid where sn=p_sn  ;
     end if;

end if;
/*更新tm_main_sims*/
select count(1) into p_num from tm_main_sims where iccid=p_iccid;
if p_num =0 then
insert into tm_main_sims( iccid   ,
   combo_name ,
  customer_name,
  create_time
)
values( p_iccid  ,
   p_combo_name  ,
   p_customer_name,
   now()
);
   else
 if p_customer_name!='' and p_customer_name is not null  then
    update tm_main_sims   set customer_name=p_customer_name where iccid=p_iccid  ;
     end if;
 if p_combo_name!='' and p_combo_name is not null then
    update tm_main_sims   set combo_name=p_combo_name where iccid=p_iccid  ;
     end if;

end if;

/*更新tm_main_customers*/
select count(1) into p_num from tm_main_customers where customer_name=p_customer_name;
if p_num =0 then
insert into tm_main_customers( customer_name   ,
   industry ,
  province,
  create_time
)
values( p_customer_name  ,
   p_industry   ,
   p_province,
   now()
 );
 else

 if p_industry!='' and p_industry is not null  then
    update tm_main_customers   set industry=p_industry where customer_name=p_customer_name;
    end if;
 if p_province!='' and p_province is not null then
    update tm_main_customers   set province=p_province where customer_name=p_customer_name;
end if;
end if;


update tm_main_sims s set carrier=(
 case  when substr(iccid,1,6) in('898600' ,'898602')then 'CMCC'
when substr(iccid,1,6) in('898601' , '898606' ,'898609') then 'CTCC'
when substr(iccid,1,6)='898603' then 'CUCC' end )
 where carrier is NULL ;


end;
UNTIL stopFlag = 1
END REPEAT;
CLOSE cursor_import;

drop table ti_base_imported_tmp;
 END$$
DELIMITER ;