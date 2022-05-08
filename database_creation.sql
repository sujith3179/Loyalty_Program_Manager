BEGIN EXECUTE IMMEDIATE 'DROP TABLE HAS_ACTIVITIES'; EXCEPTION WHEN OTHERS THEN NULL;  END;
BEGIN EXECUTE IMMEDIATE 'DROP TABLE HAS_REWARDS'; EXCEPTION WHEN OTHERS THEN NULL;  END;
BEGIN EXECUTE IMMEDIATE 'DROP TABLE SET_UP'; EXCEPTION WHEN OTHERS THEN NULL;  END;
BEGIN EXECUTE IMMEDIATE 'DROP TABLE REWARD_INFO'; EXCEPTION WHEN OTHERS THEN NULL; END;
BEGIN EXECUTE IMMEDIATE 'DROP TABLE REWARD_INSTANCES'; EXCEPTION WHEN OTHERS THEN NULL;  END;
BEGIN EXECUTE IMMEDIATE 'DROP TABLE ACTIVITIES_INFO'; EXCEPTION WHEN OTHERS THEN NULL;  END;
BEGIN EXECUTE IMMEDIATE 'DROP TABLE WALLET'; EXCEPTION WHEN OTHERS THEN NULL;  END;
BEGIN EXECUTE IMMEDIATE 'DROP TABLE RE_RULES'; EXCEPTION WHEN OTHERS THEN NULL;  END;
BEGIN EXECUTE IMMEDIATE 'DROP TABLE RR_RULES'; EXCEPTION WHEN OTHERS THEN NULL;  END;
BEGIN EXECUTE IMMEDIATE 'DROP TABLE ACTIVITY_TYPE'; EXCEPTION WHEN OTHERS THEN NULL;  END;
BEGIN EXECUTE IMMEDIATE 'DROP TABLE REWARD_TYPE'; EXCEPTION WHEN OTHERS THEN NULL;  END;
BEGIN EXECUTE IMMEDIATE 'DROP TABLE TIERS'; EXCEPTION WHEN OTHERS THEN NULL;  END;
BEGIN EXECUTE IMMEDIATE 'DROP TABLE LOYALTY_PROGRAM'; EXCEPTION WHEN OTHERS THEN NULL;  END;
BEGIN EXECUTE IMMEDIATE 'DROP TABLE CUSTOMER'; EXCEPTION WHEN OTHERS THEN NULL;  END;
BEGIN EXECUTE IMMEDIATE 'DROP TABLE BRANDS'; EXCEPTION WHEN OTHERS THEN NULL;  END;
BEGIN EXECUTE IMMEDIATE 'DROP TABLE USER_TABLE'; EXCEPTION WHEN OTHERS THEN NULL;  END;

CREATE TABLE USER_TABLE (ID VARCHAR(32) NOT NULL, PASSWORD VARCHAR(32) NOT NULL, TYPE VARCHAR(32) NOT NULL)
ALTER TABLE USER_TABLE ADD CONSTRAINT USER_PK PRIMARY KEY(ID)

CREATE TABLE BRANDS (B_ID VARCHAR(32) NOT NULL, BRAND_NAME VARCHAR(32) NOT NULL, ADDRESS VARCHAR(32) NOT NULL, JOIN_DATE DATE NOT NULL)
ALTER TABLE BRANDS ADD CONSTRAINT BRANDS_PK PRIMARY KEY(B_ID)
ALTER TABLE BRANDS ADD CONSTRAINT USER_FK2 FOREIGN KEY(B_ID) REFERENCES BRANDS

CREATE TABLE CUSTOMER (C_ID VARCHAR(32) NOT NULL, C_NAME VARCHAR(32) NOT NULL, C_ADDRESS VARCHAR(32) NOT NULL, C_PHONE_NUMBER VARCHAR(32) NOT NULL)
ALTER TABLE CUSTOMER ADD CONSTRAINT CUSTOMER_PK PRIMARY KEY(C_ID)
ALTER TABLE CUSTOMER ADD CONSTRAINT USER_FK FOREIGN KEY(C_ID) REFERENCES USER_TABLE

CREATE TABLE LOYALTY_PROGRAM(LP_ID VARCHAR(32) NOT NULL, LP_NAME VARCHAR(32), STATUS VARCHAR(32) NOT NULL)
ALTER TABLE LOYALTY_PROGRAM ADD CONSTRAINT LOYALTY_PROGRAM_PK PRIMARY KEY(LP_ID)

