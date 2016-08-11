DELIMITER $$
DROP PROCEDURE IF EXISTS prc_update_moduletype_loc$$
CREATE PROCEDURE prc_update_moduletype_loc()
  BEGIN
    /*
    根据设备型号统计设备分布

    */
    DECLARE p_id INT;
    DECLARE p_module_type VARCHAR(20);
    DECLARE p_province VARCHAR(30);
    DECLARE p_city VARCHAR(30);
    DECLARE p_module_count INT DEFAULT 0;
    DECLARE p_customer_name VARCHAR(30);
    DECLARE p_num INT;

    DECLARE stopFlag INT;
    DECLARE moduletype_loc CURSOR
    FOR SELECT
          module_type,
          province,
          city,
          count(sn) AS module_count
        FROM tm_main_modules_tmp2
        GROUP BY module_type, province, city
        ORDER BY module_type;

    DECLARE customer_module_type_loc CURSOR
    FOR SELECT
          customer_name,
          module_type,
          province,
          city,
          count(sn) AS module_count
        FROM tm_main_modules_tmp2
        GROUP BY customer_name, module_type, province, city
        ORDER BY customer_name;


    DECLARE CONTINUE HANDLER FOR NOT FOUND SET stopFlag = 1;


    DROP TABLE IF EXISTS tm_main_modules_tmp2;
    CREATE TABLE tm_main_modules_tmp2 SELECT
                                        a.sn            AS sn,
                                        a.customer_name AS customer_name,
                                        a.module_type   AS module_type,
                                        a.province      AS province,
                                        a.city          AS city
                                      FROM tm_main_modules a;
    CREATE INDEX tm_main_modules_tmp2_idx ON tm_main_modules_tmp2 (sn);
    CREATE INDEX tm_main_modules_tmp2_idx1 ON tm_main_modules_tmp2 (customer_name);
    CREATE INDEX tm_main_modules_tmp2_idx2 ON tm_main_modules_tmp2 (module_type);
    CREATE INDEX tm_main_modules_tmp2_idx3 ON tm_main_modules_tmp2 (province);
    CREATE INDEX tm_main_modules_tmp2_idx4 ON tm_main_modules_tmp2 (city);

    /*根据设备型号统计设备分布*/
    OPEN moduletype_loc;
    REPEAT
      FETCH moduletype_loc
      INTO p_module_type, p_province, p_city, p_module_count;
      BEGIN
        SELECT count(*)
        INTO p_num
        FROM ts_sys_statistics_analysis
        WHERE module_type = p_module_type AND province = p_province AND city = p_city AND
              module_add_count = -1 AND customer_count = -1 AND customer_add_count = -1 AND percentage = -1 AND
              industry = -1 AND comm_sys = -1 AND customer_value = -1 AND customer_name = -1;
        IF p_num = 0
        THEN
          INSERT INTO ts_sys_statistics_analysis (module_type,
                                                  province,
                                                  city,
                                                  module_add_count,
                                                  customer_count,
                                                  customer_add_count,
                                                  percentage,
                                                  industry,
                                                  comm_sys,
                                                  customer_value,
                                                  customer_name
          )
          VALUES (p_module_type,
            p_province,
            p_city,
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
        WHERE module_type = p_module_type AND province = p_province AND city = p_city AND
              module_add_count = -1 AND customer_count = -1 AND customer_add_count = -1 AND percentage = -1 AND
              industry = -1 AND comm_sys = -1 AND customer_value = -1 AND customer_name = -1;

        UPDATE ts_sys_statistics_analysis
        SET module_count = p_module_count
        WHERE id = p_id;


      END;


    UNTIL stopFlag = 1
    END REPEAT;
    CLOSE moduletype_loc;


    SET stopFlag = 0;
    /*根据设备型号统计每个客户的设备分布*/
    OPEN customer_module_type_loc;
    REPEAT
      FETCH customer_module_type_loc
      INTO p_customer_name, p_module_type, p_province, p_city, p_module_count;
      BEGIN
        SELECT count(*)
        INTO p_num
        FROM ts_sys_statistics_analysis
        WHERE module_type = p_module_type AND province = p_province AND city = p_city AND
              module_add_count = -1 AND customer_count = -1 AND customer_add_count = -1 AND percentage = -1 AND
              industry = -1 AND comm_sys = -1 AND customer_value = -1 AND customer_name = p_customer_name;
        IF p_num = 0
        THEN
          INSERT INTO ts_sys_statistics_analysis (module_type,
                                                  province,
                                                  city,
                                                  module_add_count,
                                                  customer_count,
                                                  customer_add_count,
                                                  percentage,
                                                  industry,
                                                  comm_sys,
                                                  customer_value,
                                                  customer_name
          )
          VALUES (p_module_type,
            p_province,
            p_city,
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
        WHERE module_type = p_module_type AND province = p_province AND city = p_city AND
              module_add_count = -1 AND customer_count = -1 AND customer_add_count = -1 AND percentage = -1 AND
              industry = -1 AND comm_sys = -1 AND customer_value = -1 AND customer_name = p_customer_name;

        UPDATE ts_sys_statistics_analysis
        SET module_count = p_module_count
        WHERE id = p_id;


      END;
    UNTIL stopFlag = 1
    END REPEAT;
    CLOSE customer_module_type_loc;

    DROP TABLE tm_main_modules_tmp2;

  END$$
DELIMITER ;