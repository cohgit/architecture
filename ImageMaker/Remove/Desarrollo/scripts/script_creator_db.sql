-- Schema TellMe GU
CREATE SCHEMA "|schema_remove|"
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."configurations"
	-- -----------------------------------------------------
	CREATE TABLE "configurations" (
	  "key" CHARACTER VARYING(100) NOT NULL UNIQUE,
	  "value" text
	)
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."schemas_tools_executed"
	-- -----------------------------------------------------
	CREATE TABLE "schemas_tools_executed" (
	  "id" SERIAL,
	  "name" CHARACTER VARYING(500) NOT NULL,
	  "schema_executed" CHARACTER VARYING(100) NOT NULL,
	  "success" boolean DEFAULT false,
	  "date" TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	  "message" text,
	  
	  CONSTRAINT "pk_schemas_tools_executed" PRIMARY KEY ("id")
	)

	-- -----------------------------------------------------
	-- Table "|schema_remove|"."lists"
	-- -----------------------------------------------------
	CREATE TABLE "lists" (
	  "id" SERIAL,
	  "name" CHARACTER VARYING(100) NOT NULL,
	  "description" CHARACTER VARYING(100) NOT NULL,
	  "tag" CHARACTER VARYING(50) NOT NULL UNIQUE,
	  
	  CONSTRAINT "pk_lists" PRIMARY KEY ("id")
	)
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."lists_options"
	-- -----------------------------------------------------
	CREATE TABLE "lists_options" (
	  "id" SERIAL,
	  "id_list" integer,
	  "name" CHARACTER VARYING(100) NOT NULL,
	  "active" boolean DEFAULT true,
	  
	  UNIQUE ("id_list", "name"),
	  
	  CONSTRAINT "pk_list_option" PRIMARY KEY ("id"),
	  CONSTRAINT "fk_lists_options" FOREIGN KEY ("id_list")
		  REFERENCES |schema_remove|."lists" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."countries"
	-- -----------------------------------------------------
	CREATE TABLE "countries" (
	  "id" SERIAL,
	  "tag" CHARACTER VARYING(5) NOT NULL,
	  "name" CHARACTER VARYING(250) NOT NULL,
	  "active" BOOLEAN DEFAULT true,
	  
	  CONSTRAINT "pk_countries" PRIMARY KEY ("id")
	)
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."plans"
	-- -----------------------------------------------------
	CREATE TABLE "plans" (
	  "id" SERIAL,
	  "description" CHARACTER VARYING(250) NOT NULL,
	  "duration" integer,
	  "cost" bigint,
	  "active" BOOLEAN DEFAULT true,
	  
	  CONSTRAINT "pk_plans" PRIMARY KEY ("id")
	)
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."plans_access" (?)
	-- -----------------------------------------------------
	
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."clients"
	-- -----------------------------------------------------
	CREATE TABLE "clients" (
	  "id" SERIAL,
	  "id_country" integer NOT NULL,
	  "id_plan" integer NOT NULL,
	  "name" CHARACTER VARYING(250) NOT NULL,
	  "phone" CHARACTER VARYING(20) NOT NULL,
	  "email" CHARACTER VARYING(250) NOT NULL,
	  "web_site" CHARACTER VARYING(250) NOT NULL,
	  "address" CHARACTER VARYING(250) NOT NULL,
	  "active" BOOLEAN DEFAULT true,
	  "failed_attempts" INTEGER DEFAULT 0,
	  
	  CONSTRAINT "pk_clients" PRIMARY KEY ("id"),
	  
	  CONSTRAINT "fk_clients_country" FOREIGN KEY ("id_country")
		  REFERENCES |schema_remove|."countries" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	
	
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."users"
	-- -----------------------------------------------------
	CREATE TABLE "users" (
	  "id" SERIAL,
	  "first_name" CHARACTER VARYING(250) NOT NULL,
	  "middle_name" CHARACTER VARYING(250),
	  "first_lastname" CHARACTER VARYING(250) NOT NULL,
	  "second_lastname" CHARACTER VARYING(250),
	  "email" CHARACTER VARYING(250) UNIQUE,
	  "phone" CHARACTER VARYING(20) UNIQUE,
	  "active" BOOLEAN DEFAULT true,
	  "locked" BOOLEAN DEFAULT false,
	  "failed_attempts" INTEGER DEFAULT 0,
	  "language" CHARACTER VARYING(15) NOT NULL,
	  
	  CONSTRAINT "pk_users" PRIMARY KEY ("id"),
	  CONSTRAINT "fk_profile_user" FOREIGN KEY ("id_profile")
		  REFERENCES |schema_remove|."profiles" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."sessions_users"
	-- -----------------------------------------------------
	CREATE TABLE "sessions_users" (
	  "id" SERIAL,
	  "id_owner" INTEGER NOT NULL,
	  "token" text NOT NULL,
	  "entry_date" TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	  "refresh_date" TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	  "close_date" TIMESTAMP WITHOUT TIME ZONE,
	  "ip" CHARACTER VARYING(15) NOT NULL,
	  "agent" text NOT NULL,
	  "provisional" BOOLEAN DEFAULT FALSE,
	  
	  CONSTRAINT "pk_sessions_users" PRIMARY KEY ("id"),
	  
	  CONSTRAINT "fk_sessions_user" FOREIGN KEY ("id_owner")
		  REFERENCES |schema_remove|."users" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."sessions_clients"
	-- -----------------------------------------------------
	CREATE TABLE "sessions_users" (
	  "id" SERIAL,
	  "id_owner" INTEGER NOT NULL,
	  "token" text NOT NULL,
	  "entry_date" TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	  "refresh_date" TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	  "close_date" TIMESTAMP WITHOUT TIME ZONE,
	  "ip" CHARACTER VARYING(15) NOT NULL,
	  "agent" text NOT NULL,
	  "provisional" BOOLEAN DEFAULT FALSE,
	  "language" CHARACTER VARYING(15) NOT NULL,
	  
	  CONSTRAINT "pk_sessions_cclients" PRIMARY KEY ("id"),
	  
	  CONSTRAINT "fk_sessions_client" FOREIGN KEY ("id_owner")
		  REFERENCES |schema_remove|."clients" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."users_passwords"
	-- -----------------------------------------------------
	CREATE TABLE "users_passwords" (
	  "id_owner" integer,
	  "password" CHARACTER VARYING(255) NOT NULL,
	  "creation_date" TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	  "expiration_date" TIMESTAMP WITHOUT TIME ZONE,
	  "active" boolean DEFAULT true,
	  "provisional" boolean DEFAULT false,
	  
	  CONSTRAINT "fk_user_password" FOREIGN KEY ("id_owner")
		  REFERENCES |schema_remove|."users" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."clients_passwords"
	-- -----------------------------------------------------
	CREATE TABLE "clients_passwords" (
	  "id_owner" integer,
	  "password" CHARACTER VARYING(255) NOT NULL,
	  "creation_date" TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	  "expiration_date" TIMESTAMP WITHOUT TIME ZONE,
	  "active" boolean DEFAULT true,
	  "provisional" boolean DEFAULT false,
	  
	  CONSTRAINT "fk_client_password" FOREIGN KEY ("id_owner")
		  REFERENCES |schema_remove|."clients" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."scanners"
	-- -----------------------------------------------------
	CREATE TABLE "scanners" (
	  "id" SERIAL,
	  "id_owner" integer,
	  "type" CHARACTER VARYING(100) NOT NULL,
	  "creation_date" TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	  "target" text,
	  "keywords" text,
	  "position" integer,
	  "active" BOOLEAN DEFAULT true,
	  
	  CONSTRAINT "pk_scanners" PRIMARY KEY ("id"),
	  CONSTRAINT "fk_client_scanners" FOREIGN KEY ("id_owner")
		  REFERENCES |schema_remove|."clients" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."scanners_urls"
	-- -----------------------------------------------------
	CREATE TABLE "scanners_urls" (
	  "id" SERIAL,
	  "id_scanner" integer,
	  "url" text NOT NULL,
	  
	  CONSTRAINT "pk_scanners_urls" PRIMARY KEY ("id"),
	  CONSTRAINT "fk_scanners_urls" FOREIGN KEY ("id_scanner")
		  REFERENCES |schema_remove|."scanners" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."scanners_countries"
	-- -----------------------------------------------------
	CREATE TABLE "scanners_countries" (
	  "id" SERIAL,
	  "id_scanner" integer,
	  "id_country" integer,
	  
	  CONSTRAINT "pk_scanners_countries" PRIMARY KEY ("id"),
	  CONSTRAINT "fk_scanners_countries" FOREIGN KEY ("id_scanner")
		  REFERENCES |schema_remove|."scanners" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION,
	  CONSTRAINT "fk_countries_scanners" FOREIGN KEY ("id_country")
		  REFERENCES |schema_remove|."countries" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."scanners_impulses"
	-- -----------------------------------------------------
	CREATE TABLE "scanners_impulses" (
	  "id" SERIAL,
	  "id_scanner" integer,
	  "id_owner" integer,
	  "title" text,
	  "description" text,
	  "creation_date" TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	  "url" text NOT NULL,
	  
	  CONSTRAINT "pk_scanners_impulses" PRIMARY KEY ("id"),
	  CONSTRAINT "fk_scanners_impulses" FOREIGN KEY ("id_scanner")
		  REFERENCES |schema_remove|."scanners" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	  CONSTRAINT "fk_scanners_owner" FOREIGN KEY ("id_owner")
		  REFERENCES |schema_remove|."users" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	
	-- -----------------------------------------------------
	-- Table "|corp_token|"."alerts_messages"
	-- -----------------------------------------------------
	CREATE TABLE "alerts_messages" (
	  "id" SERIAL,
	  "id_user" integer,
	  "title" CHARACTER VARYING(80),
	  "description" CHARACTER VARYING(200),
	  "type" CHARACTER VARYING(30),
	  "params" text,
	  "creation_date" TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	  "readed" boolean DEFAULT false,
	  "urgent" boolean DEFAULT false,
	  
	  CONSTRAINT "pk_alert_message" PRIMARY KEY ("id")
	)

	-- -----------------------------------------------------
	-- Table "|schema_remove|"."audit_access"
	-- -----------------------------------------------------
	--CREATE TABLE "audit_access" (
	--  "id" SERIAL,
	--  "id_user" integer,
	--  "id_profile" integer,
	--  "id_service_function" integer,
	--  "token" text,
	--  "ip" CHARACTER VARYING(30),
	--  "agent" text,
	--  "uri" CHARACTER VARYING(100),
	--  "method" CHARACTER VARYING(10),
	--  "action_date" TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	--  "request_params" text,
	--  "request_body" text,
	--  "response_service_code" CHARACTER VARYING(10),
	--  "response_code" CHARACTER VARYING(10),
	--  "response_body" text,
	  
	--  CONSTRAINT "pk_audit_access" PRIMARY KEY ("id"),
	  
	--  CONSTRAINT "fk_user_access" FOREIGN KEY ("id_user")
	--	  REFERENCES |schema_remove|."users" ("id") MATCH SIMPLE
	--	  ON UPDATE NO ACTION ON DELETE NO ACTION
	--);

-- CONSTRAINTS
CREATE UNIQUE INDEX ux_countries_names ON |schema_remove|.countries(UPPER(name));
CREATE UNIQUE INDEX ux_countries_tags ON |schema_remove|.countries(UPPER(tag));

-- COMMENTS
COMMENT ON TABLE |schema_remove|.configurations IS 'System configurations.';
COMMENT ON TABLE |schema_remove|.lists IS 'System lists.';
COMMENT ON TABLE |schema_remove|.lists_options IS 'Options lists.';


-- DATA
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('CL', 'Chile', true);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('AX', 'Islas Gland', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('AL', 'Albania', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('DE', 'Alemania', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('AD', 'Andorra', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('AO', 'Angola', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('AI', 'Anguilla', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('AQ', 'Antártida', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('AG', 'Antigua y Barbuda', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('AN', 'Antillas Holandesas', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('SA', 'Arabia Saudí', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('DZ', 'Argelia', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('AR', 'Argentina', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('AM', 'Armenia', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('AW', 'Aruba', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('AU', 'Australia', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('AT', 'Austria', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('AZ', 'Azerbaiyán', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('BS', 'Bahamas', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('BH', 'Bahréin', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('BD', 'Bangladesh', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('BB', 'Barbados', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('BY', 'Bielorrusia', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('BE', 'Bélgica', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('BZ', 'Belice', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('BJ', 'Benin', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('BM', 'Bermudas', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('BT', 'Bhután', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('BO', 'Bolivia', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('BA', 'Bosnia y Herzegovina', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('BW', 'Botsuana', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('BV', 'Isla Bouvet', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('BR', 'Brasil', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('BN', 'Brunéi', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('BG', 'Bulgaria', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('BF', 'Burkina Faso', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('BI', 'Burundi', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('CV', 'Cabo Verde', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('KY', 'Islas Caimán', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('KH', 'Camboya', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('CM', 'Camerún', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('CA', 'Canadá', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('CF', 'República Centroafricana', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('TD', 'Chad', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('CZ', 'República Checa', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('CN', 'China', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('CY', 'Chipre', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('CX', 'Isla de Navidad', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('VA', 'Ciudad del Vaticano', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('CC', 'Islas Cocos', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('CO', 'Colombia', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('KM', 'Comoras', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('CD', 'República Democrática del Congo', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('CG', 'Congo', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('CK', 'Islas Cook', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('KP', 'Corea del Norte', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('KR', 'Corea del Sur', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('CI', 'Costa de Marfil', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('CR', 'Costa Rica', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('HR', 'Croacia', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('CU', 'Cuba', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('DK', 'Dinamarca', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('DM', 'Dominica', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('DO', 'República Dominicana', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('EC', 'Ecuador', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('EG', 'Egipto', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('SV', 'El Salvador', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('AE', 'Emiratos Árabes Unidos', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('ER', 'Eritrea', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('SK', 'Eslovaquia', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('SI', 'Eslovenia', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('ES', 'España', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('UM', 'Islas ultramarinas de Estados Unidos', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('US', 'Estados Unidos', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('EE', 'Estonia', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('ET', 'Etiopía', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('FO', 'Islas Feroe', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('PH', 'Filipinas', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('FI', 'Finlandia', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('FJ', 'Fiyi', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('FR', 'Francia', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('GA', 'Gabón', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('GM', 'Gambia', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('GE', 'Georgia', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('GS', 'Islas Georgias del Sur y Sandwich del Sur', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('GH', 'Ghana', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('GI', 'Gibraltar', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('GD', 'Granada', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('GR', 'Grecia', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('GL', 'Groenlandia', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('GP', 'Guadalupe', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('GU', 'Guam', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('GT', 'Guatemala', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('GF', 'Guayana Francesa', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('GN', 'Guinea', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('GQ', 'Guinea Ecuatorial', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('GW', 'Guinea-Bissau', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('GY', 'Guyana', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('HT', 'Haití', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('HM', 'Islas Heard y McDonald', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('HN', 'Honduras', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('HK', 'Hong Kong', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('HU', 'Hungría', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('IN', 'India', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('ID', 'Indonesia', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('IR', 'Irán', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('IQ', 'Iraq', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('IE', 'Irlanda', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('IS', 'Islandia', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('IL', 'Israel', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('IT', 'Italia', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('JM', 'Jamaica', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('JP', 'Japón', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('JO', 'Jordania', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('KZ', 'Kazajstán', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('KE', 'Kenia', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('KG', 'Kirguistán', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('KI', 'Kiribati', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('KW', 'Kuwait', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('LA', 'Laos', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('LS', 'Lesotho', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('LV', 'Letonia', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('LB', 'Líbano', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('LR', 'Liberia', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('LY', 'Libia', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('LI', 'Liechtenstein', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('LT', 'Lituania', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('LU', 'Luxemburgo', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('MO', 'Macao', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('MK', 'ARY Macedonia', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('MG', 'Madagascar', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('MY', 'Malasia', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('MW', 'Malawi', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('MV', 'Maldivas', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('ML', 'Malí', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('MT', 'Malta', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('FK', 'Islas Malvinas', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('MP', 'Islas Marianas del Norte', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('MA', 'Marruecos', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('MH', 'Islas Marshall', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('MQ', 'Martinica', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('MU', 'Mauricio', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('MR', 'Mauritania', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('YT', 'Mayotte', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('MX', 'México', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('FM', 'Micronesia', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('MD', 'Moldavia', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('MC', 'Mónaco', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('MN', 'Mongolia', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('MS', 'Montserrat', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('MZ', 'Mozambique', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('MM', 'Myanmar', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('NA', 'Namibia', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('NR', 'Nauru', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('NP', 'Nepal', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('NI', 'Nicaragua', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('NE', 'Níger', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('NG', 'Nigeria', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('NU', 'Niue', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('NF', 'Isla Norfolk', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('NO', 'Noruega', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('NC', 'Nueva Caledonia', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('NZ', 'Nueva Zelanda', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('OM', 'Omán', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('NL', 'Países Bajos', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('PK', 'Pakistán', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('PW', 'Palau', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('PS', 'Palestina', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('PA', 'Panamá', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('PG', 'Papúa Nueva Guinea', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('PY', 'Paraguay', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('PE', 'Perú', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('PN', 'Islas Pitcairn', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('PF', 'Polinesia Francesa', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('PL', 'Polonia', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('PT', 'Portugal', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('PR', 'Puerto Rico', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('QA', 'Qatar', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('GB', 'Reino Unido', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('RE', 'Reunión', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('RW', 'Ruanda', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('RO', 'Rumania', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('RU', 'Rusia', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('EH', 'Sahara Occidental', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('SB', 'Islas Salomón', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('WS', 'Samoa', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('AS', 'Samoa Americana', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('KN', 'San Cristóbal y Nevis', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('SM', 'San Marino', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('PM', 'San Pedro y Miquelón', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('VC', 'San Vicente y las Granadinas', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('SH', 'Santa Helena', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('LC', 'Santa Lucía', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('ST', 'Santo Tomé y Príncipe', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('SN', 'Senegal', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('CS', 'Serbia y Montenegro', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('SC', 'Seychelles', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('SL', 'Sierra Leona', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('SG', 'Singapur', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('SY', 'Siria', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('SO', 'Somalia', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('LK', 'Sri Lanka', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('SZ', 'Suazilandia', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('ZA', 'Sudáfrica', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('SD', 'Sudán', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('SE', 'Suecia', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('CH', 'Suiza', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('SR', 'Surinam', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('SJ', 'Svalbard y Jan Mayen', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('TH', 'Tailandia', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('TW', 'Taiwán', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('TZ', 'Tanzania', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('TJ', 'Tayikistán', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('IO', 'Territorio Británico del Océano Índico', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('TF', 'Territorios Australes Franceses', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('TL', 'Timor Oriental', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('TG', 'Togo', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('TK', 'Tokelau', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('TO', 'Tonga', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('TT', 'Trinidad y Tobago', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('TN', 'Túnez', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('TC', 'Islas Turcas y Caicos', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('TM', 'Turkmenistán', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('TR', 'Turquía', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('TV', 'Tuvalu', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('UA', 'Ucrania', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('UG', 'Uganda', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('UY', 'Uruguay', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('UZ', 'Uzbekistán', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('VU', 'Vanuatu', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('VE', 'Venezuela', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('VN', 'Vietnam', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('VG', 'Islas Vírgenes Británicas', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('VI', 'Islas Vírgenes de los Estados Unidos', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('WF', 'Wallis y Futuna', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('YE', 'Yemen', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('DJ', 'Yibuti', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('ZM', 'Zambia', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('ZW', 'Zimbabue', false);
INSERT INTO |schema_remove|.countries (tag, name, active) VALUES  ('AF', 'Afganistán', false);


-- USER TEST
INSERT INTO |schema_remove|.users(first_name, first_lastname, email, active, id_profile)
    VALUES ('Paola', 'Godoy', 'paola@prueba.com', true, 1);
INSERT INTO |schema_remove|.users(first_name, first_lastname, email, active, id_profile)
    VALUES ('Quili', 'Gutierrez', 'quili@prueba.com', true, 1);
INSERT INTO |schema_remove|.users_passwords(id_user, password, creation_date,  active, provisional)
    VALUES (1, 'LsKMY85vI76wvEM2dqqpQaBzBjpxhw==', CURRENT_DATE, true, false);
INSERT INTO |schema_remove|.users_passwords(id_user, password, creation_date,  active, provisional)
    VALUES (2, 'LsKMY85vI76wvEM2dqqpQaBzBjpxhw==', CURRENT_DATE, true, false);