CREATE TABLE TIERS(LP_ID VARCHAR(32) NOT NULL, TIER_NAME VARCHAR(32) NOT NULL, TIER_LEVEL INTEGER NOT NULL, MULTIPLIER DECIMAL NOT NULL, POINTSREQUIRED INTEGER NOT NULL)
ALTER TABLE TIERS ADD CONSTRAINT TIERS_PK PRIMARY KEY(LP_ID, TIER_NAME)
ALTER TABLE TIERS ADD CONSTRAINT TIERS_FK FOREIGN KEY(LP_ID) REFERENCES LOYALTY_PROGRAM

CREATE TABLE ACTIVITY_TYPE(ACTIVITY_TYPE_ID VARCHAR(32) NOT NULL, ACTIVITY_NAME VARCHAR(32) NOT NULL)
ALTER TABLE ACTIVITY_TYPE ADD CONSTRAINT ACTIVITY_TYPE_PK PRIMARY KEY(ACTIVITY_TYPE_ID)

CREATE TABLE RE_RULES(ACTIVITY_TYPE_ID VARCHAR(32), LP_ID VARCHAR(32) NOT NULL, VERSION_NO INTEGER NOT NULL, NO_OF_POINTS INTEGER NOT NULL, RULE_CODE VARCHAR(32))
ALTER TABLE RE_RULES ADD CONSTRAINT RE_RULES_PK PRIMARY KEY(LP_ID, ACTIVITY_TYPE_ID, version_no)
ALTER TABLE RE_RULES ADD CONSTRAINT RE_RULES_FK FOREIGN KEY(LP_ID) REFERENCES LOYALTY_PROGRAM(LP_ID)
ALTER TABLE RE_RULES ADD CONSTRAINT RE_RULES_FK2 FOREIGN KEY(ACTIVITY_TYPE_ID) REFERENCES ACTIVITY_TYPE(ACTIVITY_TYPE_ID)

CREATE TABLE REWARD_TYPE(REWARD_TYPE_ID VARCHAR(32) NOT NULL, REWARD_NAME VARCHAR(32) NOT NULL)
ALTER TABLE REWARD_TYPE ADD CONSTRAINT REWARD_TYPE_PK PRIMARY KEY(REWARD_TYPE_ID)

CREATE TABLE RR_RULES(REWARD_TYPE_ID VARCHAR(32) NOT NULL, LP_ID VARCHAR(32) NOT NULL, VERSION_NO INTEGER NOT NULL, WORTH_POINTS INTEGER NOT NULL, RULE_CODE VARCHAR(32))
ALTER TABLE RR_RULES ADD CONSTRAINT RR_RULES_PK PRIMARY KEY(LP_ID, REWARD_TYPE_ID, version_no)
ALTER TABLE RR_RULES ADD CONSTRAINT RR_RULES_FK FOREIGN KEY(LP_ID) REFERENCES LOYALTY_PROGRAM(LP_ID)
ALTER TABLE RR_RULES ADD CONSTRAINT RR_RULES_FK2 FOREIGN KEY(REWARD_TYPE_ID) REFERENCES REWARD_TYPE(REWARD_TYPE_ID)

CREATE TABLE WALLET(C_ID VARCHAR(32) NOT NULL, POINTS INTEGER NOT NULL, LP_ID VARCHAR(32) NOT NULL, WALLET_LEVEL INTEGER NOT NULL)
ALTER TABLE WALLET ADD CONSTRAINT WALLET_PK PRIMARY KEY(LP_ID, C_ID)
ALTER TABLE WALLET ADD CONSTRAINT WALLET_FK FOREIGN KEY(LP_ID) REFERENCES LOYALTY_PROGRAM(LP_ID)
ALTER TABLE WALLET ADD CONSTRAINT WALLET_FK2 FOREIGN KEY(C_ID) REFERENCES CUSTOMER(C_ID)

CREATE TABLE ACTIVITY_INFO(ACTIVITY_DATE_TIME TIMESTAMP NOT NULL, POINTS_EARNED INTEGER NOT NULL, ACTIVITY_TYPE_ID VARCHAR(32) NOT NULL, ACTIVITY_DATA VARCHAR(32), C_ID VARCHAR(32) NOT NULL, LP_ID VARCHAR(32) NOT NULL, VERSION_NO INTEGER NOT NULL)
ALTER TABLE ACTIVITY_INFO ADD CONSTRAINT ACTIVITY_INFO_PK PRIMARY KEY(ACTIVITY_DATE_TIME, LP_ID, ACTIVITY_TYPE_ID, C_ID, VERSION_NO)
ALTER TABLE ACTIVITY_INFO ADD CONSTRAINT ACTIVITY_INFO_FK FOREIGN KEY(C_ID) REFERENCES CUSTOMER(C_ID)
ALTER TABLE ACTIVITY_INFO ADD CONSTRAINT ACTIVITY_INFO_FK2 FOREIGN KEY(LP_ID,ACTIVITY_TYPE_ID,VERSION_NO) REFERENCES RE_RULES(LP_ID, ACTIVITY_TYPE_ID, VERSION_NO)

