DELIMITER $$
DROP PROCEDURE IF EXISTS prc_update_industry_loc $$
CREATE PROCEDURE prc_update_industry_loc()
  BEGIN
    /*
    根据行业统计设备分布

    */
    DECLARE p_id INT;
    DECLARE p_industry VARCHAR(20);
    DECLARE p_province VARCHAR(30);
    DECLARE p_city VARCHAR(30);
    DECLARE p_module_count INT DEFAULT 0;
    DECLARE p_num INT;

    DECLARE stopFlag INT;
    DECLARE industry_loc CURSOR
    FOR SELECT
          industry,
          province,
          city,
          count(sn) AS module_count
        FROM tm_main_modules_tmp
        GROUP BY industry, province, city
        ORDER BY industry;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET stopFlag = 1;


    DROP TABLE IF EXISTS tm_main_modules_tmp;
    CREATE TABLE tm_main_modules_tmp SELECT
                                       a.sn       AS sn,
                                       a.industry AS industry,
                                       a.province AS province,
                                       a.city     AS city
                                     FROM tm_main_modules a;
    CREATE INDEX tm_main_modules_tmp_idx ON tm_main_modules_tmp (sn);
    CREATE INDEX tm_main_modules_tmp_idx2 ON tm_main_modules_tmp (industry);
    CREATE INDEX tm_main_modules_tmp_idx3 ON tm_main_modules_tmp (province);
    CREATE INDEX tm_main_modules_tmp_idx4 ON tm_main_modules_tmp (city);

    /*根据行业统计设备分布*/
    OPEN industry_loc;
    REPEAT
      FETCH industry_loc
      INTO p_industry, p_province, p_city, p_module_count;
      BEGIN
        SELECT count(*)
        INTO p_num
        FROM ts_sys_statistics_analysis
        WHERE industry = p_industry AND province = p_province AND city = p_city AND
              module_add_count = -1 AND customer_count = -1 AND customer_add_count = -1 AND percentage = -1 AND
              module_type = -1 AND comm_sys = -1 AND customer_value = -1;
        IF p_num = 0
        THEN
          INSERT INTO ts_sys_statistics_analysis (industry,
                                                  province,
                                                  city,
                                                  module_add_count,
                                                  customer_count,
                                                  customer_add_count,
                                                  percentage,
                                                  module_type,
                                                  comm_sys,
                                                  customer_value
          )
          VALUES (p_industry,
                  p_province,
                  p_city,
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
        WHERE industry = p_industry AND province = p_province AND city = p_city AND
              module_add_count = -1 AND customer_count = -1 AND customer_add_count = -1 AND percentage = -1 AND
              module_type = -1 AND comm_sys = -1 AND customer_value = -1;

        UPDATE ts_sys_statistics_analysis
        SET module_count = p_module_count
        WHERE id = p_id;


      END;
    UNTIL stopFlag = 1
    END REPEAT;
    CLOSE industry_loc;

    DROP TABLE tm_main_modules_tmp;
  END$$
DELIMITER ;