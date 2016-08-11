DELIMITER $$
DROP PROCEDURE IF EXISTS prc_update_commsys_loc_2$$
CREATE PROCEDURE prc_update_commsys_loc_2()
  BEGIN
    /*
    根据通讯制式统计设备分布

    */
    DECLARE p_id INT;
    DECLARE p_comm_sys VARCHAR(10);
    DECLARE p_province VARCHAR(30);
    DECLARE p_module_count INT DEFAULT 0;
    DECLARE p_customer_name VARCHAR(30);
    DECLARE p_num INT;

    DECLARE stopFlag INT;
    DECLARE commsys_loc CURSOR
    FOR SELECT
          comm_sys,
          province,
          count(sn) AS module_count
        FROM tm_main_modules_tmp1
        GROUP BY comm_sys, province
        ORDER BY comm_sys;

    DECLARE customer_commsys_loc CURSOR
    FOR SELECT
          customer_name,
          comm_sys,
          province,
          count(sn) AS module_count
        FROM tm_main_modules_tmp1
        GROUP BY customer_name, comm_sys, province
        ORDER BY customer_name;


    DECLARE CONTINUE HANDLER FOR NOT FOUND SET stopFlag = 1;


    DROP TABLE IF EXISTS tm_main_modules_tmp1;
    CREATE TABLE tm_main_modules_tmp1 SELECT
                                        a.sn            AS sn,
                                        a.customer_name AS customer_name,
                                        a.comm_sys      AS comm_sys,
                                        a.province      AS province
                                      FROM tm_main_modules a;
    CREATE INDEX tm_main_modules_tmp1_idx ON tm_main_modules_tmp1 (sn);
    CREATE INDEX tm_main_modules_tmp1_idx1 ON tm_main_modules_tmp1 (customer_name);
    CREATE INDEX tm_main_modules_tmp1_idx2 ON tm_main_modules_tmp1 (comm_sys);
    CREATE INDEX tm_main_modules_tmp1_idx3 ON tm_main_modules_tmp1 (province);

    OPEN commsys_loc;
    REPEAT
      FETCH commsys_loc
      INTO p_comm_sys, p_province, p_module_count;
      BEGIN
        SELECT count(*)
        INTO p_num
        FROM ts_sys_statistics_analysis
        WHERE comm_sys = p_comm_sys AND province = p_province AND city = -1 AND
              module_add_count = -1 AND customer_count = -1 AND customer_add_count = -1 AND percentage = -1 AND
              module_type = -1 AND industry = -1 AND customer_value = -1 AND customer_name = -1;
        IF p_num = 0
        THEN
          INSERT INTO ts_sys_statistics_analysis (comm_sys,
                                                  province,
                                                  city,
                                                  module_add_count,
                                                  customer_count,
                                                  customer_add_count,
                                                  percentage,
                                                  module_type,
                                                  industry,
                                                  customer_value,
                                                  customer_name
          )
          VALUES (p_comm_sys,
            p_province,
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

        SELECT id
        INTO p_id
        FROM ts_sys_statistics_analysis
        WHERE comm_sys = p_comm_sys AND province = p_province AND city = -1 AND
              module_add_count = -1 AND customer_count = -1 AND customer_add_count = -1 AND percentage = -1 AND
              module_type = -1 AND industry = -1 AND customer_value = -1 AND customer_name = -1;

        UPDATE ts_sys_statistics_analysis
        SET module_count = p_module_count
        WHERE id = p_id;
      END;
    UNTIL stopFlag = 1
    END REPEAT;
    CLOSE commsys_loc;


    SET stopFlag = 0;
    /*根据行业统计每个客户的设备分布*/
    OPEN customer_commsys_loc;
    REPEAT
      FETCH customer_commsys_loc
      INTO p_customer_name, p_comm_sys, p_province, p_module_count;
      BEGIN
        SELECT count(*)
        INTO p_num
        FROM ts_sys_statistics_analysis
        WHERE comm_sys = p_comm_sys AND province = p_province AND city = -1 AND
              module_add_count = -1 AND customer_count = -1 AND customer_add_count = -1 AND percentage = -1 AND
              module_type = -1 AND industry = -1 AND customer_value = -1 AND customer_name = p_customer_name;
        IF p_num = 0
        THEN
          INSERT INTO ts_sys_statistics_analysis (comm_sys,
                                                  province,
                                                  city,
                                                  module_add_count,
                                                  customer_count,
                                                  customer_add_count,
                                                  percentage,
                                                  module_type,
                                                  industry,
                                                  customer_value,
                                                  customer_name
          )
          VALUES (p_comm_sys,
            p_province,
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

        SELECT id
        INTO p_id
        FROM ts_sys_statistics_analysis
        WHERE comm_sys = p_comm_sys AND province = p_province AND city = -1 AND
              module_add_count = -1 AND customer_count = -1 AND customer_add_count = -1 AND percentage = -1 AND
              module_type = -1 AND industry = -1 AND customer_value = -1 AND customer_name = p_customer_name;

        UPDATE ts_sys_statistics_analysis
        SET module_count = p_module_count
        WHERE id = p_id;
      END;
    UNTIL stopFlag = 1
    END REPEAT;
    CLOSE customer_commsys_loc;

    DROP TABLE tm_main_modules_tmp1;

  END$$
DELIMITER ;