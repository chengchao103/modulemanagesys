DELIMITER $$
DROP PROCEDURE IF EXISTS prc_update_carrier_Anal $$
CREATE PROCEDURE prc_update_carrier_Anal()
  BEGIN
    /*
    根据运营商模组数量
    1.分析各种运营商全局数量及占比
    2.分析每个客户的各种运营商数量及占比
    */
    DECLARE p_id INT;
    DECLARE p_carrier VARCHAR(10);
    DECLARE p_module_count INT DEFAULT 0;
    DECLARE p_module_add_count INT DEFAULT 0;
    DECLARE p_percentage FLOAT DEFAULT 0.0;
    DECLARE p_total_module_count INT DEFAULT 0;
    DECLARE p_customer_name VARCHAR(30);
    DECLARE p_num INT;


    DECLARE stopFlag INT;
    DECLARE carrier_Anal CURSOR
    FOR SELECT DISTINCT carrier
        FROM tm_main_sims;

    DECLARE customer_carrier_Anal CURSOR
    FOR SELECT
          customer_name,
          carrier,
          count(sn)                                            AS module_count,
          count(create_time > date_sub(now(), INTERVAL 1 DAY)) AS module_add_count
        FROM tm_main_modules_tmp3
        GROUP BY customer_name, carrier;


    DECLARE CONTINUE HANDLER FOR NOT FOUND SET stopFlag = 1;

    DROP TABLE IF EXISTS tm_main_modules_tmp3;
    CREATE TABLE tm_main_modules_tmp3 SELECT
                                        a.sn            AS sn,
                                        a.customer_name AS customer_name,
                                        b.carrier       AS carrier,
                                        a.create_time   AS create_time
                                      FROM tm_main_modules a, tm_main_sims b
                                      WHERE a.iccid = b.iccid;
    CREATE INDEX tm_main_modules_tmp3_idx ON tm_main_modules_tmp3 (sn);
    CREATE INDEX tm_main_modules_tmp3_idx1 ON tm_main_modules_tmp3 (customer_name);
    CREATE INDEX tm_main_modules_tmp3_idx2 ON tm_main_modules_tmp3 (carrier);
    CREATE INDEX tm_main_modules_tmp3_idx3 ON tm_main_modules_tmp3 (create_time);


    SELECT COUNT(*)
    INTO p_total_module_count
    FROM tm_main_modules;

    /*遍历通讯制式，分析全局各种通讯制式的 module_count percentage*/
    OPEN carrier_Anal;
    REPEAT
      FETCH carrier_Anal
      INTO p_carrier;
      BEGIN
        SELECT count(*)
        INTO p_num
        FROM ts_sys_statistics_analysis
        WHERE
          carrier = p_carrier AND province = -1 AND customer_name = -1;
        IF p_num = 0
        THEN
          INSERT INTO ts_sys_statistics_analysis (carrier,
                                                  province,
                                                  customer_name
          )
          VALUES (p_carrier,
                  -1,
                  -1);

        END IF;
        SELECT id
        INTO p_id
        FROM ts_sys_statistics_analysis
        WHERE
          carrier = p_carrier AND province = -1 AND customer_name = -1;

        SELECT count(*)
        INTO p_module_count
        FROM tm_main_modules
        WHERE iccid IN (SELECT iccid
                        FROM tm_main_sims
                        WHERE carrier = p_carrier);

        SELECT count(*)
        INTO p_module_add_count
        FROM tm_main_modules
        WHERE iccid IN (SELECT iccid
                        FROM tm_main_sims
                        WHERE carrier = p_carrier) AND create_time > date_sub(now(), INTERVAL 1 DAY);

        SET p_percentage = p_module_count / p_total_module_count;

        UPDATE ts_sys_statistics_analysis
        SET module_add_count = p_module_add_count, module_count = p_module_count, percentage = p_percentage
        WHERE id = p_id;
      END;
    UNTIL stopFlag = 1
    END REPEAT;
    CLOSE carrier_Anal;


    SET stopFlag = 0;
    /*分析每个客户各种运营商的 module_count percentage*/
    OPEN customer_carrier_Anal;
    REPEAT
      FETCH customer_carrier_Anal
      INTO p_customer_name,p_carrier,p_module_count,p_module_add_count;
      begin
        SELECT count(*)
        INTO p_num
        FROM ts_sys_statistics_analysis
        WHERE
          carrier = p_carrier AND province = -1 AND customer_name = p_customer_name;
        IF p_num = 0
        THEN
          INSERT INTO ts_sys_statistics_analysis (carrier,
                                                  province,
                                                  customer_name
          )
          VALUES (p_carrier,
                  -1,
                  p_customer_name);
        END IF;
        SELECT id
        INTO p_id
        FROM ts_sys_statistics_analysis
        WHERE
          carrier = p_carrier AND province = -1 AND customer_name = p_customer_name;
        SET p_percentage = p_module_count / p_total_module_count;

        UPDATE ts_sys_statistics_analysis
        SET module_add_count = p_module_add_count, module_count = p_module_count, percentage = p_percentage
        WHERE id = p_id;
      end;
    UNTIL stopFlag = 1
    END REPEAT;
    CLOSE customer_carrier_Anal;


DROP TABLE tm_main_modules_tmp3;
END$$
DELIMITER ;