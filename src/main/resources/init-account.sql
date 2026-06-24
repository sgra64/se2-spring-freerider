-- probe database users and accounts
-- SELECT host, user, plugin FROM mysql.user;

-- grant user 'root' all privileges on all databases
CREATE USER 'root'@'%' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%';

-- grant user 'freerider' all privileges on database FREERIDER_DB
CREATE USER 'freerider'@'%' IDENTIFIED BY 'free.ride';
GRANT ALL PRIVILEGES ON FREERIDER_DB.* to 'freerider'@'%';
