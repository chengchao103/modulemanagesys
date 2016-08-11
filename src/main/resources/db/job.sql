DELIMITER $$
SET GLOBAL event_scheduler = 1 $$
DROP EVENT IF EXISTS job_day $$
CREATE EVENT job_day
  ON SCHEDULE EVERY 300 SECOND
DO
  /*
  20 second=>on schedule every 1  DAY STARTS '2012-07-18 00:00:00'
  mysql 每天定时任务
  1.生成模组表
  2.生成SIM卡数据表
  3.更新模组表和SIM卡表
  */
    BEGIN

    CALL prc_insert_modul;
    CALL prc_insert_sim;
    CALL prc_update_imported();
    CALL prc_update_customers_stat();
    CALL prc_update_modules_stat();
    CALL prc_update_sepcardcustomers_stat();
    CALL prc_update_sepcardmodules_stat();
    CALL prc_update_carrier_Anal();
    CALL prc_update_commsys_Anal();
    CALL prc_update_industry_Anal();
    CALL prc_update_commsys_loc();
    CALL prc_update_industry_loc();
    CALL prc_update_moduletype_loc();
    CALL prc_update_customer_list();
    CALL prc_update_customer_module_list();


  END$$

DELIMITER ;