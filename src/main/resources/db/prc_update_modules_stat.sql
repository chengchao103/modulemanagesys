DELIMITER $$
DROP PROCEDURE IF EXISTS prc_update_modules_stat $$
CREATE PROCEDURE prc_update_modules_stat()
  BEGIN
    /*
    根据模组信息表更新统计分析表
    1.根据模组信息表更新统计分析表的 module_count module_add_count(全局模组,当日新增模组数)
    2.遍历客户信息表，统计每个客户的module_count module_add_count
    */
    DECLARE p_id INT;
    DECLARE p_module_count INT;
    DECLARE p_module_add_count INT;


    DECLARE p_customer_name VARCHAR(30);


    declare p_num int;
    DECLARE stopFlag INT DEFAULT 0;
    DECLARE cursor_customer_modules CURSOR
    FOR SELECT customer_name,count(sn) as module_count,count(create_time > date_sub(now(), INTERVAL 1 DAY)) as module_add_count FROM tm_main_modules group by customer_name;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET stopFlag = 1;


    /*根据模组信息表更新统计分析表的 module_count module_add_count(全局模组,当日新增模组数)*/
    SELECT count(*) INTO p_num FROM ts_sys_statistics_analysis WHERE industry = -1 AND module_type = -1 AND carrier = -1 AND comm_sys = -1
                                                                     AND separate = -1 AND customer_value = -1 AND customer_count = -1
                                                                     AND customer_add_count = -1 AND province = -1 AND percentage = -1
                                                                     AND customer_name = -1;

    IF p_num=0 THEN
      INSERT INTO ts_sys_statistics_analysis (industry,
                                              module_type,
                                              carrier,
                                              comm_sys,
                                              separate,
                                              customer_value,
                                              customer_count,
                                              customer_add_count,
                                              province,
                                              percentage,
                                              customer_name
      )
      VALUES (-1,
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


    END IF;
    SELECT id INTO p_id FROM ts_sys_statistics_analysis WHERE industry = -1 AND module_type = -1 AND carrier = -1 AND comm_sys = -1
                                                              AND separate = -1 AND customer_value = -1 AND customer_count = -1
                                                              AND customer_add_count = -1 AND province = -1 AND percentage = -1
                                                              AND customer_name = -1;
    SELECT count(*)
    INTO p_module_count
    FROM tm_main_modules;
    SELECT count(*)
    INTO p_module_add_count
    FROM tm_main_modules
    WHERE
      create_time > date_sub(now(), INTERVAL 1 DAY);
    UPDATE ts_sys_statistics_analysis
    SET module_count = p_module_count, module_add_count = p_module_add_count
    WHERE id = p_id;


    /*，统计每个客户的module_count module_add_count*/
    OPEN cursor_customer_modules;
    REPEAT
      FETCH cursor_customer_modules INTO p_customer_name, p_module_count, p_module_add_count;
      BEGIN
        SELECT count(*) INTO p_num FROM ts_sys_statistics_analysis WHERE industry = -1 AND module_type = -1 AND carrier = -1 AND comm_sys = -1 AND separate = -1 AND customer_value = -1 AND customer_count = -1 AND customer_add_count = -1 AND province = -1 AND percentage = -1 AND customer_name = p_customer_name;
        if p_num =0 then
          INSERT INTO ts_sys_statistics_analysis (industry,
                                                  module_type,
                                                  carrier,
                                                  comm_sys,
                                                  separate,
                                                  customer_value,
                                                  customer_count,
                                                  customer_add_count,
                                                  province,
                                                  percentage,
                                                  customer_name
          )
          VALUES (-1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            p_customer_name);
        END IF;
        SELECT id INTO p_id FROM ts_sys_statistics_analysis WHERE industry = -1 AND module_type = -1 AND carrier = -1 AND comm_sys = -1 AND separate = -1 AND customer_value = -1 AND customer_count = -1 AND customer_add_count = -1 AND province = -1 AND percentage = -1 AND customer_name = p_customer_name;
        UPDATE ts_sys_statistics_analysis SET module_count = p_module_count, module_add_count = p_module_add_count WHERE id = p_id;

      END;
    UNTIL stopFlag=1
    END REPEAT;
    CLOSE cursor_customer_modules;

  END$$
DELIMITER ;