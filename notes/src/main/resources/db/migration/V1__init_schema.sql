CREATE TABLE "app_user" (
  "id" bigint NOT NULL AUTO_INCREMENT,
  "password" varchar(255) NOT NULL,
  "role" varchar(30) NOT NULL,
  "username" varchar(50) NOT NULL,
  PRIMARY KEY ("id"),
  UNIQUE KEY "UK3k4cplvh82srueuttfkwnylq0" ("username")
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE "note" (
  "id" bigint NOT NULL AUTO_INCREMENT,
  "completed" bit(1) NOT NULL,
  "content" varchar(255) DEFAULT NULL,
  "title" varchar(255) DEFAULT NULL,
  "owner_id" bigint DEFAULT NULL,
  PRIMARY KEY ("id"),
  KEY "FKjl54w6uv8owox1s3dqb0w4r0y" ("owner_id"),
  CONSTRAINT "FKjl54w6uv8owox1s3dqb0w4r0y" FOREIGN KEY ("owner_id") REFERENCES "app_user" ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci