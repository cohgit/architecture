-- Schema Remove
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
	  "id" BIGSERIAL,
	  "name" CHARACTER VARYING(500) NOT NULL,
	  "schema_executed" CHARACTER VARYING(100) NOT NULL,
	  "success" boolean DEFAULT false,
	  "date" TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	  "message" text,
	  
	  CONSTRAINT "pk_schemas_tools_executed" PRIMARY KEY ("id")
	)

	-- -----------------------------------------------------
	-- Table "|schema_remove|"."countries"
	-- -----------------------------------------------------
	CREATE TABLE "countries" (
	  "id" BIGSERIAL,
	  "tag" CHARACTER VARYING(5) NOT NULL,
	  "name" CHARACTER VARYING(250) NOT NULL,
	  "domain" CHARACTER VARYING(10),
	  "active" BOOLEAN DEFAULT true,
	  
	  CONSTRAINT "pk_countries" PRIMARY KEY ("id")
	)
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."languages"
	-- -----------------------------------------------------
	CREATE TABLE "languages" (
	  "id" BIGSERIAL,
	  "tag" CHARACTER VARYING(8) NOT NULL,
	  "name" CHARACTER VARYING(250) NOT NULL,
	  "active" BOOLEAN DEFAULT true,
	  
	  CONSTRAINT "pk_languages" PRIMARY KEY ("id")
	)
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."tracking_words"
	-- -----------------------------------------------------
	CREATE TABLE "tracking_words" (
	  "id" BIGSERIAL,
	  "word" text NOT NULL,
	  "feeling" CHARACTER VARYING(250) NOT NULL,
	  "active" BOOLEAN DEFAULT true,
	  
	  CONSTRAINT "pk_tracking_word" PRIMARY KEY ("id")
	)
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."tracking_words"
	-- -----------------------------------------------------
	CREATE TABLE "forbidden_words" (
	  "id" BIGSERIAL,
	  "word" text NOT NULL,
	  "languages" text,
	  
	  CONSTRAINT "pk_forbidden_word" PRIMARY KEY ("id")
	)
	
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."plans"
	-- -----------------------------------------------------
	CREATE TABLE "plans" (
	  "id" BIGSERIAL,
	  "name" CHARACTER VARYING(250) NOT NULL,
	  "description" text,
	  "duration_months" integer,
	  "cost" numeric,
	  "automatic_renewal" boolean,
	  
	  "max_keywords" integer,
	  "max_countries" integer,
	  "max_search_page" integer,
	  "sections_to_search" CHARACTER VARYING(250),
	  
	  "access_scanner" BOOLEAN,
	  "limit_credits" bigint,
	  
	  "access_monitor" BOOLEAN,
	  "total_monitor_licenses" integer,
	  
	  "access_transforma" BOOLEAN,
	  "total_transforma_licenses" integer,
	  "max_url_to_remove" integer,
	  "max_url_to_impulse" integer,
	  "max_url_to_deindexate" integer,
	  "target_page" integer,
	  
	  "access_deindexation" BOOLEAN,
	  
	  "customized" BOOLEAN DEFAULT false,
	  "quote_requested" BOOLEAN DEFAULT false,
	  "quote_approved" BOOLEAN,
	  "active" BOOLEAN DEFAULT true,
	  
	  CONSTRAINT "pk_plans" PRIMARY KEY ("id")
	)
	
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."plans_external_key"
	-- -----------------------------------------------------
	CREATE TABLE "plans_external_key" (
	  "id" BIGSERIAL,
	  "id_plan" bigint NOT NULL,
	  "key" text,
	  "key_payment" text,
	  "platform" text,
	  "active" boolean default true,
	  
	  CONSTRAINT "pk_plans_external_key" PRIMARY KEY ("id")
	)
	
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."plans_clients_suggestions"
	-- -----------------------------------------------------
	CREATE TABLE "plans_clients_suggestions" (
	  "id" BIGSERIAL,
	  "id_plan" bigint NOT NULL,
	  "id_client" bigint,
	  "uuid" text,
	  "client_name" text,
	  "client_email" text,
	  "email_sent" boolean default false,
	  "client_already_registred" boolean default false,
	  "client_attend_suggestion" boolean default false,
	  
	  CONSTRAINT "pk_plans_clients_suggestions" PRIMARY KEY ("id")
	)

	-- -----------------------------------------------------
	-- Table "|schema_remove|"."clients"
	-- -----------------------------------------------------
	CREATE TABLE "clients" (
	  "id" BIGSERIAL,
	  "id_country" bigint NOT NULL,
	  "uuid" text NOT NULL,
	  "name" CHARACTER VARYING(250) NOT NULL,
	  "lastname" CHARACTER VARYING(250) NOT NULL,
	  "email" CHARACTER VARYING(250) NOT NULL,
	  "language" CHARACTER VARYING(2) NOT NULL DEFAULT 'es',
	  "phone" CHARACTER VARYING(20),
	  "project_name" CHARACTER VARYING(250) NOT NULL,
	  "corporative" boolean DEFAULT FALSE,
	  "business_name" CHARACTER VARYING(250),
	  "dni" CHARACTER VARYING(250),
	  "fiscal_address" text,
	  "postal" CHARACTER VARYING(20),
	  "active" BOOLEAN DEFAULT true,
	  "failed_attempts" INTEGER DEFAULT 0,
	  "email_verified" BOOLEAN DEFAULT FALSE,
	  "readonly" BOOLEAN DEFAULT TRUE,
	  "message" text,
	  "send_email" BOOLEAN DEFAULT TRUE,
	  
	  "first_time" BOOLEAN DEFAULT true,
	  
	  CONSTRAINT "pk_clients" PRIMARY KEY ("id"),
	  
	  CONSTRAINT "fk_clients_country" FOREIGN KEY ("id_country")
		  REFERENCES |schema_remove|."countries" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."clients_payment_keys"
	-- -----------------------------------------------------
	CREATE TABLE "clients_payment_keys" (
	  "id" BIGSERIAL,
	  "id_client" bigint,
	  "platform" CHARACTER VARYING(250) NOT NULL,
	  "key" text,
	  "active" boolean DEFAULT true,
	  
	  CONSTRAINT "pk_clients_payment_keys" PRIMARY KEY ("id"),
	  
	  CONSTRAINT "fk_client_payment_keys" FOREIGN KEY ("id_client")
		  REFERENCES |schema_remove|."clients" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."clients_paybills"
	-- -----------------------------------------------------
	CREATE TABLE "clients_paybills" (
	  "id" BIGSERIAL,
	  "id_client_key" bigint,
	  "id_plan" bigint,
	  "id_approved_by" bigint,
	  "amount" numeric,
	  "payment_date" TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	  "tracking_code" text,
	  "raw_response" text,
	  "confirmed" boolean DEFAULT false,
	  
	  CONSTRAINT "pk_clients_paybills" PRIMARY KEY ("id"),
	  
	  CONSTRAINT "fk_client_key_paybills" FOREIGN KEY ("id_client_key")
		  REFERENCES |schema_remove|."clients_payment_keys" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION,
	  CONSTRAINT "fk_client_paybills_plan" FOREIGN KEY ("id_plan")
		  REFERENCES |schema_remove|."plans" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION,
	  CONSTRAINT "fk_client_paybills_plan_approved_by" FOREIGN KEY ("id_approved_by")
		  REFERENCES |schema_remove|."user" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."clients_plans"
	-- -----------------------------------------------------
	CREATE TABLE "clients_plans" (
	  "id" BIGSERIAL,
	  "id_client" bigint,
	  "id_plan" bigint,
	  "uuid" text,
	  "id_client_paybills" bigint,
	  "suscribe_date" TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	  "expiration_date" TIMESTAMP,
	  "cancellation_date" TIMESTAMP,
	  "credits_used" bigint DEFAULT 0,
	  "deindex_used" bigint DEFAULT 0,
	  "external_subscription_platform" text,
	  "external_subscription_key" text,
	  "active" boolean DEFAULT true,
	  
	  CONSTRAINT "pk_clients_plan" PRIMARY KEY ("id"),
	  
	  CONSTRAINT "fk_client_plan" FOREIGN KEY ("id_client")
		  REFERENCES |schema_remove|."clients" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION,
	  CONSTRAINT "fk_client_plan_plan" FOREIGN KEY ("id_plan")
		  REFERENCES |schema_remove|."plans" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."clients_plans_migrations"
	-- -----------------------------------------------------
	CREATE TABLE "clients_plans_migrations" (
	  "id" BIGSERIAL,
	  "id_client" bigint,
	  "id_client_plan_from" bigint,
	  "id_client_plan_to" bigint,
	  "payment_platform" CHARACTER VARYING(100),
	  "payment_method_key" text,
	  "scanners_to_migrate" text,
	  "creation_date" TIMESTAMP,
	  "programmed_date" TIMESTAMP,
	  "execution_date" TIMESTAMP,
	  "programmed" boolean,
	  "executed" boolean,
	  
	  CONSTRAINT "pk_clients_plans_migrations" PRIMARY KEY ("id"),
	  
	  CONSTRAINT "fk_client_plan_migration" FOREIGN KEY ("id_client")
		  REFERENCES |schema_remove|."clients" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION,
	  CONSTRAINT "fk_client_plan_from" FOREIGN KEY ("id_client_plan_from")
		  REFERENCES |schema_remove|."plans" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION,
	  CONSTRAINT "fk_client_plan_to" FOREIGN KEY ("id_client_plan_to")
		  REFERENCES |schema_remove|."plans" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."clients_sessions"
	-- -----------------------------------------------------
	CREATE TABLE "clients_sessions" (
	  "id" BIGSERIAL,
	  "id_owner" bigint NOT NULL,
	  "token" text NOT NULL,
	  "entry_date" TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	  "refresh_date" TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	  "close_date" TIMESTAMP WITH TIME ZONE,
	  "ip" CHARACTER VARYING(300) NOT NULL,
	  "agent" text NOT NULL,
	  
	  CONSTRAINT "pk_sessions_cclients" PRIMARY KEY ("id"),
	  
	  CONSTRAINT "fk_sessions_client" FOREIGN KEY ("id_owner")
		  REFERENCES |schema_remove|."clients" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."clients_email_verification_requests"
	-- -----------------------------------------------------
	CREATE TABLE "clients_email_verification_requests" (
	  "id_client" bigint,
	  "uuid" text NOT NULL,
	  "creation_date" TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	  "expiration_date" TIMESTAMP WITH TIME ZONE,
	  "email" CHARACTER VARYING(250) NOT NULL,
	  "confirmed" boolean DEFAULT false,
	  "actived" boolean DEFAULT true,
	  
	  CONSTRAINT "fk_client_password_requests" FOREIGN KEY ("id_client")
		  REFERENCES |schema_remove|."clients" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."clients_passwords_change_requests"
	-- -----------------------------------------------------
	CREATE TABLE "clients_passwords_change_requests" (
	  "id_client" bigint,
	  "uuid" text NOT NULL,
	  "creation_date" TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	  "expiration_date" TIMESTAMP WITH TIME ZONE,
	  "confirmed" boolean DEFAULT false,
	  
	  CONSTRAINT "fk_client_password_requests" FOREIGN KEY ("id_client")
		  REFERENCES |schema_remove|."clients" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."clients_passwords"
	-- -----------------------------------------------------
	CREATE TABLE "clients_passwords" (
	  "id_owner" bigint,
	  "password" CHARACTER VARYING(255) NOT NULL,
	  "creation_date" TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	  "expiration_date" TIMESTAMP WITH TIME ZONE,
	  "active" boolean DEFAULT true,
	  
	  CONSTRAINT "fk_client_password" FOREIGN KEY ("id_owner")
		  REFERENCES |schema_remove|."clients" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."users"
	-- -----------------------------------------------------
	CREATE TABLE "users" (
	  "id" BIGSERIAL,
	  "uuid" text NOT NULL,
	  "name" text NOT NULL,
	  "lastname" CHARACTER VARYING(250) NOT NULL,
	  "email" CHARACTER VARYING(250) UNIQUE,
	  "phone" CHARACTER VARYING(20) UNIQUE,
	  "profile" CHARACTER VARYING(250) NOT NULL,
	  "active" BOOLEAN DEFAULT true,
	  "locked" BOOLEAN DEFAULT false,
	  "failed_attempts" INTEGER DEFAULT 0,
	  "language" CHARACTER VARYING(2) NOT NULL DEFAULT 'es',
	  "first_time" BOOLEAN DEFAULT true,
	  "send_email" BOOLEAN DEFAULT TRUE,
	  
	  CONSTRAINT "pk_users" PRIMARY KEY ("id")
	)
	
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."users_sessions"
	-- -----------------------------------------------------
	CREATE TABLE "users_sessions" (
	  "id" BIGSERIAL,
	  "id_owner" bigint NOT NULL,
	  "token" text NOT NULL,
	  "entry_date" TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	  "refresh_date" TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	  "close_date" TIMESTAMP WITH TIME ZONE,
	  "ip" CHARACTER VARYING(300) NOT NULL,
	  "agent" text NOT NULL,
	  
	  CONSTRAINT "pk_sessions_users" PRIMARY KEY ("id"),
	  
	  CONSTRAINT "fk_sessions_user" FOREIGN KEY ("id_owner")
		  REFERENCES |schema_remove|."users" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
-- -----------------------------------------------------
	-- Table "|schema_remove|"."users_passwords_change_requests"
	-- -----------------------------------------------------
	CREATE TABLE "users_passwords_change_requests" (
	  "id_owner" bigint,
	  "uuid" text NOT NULL,
	  "creation_date" TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	  "expiration_date" TIMESTAMP WITH TIME ZONE,
	  "confirmed" boolean DEFAULT false,
	  
	  CONSTRAINT "fk_client_password_request" FOREIGN KEY ("id_owner")
		  REFERENCES |schema_remove|."users" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."users_passwords"
	-- -----------------------------------------------------
	CREATE TABLE "users_passwords" (
	  "id_owner" bigint,
	  "password" CHARACTER VARYING(255) NOT NULL,
	  "creation_date" TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	  "expiration_date" TIMESTAMP WITH TIME ZONE,
	  "active" boolean DEFAULT true,
	  
	  CONSTRAINT "fk_user_password" FOREIGN KEY ("id_owner")
		  REFERENCES |schema_remove|."users" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."users_passwords"
	-- -----------------------------------------------------
	CREATE TABLE "users_clients" (
	  "id_user" bigint,
	  "id_client" bigint,
	  
	  CONSTRAINT "fk_user_client" FOREIGN KEY ("id_user")
		  REFERENCES |schema_remove|."users" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION,
	  CONSTRAINT "fk_client_client" FOREIGN KEY ("id_client")
		  REFERENCES |schema_remove|."clients" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."scanners"
	-- -----------------------------------------------------
	CREATE TABLE "scanners" (
	  "id" BIGSERIAL,
	  "uuid" text NOT NULL,
	  "uuid_from" text,
	  "name" text NOT NULL,
	  "id_owner" bigint NOT NULL,
	  "id_client_plan" bigint NOT NULL,
	  "creation_date" TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	  "activation_date" TIMESTAMP WITH TIME ZONE,
	  "deactivation_date" TIMESTAMP WITH TIME ZONE,
	  "status" CHARACTER VARYING(20),
	  "type" CHARACTER VARYING(20) NOT NULL,
	  "total_scans" bigint,
	  
	  CONSTRAINT "pk_scanners" PRIMARY KEY ("id"),
	  CONSTRAINT "fk_client_scanners" FOREIGN KEY ("id_owner")
		  REFERENCES |schema_remove|."clients" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION,
	  CONSTRAINT "fk_client_scanners_plan" FOREIGN KEY ("id_client_plan")
		  REFERENCES |schema_remove|."clients_plans" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."scanners_configuration"
	-- -----------------------------------------------------
	CREATE TABLE "scanners_configuration" (
	  "id" BIGSERIAL,
	  "id_scanner" bigint,
	  "device" CHARACTER VARYING(20) NOT NULL,
	  "language" CHARACTER VARYING(2) NOT NULL,
	  "pages" integer DEFAULT 2,
	  "search_type" text NOT NULL,
	  "cities" text,
	  -- images search params
	  "images_color" CHARACTER VARYING(20),
	  "images_size" CHARACTER VARYING(20),
	  "images_type" CHARACTER VARYING(20),
	  "images_use_rights" CHARACTER VARYING(50),
	  -- news search params
	  "news_type" CHARACTER VARYING(20),
	  
	  CONSTRAINT "pk_scanners_configuration" PRIMARY KEY ("id"),
	  CONSTRAINT "fk_scanners_configuration" FOREIGN KEY ("id_scanner")
		  REFERENCES |schema_remove|."scanners" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."scanners_countries"
	-- -----------------------------------------------------
	CREATE TABLE "scanners_configuration_countries" (
	  "id" BIGSERIAL,
	  "id_scanner_config" bigint NOT NULL,
	  "id_country" bigint NOT NULL,
	  
	  CONSTRAINT "pk_scanners_configuration_countries" PRIMARY KEY ("id"),
	  CONSTRAINT "fk_scanners_configuration_countries" FOREIGN KEY ("id_scanner_config")
		  REFERENCES |schema_remove|."scanners_configuration" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION,
	  CONSTRAINT "fk_scanners_configuration_scanners" FOREIGN KEY ("id_country")
		  REFERENCES |schema_remove|."countries" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."scanners_keywords"
	-- -----------------------------------------------------
	CREATE TABLE "scanners_keywords" (
	  "id" BIGSERIAL,
	  "id_scanner" bigint NOT NULL,
	  "id_suggested_from" bigint,
	  "id_country_suggested" bigint,
	  "word" text NOT NULL,
	  "suggested" boolean DEFAULT false,
	  
	  CONSTRAINT "pk_scanners_keywords" PRIMARY KEY ("id"),
	  CONSTRAINT "fk_scanners_keywords" FOREIGN KEY ("id_scanner")
		  REFERENCES |schema_remove|."scanners" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION,
	  CONSTRAINT "fk_scanners_keywords_suggested" FOREIGN KEY ("id_suggested_from")
		  REFERENCES |schema_remove|."scanners_keywords" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."scanners_tracking_words"
	-- -----------------------------------------------------
	CREATE TABLE "scanners_tracking_words" (
	  "id" BIGSERIAL,
	  "id_scanner" bigint NOT NULL,
	  "word" text NOT NULL,
	  "type" CHARACTER VARYING(10) NOT NULL,
	  "feeling" CHARACTER VARYING(250) NOT NULL,
	  
	  CONSTRAINT "pk_scanners_tracking_words" PRIMARY KEY ("id"),
	  CONSTRAINT "fk_scanners_tracking_words" FOREIGN KEY ("id_scanner")
		  REFERENCES |schema_remove|."scanners" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."scanners_results"
	-- -----------------------------------------------------
	CREATE TABLE "scanners_results" (
	  "id" BIGSERIAL,
	  "uuid" text NOT NULL,
	  "rating_visualization" numeric,
	  "rating_reputation" numeric,
	  "total_last_scan" integer,
	  "progress" numeric default 0,
	  "id_scanner" bigint NOT NULL,
	  "id_country" bigint NOT NULL,
	  "id_keyword" bigint NOT NULL,
	  "language" CHARACTER VARYING(2) NOT NULL,
	  "device" CHARACTER VARYING(20) NOT NULL,
	  "search_type" CHARACTER VARYING(30) NOT NULL,
	  "city" text,
	  "query_date" TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	  
	  CONSTRAINT "pk_scanners_results" PRIMARY KEY ("id"),
	  CONSTRAINT "fk_scanners_results" FOREIGN KEY ("id_scanner")
		  REFERENCES |schema_remove|."scanners" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION,
	  CONSTRAINT "fk_countries_scanners" FOREIGN KEY ("id_country")
		  REFERENCES |schema_remove|."countries" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION,
	  CONSTRAINT "fk_countries_scanners_keywords" FOREIGN KEY ("id_keyword")
		  REFERENCES |schema_remove|."scanners_keywords" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."scanners_results_raw"
	-- -----------------------------------------------------
	CREATE TABLE "scanners_results_raw" (
	  "id" BIGSERIAL,
	  "id_scanner_result" bigint NOT NULL,
	  "rating_visualization" numeric,
	  "rating_reputation" numeric,
	  "uuid" text,
	  "scan_number" bigint,
	  "type" text,
	  "date_scan" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	  "raw" text NOT NULL,
	  
	  CONSTRAINT "pk_scanners_results_raw" PRIMARY KEY ("id"),
	  CONSTRAINT "fk_scanners_results" FOREIGN KEY ("id_scanner_result")
		  REFERENCES |schema_remove|."scanners_results" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."scanners_results_web_snippets"
	-- -----------------------------------------------------
	CREATE TABLE "scanners_results_web_snippets" (
	  "id" BIGSERIAL,
	  "id_scanner_result" bigint NOT NULL,
	  "id_scanner_raw" bigint,
	  "title" text NOT NULL,
	  "link" text NOT NULL,
	  "domain" text NOT NULL,
	  "displayed_link" text,
	  "snippet" text, 
	  "date" text,
	  "date_utc" TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	  "feeling" CHARACTER VARYING(250) NOT NULL,
	  
	  CONSTRAINT "pk_scanners_results_web_snippets" PRIMARY KEY ("id"),
	  CONSTRAINT "fk_scanners_results_web" FOREIGN KEY ("id_scanner_result")
		  REFERENCES |schema_remove|."scanners_results" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."scanners_results_web_snippets_tracks"
	-- -----------------------------------------------------
	CREATE TABLE "scanners_results_web_snippets_tracks" (
	  "id" BIGSERIAL,
	  "id_scanner_result_web_snippet" bigint NOT NULL,
	  "id_scanner_raw" bigint,
	  "page" integer NOT NULL,
	  "position" integer NOT NULL,
	  "position_in_page" integer,
	  "date_scan" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	  
	  CONSTRAINT "pk_scanners_results_web_snippets_track" PRIMARY KEY ("id"),
	  CONSTRAINT "fk_scanners_results_web_snippets" FOREIGN KEY ("id_scanner_result_web_snippet")
		  REFERENCES |schema_remove|."scanners_results_web_snippets" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."scanners_results_web_snippets_tw"
	-- -----------------------------------------------------
	CREATE TABLE "scanners_results_web_snippets_tw" (
	  "id_scanner_result_web_snippet" bigint NOT NULL,
	  "id_scanner_tracking_word" bigint NOT NULL,
	  
	  CONSTRAINT "fk_scanner_result_snippet" FOREIGN KEY ("id_scanner_result_web_snippet")
		  REFERENCES |schema_remove|."scanners_results_web_snippets" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION,
	  CONSTRAINT "fk_scanner_result_snippet_tw" FOREIGN KEY ("id_scanner_tracking_word")
		  REFERENCES |schema_remove|."scanners_tracking_words" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."scanners_results_images_snippets"
	-- -----------------------------------------------------
	CREATE TABLE "scanners_results_images_snippets" (
	  "id" BIGSERIAL,
	  "id_scanner_result" bigint NOT NULL,
	  "id_scanner_raw" bigint,
	  "title" text NOT NULL,
	  "link" text NOT NULL,
	  "domain" text NOT NULL,
	  "width" int,
	  "height" int, 
	  "image" text NOT NULL,
	  "description" text,
	  "brand" text,
	  "feeling" CHARACTER VARYING(250) NOT NULL,
	  
	  CONSTRAINT "pk_scanners_results_image_snippets" PRIMARY KEY ("id"),
	  CONSTRAINT "fk_scanners_results_image" FOREIGN KEY ("id_scanner_result")
		  REFERENCES |schema_remove|."scanners_results" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."scanners_results_images_snippets_tracks"
	-- -----------------------------------------------------
	CREATE TABLE "scanners_results_images_snippets_tracks" (
	  "id" BIGSERIAL,
	  "id_scanner_result_image_snippet" bigint NOT NULL,
	  "id_scanner_raw" bigint,
	  "page" integer NOT NULL,
	  "position" integer NOT NULL,
	  "position_in_page" integer,
	  "date_scan" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	  
	  CONSTRAINT "pk_scanners_results_image_snippets_track" PRIMARY KEY ("id"),
	  CONSTRAINT "fk_scanners_results_image_snippets" FOREIGN KEY ("id_scanner_result_image_snippet")
		  REFERENCES |schema_remove|."scanners_results_images_snippets" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."scanners_results_images_keyword_tw"
	-- -----------------------------------------------------
	CREATE TABLE "scanners_results_images_snippets_keyword_tw" (
	  "id" BIGSERIAL,
	  "id_scanner_result_image_snippet" bigint NOT NULL,
	  "id_scanner_tracking_word" bigint NOT NULL,
	  
	  CONSTRAINT "pk_scanners_results_images_snippet_keyword_tw" PRIMARY KEY ("id"),
	  CONSTRAINT "fk_scanner_result_image_snippet_keyword_tw" FOREIGN KEY ("id_scanner_result_image_snippet")
		  REFERENCES |schema_remove|."scanners_results_images_snippets" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION,
	  CONSTRAINT "fk_scanner_result_image_snippet_tw" FOREIGN KEY ("id_scanner_tracking_word")
		  REFERENCES |schema_remove|."scanners_tracking_words" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."scanners_results_news_snippets"
	-- -----------------------------------------------------
	CREATE TABLE "scanners_results_news_snippets" (
	  "id" BIGSERIAL,
	  "id_scanner_result" bigint NOT NULL,
	  "id_scanner_raw" bigint,
	  "title" text NOT NULL,
	  "link" text NOT NULL,
	  "domain" text NOT NULL,
	  "source" text,
	  "snippet" text,
	  "date" text, 
	  "date_utc" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	  "thumbnail" text,
	  "feeling" CHARACTER VARYING(250) NOT NULL,
	  
	  CONSTRAINT "pk_scanners_results_news_snippets" PRIMARY KEY ("id"),
	  CONSTRAINT "fk_scanners_results_news" FOREIGN KEY ("id_scanner_result")
		  REFERENCES |schema_remove|."scanners_results" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."scanners_results_news_snippets_tracks"
	-- -----------------------------------------------------
	CREATE TABLE "scanners_results_news_snippets_tracks" (
	  "id" BIGSERIAL,
	  "id_scanner_result_news_snippet" bigint NOT NULL,
	  "id_scanner_raw" bigint,
	  "page" integer NOT NULL,
	  "position" integer NOT NULL,
	  "position_in_page" integer,
	  "date_scan" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	  
	  CONSTRAINT "pk_scanners_results_news_snippets_track" PRIMARY KEY ("id"),
	  CONSTRAINT "fk_scanners_results_news_snippets" FOREIGN KEY ("id_scanner_result_news_snippet")
		  REFERENCES |schema_remove|."scanners_results_news_snippets" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."scanners_results_news_keyword_tw"
	-- -----------------------------------------------------
	CREATE TABLE "scanners_results_news_snippets_keyword_tw" (
	  "id" BIGSERIAL,
	  "id_scanner_result_news_snippet" bigint NOT NULL,
	  "id_scanner_tracking_word" bigint NOT NULL,
	  
	  CONSTRAINT "pk_scanners_results_news_snippet_keyword_tw" PRIMARY KEY ("id"),
	  CONSTRAINT "fk_scanner_result_news_snippet_keyword_tw" FOREIGN KEY ("id_scanner_result_news_snippet")
		  REFERENCES |schema_remove|."scanners_results_news_snippets" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION,
	  CONSTRAINT "fk_scanner_result_news_snippet_tw" FOREIGN KEY ("id_scanner_tracking_word")
		  REFERENCES |schema_remove|."scanners_tracking_words" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."scanners_results_views"
	-- -----------------------------------------------------
	CREATE TABLE "scanners_results_views" (
	  "id" BIGSERIAL,
	  "uuid" text NOT NULL UNIQUE,
	  "content" text NOT NULL,
	  
	  CONSTRAINT "pk_scanners_results_views" PRIMARY KEY ("id")
	)
	
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."scanners_impulses"
	-- -----------------------------------------------------
	CREATE TABLE "scanners_impulses" (
	  "id" BIGSERIAL,
	  "id_creator" bigint,
	  "creator_profile" CHARACTER VARYING(20),
	  "id_scanner" bigint,
	  "id_keyword" bigint,
	  "type" CHARACTER VARYING(20),
	  "reference_link" text,
	  "comments" text,
	  "status" CHARACTER VARYING(20),
	  "estimated_publish" date,
	  "real_publish_link" text,
	  "real_publish_date" TIMESTAMP WITH TIME ZONE,
	  "creation_date" TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	  "initial_position" integer,
	  "target_reached" boolean DEFAULT false,
	  
	  CONSTRAINT "pk_scanners_impulses" PRIMARY KEY ("id"),
	  CONSTRAINT "fk_scanners_impulses" FOREIGN KEY ("id_scanner")
		  REFERENCES |schema_remove|."scanners" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION,
	  CONSTRAINT "fk_scanners_impulses_keywords" FOREIGN KEY ("id_keyword")
		  REFERENCES |schema_remove|."scanners_keywords" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."scanners_impulses_contents"
	-- -----------------------------------------------------
	CREATE TABLE "scanners_impulses_contents" (
	  "id" BIGSERIAL,
	  "id_scanner_impulse" bigint,
	  "title" text,
	  "content" text,
	  "image_link" text,
	  "creation_date" TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	  "id_author" bigint,
	  "author_profile" CHARACTER VARYING(20),
	  
	  CONSTRAINT "pk_scanners_impulses_contents" PRIMARY KEY ("id"),
	  CONSTRAINT "fk_scanners_impulses_contents" FOREIGN KEY ("id_scanner_impulse")
		  REFERENCES |schema_remove|."scanners_impulses" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."scanners_impulses_observations"
	-- -----------------------------------------------------
	CREATE TABLE "scanners_impulses_observations" (
	  "id" BIGSERIAL,
	  "id_scanner_impulse_content" bigint,
	  "status" CHARACTER VARYING(20),
	  "creation_date" TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	  "commentary" text,
	  "id_owner" bigint,
	  "owner_profile" CHARACTER VARYING(20),
	  
	  CONSTRAINT "pk_scanners_impulses_Observations" PRIMARY KEY ("id"),
	  CONSTRAINT "fk_scanners_impulses_contents_observations" FOREIGN KEY ("id_scanner_impulse_content")
		  REFERENCES |schema_remove|."scanners_impulses_contents" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."scanners_impulses_interactions"
	-- -----------------------------------------------------
	CREATE TABLE "scanners_impulses_variables" (
	  "id" BIGSERIAL,
	  "id_scanner_impulse" bigint,
	  "id_country" bigint,
	  "traffic_site" CHARACTER VARYING(100),
	  "da" CHARACTER VARYING(100),
	  "dr" CHARACTER VARYING(100),
	  "pa" CHARACTER VARYING(100),
	  "total_links" integer,
	  "total_keywords" integer,
	  "category_content" CHARACTER VARYING(100),
	  "id_author" bigint,
	  "author_profile" CHARACTER VARYING(20),
	  
	  CONSTRAINT "pk_scanners_impulses_variables" PRIMARY KEY ("id"),
	  CONSTRAINT "fk_scanners_impulses_variables" FOREIGN KEY ("id_scanner_impulse")
		  REFERENCES |schema_remove|."scanners_impulses" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."scanners_impulses_interactions"
	-- -----------------------------------------------------
	CREATE TABLE "scanners_impulses_interactions" (
	  "id" BIGSERIAL,
	  "id_scanner_impulse_variables" bigint,
	  "concept" CHARACTER VARYING(100),
	  "category" CHARACTER VARYING(100),
	  "section" CHARACTER VARYING(100),
	  "action_url" text,
	  "exact_url" text,
	  "quantity" bigint,
	  "init_date" date,
	  "end_date" date,
	  
	  CONSTRAINT "pk_scanners_impulses_interactions" PRIMARY KEY ("id"),
	  CONSTRAINT "fk_scanners_impulses_interactions" FOREIGN KEY ("id_scanner_impulse_variables")
		  REFERENCES |schema_remove|."scanners_impulses_variables" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."scanners_results"
	-- -----------------------------------------------------
	CREATE TABLE "scanners_suscriptions_historics" (
	  "id" BIGSERIAL,
	  "id_scanner" bigint NOT NULL,
	  "id_client_plan" bigint NOT NULL,
	  "suscription_date" date,
	  "unsuscription_date" date,
	  
	  CONSTRAINT "pk_scanners_suscriptions_historics" PRIMARY KEY ("id"),
	  CONSTRAINT "fk_scanners_suscriptions_historics" FOREIGN KEY ("id_scanner")
		  REFERENCES |schema_remove|."scanners" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION,
	  CONSTRAINT "fk_scanners_suscriptions_h_cli_plans" FOREIGN KEY ("id_client_plan")
		  REFERENCES |schema_remove|."clients_plans" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."dynamic_forms"
	-- -----------------------------------------------------
	CREATE TABLE "dynamic_forms" (
	  "id" BIGSERIAL,
	  "type" CHARACTER VARYING(50),
	  "version" CHARACTER VARYING(10),
	  "creation_date" TIMESTAMP WITH TIME ZONE,
	  "activation_date" TIMESTAMP WITH TIME ZONE,
	  "deactivation_date" TIMESTAMP WITH TIME ZONE,
	  "template" text,
	  "active" boolean,
	  
	  CONSTRAINT "pk_dynamic_forms" PRIMARY KEY ("id")
	)
	
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."dynamic_forms_section"
	-- -----------------------------------------------------
	CREATE TABLE "dynamic_forms_section" (
	  "id" BIGSERIAL,
	  "id_form" bigint,
	  "position" integer,
	  "visible" boolean,
	  "visible_condition" text,
	  "active" boolean,
	  
	  CONSTRAINT "pk_dynamic_forms_section" PRIMARY KEY ("id"),
	  CONSTRAINT "fk_dynamic_forms_section" FOREIGN KEY ("id_form")
		  REFERENCES |schema_remove|."dynamic_forms" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."dynamic_forms_section_labels"
	-- -----------------------------------------------------
	CREATE TABLE "dynamic_forms_section_labels" (
	  "id" BIGSERIAL,
	  "id_dynamic_forms_section" bigint,
	  "label" text,
	  "language" CHARACTER VARYING(8),
	  
	  CONSTRAINT "pk_dynamic_forms_section_labels" PRIMARY KEY ("id"),
	  CONSTRAINT "fk_dynamic_forms_section_labels" FOREIGN KEY ("id_dynamic_forms_section")
		  REFERENCES |schema_remove|."dynamic_forms_section" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."dynamic_forms_input"
	-- -----------------------------------------------------
	CREATE TABLE "dynamic_forms_input" (
	  "id" BIGSERIAL,
	  "id_section" bigint,
	  "position" integer,
	  "name" text,
	  "type" CHARACTER VARYING(10),
	  "width" integer,
	  "required" boolean,
	  "required_condition" text,
	  "visible" boolean,
	  "visible_condition" text,
	  "enabled" boolean,
	  "enabled_condition" text,
	  "value" text,
	  "value_condition" text,
	  "active" boolean,
	  
	  CONSTRAINT "pk_dynamic_forms_input" PRIMARY KEY ("id"),
	  CONSTRAINT "fk_dynamic_forms_input" FOREIGN KEY ("id_section")
		  REFERENCES |schema_remove|."dynamic_forms_section" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."dynamic_forms_input_labels"
	-- -----------------------------------------------------
	CREATE TABLE "dynamic_forms_input_labels" (
	  "id" BIGSERIAL,
	  "id_dynamic_forms_input" bigint,
	  "label" text,
	  "language" CHARACTER VARYING(8),
	  
	  CONSTRAINT "pk_dynamic_forms_input_labels" PRIMARY KEY ("id"),
	  CONSTRAINT "fk_dynamic_forms_input_labels" FOREIGN KEY ("id_dynamic_forms_input")
		  REFERENCES |schema_remove|."dynamic_forms_input" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."dynamic_forms_input_description"
	-- -----------------------------------------------------
	CREATE TABLE "dynamic_forms_input_description" (
	  "id" BIGSERIAL,
	  "id_dynamic_forms_input" bigint,
	  "description" text,
	  "language" CHARACTER VARYING(8),
	  
	  CONSTRAINT "pk_dynamic_forms_input_description" PRIMARY KEY ("id"),
	  CONSTRAINT "fk_dynamic_forms_input_description" FOREIGN KEY ("id_dynamic_forms_input")
		  REFERENCES |schema_remove|."dynamic_forms_input" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."dynamic_forms_input_options"
	-- -----------------------------------------------------
	CREATE TABLE "dynamic_forms_input_options" (
	  "id" BIGSERIAL,
	  "id_input" bigint,
	  "name" text,
	  "active" boolean,
	  
	  CONSTRAINT "pk_dynamic_forms_input_options" PRIMARY KEY ("id"),
	  CONSTRAINT "fk_dynamic_forms_input_options" FOREIGN KEY ("id_input")
		  REFERENCES |schema_remove|."dynamic_forms_input" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."dynamic_forms_input_options_labels"
	-- -----------------------------------------------------
	CREATE TABLE "dynamic_forms_input_options_labels" (
	  "id" BIGSERIAL,
	  "id_dynamic_forms_input_options" bigint,
	  "label" text,
	  "language" CHARACTER VARYING(8),
	  
	  CONSTRAINT "pk_dynamic_forms_input_options_labels" PRIMARY KEY ("id"),
	  CONSTRAINT "fk_dynamic_forms_input_options_labels" FOREIGN KEY ("id_dynamic_forms_input_options")
		  REFERENCES |schema_remove|."dynamic_forms_input_options" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."dynamic_forms_conditions_results"
	-- -----------------------------------------------------
	CREATE TABLE "dynamic_forms_conditions_results" (
	  "id" BIGSERIAL,
	  "id_form" bigint,
	  "type" CHARACTER VARYING(100),
	  "position" integer,
	  "condition" text,
	  "active" boolean,
	  
	  CONSTRAINT "pk_dynamic_forms_conditions_results" PRIMARY KEY ("id"),
	  CONSTRAINT "fk_dynamic_forms_conditions_results" FOREIGN KEY ("id_form")
		  REFERENCES |schema_remove|."dynamic_forms" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."dynamic_forms_conditions_results_labels"
	-- -----------------------------------------------------
	CREATE TABLE "dynamic_forms_conditions_results_labels" (
	  "id" BIGSERIAL,
	  "id_dynamic_forms_conditions_results" bigint,
	  "label" text,
	  "language" CHARACTER VARYING(8),
	  
	  CONSTRAINT "pk_dynamic_forms_conditions_results_labels" PRIMARY KEY ("id"),
	  CONSTRAINT "fk_dynamic_forms_conditions_results_labels" FOREIGN KEY ("id_dynamic_forms_conditions_results")
		  REFERENCES |schema_remove|."dynamic_forms_conditions_results" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."deindexations"
	-- -----------------------------------------------------
	CREATE TABLE "deindexations" (
	  "id" BIGSERIAL,
	  "id_owner" bigint,
	  "id_client_plan" bigint,
	  "id_form" bigint,
	  "uuid" text,
	  
	  "person_name" CHARACTER VARYING(200),
	  "person_lastname" CHARACTER VARYING(200),
	  "person_institution" CHARACTER VARYING(200),
	  "person_charge" CHARACTER VARYING(200),
	  "url_to_deindex_keywords" text,
	  
	  "comments" text,
	  "creation_date" TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	  "tracking_code" text,
	  "status" CHARACTER VARYING(100),
	  
	  CONSTRAINT "pk_deindexations" PRIMARY KEY ("id"),
	  CONSTRAINT "fk_deindexations_clients" FOREIGN KEY ("id_owner")
		  REFERENCES |schema_remove|."clients" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION,
	  CONSTRAINT "fk_deindexations_client_plan" FOREIGN KEY ("id_client_plan")
		  REFERENCES |schema_remove|."clients_plans" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION,
	  CONSTRAINT "fk_deindexation_dynamic_forms" FOREIGN KEY ("id_form")
		  REFERENCES |schema_remove|."dynamic_forms" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."deindexations_urls"
	-- -----------------------------------------------------
	CREATE TABLE "deindexations_urls" (
	  "id" BIGSERIAL,
	  "id_deindexation" bigint,
	  "url" text,
	  "publish_date" date,
	  "ask_google" boolean DEFAULT FALSE,
	  "ask_media" boolean DEFAULT FALSE,
	  "sent_to_google" boolean DEFAULT false,
	  "sent_to_google_date" date,
	  "sent_to_media" boolean DEFAULT false,
	  "sent_to_media_date" date,
	  "google_approved" boolean,
	  "media_approved" boolean,
	  
	  CONSTRAINT "pk_deindexations_urls" PRIMARY KEY ("id"),
	  CONSTRAINT "fk_deindexations_urls" FOREIGN KEY ("id_deindexation")
		  REFERENCES |schema_remove|."deindexations" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."deindexations_input_values"
	-- -----------------------------------------------------
	CREATE TABLE "deindexations_input_values" (
	  "id" BIGSERIAL,
	  "id_deindexation" bigint,
	  "id_form_input" bigint,
	  "id_option" bigint,
	  "value" text,
	  "value_numeric" numeric,
	  "value_date" date,
	  "value_boolean" boolean,
	  
	  CONSTRAINT "pk_deindexations_input_values" PRIMARY KEY ("id"),
	  CONSTRAINT "fk_deindexations_input_values" FOREIGN KEY ("id_deindexation")
		  REFERENCES |schema_remove|."deindexations" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION,
	  CONSTRAINT "fk_deindexations_dynamic_input_values" FOREIGN KEY ("id_form_input")
		  REFERENCES |schema_remove|."dynamic_forms_input" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."deindexations_results"
	-- -----------------------------------------------------
	CREATE TABLE "deindexations_results" (
	  "id_deindexation" bigint,
	  "id_dynamic_result" bigint,
	  
	  CONSTRAINT "pk_deindexations_result" PRIMARY KEY ("id_deindexation", "id_dynamic_result"),
	  CONSTRAINT "fk_deindexations_result" FOREIGN KEY ("id_deindexation")
		  REFERENCES |schema_remove|."deindexations" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION,
	  CONSTRAINT "fk_deindexations_dynamic_input_values" FOREIGN KEY ("id_dynamic_result")
		  REFERENCES |schema_remove|."dynamic_forms_conditions_results" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."deindexations_files_evidences"
	-- -----------------------------------------------------
	CREATE TABLE "deindexations_files_evidences" (
	  "id" BIGSERIAL,
	  "id_deindexation" bigint,
	  "id_uploader" bigint,
	  "uploader_profile" CHARACTER VARYING(100),
	  "file_address" CHARACTER VARYING(300),
	  "file_description" text,
	  "file_type" CHARACTER VARYING(100),
	  "creation_date" TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	  
	  CONSTRAINT "pk_deindexations_files_evidences" PRIMARY KEY ("id"),
	  CONSTRAINT "fk_deindexations_files_evidences" FOREIGN KEY ("id_deindexation")
		  REFERENCES |schema_remove|."deindexations" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."deindexations_historic"
	-- -----------------------------------------------------
	CREATE TABLE "deindexations_historic" (
	  "id" BIGSERIAL,
	  "id_deindexation" bigint,
	  "id_owner" bigint,
	  "profile" CHARACTER VARYING(300),
	  "status" CHARACTER VARYING(100),
	  "comments" text,
	  "creation_date" TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	  
	  CONSTRAINT "pk_deindexations_historic" PRIMARY KEY ("id"),
	  CONSTRAINT "fk_deindexations_historic" FOREIGN KEY ("id_deindexation")
		  REFERENCES |schema_remove|."deindexations" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	
	-- -----------------------------------------------------
	-- Table "|schema_remove|"."clients_suscriptions_attempts"
	-- -----------------------------------------------------
	CREATE TABLE "clients_suscriptions_attempts" (
	  "id" BIGSERIAL,
	  "id_plan" bigint,
	  "uuid" text,
	  "email" CHARACTER VARYING(200),
	  "name" CHARACTER VARYING(200),
	  "lastname" CHARACTER VARYING(200),
	  "creation_date" TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	  "finished" boolean DEFAULT false,
	  "successful" boolean DEFAULT false,
	  
	  CONSTRAINT "pk_clients_suscriptions_attempts" PRIMARY KEY ("id"),
	  CONSTRAINT "fk_clients_suscriptions_attempts_plans" FOREIGN KEY ("id_plan")
		  REFERENCES |schema_remove|."plans" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	
	-- -----------------------------------------------------
	-- Table "|corp_token|"."clients_audit_access"
	-- -----------------------------------------------------
	CREATE TABLE "clients_audit_access" (
	  "id" BIGSERIAL,
	  "id_client" bigint,
	  "id_user_action" bigint,
	  "profile_user_action" CHARACTER VARYING(100),
	  "description" text,
	  "parameters" text,
	  "action_date" TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	  
	  CONSTRAINT "pk_clients_audit_access" PRIMARY KEY ("id"),
	  CONSTRAINT "fk_clients_audit_access" FOREIGN KEY ("id_client")
		  REFERENCES |schema_remove|."clients" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	
	-- -----------------------------------------------------
	-- Table "|corp_token|"."users_audit_access"
	-- -----------------------------------------------------
	CREATE TABLE "users_audit_access" (
	  "id" BIGSERIAL,
	  "id_user" bigint,
	  "profile" CHARACTER VARYING(100),
	  "description" text,
	  "parameters" text,
	  "action_date" TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	  
	  CONSTRAINT "pk_user_audit_access" PRIMARY KEY ("id"),
	  CONSTRAINT "fk_user_audit_access" FOREIGN KEY ("id_user")
		  REFERENCES |schema_remove|."users" ("id") MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	
	-- -----------------------------------------------------
	-- Table "|corp_token|"."clients_alerts_messages"
	-- -----------------------------------------------------
	CREATE TABLE "clients_alerts_messages" (
	  "id" BIGSERIAL,
	  "id_owner" bigint,
	  "title" CHARACTER VARYING(100),
	  "description" text,
	  "type" CHARACTER VARYING(100),
	  "params" text,
	  "creation_date" TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	  "readed" boolean DEFAULT false,
	  "hidden" boolean DEFAULT false,
	  "urgent" boolean DEFAULT false,
	  "must_send_email" boolean DEFAULT false,
	  "email_sent" boolean DEFAULT false,
	  
	  CONSTRAINT "pk_client_alert_message" PRIMARY KEY ("id")
	)
	
	-- -----------------------------------------------------
	-- Table "|corp_token|"."users_alerts_messages"
	-- -----------------------------------------------------
	CREATE TABLE "users_alerts_messages" (
	  "id" BIGSERIAL,
	  "id_owner" bigint,
	  "title" CHARACTER VARYING(100),
	  "description" text,
	  "type" CHARACTER VARYING(100),
	  "params" text,
	  "creation_date" TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	  "readed" boolean DEFAULT false,
	  "hidden" boolean DEFAULT false,
	  "urgent" boolean DEFAULT false,
	  "must_send_email" boolean DEFAULT false,
	  "email_sent" boolean DEFAULT false,
	  
	  CONSTRAINT "pk_user_alert_message" PRIMARY KEY ("id")
	);

	-- -----------------------------------------------------
	-- Table "|schema_remove|"."audit_access"
	-- -----------------------------------------------------
	--CREATE TABLE "audit_access" (
	--  "id" BIGSERIAL,
	--  "id_user" bigint,
	--  "id_profile" bigint,
	--  "id_service_function" bigint,
	--  "token" text,
	--  "ip" CHARACTER VARYING(30),
	--  "agent" text,
	--  "uri" CHARACTER VARYING(100),
	--  "method" CHARACTER VARYING(10),
	--  "action_date" TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
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
CREATE UNIQUE INDEX ux_language_tags ON |schema_remove|.languages(UPPER(tag));