CREATE TABLE REWARD_INSTANCES (REWARD_INSTANCE_ID NUMBER GENERATED AS IDENTITY, VALUE INTEGER, IS_USED INTEGER NOT NULL, CLAIMED_BY VARCHAR(32), LP_ID VARCHAR(32) NOT NULL, REWARD_TYPE_ID VARCHAR(32) NOT NULL)
ALTER TABLE REWARD_INSTANCES ADD CONSTRAINT REWARD_INSTANCES_PK PRIMARY KEY (REWARD_INSTANCE_ID)
ALTER TABLE REWARD_INSTANCES ADD CONSTRAINT REWARD_INSTANCES_FK FOREIGN KEY (LP_ID) REFERENCES LOYALTY_PROGRAM(LP_ID)
ALTER TABLE REWARD_INSTANCES ADD CONSTRAINT REWARD_INSTANCES_FK2 FOREIGN KEY (REWARD_TYPE_ID) REFERENCES REWARD_TYPE(REWARD_TYPE_ID)

CREATE TABLE REWARD_INFO(REWARD_DATE_TIME TIMESTAMP NOT NULL, REWARD_INSTANCE_ID NUMBER NOT NULL, POINTS_SPENT INTEGER NOT NULL, REWARD_TYPE_ID VARCHAR(32) NOT NULL, REWARD_DATA VARCHAR(32), C_ID VARCHAR(32) NOT NULL, LP_ID VARCHAR(32) NOT NULL, VERSION_NO INTEGER NOT NULL)
ALTER TABLE REWARD_INFO ADD CONSTRAINT REWARD_INFO_PK PRIMARY KEY(REWARD_DATE_TIME, LP_ID, REWARD_TYPE_ID, C_ID, VERSION_NO, REWARD_INSTANCE_ID)
ALTER TABLE REWARD_INFO ADD CONSTRAINT REWARD_INFO_FK FOREIGN KEY(C_ID) REFERENCES CUSTOMER(C_ID)
ALTER TABLE REWARD_INFO ADD CONSTRAINT REWARD_INFO_FK2 FOREIGN KEY(LP_ID,REWARD_TYPE_ID,VERSION_NO) REFERENCES RR_RULES(LP_ID, REWARD_TYPE_ID, VERSION_NO)
ALTER TABLE REWARD_INFO ADD CONSTRAINT REWARD_INFO_FK3 FOREIGN KEY(REWARD_INSTANCE_ID) REFERENCES REWARD_INSTANCES(REWARD_INSTANCE_ID)

CREATE TABLE SET_UP(B_ID VARCHAR(32), LP_ID VARCHAR(32))
ALTER TABLE SET_UP ADD CONSTRAINT SET_UP_PK PRIMARY KEY(B_ID, LP_ID)
ALTER TABLE SET_UP ADD CONSTRAINT SET_UP_FK FOREIGN KEY(B_ID) REFERENCES BRANDS(B_ID)
ALTER TABLE SET_UP ADD CONSTRAINT SET_UP_FK2 FOREIGN KEY(LP_ID) REFERENCES LOYALTY_PROGRAM(LP_ID)

CREATE TABLE HAS_ACTIVITIES(ACTIVITY_TYPE_ID VARCHAR(32), LP_ID VARCHAR(32))
ALTER TABLE HAS_ACTIVITIES ADD CONSTRAINT HAS_ACTIVITIES_PK PRIMARY KEY(ACTIVITY_TYPE_ID, LP_ID)
ALTER TABLE HAS_ACTIVITIES ADD CONSTRAINT HAS_ACTIVITIES_FK FOREIGN KEY(ACTIVITY_TYPE_ID) REFERENCES ACTIVITY_TYPE(ACTIVITY_TYPE_ID)
ALTER TABLE HAS_ACTIVITIES ADD CONSTRAINT HAS_ACTIVITIES_FK2 FOREIGN KEY(LP_ID) REFERENCES LOYALTY_PROGRAM(LP_ID)

CREATE TABLE HAS_REWARDS(REWARD_TYPE_ID VARCHAR(32), LP_ID VARCHAR(32))
ALTER TABLE HAS_REWARDS ADD CONSTRAINT HAS_REWARD_PK PRIMARY KEY(REWARD_TYPE_ID, LP_ID)
ALTER TABLE HAS_REWARDS ADD CONSTRAINT HAS_REWARDS_FK FOREIGN KEY(REWARD_TYPE_ID) REFERENCES REWARD_TYPE(REWARD_TYPE_ID)
ALTER TABLE HAS_REWARDS ADD CONSTRAINT HAS_REWARDS_FK2 FOREIGN KEY(LP_ID) REFERENCES LOYALTY_PROGRAM(LP_ID)

