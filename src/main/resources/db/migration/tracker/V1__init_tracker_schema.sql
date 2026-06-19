CREATE TABLE categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    user_id BIGINT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE resources (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(50) NOT NULL,
    url TEXT,
    author VARCHAR(255),
    estimated_hours INTEGER CHECK (estimated_hours >= 0),
    difficulty VARCHAR(50),
    notes TEXT,
    user_id BIGINT NOT NULL,
    category_id BIGINT REFERENCES categories(id) ON DELETE SET NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE resource_tags (
    resource_id BIGINT NOT NULL REFERENCES resources(id) ON DELETE CASCADE,
    tag VARCHAR(255) NOT NULL,
    PRIMARY KEY (resource_id, tag)
);

CREATE INDEX idx_resources_user_id ON resources(user_id);
CREATE INDEX idx_resources_category_id ON resources(category_id);
CREATE INDEX idx_resources_type ON resources(type);

CREATE TABLE preparation_plans (
    id BIGSERIAL PRIMARY KEY,
    goal VARCHAR(255) NOT NULL,
    resource_id BIGINT REFERENCES resources(id) ON DELETE SET NULL,
    target_completion_date DATE,
    estimated_hours INTEGER CHECK (estimated_hours >= 0),
    priority VARCHAR(50) NOT NULL DEFAULT 'MEDIUM',
    status VARCHAR(50) NOT NULL DEFAULT 'NOT_STARTED',
    progress INTEGER NOT NULL DEFAULT 0 CHECK (progress >= 0 AND progress <= 100),
    user_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_prep_plans_user_id ON preparation_plans(user_id);
CREATE INDEX idx_prep_plans_status ON preparation_plans(status);

CREATE TABLE study_sessions (
    id BIGSERIAL PRIMARY KEY,
    session_date DATE NOT NULL,
    duration_minutes INTEGER NOT NULL CHECK (duration_minutes > 0),
    notes TEXT,
    topic_covered VARCHAR(255),
    resource_id BIGINT REFERENCES resources(id) ON DELETE SET NULL,
    user_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_study_sessions_user_id ON study_sessions(user_id);
CREATE INDEX idx_study_sessions_date ON study_sessions(session_date);

CREATE TABLE certifications (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    provider VARCHAR(255),
    exam_date DATE,
    target_date DATE,
    cost NUMERIC(10,2),
    progress INTEGER NOT NULL DEFAULT 0 CHECK (progress >= 0 AND progress <= 100),
    status VARCHAR(50) NOT NULL DEFAULT 'NOT_STARTED',
    user_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_certifications_user_id ON certifications(user_id);
CREATE INDEX idx_certifications_status ON certifications(status);

CREATE TABLE goals (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    start_date DATE,
    target_date DATE,
    progress INTEGER NOT NULL DEFAULT 0 CHECK (progress >= 0 AND progress <= 100),
    status VARCHAR(50) NOT NULL DEFAULT 'ACTIVE',
    user_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_goals_user_id ON goals(user_id);
CREATE INDEX idx_goals_status ON goals(status);

CREATE TABLE roadmaps (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    user_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_roadmaps_user_id ON roadmaps(user_id);

CREATE TABLE roadmap_items (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    order_index INTEGER NOT NULL DEFAULT 0,
    completed BOOLEAN NOT NULL DEFAULT false,
    roadmap_id BIGINT NOT NULL REFERENCES roadmaps(id) ON DELETE CASCADE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_roadmap_items_roadmap_id ON roadmap_items(roadmap_id);

CREATE TABLE notes (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT,
    attachment_url TEXT,
    user_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE note_tags (
    note_id BIGINT NOT NULL REFERENCES notes(id) ON DELETE CASCADE,
    tag VARCHAR(255) NOT NULL,
    PRIMARY KEY (note_id, tag)
);

CREATE INDEX idx_notes_user_id ON notes(user_id);

CREATE TABLE revisions (
    id BIGSERIAL PRIMARY KEY,
    subject VARCHAR(255) NOT NULL,
    first_revision DATE,
    second_revision DATE,
    third_revision DATE,
    next_reminder DATE,
    user_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_revisions_user_id ON revisions(user_id);
CREATE INDEX idx_revisions_next_reminder ON revisions(next_reminder);

CREATE TABLE interview_topics (
    id BIGSERIAL PRIMARY KEY,
    topic VARCHAR(255) NOT NULL,
    total_questions INTEGER NOT NULL DEFAULT 0 CHECK (total_questions >= 0),
    solved_questions INTEGER NOT NULL DEFAULT 0 CHECK (solved_questions >= 0),
    revision_count INTEGER NOT NULL DEFAULT 0,
    difficulty VARCHAR(50),
    user_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_interview_topics_user_id ON interview_topics(user_id);
