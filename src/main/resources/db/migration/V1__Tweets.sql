CREATE TABLE user (
    id BIGINT       NOT NULL AUTO_INCREMENT,
	name varchar(255) NOT NULL,
	PRIMARY KEY(id)
);

CREATE TABLE tweet (
  id             BIGINT       NOT NULL AUTO_INCREMENT,
  user_id        BIGINT       NOT NULL,
  created_at     DATETIME(3)  NOT NULL,
  message        VARCHAR(255) NOT NULL,
  PRIMARY KEY(id)
);

CREATE TABLE subscription (
  id               BIGINT       NOT NULL AUTO_INCREMENT,
  follower_id      BIGINT       NOT NULL,
  followee_id      BIGINT       NOT NULL,
  PRIMARY KEY(id)
);