CREATE OR REPLACE PROCEDURE add_reward_instances(reward_type_id VARCHAR2, value_in INTEGER, lp_id VARCHAR2, quantity NUMBER) AS BEGIN FOR i in 1 .. quantity LOOP INSERT INTO REWARD_INSTANCES(VALUE, IS_USED, CLAIMED_BY, LP_ID, REWARD_TYPE_ID) VALUES (value_in, 0, null, lp_id, reward_type_id); END LOOP; END;

CREATE OR REPLACE TRIGGER INCRIMENT_RERULE_VERSION BEFORE INSERT ON RE_RULES REFERENCING NEW AS NEW_ROW FOR EACH ROW DECLARE VNO NUMBER; BEGIN SELECT BIG INTO VNO FROM (SELECT COALESCE(MAX(VERSION_NO),0) AS BIG FROM RE_RULES WHERE LP_ID = :NEW_ROW.LP_ID AND ACTIVITY_TYPE_ID = :NEW_ROW.ACTIVITY_TYPE_ID); :NEW_ROW.VERSION_NO := VNO + 1; END;

CREATE OR REPLACE TRIGGER INCRIMENT_RRRULE_VERSION BEFORE INSERT ON RR_RULES REFERENCING NEW AS NEW_ROW FOR EACH ROW DECLARE VNO NUMBER; BEGIN SELECT BIG INTO VNO FROM (SELECT COALESCE(MAX(VERSION_NO),0) AS BIG FROM RR_RULES WHERE LP_ID = :NEW_ROW.LP_ID AND REWARD_TYPE_ID = :NEW_ROW.REWARD_TYPE_ID); :NEW_ROW.VERSION_NO := VNO + 1; END;

CREATE OR REPLACE TRIGGER ADD_WALLET_POINTS AFTER INSERT ON ACTIVITY_INFO FOR EACH ROW DECLARE MULTI DECIMAL; BEGIN SELECT CORRECT INTO MULTI FROM (SELECT COALESCE(MAX(TIERS.MULTIPLIER),1) AS CORRECT FROM TIERS, WALLET WHERE :new.LP_ID = WALLET.LP_ID AND :new.C_ID = WALLET.C_ID AND WALLET.WALLET_LEVEL = TIERS.TIER_LEVEL AND :new.LP_ID = TIERS.LP_ID); UPDATE WALLET SET POINTS = POINTS + (:new.POINTS_EARNED * MULTI) WHERE LP_ID = :new.LP_ID AND C_ID = :new.C_ID; END;

CREATE OR REPLACE TRIGGER REMOVE_WALLET_POINTS AFTER INSERT ON REWARD_INFO FOR EACH ROW BEGIN UPDATE WALLET SET POINTS = POINTS + :new.POINTS_SPENT WHERE LP_ID = :new.LP_ID AND C_ID = :new.C_ID; END;

CREATE OR REPLACE TRIGGER CREATE_JOIN_RE_RULE AFTER INSERT ON LOYALTY_PROGRAM FOR EACH ROW BEGIN INSERT INTO RE_RULES VALUES('JoinEvent', :new.LP_ID, 1, 0, 'JoinEventRule'); END;

CREATE OR REPLACE TRIGGER CLAIM_REWARD AFTER INSERT ON REWARD_INFO FOR EACH ROW BEGIN UPDATE REWARD_INSTANCES SET CLAIMED_BY = :new.C_ID WHERE REWARD_INSTANCE_ID = :new.REWARD_INSTANCE_ID; END;

CREATE OR REPLACE PROCEDURE CREATE_INITIAL_WALLET_EVENT(cid VARCHAR2, lpid VARCHAR2, joindatestring VARCHAR2) AS BEGIN INSERT INTO ACTIVITY_INFO VALUES (TO_DATE(joindatestring,'YYYY-MM-DD HH24:MI:SS'), 0, 'JoinEvent', 'None', cid, lpid, 1); END;

CREATE OR REPLACE PROCEDURE UPDATE_WALLET_TIER(cid VARCHAR2, lpid VARCHAR2) IS NEW_TIER INTEGER; POINT_SUM NUMBER; BEGIN SELECT TOTAL INTO POINT_SUM FROM (SELECT SUM(POINTS_EARNED) AS TOTAL FROM ACTIVITY_INFO WHERE lpid = LP_ID AND cid = C_ID); SELECT MAX_TIER INTO NEW_TIER FROM (SELECT COALESCE(MAX(TIER_LEVEL), 1) AS MAX_TIER FROM TIERS WHERE lpid = LP_ID AND POINT_SUM >= POINTSREQUIRED); UPDATE WALLET SET WALLET_LEVEL = NEW_TIER WHERE LP_ID = lpid AND C_ID = cid; END;