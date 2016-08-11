 DELIMITER $$
DROP PROCEDURE IF EXISTS prc_insert_modul $$
CREATE PROCEDURE prc_insert_modul()
Begin
/*
生成模组信息表
1.生成临时表上传数据，
2.生成模组数据
3.更新上传表数据处理时间
4.删除临时表
ICCID：Integrate circuit card identity 集成电路卡识别码（固化在SIM卡中） ICCID为IC卡的唯一识别号码，共有20位数字组成，其编
码格式为：XXXXXX 0MFSS YYGXX XXXXX。分别介绍如下： 前六位运营商代码：中国移动的为：898600；898602 ，中国联通的为：898601、
898606、898609，中国电信898603
CUCC中国电信  CMCC中国移动通信 CTCC中国联通通讯
898602 B4111630150273
*/
drop TABLE  if EXISTS ti_base_uploaded_tmp ;
CREATE TABLE ti_base_uploaded_tmp SELECT * FROM
    ti_base_uploaded
WHERE
    import_time >date_sub(now(),interval 1 day);
create index ti_base_uploaded_tmp_idx on ti_base_uploaded_tmp(sn);

 update tm_main_modules s join ti_base_uploaded_tmp p on s.sn=p.sn and s.communityid is null
   set s.communityid=p.communityid,
   s.signal_strength=p.signal_strength;

insert into tm_main_modules( sn   ,
   imei ,
   iccid    ,
   comm_sys       ,
  /* module_type          varchar(20) comment '模组型号',
   firmware_ver         varchar(10) comment '固件版本',
   app_ver              varchar(10) comment '应用版本',*/
   communityid        ,
   signal_strength ,
   province,
   city,
   create_time)
 select sn  ,
   imei   ,
   iccid      ,
   comm_sys   ,
   communityid       ,
   signal_strength,
   province,
   city,
   now()
 from ti_base_uploaded_tmp p
 where   not exists(select 1 from tm_main_modules s where s.sn=p.sn);

insert into tm_main_sims (iccid)
select iccid from ti_base_uploaded_tmp p
where not exists(select 1 from tm_main_sims s where s.iccid=p.iccid);


 update tm_main_sims s set carrier=(
 case  when substr(iccid,1,6) in('898600' ,'898602')then 'CMCC'
when substr(iccid,1,6) in('898601' , '898606' ,'898609') then 'CTCC'
when substr(iccid,1,6)='898603' then 'CUCC' end )
 where carrier is NULL ;


drop table ti_base_uploaded_tmp;



 END$$
DELIMITER ;