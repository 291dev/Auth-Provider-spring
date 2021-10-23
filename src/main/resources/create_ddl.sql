--
-- PostgreSQL database dump
--

-- Dumped from database version 12.7
-- Dumped by pg_dump version 12.7

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: public; Type: SCHEMA; Schema: -; Owner: aha05407
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO aha05407;

--
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: aha05407
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: app_users; Type: TABLE; Schema: public; Owner: aha05407
--

CREATE TABLE public.app_users (
    id bigint NOT NULL,
    authorities bytea,
    email character varying(255),
    first_name character varying(255),
    is_active boolean NOT NULL,
    is_not_locked boolean NOT NULL,
    join_date timestamp without time zone,
    last_login_date timestamp without time zone,
    last_login_date_display timestamp without time zone,
    last_name character varying(255),
    password character varying(255),
    profile_image_url character varying(255),
    roles character varying(255),
    user_id character varying(255),
    user_name character varying(255)
);


ALTER TABLE public.app_users OWNER TO aha05407;

--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: aha05407
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO aha05407;

--
-- Name: app_users app_users_pkey; Type: CONSTRAINT; Schema: public; Owner: aha05407
--

ALTER TABLE ONLY public.app_users
    ADD CONSTRAINT app_users_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

