CREATE DATABASE IF NOT EXISTS personal_resume DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE personal_resume;

SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS contact;
DROP TABLE IF EXISTS honor;
DROP TABLE IF EXISTS education_experience;
DROP TABLE IF EXISTS work_experience;
DROP TABLE IF EXISTS project_experience;
DROP TABLE IF EXISTS skill;
DROP TABLE IF EXISTS profile;
DROP TABLE IF EXISTS admin_user;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE admin_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(64) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    status VARCHAR(16) NOT NULL DEFAULT 'ACTIVE',
    last_login_at DATETIME NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_admin_user_status (status)
);

CREATE TABLE profile (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    full_name VARCHAR(100) NOT NULL,
    title VARCHAR(120) NOT NULL,
    avatar_url VARCHAR(255) NULL,
    location VARCHAR(120) NULL,
    bio TEXT NULL,
    resume_pdf_url VARCHAR(255) NULL,
    is_active TINYINT(1) NOT NULL DEFAULT 0,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_profile_active_updated (is_active, updated_at)
);

CREATE TABLE skill (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    profile_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    sort_order INT NOT NULL DEFAULT 0,
    is_visible TINYINT(1) NOT NULL DEFAULT 1,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_skill_profile_visible_sort_updated (profile_id, is_visible, sort_order, updated_at),
    CONSTRAINT fk_skill_profile FOREIGN KEY (profile_id) REFERENCES profile(id) ON DELETE CASCADE
);

CREATE TABLE project_experience (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    profile_id BIGINT NOT NULL,
    name VARCHAR(120) NOT NULL,
    role VARCHAR(80) NOT NULL,
    summary TEXT NULL,
    start_date DATE NOT NULL,
    end_date DATE NULL,
    highlight TEXT NULL,
    demo_url VARCHAR(255) NULL,
    repo_url VARCHAR(255) NULL,
    sort_order INT NOT NULL DEFAULT 0,
    is_visible TINYINT(1) NOT NULL DEFAULT 1,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_project_profile_visible_sort_updated (profile_id, is_visible, sort_order, updated_at),
    CONSTRAINT fk_project_profile FOREIGN KEY (profile_id) REFERENCES profile(id) ON DELETE CASCADE
);

CREATE TABLE work_experience (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    profile_id BIGINT NOT NULL,
    company VARCHAR(120) NOT NULL,
    position VARCHAR(80) NOT NULL,
    type VARCHAR(32) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NULL,
    description TEXT NULL,
    sort_order INT NOT NULL DEFAULT 0,
    is_visible TINYINT(1) NOT NULL DEFAULT 1,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_work_profile_visible_sort_updated (profile_id, is_visible, sort_order, updated_at),
    CONSTRAINT fk_work_profile FOREIGN KEY (profile_id) REFERENCES profile(id) ON DELETE CASCADE
);

CREATE TABLE education_experience (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    profile_id BIGINT NOT NULL,
    school VARCHAR(120) NOT NULL,
    degree VARCHAR(50) NOT NULL,
    major VARCHAR(80) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NULL,
    gpa VARCHAR(32) NULL,
    sort_order INT NOT NULL DEFAULT 0,
    is_visible TINYINT(1) NOT NULL DEFAULT 1,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_education_profile_visible_sort_updated (profile_id, is_visible, sort_order, updated_at),
    CONSTRAINT fk_education_profile FOREIGN KEY (profile_id) REFERENCES profile(id) ON DELETE CASCADE
);

CREATE TABLE honor (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    profile_id BIGINT NOT NULL,
    title VARCHAR(120) NOT NULL,
    issuer VARCHAR(120) NULL,
    award_date DATE NULL,
    description TEXT NULL,
    sort_order INT NOT NULL DEFAULT 0,
    is_visible TINYINT(1) NOT NULL DEFAULT 1,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_honor_profile_visible_sort_updated (profile_id, is_visible, sort_order, updated_at),
    CONSTRAINT fk_honor_profile FOREIGN KEY (profile_id) REFERENCES profile(id) ON DELETE CASCADE
);

CREATE TABLE contact (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    profile_id BIGINT NOT NULL,
    type VARCHAR(32) NOT NULL,
    label VARCHAR(100) NOT NULL,
    value VARCHAR(255) NOT NULL,
    sort_order INT NOT NULL DEFAULT 0,
    is_visible TINYINT(1) NOT NULL DEFAULT 1,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_contact_profile_visible_sort_updated (profile_id, is_visible, sort_order, updated_at),
    CONSTRAINT fk_contact_profile FOREIGN KEY (profile_id) REFERENCES profile(id) ON DELETE CASCADE
);